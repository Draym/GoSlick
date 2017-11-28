package com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.characters;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.components.gameComponent.movement.MovementController2DWorld;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.tools.MathTools;
import org.newdawn.slick.SlickException;

public class Player2DWorld extends Player2D {

    private float currentRotate;
    private float nextRotate;
    private float toRotateNext;
    private float speedRotate;
    private Pair<EDirection, EDirection> moving;

    protected Player2DWorld(AnimatorController animatorController, EGameObject type, String id, int team, float x, float y, float life, float damage, float moveSpeed, float weight) {
        super(animatorController, new MovementController2DWorld(x, y, moveSpeed, weight), type, id, team, life, damage);

        this.currentRotate = 0f;
        this.nextRotate = 0f;
        this.toRotateNext = 0f;
        this.speedRotate = 1;
        this.moving = new Pair<>(EDirection.NONE, EDirection.NONE);
        this.animatorController.setEyesDirection(EDirection.NONE);
    }

    protected void rotatePlayer() {
        if (this.currentRotate != this.nextRotate) {
            if (this.speedRotate >= 10) {
                this.currentRotate = this.nextRotate;
            } else {
                this.currentRotate += this.toRotateNext;
                if (this.currentRotate < 0) {
                    this.currentRotate += 360;
                } else if (this.currentRotate > 360) {
                    this.currentRotate -= 360;
                }
                if (MathTools.isInRange(this.nextRotate, this.currentRotate, 2)) {
                    this.currentRotate = this.nextRotate;
                }
            }
        }
        this.animatorController.setRotateAngle(this.currentRotate + 90);
    }

    @Override
    public void executeActionOnNoEvent() throws SlickException {
        this.animatorController.changeAnimation(EAnimation.IDLE);
        this.moving.copy(EDirection.NONE, EDirection.NONE);
    }

    @Override
    public Object doTask(Object task) {
        if (task instanceof Pair && ((Pair) task).getV1() instanceof ETaskType) {
            Pair<ETaskType, String> received = (Pair<ETaskType, String>) task;

            if (received.getV1().equals(ETaskType.GETTER) && received.getV2().equals("currentRotate")) {
                return this.currentRotate;
            }
        }
        return super.doTask(task);
    }


    @Override
    protected boolean moveDown() throws SlickException {
        if (this.moving.getV1() != EDirection.DOWN) {
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.NONE);
            this.event.addStackEvent(EInput.MOVE_DOWN);
            this.setNextRotate(EDirection.DOWN.getAngle());
            this.moving.setV2(this.moving.getV1());
            this.moving.setV1(EDirection.DOWN);
        }
        return true;
    }

    @Override
    protected boolean moveRight() throws SlickException {
        if (this.moving.getV1() != EDirection.RIGHT) {
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.NONE);
            this.event.addStackEvent(EInput.MOVE_RIGHT);
            this.setNextRotate(EDirection.RIGHT.getAngle());
            this.moving.setV2(this.moving.getV1());
            this.moving.setV1(EDirection.RIGHT);
        }
        return true;
    }

    @Override
    protected boolean moveLeft() throws SlickException {
        if (this.moving.getV1() != EDirection.LEFT) {
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.NONE);
            this.event.addStackEvent(EInput.MOVE_LEFT);
            this.setNextRotate(EDirection.LEFT.getAngle());
            this.moving.setV2(this.moving.getV1());
            this.moving.setV1(EDirection.LEFT);
        }
        return true;
    }

    @Override
    protected boolean moveUp() throws SlickException {
        if (this.moving.getV1() != EDirection.UP) {
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.NONE);
            this.event.addStackEvent(EInput.MOVE_UP);
            this.setNextRotate(EDirection.UP.getAngle());
            this.moving.setV2(this.moving.getV1());
            this.moving.setV1(EDirection.UP);
        }
        return true;
    }

    @Override
    protected void changeDirection() {
    }

    private void setNextRotate(float value) {
        this.nextRotate = value;
        this.toRotateNext = (MathTools.getShortestAngle(this.currentRotate, this.nextRotate) * this.speedRotate * ((float) GameConfig.currentTimeLoop / 1000));
    }
}
