package com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.characters;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.components.gameComponent.movement.MovementController2DWorld;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import org.newdawn.slick.SlickException;

public class Player2DWorld extends Player2D {

    private float currentRotate;
    private float nextRotate;
    private float speedRotate;

    protected Player2DWorld(AnimatorController animatorController, EGameObject type, String id, int team, float x, float y, float life, float damage, float moveSpeed, float weight) {
        super(animatorController, new MovementController2DWorld(x, y, moveSpeed, weight), type, id, team, life, damage);

        this.currentRotate = 0f;
        this.nextRotate = 0f;
        this.speedRotate = 1f;
    }

    protected void rotatePlayer() {
        if (this.currentRotate != this.nextRotate) {
            if (this.speedRotate == 1) {
                this.currentRotate = this.nextRotate;
            } else {
                this.currentRotate += ((this.nextRotate - this.currentRotate) * this.speedRotate * (GameConfig.currentTimeLoop / 1000));
                if (this.currentRotate > this.nextRotate) {
                    this.currentRotate = this.nextRotate;
                }
            }
        }
        this.animatorController.setRotateAngle(this.currentRotate + 90);
    }

    @Override
    protected boolean moveDown() throws SlickException {
        this.animatorController.setEyesDirection(EDirection.NONE);
        this.animatorController.changeAnimation(EAnimation.RUN);
        this.movement.setMoveDirection(EDirection.DOWN);
        this.event.addStackEvent(EInput.MOVE_DOWN);
        return true;
    }

    @Override
    protected boolean moveRight() throws SlickException {
        this.animatorController.setEyesDirection(EDirection.NONE);
        this.animatorController.changeAnimation(EAnimation.RUN);
        this.movement.setMoveDirection(EDirection.RIGHT);
        this.event.addStackEvent(EInput.MOVE_RIGHT);
        return true;
    }

    @Override
    protected boolean moveLeft() throws SlickException {
        this.animatorController.setEyesDirection(EDirection.NONE);
        this.animatorController.changeAnimation(EAnimation.RUN);
        this.movement.setMoveDirection(EDirection.LEFT);
        this.event.addStackEvent(EInput.MOVE_LEFT);
        return true;
    }

    @Override
    protected boolean moveUp() throws SlickException {
        this.animatorController.changeAnimation(EAnimation.RUN);
        this.movement.setMoveDirection(EDirection.NONE);
        this.event.addStackEvent(EInput.MOVE_UP);
        return true;
    }

    @Override
    protected void changeDirection() {

    }

    @Override
    protected boolean executeLastDirectionEvent() throws SlickException {
        return false;
    }
}
