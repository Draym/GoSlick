package com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.characters;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.components.gameComponent.movement.MovementController2DPlatform;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 10/07/2015.
 */
public class Player2DPlatform extends Player2D {

    protected Player2DPlatform(AnimatorController animatorController, EGameObject type, String id, int team, float x, float y, float life, float damage, float moveSpeed, float gravitySpeed, float weight) {
        super(animatorController, new MovementController2DPlatform(x, y, moveSpeed, gravitySpeed, weight, false), type, id, team, life, damage);
    }

    @Override
    public void executeActionOnNoEvent() throws SlickException {
        this.moveDown();
    }

    //ACTIONS

    @Override
    protected boolean moveDown() throws SlickException {
        if (!this.isOnEarth()
                && this.animatorController.currentAnimationType() != EAnimation.FALL
                && this.animatorController.currentAnimationType() != EAnimation.RECEIPT
                && this.animatorController.currentAnimationType() != EAnimation.TOUCHED_FALL
                && this.animatorController.currentAnimationType() != EAnimation.TOUCHED_RECEIPT
                && this.animatorController.canSwitchCurrent()) {
            this.animatorController.changeAnimation(EAnimation.FALL);
            this.movement.resetGravity();
            return true;
        }
        return false;
    }

    @Override
    protected boolean moveRight() throws SlickException {
        if (this.movement.getMoveDirection() != EDirection.RIGHT || this.animatorController.currentAnimationType() != EAnimation.RUN) {
            this.animatorController.setEyesDirection(EDirection.RIGHT);
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.RIGHT);
            this.event.addStackEvent(EInput.MOVE_RIGHT);
            if (this.event.isActivated(EInput.MOVE_UP))
                this.moveUp();
            return true;
        }
        return false;
    }

    @Override
    protected boolean moveLeft() throws SlickException {
        if (this.movement.getMoveDirection() != EDirection.LEFT || this.animatorController.currentAnimationType() != EAnimation.RUN) {
            this.animatorController.setEyesDirection(EDirection.LEFT);
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.LEFT);
            this.event.addStackEvent(EInput.MOVE_LEFT);
            if (this.event.isActivated(EInput.MOVE_UP)) {
                this.moveUp();
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean moveUp() throws SlickException {
        this.changeDirection();
        this.animatorController.changeAnimation(EAnimation.JUMP);
        if (!this.isOnEarth())
            this.animatorController.forceCurrentAnimationIndex(1);
        this.setOnEarth(false);
        this.movement.resetGravity();
        this.event.addStackEvent(EInput.MOVE_UP);
        return true;
    }

    @Override
    protected void changeDirection() {
        EInput recentMove = this.event.getMoreRecentEventBetween(EInput.MOVE_RIGHT, EInput.MOVE_LEFT);
        if (recentMove == EInput.MOVE_RIGHT) {
            this.movement.setMoveDirection(EDirection.RIGHT);
        } else if (recentMove == EInput.MOVE_LEFT) {
            this.movement.setMoveDirection(EDirection.LEFT);
        } else {
            this.movement.setMoveDirection(EDirection.NONE);
        }
    }
}
