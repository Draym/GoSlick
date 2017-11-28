package com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.characters;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.eventComponents.events.EventController;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.animations.details.AnimationRepercussionItem;
import com.andres_k.gameToolsLib.components.gameComponent.commands.comboComponent.ComboController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Character;
import com.andres_k.gameToolsLib.components.gameComponent.movement.MovementController;
import com.andres_k.gameToolsLib.components.networkComponents.networkGame.NetworkController;
import com.andres_k.gameToolsLib.components.networkComponents.networkSend.messageServer.MessageStatePlayer;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.gameToolsLib.components.taskComponent.TaskFactory;
import com.andres_k.gameToolsLib.utils.configs.GlobalVariable;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.stockage.Tuple;
import com.andres_k.gameToolsLib.utils.tools.Console;
import com.andres_k.gameToolsLib.utils.tools.StringTools;
import org.newdawn.slick.SlickException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class Player2D extends Character {
    protected Map<String, Method> specialActions;
    protected Map<EAnimation, Method> checkBeforeLaunch;
    protected EventController event;
    protected ComboController comboController;
    protected int team;

    protected Player2D(AnimatorController animatorController, MovementController movementController, EGameObject type, String id, int team, float life, float damage) {
        super(animatorController, movementController, type, id, life, damage);

        this.event = new EventController();
        this.team = team;
        this.specialActions = new HashMap<>();
        this.checkBeforeLaunch = new HashMap<>();
        try {
            this.comboController = new ComboController(this.type);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            this.comboController = null;
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        this.resetActions();
    }

    @Override
    public void update() throws SlickException {
        super.update();
        this.comboController.update();
        this.animatorController.update();
        this.executeLastActionEvent();
        this.animatorController.updateAnimation(this);
        this.movement.update();
    }

    @Override
    public void resetActions() {
        this.comboController.reset();
        this.event.reset();
    }

    // ACTIONS

    protected abstract boolean moveRight() throws SlickException;
    protected abstract boolean moveLeft() throws SlickException;
    protected abstract boolean moveUp() throws SlickException;
    protected abstract boolean moveDown() throws SlickException;

    protected abstract void changeDirection();

    protected abstract boolean executeLastDirectionEvent() throws SlickException;

    private boolean executeLastActionEvent() {
        EInput last = this.event.consumeStackEvent();

        if (last == EInput.NOTHING) {
            last = this.event.getTheLastEvent();
        }
        if (last != EInput.NOTHING && this.comboController != null) {
            if (this.comboController.nextComboStep(this.animatorController, last)) {
                Pair<EAnimation, Integer> nextAnim = this.comboController.getCurrentAnimation();

                if (this.canLaunchAction(nextAnim.getV1())) {
                    try {
                        this.animatorController.changeAnimation(nextAnim.getV1(), nextAnim.getV2());
                        return true;
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    private boolean canLaunchAction(EAnimation action) {
        if (this.checkBeforeLaunch.containsKey(action)) {
            try {
                Object result = this.checkBeforeLaunch.get(action).invoke(this);

                if (result instanceof Boolean) {
                    return (boolean) result;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    // EVENT
    @Override
    public void eventPressed(EInput input) {
        if (this.isAlive()) {
            Console.write("input pressed: " + input.getContainer());
            this.event.setActivated(input.getContainer(), true);
        }
    }

    @Override
    public void eventReleased(EInput input) {
        if (this.isAlive()) {
            Console.write("input released: " + input.getContainer());
            this.event.setActivated(input.getContainer(), false);
        }
    }

    @Override
    public Object doTask(Object task) {
        if (task instanceof Pair && ((Pair) task).getV1() instanceof ETaskType) {
            Pair<ETaskType, Object> received = (Pair<ETaskType, Object>) task;

            if (received.getV1() == ETaskType.CREATE && received.getV2() instanceof String) {
                if (this.specialActions.containsKey(received.getV2())) {
                    try {
                        this.specialActions.get(received.getV2()).invoke(this);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } else if (received.getV1() == ETaskType.NEXT && received.getV2().equals("frame")) {
                if (this.animatorController != null) {
                    this.animatorController.forceNextFrame();
                }
            }
        }
        return null;
    }

    @Override
    public void manageEachCollisionExceptValidHit(EGameObject mine, GameObject enemy, EGameObject him) {
        if (mine == EGameObject.BLOCK_BODY && him == EGameObject.ATTACK_BODY) {
            this.setLastAttacker(enemy);
            if (this.animatorController.currentAnimationType() == EAnimation.DEFENSE) {
                AnimationRepercussionItem repercussionItem = enemy.getAnimatorController().getCurrentContainer().getRepercussion();
                if (repercussionItem != null &&
                        (repercussionItem.getTargetType() == EAnimation.TOUCHED_PROJECTED ||
                                repercussionItem.getTargetType() == EAnimation.TOUCHED_FLIP ||
                                repercussionItem.getTargetType() == EAnimation.TOUCHED_FALL)) {
                    this.manageGetHit(enemy);
                }
            }
        }
    }

    @Override
    public boolean die() {
        if (super.die()) {
            NetworkController.get().sendMessage(this.id, new MessageStatePlayer(this));
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, (this.team == 1 ? ELocation.GAME_GUI_State_AlliedPlayers : ELocation.GAME_GUI_State_EnemyPlayers), new Pair<>(ETaskType.DELETE, this.getId() + GlobalVariable.id_delimiter + EGuiElement.STATE_PLAYER)));
            return true;
        }
        return false;
    }

    // GETTERS

    public String getPseudo() {
        String pseudo = StringTools.getWord(this.id, GlobalVariable.id_delimiter, "", 2, -1);
        Console.write("pseudo: " + pseudo);

        if (!pseudo.equals("")) {
            return pseudo;
        } else {
            return id;
        }
    }

    public int getIdIndex() {
        String index = StringTools.getWord(this.id, GlobalVariable.id_delimiter, GlobalVariable.id_delimiter, 1, 1);
        Console.write("index: " + index);

        if (!index.equals("")) {
            return Integer.valueOf(index);
        } else {
            return -1;
        }
    }

    // SETTERS

    @Override
    public boolean setCurrentLife(float value) {
        if (super.setCurrentLife(value)) {
            NetworkController.get().sendMessage(this.id, new MessageStatePlayer(this));
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, (this.team == 1 ? ELocation.GAME_GUI_State_AlliedPlayers : ELocation.GAME_GUI_State_EnemyPlayers), new Tuple<>(ETaskType.RELAY, this.getId() + GlobalVariable.id_delimiter + EGuiElement.STATE_PLAYER, new Tuple<>(ETaskType.SETTER, "life", value / this.maxLife))));
            return true;
        }
        return false;
    }
}
