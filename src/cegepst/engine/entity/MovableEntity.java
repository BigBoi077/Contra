package cegepst.engine.entity;

import cegepst.GameSettings;
import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;

import java.awt.*;

public abstract class MovableEntity extends UpdatableEntity {

    private final Collision collision;
    private Direction direction = Direction.UP;
    protected int speed = 1;
    protected final int jumpMaxHeight = 24;
    protected int lastX;
    private boolean moved;
    protected int lastY;
    protected int currentJumpMeter = 0;
    protected double gravity = 1;
    private double jumpSpeed = 4;
    protected boolean jumping = false;
    protected boolean falling = false;
    protected boolean isGravityApplied;

    public MovableEntity() {
        collision = new Collision(this);
    }

    @Override
    public void update() {
        if (GameSettings.GRAVITY_ENABLED && isGravityApplied) {
            if (jumping) {
                jump();
            } else {
                if (collision.getAllowedSpeed(Direction.DOWN) > 0) {
                    fall();
                } else {
                    falling = false;
                    gravity = 1;
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
        jumpSpeed -= 0.05;
        if (jumpSpeed < 1) {
            jumpSpeed = 1;
        }
        if (currentJumpMeter == jumpMaxHeight) {
            jumping = false;
            currentJumpMeter = 0;
            jumpSpeed = 4;
        }
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void fall() {
        falling = true;
        move(Direction.DOWN);
        gravity += GameSettings.GRAVITY_ACCELERATION;
    }

    public void startJump() {
        if (!GameSettings.GRAVITY_ENABLED) {
            return;
        }
        if (falling) {
            return;
        }
        if (collision.getAllowedSpeed(Direction.DOWN) > 0) {
            return;
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
        if (GameSettings.GRAVITY_ENABLED && isGravityApplied) {
            return;
        }
        move(Direction.UP);
    }

    public void moveDown() {
        if (GameSettings.GRAVITY_ENABLED && isGravityApplied) {
            return;
        }
        move(Direction.DOWN);
    }

    public void move(Direction direction) {
        if (GameSettings.GRAVITY_ENABLED && isGravityApplied) {
            if (jumping) {
                collision.setSpeed(direction == Direction.UP ? (int) jumpSpeed : 2);
            } else if (falling) {
                collision.setSpeed((direction == Direction.DOWN) ? (int) gravity : 2);
            } else {
                collision.setSpeed(speed);
            }
        } else {
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
