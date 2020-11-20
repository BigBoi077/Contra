package cegepst;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteReader {

    private BufferedImage spriteSheet;

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

    public void flipImage(BufferedImage image) {
        for (int i = 0; i < image.getWidth(); i++){
            for (int j = 0; j < image.getHeight() / 2; j++) {
                int currentPixel = image.getRGB(i, j);
                image.setRGB(i, j, image.getRGB(i, image.getHeight() - j - 1));
                image.setRGB(i, image.getHeight() - j - 1, currentPixel);
            }
        }
    }

    public BufferedImage flip(BufferedImage image) {
        for (int i = 0; i < image.getWidth(); i++){
            for (int j = 0; j < image.getHeight() / 2; j++) {
                int currentPixel = image.getRGB(i, j);
                image.setRGB(i, j, image.getRGB(i, image.getHeight() - j - 1));
                image.setRGB(i, image.getHeight() - j - 1, currentPixel);
            }
        }
        return image;
    }

    public Image[] flipSprite(Image[] images) {
        for (int i = 0; i < images.length; i++) {
            flipImage((BufferedImage) images[i]);
        }
        return images;
    }
}
