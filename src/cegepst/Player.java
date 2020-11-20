package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.controls.Direction;
import cegepst.engine.entity.ControllableEntity;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class Player extends ControllableEntity {

    private static final String SPRITE_PATH = "images/PlayerSprites.png";
    private GamePad gamePad;
    private HUD hud;
    private SpriteReader spriteReader;
    private BufferedImage spriteSheet;
    private Animator animator;
    private Image[] gunningRight;
    private Image[] runningRight;
    private Image[] jumpingRight;
    private Image[] gunningLeft;
    private Image[] runningLeft;
    private Image[] jumpingLeft;
    private Image[] currentActiveSprites;
    private BufferedImage crouchRight;
    private BufferedImage crouchLeft;
    private BufferedImage deathSprite;
    private BufferedImage currentSprite;
    private int fireCooldow;
    private int numberLives;
    private static final int ANIMATION_SPEED = 8;
    private int nextFrame;
    private int currentAnimationFrame;

    public Player(GamePad gamePad) {
        super(gamePad);
        this.gamePad = gamePad;
        this.numberLives = GameSettings.NUMBER_PLAYER_LIVES;
        super.setDimension(30, 30);
        super.setSpeed(2);
        initClassContent();
        CollidableRepository.getInstance().registerEntity(this);
        currentActiveSprites = gunningRight;
    }

    public Bullet fire() {
        fireCooldow = 10;
        return new Bullet(this);
    }

    public Boolean canFire() {
        return fireCooldow == 0;
    }

    @Override
    public void update() {
        super.update();
        updateFireCooldown();
        updatePlayerSize();
        if (hasMoved()) {
            if (isRunning()) {
                animator.cycleRunningFrames();
            } else if (isJumping()) {
                animator.cycleJumpingFrames();
            }
        } else {
            animator.cycleGunningFrames();
        }
        if (gamePad.isJumpPressed()) {
            super.startJump();
        }
    }

    @Override
    public void draw(Buffer buffer) {
        if (isRunning() && isMoving(Direction.RIGHT)) {
            buffer.drawImage(runningRight[currentAnimationFrame], x, y);
        } else if (isRunning() && isMoving(Direction.LEFT)) {
            buffer.drawImage(runningLeft[currentAnimationFrame], x, y);
        } else if (isGunning() && isMoving(Direction.RIGHT)) {
            buffer.drawImage(gunningRight[currentAnimationFrame], x, y);
        } else if (isGunning() && isMoving(Direction.LEFT)) {
            buffer.drawImage(gunningLeft[currentAnimationFrame], x, y);
        } else if (isJumping() && isMoving(Direction.RIGHT)) {
            buffer.drawImage(jumpingRight[currentAnimationFrame], x, y);
        } else if (isJumping() && isMoving(Direction.LEFT)) {
            buffer.drawImage(jumpingLeft[currentAnimationFrame], x, y);
        } else if (isCrouching() && isMoving(Direction.RIGHT)) {
            buffer.drawImage(crouchRight, x, y);
        } else if (isCrouching() && isMoving(Direction.LEFT)) {
            buffer.drawImage(crouchLeft, x, y);
        }

        if (GameSettings.DEBUG_ENABLED) {
            drawHitBox(buffer);
        }
        hud.draw(this, buffer);
    }

    private boolean isMoving(Direction direction) {
        return getDirection() == direction;
    }

    private void initClassContent() {
        initSpriteSheets();
        initSprites();
        initUtilitiesClasses();
    }

    private void initUtilitiesClasses() {
        this.animator = new Animator(this);
        this.hud = new HUD();
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
        gunningLeft = new Image[2];
        runningLeft = new Image[5];
        jumpingLeft = new Image[4];
        crouchRight = new BufferedImage(17, 34, TYPE_INT_RGB);
        crouchLeft = new BufferedImage(17, 34, TYPE_INT_RGB);
        deathSprite = new BufferedImage(11, 34, TYPE_INT_RGB);
        readSprites();
    }

    private void readSprites() {
        spriteReader.readRightSpriteSheet(gunningRight, PlayerSpritesheetInfo.GUNNING_RIGHT_FRAMES_START_X, PlayerSpritesheetInfo.GUNNING_RIGHT_FRAMES_START_Y, PlayerSpritesheetInfo.GUNNING_WIDTH, PlayerSpritesheetInfo.GUNNING_HEIGHT, gunningRight.length);
        spriteReader.readRightSpriteSheet(runningRight, PlayerSpritesheetInfo.RUNNING_RIGHT_FRAMES_START_X, PlayerSpritesheetInfo.RUNNING_RIGHT_FRAMES_START_Y, PlayerSpritesheetInfo.RUNNING_WIDTH, PlayerSpritesheetInfo.RUNNING_HEIGHT, runningRight.length);
        spriteReader.readRightSpriteSheet(jumpingRight, PlayerSpritesheetInfo.RIGHT_JUMPING_FRAMES_START_X, PlayerSpritesheetInfo.RIGHT_JUMPING_FRAMES_START_Y, PlayerSpritesheetInfo.JUMPING_WIDTH, PlayerSpritesheetInfo.JUMPING_HEIGHT, jumpingRight.length);

        spriteReader.readLeftSpriteSheet(gunningLeft, PlayerSpritesheetInfo.GUNNING_LEFT_FRAMES_START_X, PlayerSpritesheetInfo.GUNNING_LEFT_FRAMES_START_Y, PlayerSpritesheetInfo.GUNNING_WIDTH, PlayerSpritesheetInfo.GUNNING_HEIGHT, gunningRight.length);
        spriteReader.readLeftSpriteSheet(runningLeft, PlayerSpritesheetInfo.RUNNING_LEFT_FRAMES_START_X, PlayerSpritesheetInfo.RUNNING_LEFT_FRAMES_START_Y, PlayerSpritesheetInfo.RUNNING_WIDTH, PlayerSpritesheetInfo.RUNNING_HEIGHT, runningRight.length);
        spriteReader.readLeftSpriteSheet(jumpingLeft, PlayerSpritesheetInfo.LEFT_JUMPING_FRAMES_START_X, PlayerSpritesheetInfo.LEFT_JUMPING_FRAMES_START_Y, PlayerSpritesheetInfo.JUMPING_WIDTH, PlayerSpritesheetInfo.JUMPING_HEIGHT, jumpingRight.length);

        crouchRight = spriteReader.readSingleFrame(PlayerSpritesheetInfo.RIGHT_CROUCH_FRAME_START_X, PlayerSpritesheetInfo.RIGHT_CROUCH_FRAME_START_Y, PlayerSpritesheetInfo.CROUCH_WIDTH, PlayerSpritesheetInfo.CROUCH_HEIGHT);
        crouchLeft = spriteReader.readSingleFrame(PlayerSpritesheetInfo.LEFT_CROUCH_FRAME_START_X, PlayerSpritesheetInfo.LEFT_CROUCH_FRAME_START_Y, PlayerSpritesheetInfo.CROUCH_WIDTH, PlayerSpritesheetInfo.CROUCH_HEIGHT);

        deathSprite = spriteReader.readSingleFrame(PlayerSpritesheetInfo.DEATH_FRAME_START_X, PlayerSpritesheetInfo.DEATH_FRAME_START_Y, PlayerSpritesheetInfo.DEATH_WIDTH, PlayerSpritesheetInfo.DEATH_HEIGHT);
    }

    private void updateFireCooldown() {
        fireCooldow--;
        if (fireCooldow <=0) {
            fireCooldow = 0;
        }
    }

    private void updateCurrentSprite() {
        if (isCrouching() && getDirection() == Direction.RIGHT) {
            currentSprite = crouchRight;
        } else if (isCrouching() && getDirection() == Direction.RIGHT) {
            currentSprite = crouchLeft;
        }
    }

    private void updatePlayerSize() {
        if (isRunning()) {
            height = PlayerSpritesheetInfo.RUNNING_HEIGHT;
            width = PlayerSpritesheetInfo.RUNNING_WIDTH;
        } else if (isGunning()) {
            height = PlayerSpritesheetInfo.GUNNING_HEIGHT;
            width = PlayerSpritesheetInfo.GUNNING_WIDTH;
        } else if (isJumping()) {
            height = PlayerSpritesheetInfo.JUMPING_HEIGHT;
            width = PlayerSpritesheetInfo.JUMPING_WIDTH;
        } else if (isCrouching()) {
            height = PlayerSpritesheetInfo.CROUCH_HEIGHT;
            width = PlayerSpritesheetInfo.CROUCH_WIDTH;
        }
    }

    public boolean isCrouching() {
        return gamePad.isCrouchPressed() && super.gravity == 1 && super.falling == false;
    }

    private boolean isRunning() {
        return getDirection() == Direction.LEFT || getDirection() == Direction.RIGHT;
    }

    private boolean isGunning() {
        return isRunning() && gamePad.isFirePressed();
    }

    private boolean isJumping() {
        return jumping;
    }

    public int getNumberLives() {
        return numberLives;
    }
}
