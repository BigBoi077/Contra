package cegepst;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteReader {

    public Image readSprite(BufferedImage spriteSheet, int xPositionStart, int yPositionStart, int imageWidth, int imagesHeight, int nbrFrames) {
        for (int i = 0; i < nbrFrames; i++, xPositionStart += imageWidth) {
            spriteSheet.getSubimage(xPositionStart, yPositionStart, imageWidth, imagesHeight);
        }
        return spriteSheet;
    }
}
