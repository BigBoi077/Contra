package cegepst;

import cegepst.engine.Buffer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animator {

    private static final int ANIMATION_SPEED = 8;
    private Player player;
    private int nextFrame = ANIMATION_SPEED;
    protected int currentAnimationFrame;

    public Animator(Player player) {
        this.player = player;
    }

    public void cycleRunningFrames() {
        if (this.player.hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= 5) {
                    currentAnimationFrame = 0;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 1; // return to idle frame
        }
    }

    public void cycleGunningFrames() {
        if (this.player.hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= 2) {
                    currentAnimationFrame = 0;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 1; // return to idle frame
        }
    }

    public void cycleJumpingFrames() {
        if (this.player.hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= 4) {
                    currentAnimationFrame = 0;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 1; // return to idle frame
        }
    }

    public void drawCurrentAnimation(BufferedImage currentSprite, Buffer buffer) {
        buffer.drawImage(currentSprite, player.getX(), player.getY());
    }

    public void drawCurrentAnimation(Image[] images, Buffer buffer) {
        buffer.drawImage(images[currentAnimationFrame], player.getX(), player.getY());
    }
}
