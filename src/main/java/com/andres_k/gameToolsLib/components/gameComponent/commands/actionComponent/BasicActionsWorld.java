package com.andres_k.gameToolsLib.components.gameComponent.commands.actionComponent;

import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.gameComponent.animations.details.AnimationRepercussionItem;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.tools.Console;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 17/11/2015.
 */
public class BasicActionsWorld {

    // ACTIONS
    public static void idle(GameObject object) {
        object.getMovement().stopMovement();
    }

    public static void explode(GameObject object) {
        if (object.getAnimatorController().getIndex() != 2) {
            if (object.getLastAttacker() != null) {
                AnimationRepercussionItem repercussionItem = object.getLastAttacker().getAnimatorController().getCurrentContainer().getRepercussion();
                if (repercussionItem != null) {
                    repercussionItem.applyMoveRepercussion(object);
                }
            }
        } else {
            object.getMovement().setPushY(0f);
        }
        if (!object.isOnEarth() && object.getAnimatorController().getIndex() == 0) {
            object.getAnimatorController().forceCurrentAnimationIndex(1);
        }
        if (object.isOnEarth() && object.getAnimatorController().getIndex() != 0) {
            object.getAnimatorController().forceCurrentAnimationIndex(0);
            object.getMovement().stopMovement();
        }
    }

    public static void defense(GameObject object) {
        object.getMovement().setPushX(0f);
        object.getMovement().setPushY(0f);
    }

    public static void transposition(GameObject object) {
        object.getMovement().setPushX(0f);
        object.getMovement().setPushY(0f);
        try {
            if (object.getAnimatorController().currentAnimation().getFrame() == 3) {
                object.teleportBehindMyAttacker();
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void block(GameObject object) {
        object.getMovement().setPushX(0f);
        object.getMovement().setPushY(0f);
        if (!object.isOnEarth()) {
            object.getAnimatorController().getCurrentContainer().getConfig().setNextType(EAnimation.FALL);
        }
        if (object.getLastAttacker() != null && object.getAnimatorController().hasAnimation(EAnimation.TRANSPOSITION)) {
            object.getAnimatorController().forceCurrentAnimationType(EAnimation.TRANSPOSITION);
            object.setUseAttackerTimer(false);
        }
    }

    public static void rush(GameObject object) {
        object.getMovement().setPushY(0);
        object.getMovement().setMoveDirection(object.getAnimatorController().getEyesDirection());
        if (object.getMovement().getMoveDirection() != EDirection.NONE) {
            object.getMovement().setPushX(GameConfig.speedTravel * 3.0f + object.getPosX() * 0.003f);
        }
    }

    // TOUCHED
    public static void touchedSimple(GameObject object) {
        AnimationRepercussionItem repercussionItem = object.getLastAttacker().getAnimatorController().getCurrentContainer().getRepercussion();
        if (repercussionItem != null) {
            repercussionItem.applyMoveRepercussion(object);
        }
    }

    public static void touchedMedium(GameObject object) {
        AnimationRepercussionItem repercussionItem = object.getLastAttacker().getAnimatorController().getCurrentContainer().getRepercussion();
        if (repercussionItem != null) {
            repercussionItem.applyMoveRepercussion(object);
        }
    }

    public static void touchedPropels(GameObject object) {
        AnimationRepercussionItem repercussionItem = object.getLastAttacker().getAnimatorController().getCurrentContainer().getRepercussion();
        if (repercussionItem != null) {
            repercussionItem.applyMoveRepercussion(object);
        }
    }

    public static void touchedFlip(GameObject object) {
        object.getMovement().setPushY(0f);
        AnimationRepercussionItem repercussionItem = object.getLastAttacker().getAnimatorController().getCurrentContainer().getRepercussion();
        if (repercussionItem != null) {
            repercussionItem.applyMoveRepercussion(object);
        }
    }

    public static void touchedProjected(GameObject object) {
        AnimationRepercussionItem repercussionItem = object.getLastAttacker().getAnimatorController().getCurrentContainer().getRepercussion();
        if (repercussionItem != null) {
            repercussionItem.applyMoveRepercussion(object);
        }
    }

    // MOVEMENT
    public static void run(GameObject object) {
        float angle = (float)object.doTask(new Pair<>(ETaskType.GETTER, "currentRotate"));
        Console.force("angle: " + angle);
        object.getMovement().setPushX((float)Math.cos(angle * Math.PI / 180));
        object.getMovement().setPushY((float)Math.sin(angle * Math.PI / 180));
        object.getMovement().setCoeffX(1f);
        object.getMovement().setCoeffY(1f);
    }
}
