package cegepst;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImagesReader {

    private BufferedImage image;

    public BufferedImage readImage(final String SPRITE_PATH) {
        try {
            return image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
