package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.entity.ControllableEntity;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class Player extends ControllableEntity {

    private static final String SPRITE_PATH = "images/PlayerSprites.png";
    private GamePad gamePad;
    private SpriteReader spriteReader;
    private BufferedImage spriteSheet;
    private Animator animator;

    private Image[] gunningRight;

    private Image[] runningRight;

    private Image[] jumpingRight;

    private BufferedImage crouchRight;

    private BufferedImage deathSprite;

    public Player(GamePad gamePad) {
        super(gamePad);
        this.gamePad = gamePad;
        super.setDimension(30, 30);
        super.setSpeed(2);
        initSpriteSheets();
        initSprites();
    }

    private void initSpriteSheets() {
        ImagesReader imagesReader = new ImagesReader();
        spriteSheet = imagesReader.readImage(SPRITE_PATH);
        this.spriteReader = new SpriteReader(spriteSheet);
    }

    private void initSprites() {
        gunningRight = new Image[2];

        runningRight = new Image[5];

        jumpingRight = new Image[4];

        crouchRight = new BufferedImage(17, 34, TYPE_INT_RGB);;

        deathSprite = new BufferedImage(11, 34, TYPE_INT_RGB);;
        readSprites();
    }

    private void readSprites() {
        spriteReader.readSpriteSheet(gunningRight, PlayerSpritesheetInfo.GUNNING_FRAMES_START_X, PlayerSpritesheetInfo.GUNNING_FRAMES_START_Y, PlayerSpritesheetInfo.GUNNING_WIDTH, PlayerSpritesheetInfo.CROUCH_HEIGHT, gunningRight.length);

        spriteReader.readSpriteSheet(runningRight, PlayerSpritesheetInfo.RUNNING_FRAMES_START_X, PlayerSpritesheetInfo.RUNNING_FRAMES_START_Y, PlayerSpritesheetInfo.RUNNING_WIDTH, PlayerSpritesheetInfo.RUNNING_HEIGHT, runningRight.length);

        spriteReader.readSpriteSheet(jumpingRight, PlayerSpritesheetInfo.JUMPING_FRAMES_START_X, PlayerSpritesheetInfo.JUMPING_FRAMES_START_Y, PlayerSpritesheetInfo.JUMPING_WIDTH, PlayerSpritesheetInfo.JUMPING_HEIGHT, jumpingRight.length);

        crouchRight = spriteReader.readSingleFrame(PlayerSpritesheetInfo.CROUCH_FRAME_START_X, PlayerSpritesheetInfo.CROUCH_FRAME_START_Y, PlayerSpritesheetInfo.CROUCH_WIDTH, PlayerSpritesheetInfo.CROUCH_HEIGHT);

        deathSprite = spriteReader.readSingleFrame(PlayerSpritesheetInfo.DEATH_FRAME_START_X, PlayerSpritesheetInfo.DEATH_FRAME_START_Y, PlayerSpritesheetInfo.DEATH_WIDTH, PlayerSpritesheetInfo.DEATH_HEIGHT);
    }

    @Override
    public void update() {
        super.update();
        if (gamePad.isJumpPressed()) {
            super.startJump();
        }
        animato
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(x, y, width, height, Color.GREEN);
        if (GameSettings.DEBUG_ENABLED) {
            drawHitBox(buffer);
        }
    }
}
