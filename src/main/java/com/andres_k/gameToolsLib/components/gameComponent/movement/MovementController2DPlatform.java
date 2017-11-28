package com.andres_k.gameToolsLib.components.gameComponent.movement;

public class MovementController2DPlatform extends MovementController {

    private GravityController gravityController;

    public MovementController2DPlatform(float x, float y, float moveSpeed, float gravitySpeed, float weight, boolean onEarth) {
        super(x, y, moveSpeed, weight);
        this.gravityController = new GravityController(gravitySpeed, weight, onEarth);
    }

    public MovementController2DPlatform(MovementController movement) {
        super(movement);
    }


    @Override
    public float calculateGravity() {
        return this.gravityController.calculateGravity();
    }

    @Override
    public boolean isOnEarth() {
        return this.gravityController.isOnEarth();
    }

    @Override
    protected float getPushGravity() {
        return this.gravityController.getPushGravity();
    }

    @Override
    public boolean isUseGravity() {
        return this.gravityController.isUseGravity();
    }

    @Override
    public float getGravity() {
        return this.gravityController.getGravity();
    }

    @Override
    public float getGravitySpeed() {
        return this.gravityController.getGravitySpeed();
    }

    @Override
    public void setOnEarth(boolean value) {
        this.gravityController.setOnEarth(value);
    }

    @Override
    public void setUseGravity(boolean value) {
        this.gravityController.setUseGravity(value);
    }

    @Override
    public void setGravitySpeed(float value) {
        this.gravityController.setGravitySpeed(value);
    }
}
