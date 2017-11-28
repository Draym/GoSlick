package com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.characters;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.components.gameComponent.movement.MovementController2DWorld;
import org.newdawn.slick.SlickException;

public class Player2DRpg extends Player2D {
    protected Player2DRpg(AnimatorController animatorController, EGameObject type, String id, int team, float x, float y, float life, float damage, float moveSpeed, float weight) {
        super(animatorController, new MovementController2DWorld(x, y, moveSpeed, weight), type, id, team, life, damage);
    }

    @Override
    public void executeActionOnNoEvent() throws SlickException {
        this.animatorController.changeAnimation(EAnimation.IDLE);
    }

    @Override
    protected boolean moveDown() throws SlickException {
        if (this.movement.getMoveDirection() != EDirection.DOWN || this.animatorController.currentAnimationType() != EAnimation.RUN) {
            this.animatorController.setEyesDirection(EDirection.DOWN);
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.DOWN);
            this.event.addStackEvent(EInput.MOVE_DOWN);
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
            return true;
        }
        return false;
    }

    @Override
    protected boolean moveUp() throws SlickException {
        if (this.movement.getMoveDirection() != EDirection.UP || this.animatorController.currentAnimationType() != EAnimation.RUN) {
            this.animatorController.setEyesDirection(EDirection.UP);
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.UP);
            this.event.addStackEvent(EInput.MOVE_UP);
            return true;
        }
        return false;
    }

    @Override
    protected void changeDirection() {
        EInput recentMove = this.event.getMoreRecentEventBetween(new EInput[]{EInput.MOVE_RIGHT, EInput.MOVE_LEFT, EInput.MOVE_UP, EInput.MOVE_DOWN});

        if (recentMove == EInput.MOVE_RIGHT) {
            this.movement.setMoveDirection(EDirection.RIGHT);
        } else if (recentMove == EInput.MOVE_LEFT) {
            this.movement.setMoveDirection(EDirection.LEFT);
        } else if (recentMove == EInput.MOVE_DOWN) {
            this.movement.setMoveDirection(EDirection.DOWN);
        } else if (recentMove == EInput.MOVE_UP) {
            this.movement.setMoveDirection(EDirection.UP);
        } else {
            this.movement.setMoveDirection(EDirection.NONE);
        }
    }
}
