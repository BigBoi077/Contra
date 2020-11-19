package cegepst.engine.entity;

import cegepst.GameSettings;
import cegepst.engine.CollidableRepository;
import cegepst.engine.controls.Direction;

import java.awt.*;

public class Collision {

    private final MovableEntity entity;
    private int speed;
    private Direction direction;

    public Collision(MovableEntity entity) {
        this.entity = entity;
        this.speed = entity.getSpeed();
        this.direction = entity.getDirection();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getAllowedSpeed() {
        switch (direction) {
            case UP: return getAllowedUpSpeed();
            case DOWN: return getAllowedDownSpeed();
            case LEFT: return getAllowedLeftSpeed();
            case RIGHT: return getAllowedRightSpeed();
        }
        return 0;
    }

    public int getAllowedSpeed(Direction direction) {
        this.direction = direction;
        return getAllowedSpeed();
    }

    private int getAllowedUpSpeed() {
        return distance((StaticEntity other) ->
                entity.y - (other.y + other.height));
    }

    private int getAllowedDownSpeed() {
        return distance((StaticEntity other) ->
                other.y - (entity.y + entity.height));
    }

    private int getAllowedLeftSpeed() {
        return distance((StaticEntity other) ->
                entity.x - (other.x + other.width));
    }

    private int getAllowedRightSpeed() {
        return distance((StaticEntity other) ->
                other.x - (entity.x + entity.width));
    }

    private int distance(DistanceCalculator calculator) {
        Rectangle collisionBound = entity.getCollisionBound(direction);
        int allowedDistance = speed;
        for (StaticEntity other : CollidableRepository.getInstance()) {
            if (collisionBound.intersects(other.getBounds())) {
                allowedDistance = Math.min(allowedDistance,
                        calculator.calculateWith(other));
            }
        }
        return allowedDistance;
    }

    private interface DistanceCalculator {
        int calculateWith(StaticEntity other);
    }
}
