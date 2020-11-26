package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.entity.MovableEntity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animator {

    private static final int ANIMATION_SPEED = 8;
    private final MovableEntity entity;
    private int nextFrame = ANIMATION_SPEED;
    protected int currentAnimationFrame;

    public Animator(Player entity) {
        this.entity = entity;
    }

    public void cycleRunningFrames() {
        if (this.entity.hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= 5) {
                    currentAnimationFrame = 0;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 1;
        }
    }

    public void cycleGunningFrames() {
        if (this.entity.hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= 2) {
                    currentAnimationFrame = 0;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 0;
        }
    }

    public void cycleJumpingFrames() {
        if (this.entity.hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= 4) {
                    currentAnimationFrame = 0;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 1;
        }
    }

    public void drawCurrentAnimation(BufferedImage currentSprite, Buffer buffer) {
        buffer.drawImage(currentSprite, entity.getX(), entity.getY());
    }

    public void drawCurrentAnimation(Image[] images, Buffer buffer, int xOffset) {
        buffer.drawImage(images[currentAnimationFrame], entity.getX() + xOffset, entity.getY());
    }
}
