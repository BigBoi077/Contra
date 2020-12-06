package cegepst;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteReader {

    private final BufferedImage spriteSheet;

    public SpriteReader(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public void readRightSpriteSheet(Image[] spriteArray, int xPositionStart, int yPositionStart, int imageWidth, int imagesHeight, int nbrFrames) {
        for (int i = 0; i < nbrFrames; i++, xPositionStart += imageWidth) {
            spriteArray[i] = spriteSheet.getSubimage(xPositionStart, yPositionStart, imageWidth, imagesHeight);
        }
    }

    public void readLeftSpriteSheet(Image[] spriteArray, int xPositionStart, int yPositionStart, int imageWidth, int imagesHeight, int nbrFrames) {
        for (int i = 0; i < nbrFrames; i++, xPositionStart -= imageWidth) {
            spriteArray[i] = spriteSheet.getSubimage(xPositionStart, yPositionStart, imageWidth, imagesHeight);
        }
    }

    public BufferedImage readSingleFrame(int xPositionStart, int yPositionStart, int imageWidth, int imagesHeight) {
        return spriteSheet.getSubimage(xPositionStart, yPositionStart, imageWidth, imagesHeight);
    }
}
