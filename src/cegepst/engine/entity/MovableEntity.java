package cegepst.engine.entity;

import cegepst.GameSettings;
import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;

import java.awt.*;

public abstract class MovableEntity extends UpdatableEntity {

    private final Collision collision;
    private Direction direction = Direction.UP;
    protected int speed = 1;
    private boolean moved;
    private int lastX;
    private int lastY;
    private double gravity = 1; // falling speed;
    private double jumpSpeed = 4;
    private int jumpMaxHeight = 24; // jumping max
    private int currentJumpMeter = 0;
    private boolean jumping = false;
    private boolean falling = false;

    public MovableEntity() {
        collision = new Collision(this);
    }

    @Override
    public void update() { // done first before any other action

        if (GameSettings.GRAVITY_ENABLED) {
            // Are we jumping?
            if (jumping) {
                jump();
            } else {
                // Are we falling?
                if (collision.getAllowedSpeed(Direction.DOWN) > 0) {
                    fall();
                } else {
                    falling = false;
                    gravity = 1; // reset
                }
            }
        }

        moved = (x != lastX || y != lastY);
        lastX = x;
        lastY = y;
    }

    private void jump() {
        move(Direction.UP);
        currentJumpMeter++;
        jumpSpeed -= 0.05; // deceleration
        if (jumpSpeed < 1) {
            jumpSpeed = 1; // minimum
        }
        if (currentJumpMeter == jumpMaxHeight) {
            jumping = false;
            currentJumpMeter = 0;
            jumpSpeed = 4; // reset
        }
    }

    private void fall() {
        falling = true;
        move(Direction.DOWN);
        gravity += 0.15; // Acceleration constant (custom to game)
    }

    public void startJump() {
        if (!GameSettings.GRAVITY_ENABLED) {
            return;
        }
        if (falling) {
            return; // prevent double jumps
        }
        if (collision.getAllowedSpeed(Direction.DOWN) > 0) { // should keep in memory since its called often
            return; // prevent continous jump midair
        }
        jumping = true;
    }

    public void moveLeft() {
        move(Direction.LEFT);
    }

    public void moveRight() {
        move(Direction.RIGHT);
    }

    public void moveUp() {
        // Force to use semantic methods jump
        if (GameSettings.GRAVITY_ENABLED) {
            return;
        }
        move(Direction.UP);
    }

    public void moveDown() {
        if (GameSettings.GRAVITY_ENABLED) {
            return;
        }
        move(Direction.DOWN);
    }

    public void move(Direction direction) {
        if (jumping) {
            // Limit movement in midair (jump mobility)
            collision.setSpeed(direction == Direction.UP ? (int) jumpSpeed : 2);
        } else if (falling) {
            // Limit movement in midair (fall mobility)
            collision.setSpeed((direction == Direction.DOWN) ? (int) gravity : 2);
        } else {
            // Make sure to apply basic speed for other cases
            collision.setSpeed(speed);
        }

        this.direction = direction;
        int allowedSpeed = collision.getAllowedSpeed(direction);
        x += direction.getVelocityX(allowedSpeed);
        y += direction.getVelocityY(allowedSpeed);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean hasMoved() {
        return moved;
    }

    public void drawHitBox(Buffer buffer) {
        if (hasMoved()) {
            Rectangle hitBox = getCollisionBound(direction);
            buffer.drawRectangle(hitBox.x, hitBox.y, hitBox.width, hitBox.height, new Color(255, 0, 0, 200));
        }
    }

    public boolean collisionBoundIntersectWith(StaticEntity other) {
        if (other == null) {
            return false;
        }
        return getCollisionBound(direction).intersects(other.getBounds());
    }

    public Rectangle getCollisionBound(Direction direction) {
        switch (direction) {
            case UP: return getCollisionUpperBound();
            case DOWN: return getCollisionLowerBound();
            case LEFT: return getCollisionLeftBound();
            case RIGHT: return getCollisionRightBound();
        }
        return getBounds();
    }

    private Rectangle getCollisionUpperBound() {
        return new Rectangle(x, y - speed, width, collision.getSpeed());
    }

    private Rectangle getCollisionLowerBound() {
        return new Rectangle(x, y + height, width, collision.getSpeed());
    }

    private Rectangle getCollisionLeftBound() {
        return new Rectangle(x - speed, y, collision.getSpeed(), height);
    }

    private Rectangle getCollisionRightBound() {
        return new Rectangle(x + width, y, collision.getSpeed(), height);
    }
}
