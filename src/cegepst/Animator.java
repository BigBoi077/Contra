package cegepst;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animator {

    private static final int ANIMATION_SPEED = 8;
    private Player player;
    private int nextFrame;
    private int currentAnimationFrame;

    public Animator(Player player) {
        this.player = player;
    }

    public void updateAnimation(Image[] currentActiveSprites) {
        if (this.player.hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= currentActiveSprites.length) {
                    currentAnimationFrame = 0;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 1; // return to idle frame
        }
        drawCurrentAnimation((BufferedImage) currentActiveSprites[currentAnimationFrame]);
    }

    public void drawCurrentAnimation(BufferedImage currentSprite) {

    }
}
