package com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.characters;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.components.gameComponent.movement.MovementController2DWorld;
import org.newdawn.slick.SlickException;

public class Player2DWorld extends Player2D {
    protected Player2DWorld(AnimatorController animatorController, EGameObject type, String id, int team, float x, float y, float life, float damage, float moveSpeed, float weight) {
        super(animatorController, new MovementController2DWorld(x, y, moveSpeed, weight), type, id, team, life, damage);
    }

    @Override
    public void update() throws SlickException {
        super.update();
        if (this.animatorController.canSwitchCurrent()) {
            this.executeLastDirectionEvent();
        }
    }

    @Override
    protected boolean moveDown() throws SlickException {
        if (this.movement.getMoveDirection() != EDirection.DOWN || this.animatorController.currentAnimationType() != EAnimation.RUN) {
            this.animatorController.setEyesDirection(EDirection.DOWN);
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.DOWN);
            this.event.addStackEvent(EInput.MOVE_DOWN);
            if (this.event.isActivated(EInput.MOVE_UP))
                this.moveUp();
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

    @Override
    protected boolean executeLastDirectionEvent() throws SlickException {
        if (this.animatorController.canSwitchCurrent()) {
            EInput last = this.event.getTheLastEvent();

            if (last != EInput.NOTHING) {
                if (last == EInput.MOVE_RIGHT) {
                    return this.moveRight();
                } else if (last == EInput.MOVE_LEFT) {
                    return this.moveLeft();
                } else if (last == EInput.MOVE_UP) {
                    return this.moveUp();
                } else if (last == EInput.MOVE_DOWN) {
                    return this.moveDown();
                }
            }
        }
        return false;
    }
}
