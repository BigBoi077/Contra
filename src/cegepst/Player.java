package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.controls.MovementController;
import cegepst.engine.entity.ControllableEntity;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends ControllableEntity {

    private static final String SPRITE_PATH = "images/PlayerSprites.png";
    private GamePad gamePad;
    private SpriteReader spriteReader;
    private BufferedImage spriteSheet;

    private Image[] gunningLeft;
    private Image[] gunningRight;

    private Image[] runningLeft;
    private Image[] runningRight;

    private Image[] jumpingLeft;
    private Image[] jumpingRight;

    private Image[] crouchLeft;
    private Image[] crouchRight;

    private Image[] deathLeft;
    private Image[] deathRight;

    public Player(GamePad gamePad) {
        super(gamePad);
        this.gamePad = gamePad;
        this.spriteReader = new SpriteReader();
        super.setDimension(30, 30);
        super.setSpeed(2);
        initSpriteSheets();
        initSprites();
    }

    private void initSpriteSheets() {
        ImagesReader imagesReader = new ImagesReader();
        spriteSheet = imagesReader.readImage(SPRITE_PATH);
    }

    private void initSprites() {
        gunningLeft = new Image[2];
        gunningRight = new Image[2];
        runningLeft = new Image[5];
        runningRight = new Image[5];
        jumpingLeft = new Image[4];
        jumpingRight = new Image[4];
        crouchLeft = new Image[1];
        crouchRight = new Image[1];
        deathLeft = new Image[5];
        deathRight = new Image[5];
        readSprites();
    }

    private void readSprites() {
        SpriteReader spriteReader = new SpriteReader();
        gunningLeft = spriteReader.readSprite(spriteSheet, xPositionStart, yPositionStart, int imageWidth, int imagesHeight, gunningLeft.lenth());
    }

    @Override
    public void update() {
        super.update();
        if (gamePad.isJumpPressed()) {
            super.startJump();
        }
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectangle(x, y, width, height, Color.GREEN);
        //drawHitBox(buffer);
    }
}
