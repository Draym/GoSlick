package com.andres_k.gameToolsLib.components.gameComponent.movement;

public class MovementController2DWorld extends MovementController {
    public MovementController2DWorld(float x, float y, float moveSpeed, float weight) {
        super(x, y, moveSpeed, weight);
    }


    /**
     * USELESS STUFF
     **/
    @Override
    public float calculateGravity() {
        return 0;
    }

    @Override
    public boolean isOnEarth() {
        return true;
    }

    @Override
    protected float getPushGravity() {
        return 0;
    }

    @Override
    public boolean isUseGravity() {
        return false;
    }

    @Override
    public float getGravity() {
        return 0;
    }

    @Override
    public float getGravitySpeed() {
        return 0;
    }

    @Override
    public void setOnEarth(boolean value) {
    }

    @Override
    public void setUseGravity(boolean value) {
    }

    @Override
    public void setGravitySpeed(float value) {
    }
}
