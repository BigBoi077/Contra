package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.entity.MovableEntity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animator {

    private static int animationSpeed = 8;
    private final MovableEntity entity;
    private int nextFrame = animationSpeed;
    protected int currentAnimationFrame;

    public Animator(MovableEntity entity) {
        this.entity = entity;
    }

    public void cycleFrames(Image[] frames) {
        if (this.entity.hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= frames.length) {
                    currentAnimationFrame = 0;
                }
                nextFrame = animationSpeed;
            }
        } else {
            currentAnimationFrame = 1;
        }
    }

    public void cycleStaticFrames(Image[] frames) {
        --nextFrame;
        if (nextFrame == 0) {
            ++currentAnimationFrame;
            if (currentAnimationFrame >= frames.length) {
                currentAnimationFrame = 0;
            }
            nextFrame = animationSpeed;
        }
    }

    public void drawCurrentAnimation(BufferedImage currentSprite, Buffer buffer, int xOffset) {
        buffer.drawImage(currentSprite, entity.getX() + xOffset, entity.getY() + 50);
    }

    public void drawCurrentAnimation(Image[] frames, Buffer buffer, int xOffset) {
        checkFrameIndex(frames);
        buffer.drawImage(frames[currentAnimationFrame], entity.getX() + xOffset, entity.getY());
    }

    public void setAnimationSpeed(int animationSpeed) {
        Animator.animationSpeed = animationSpeed;
    }

    private void checkFrameIndex(Image[] frames) {
        if (currentAnimationFrame > frames.length - 1) {
            currentAnimationFrame = 0;
        }
    }
}
