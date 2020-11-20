package cegepst;

import cegepst.engine.Buffer;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class HUD {

    private static final String SPRITE_PATH = "images/ItemsSymbol.png";
    private SpriteReader spriteReader;
    private BufferedImage spriteSheet;
    private BufferedImage lifeSymbol;

    public HUD() {
        ImagesReader imagesReader = new ImagesReader();
        spriteSheet = imagesReader.readImage(SPRITE_PATH);
        this.spriteReader = new SpriteReader(spriteSheet);
        lifeSymbol = new BufferedImage(25, 15, TYPE_INT_RGB);
        lifeSymbol = spriteReader.readSingleFrame(SymbolSpritesheetInfo.LIFE_SYMBOL_START_X, SymbolSpritesheetInfo.LIFE_SYMBOL_START_Y, SymbolSpritesheetInfo.LIFE_SYMBOL_START_WIDTH, SymbolSpritesheetInfo.LIFE_SYMBOL_START_HEIGHT);
    }

    public void draw(Player player, Buffer buffer) {
        buffer.drawImage(lifeSymbol, 20, 20);
        buffer.drawText(" X " + player.getNumberLives(), 45, 32, Color.white);
    }
}
