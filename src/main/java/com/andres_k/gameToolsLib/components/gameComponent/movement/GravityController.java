package com.andres_k.gameToolsLib.components.gameComponent.movement;

public class GravityController {

    private float gravitySpeed;
    private float pushGravity;
    private float exponential;
    private float weight;

    private float gravity;
    private boolean onEarth;
    private boolean useGravity;


    public GravityController(float gravitySpeed, float weight, boolean onEarth) {
        this.gravitySpeed = gravitySpeed;
        this.pushGravity = 0;
        this.exponential = 0;
        this.gravity = 9.8f;
        this.onEarth = onEarth;
        this.weight = weight;
        this.useGravity = true;
    }

    public void update() {
        if (!this.onEarth && this.useGravity) {
            this.pushGravity += this.calculateGravity();
            if (this.exponential < 1) {
                this.exponential = this.exponential + this.exponential / 2;
                this.exponential = (this.exponential > 0.5f ? 0.5f : this.exponential);
            }
        }
    }

    public void resetGravity() {
        this.pushGravity = 0;
        this.exponential = 0.3f;
    }

    public float calculateGravity() {
        if (this.gravitySpeed == 0) {
            return 0;
        }
        return (((this.weight * this.gravity)) / this.gravitySpeed) * this.exponential;
    }

    public float getPushGravity() {
        if (this.useGravity)
            return this.pushGravity;
        else
            return 0f;
    }

    public boolean isOnEarth() {
        return this.onEarth;
    }

    public boolean isUseGravity() {
        return this.useGravity;
    }

    public float getGravitySpeed() {
        return this.gravitySpeed;
    }


    public float getGravity() {
        return this.pushGravity / this.calculateGravity();
    }

    public void setOnEarth(boolean value) {
        this.onEarth = value;
        this.useGravity = !value;
    }

    public void setUseGravity(boolean value) {
        this.useGravity = value;
        this.resetGravity();
    }

    public void setGravitySpeed(float value) {
        this.gravitySpeed = value;
    }
}
