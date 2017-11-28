package com.andres_k.gameToolsLib.components.gameComponent.movement;

/**
 * Created by andres_k on 14/10/2015.
 */
public enum EDirection {
    NONE(0, 0, 0, false),
    UP(0, -1, 270, false),
    DOWN(0, 1, 90, false),
    LEFT(-1, 0, 180, true),
    RIGHT(1, 0, 0, false);

    private int coeffX;
    private int coeffY;
    private float angle;
    private boolean horizontalFlip;

    EDirection(int coeffX, int coeffY, float angle, boolean horizontalFlip){
        this.coeffX = coeffX;
        this.coeffY = coeffY;
        this.angle = angle;
        this.horizontalFlip = horizontalFlip;
    }

    public int  getCoeffX(){
        return this.coeffX;
    }

    public int  getCoeffY(){
        return this.coeffY;
    }

    public boolean isHorizontalFlip() {
        return this.horizontalFlip;
    }

    public float getAngle() {
        return this.angle;
    }
}
