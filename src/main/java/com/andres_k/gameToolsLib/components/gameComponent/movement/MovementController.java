package com.andres_k.gameToolsLib.components.gameComponent.movement;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.collisions.CollisionItem;
import com.andres_k.gameToolsLib.components.gameComponent.collisions.CollisionResult;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.tools.Console;
import com.andres_k.gameToolsLib.utils.tools.MathTools;

/**
 * Created by andres_k on 20/10/2015.
 */
public abstract class MovementController {
    private Pair<Float, Float> positions;

    private EDirection moveDirection;

    private float moveSpeed;
    private float weight;
    private float pushX;
    private float pushY;


    private float coeffX;
    private float coeffY;

    protected MovementController(float x, float y, float moveSpeed, float weight) {
        this.positions = new Pair<>(x, y);
        this.moveSpeed = moveSpeed;
        this.weight = weight;
        this.pushX = 0;
        this.pushY = 0;
        this.coeffX = 1;
        this.coeffY = 1;
        this.moveDirection = EDirection.NONE;
        this.resetGravity();
    }

    public MovementController(MovementController movement) {
        this.positions = new Pair<>(movement.positions.getV1(), movement.positions.getV2());
        this.moveSpeed = movement.moveSpeed;
        this.weight = movement.weight;
        this.pushX = movement.pushX;
        this.pushY = movement.pushY;
        this.moveDirection = movement.moveDirection;
    }

    // FUNCTIONS

    public void update() {
    }

    public void addPushX(float value) {
        if (this.moveDirection == EDirection.RIGHT || this.moveDirection == EDirection.NONE)
            this.positions.setV1(this.positions.getV1() + value);
        else if (this.moveDirection == EDirection.LEFT)
            this.positions.setV1(this.positions.getV1() - value);
    }

    public void addPushY(float value) {
        this.positions.setV2(this.positions.getV2() + value);
    }

    public void stopMovement() {
        this.pushX = 0;
        this.pushY = 0;
        this.moveDirection = EDirection.NONE;
        this.resetGravity();
    }

    public void resetGravity() {
    }

    public Pair<Float, Float> predictNextPosition() {
        float nx = this.getNextX();
        float ny = this.getNextY();
        return new Pair<>(nx, ny);
    }

    public void nextPosition(CollisionResult collisionResult) {
        this.nextX(collisionResult);
        this.nextY(collisionResult);
    }

    private void nextX(CollisionResult collisionResult) {
        if (!collisionResult.hasCollisionX()) {
            this.positions.setV1(this.getNextX());
        } else if (this.moveSpeed != 0) {
            CollisionItem item = collisionResult.getLowCollisionX(EGameObject.getAllFrom(EGameObject.PLATFORM));

            if (item != null) {
                int mult = (item.getCollisionDirection() == EDirection.RIGHT ? 1 : -1);
                this.positions.setV1(this.getX() + ((MathTools.abs(item.getCollisionDistance() - 1)) * mult));
                this.setPushX(0);
            }
        }
    }

    private void nextY(CollisionResult collisionResult) {
        if (!collisionResult.hasCollisionY()) {
            this.positions.setV2(this.getNextY());
        } else if (this.moveSpeed != 0) {
            CollisionItem item = collisionResult.getLowCollisionY(EGameObject.getAllFrom(EGameObject.BORDER));

            if (item != null) {
                if (item.getCollisionDirection() == EDirection.UP) {
                    if (item.getCollisionDistance() > 0)
                        this.positions.setV2(this.getY() + item.getCollisionDistance());
                    this.setOnEarth(true);
                } else if (item.getCollisionDirection() == EDirection.DOWN) {
                    this.positions.setV2(this.getY() + MathTools.abs(item.getCollisionDistance()));
                }
                this.setPushY(0);
                this.resetGravity();
            }
        }
    }

    public void nextPosition() {
        this.positions.setV1(this.getNextX());
        this.positions.setV2(this.getNextY());
    }

    public float calculateDistance(float msec) {
        return this.moveSpeed * (msec / 1000);
    }

    public abstract float calculateGravity();

    public float calculatePushX() {
        return this.calculateDistance(GameConfig.currentTimeLoop) * this.getPushX();
    }

    public float calculatePushY() {
        return this.calculateDistance(GameConfig.currentTimeLoop) * this.getPushY();
    }

    // GETTERS

    public float getX() {
        return this.positions.getV1();
    }

    public float getY() {
        return this.positions.getV2();
    }

    public float getCoeffX() {
        return this.coeffX;
    }

    public float getCoeffY() {
        return this.coeffY;
    }

    public Pair<Float, Float> getPositions() {
        return this.positions;
    }

    public abstract boolean isOnEarth();

    public float getPushX() {
        Console.force("pushX: " + this.pushX + " direction: " + this.moveDirection);
        if (this.moveDirection == EDirection.RIGHT || this.moveDirection == EDirection.NONE)
            return this.pushX;
        else if (this.moveDirection == EDirection.LEFT)
            return -this.pushX;
        return 0;
    }

    public float getPushY() {
        return this.pushY;
    }

    private float getNextX() {
        return this.getX() + this.calculatePushX();
    }

    private float getNextY() {
        return this.getY() + this.calculatePushY() + this.getPushGravity();
    }

    protected abstract float getPushGravity();

    public abstract boolean isUseGravity();

    public abstract float getGravity();

    public float getMoveSpeed() {
        return this.moveSpeed;
    }

    public abstract float getGravitySpeed();

    public float getWeight() {
        return this.weight;
    }

    public EDirection getMoveDirection() {
        return this.moveDirection;
    }

    // SETTERS

    public void setPushX(float value) {
        this.pushX = value;
    }

    public void setPushY(float value) {
        this.pushY = value;
    }

    public void setCoeffX(float value) {
        this.coeffX = value;
    }

    public void setCoeffY(float value) {
        this.coeffY = value;
    }

    public abstract void setOnEarth(boolean value);

    public abstract void setUseGravity(boolean value);

    public abstract void setGravitySpeed(float value);

    public void setMoveSpeed(float value) {
        this.moveSpeed = value;
    }

    public void setWeight(float value) {
        this.weight = value;
    }

    public void setMoveDirection(EDirection direction) {
        this.moveDirection = direction;
    }

    public void setPositions(float x, float y) {
        this.positions.setV1(x);
        this.positions.setV2(y);
    }
}
