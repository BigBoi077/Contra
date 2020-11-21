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
    private static final int ANIMATION_SPEED = 8;
    private GamePad gamePad;
    private HUD hud;
    private SpriteReader spriteReader;
    private BufferedImage spriteSheet;
    private Animator animator;
    private Direction lastDirection;
    private Image[] gunningRightFrames;
    private Image[] runningRightFrames;
    private Image[] jumpingRightFrames;
    private Image[] gunningLeftFrames;
    private Image[] runningLeftFrames;
    private Image[] jumpingLeftFrames;
    private BufferedImage crouchRight;
    private BufferedImage crouchLeft;
    private BufferedImage deathSprite;
    private int fireCooldown;
    private int numberLives;

    public Player(GamePad gamePad) {
        super(gamePad);
        super.isGravityApplied = true;
        this.gamePad = gamePad;
        this.numberLives = GameSettings.NUMBER_PLAYER_LIVES;
        super.setDimension(30, 30);
        super.setSpeed(4);
        initClassContent();
        CollidableRepository.getInstance().registerEntity(this);
    }

    public Bullet fire() {
        fireCooldown = 10;
        return new Bullet(this);
    }

    public Boolean canFire() {
        return fireCooldown == 0;
    }

    @Override
    public void update() {
        super.update();
        updateFireCooldown();
        updatePlayerSize();
        moveAccordingToHandler();
        cycleFrames();
        if (gamePad.isJumpPressed()) {
            super.startJump();
        }
        lastDirection = getDirection();
    }

    private void cycleFrames() {
        if (hasMoved()) {
            if (isRunning()) {
                animator.cycleRunningFrames();
            } else if (isJumping()) {
                animator.cycleJumpingFrames();
            }
        } else {
            animator.cycleGunningFrames();
        }
    }

    @Override
    public void draw(Buffer buffer) {
        if (isGunning() && isMoving(Direction.RIGHT)) {
            buffer.drawImage(gunningRightFrames[animator.currentAnimationFrame], x, y);
        } else if (isGunning() && isMoving(Direction.LEFT)) {
            buffer.drawImage(gunningLeftFrames[animator.currentAnimationFrame], x, y);
        } else if (isJumping() && isMoving(Direction.RIGHT)) {
            buffer.drawImage(jumpingRightFrames[animator.currentAnimationFrame], x, y);
        } else if (isJumping() && isMoving(Direction.LEFT)) {
            buffer.drawImage(jumpingLeftFrames[animator.currentAnimationFrame], x, y);
        } else if (isCrouching() && isMoving(Direction.RIGHT)) {
            buffer.drawImage(crouchRight, x, y);
        } else if (isCrouching() && isMoving(Direction.LEFT)) {
            buffer.drawImage(crouchLeft, x, y);
        } else if (isMoving(Direction.RIGHT)) {
            buffer.drawImage(runningRightFrames[animator.currentAnimationFrame], x, y);
        } else if (isMoving(Direction.LEFT)) {
            buffer.drawImage(runningLeftFrames[animator.currentAnimationFrame], x, y);
        } else {
            buffer.drawImage(runningRightFrames[animator.currentAnimationFrame], x, y);
        }
        if (GameSettings.DEBUG_ENABLED) {
            drawHitBox(buffer);
        }
        hud.draw(this, buffer);
    }

    private boolean isMoving(Direction direction) {
        return lastDirection == direction;
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
        gunningRightFrames = new Image[2];
        runningRightFrames = new Image[5];
        jumpingRightFrames = new Image[4];
        gunningLeftFrames = new Image[2];
        runningLeftFrames = new Image[5];
        jumpingLeftFrames = new Image[4];
        crouchRight = new BufferedImage(17, 34, TYPE_INT_RGB);
        crouchLeft = new BufferedImage(17, 34, TYPE_INT_RGB);
        deathSprite = new BufferedImage(11, 34, TYPE_INT_RGB);
        readSprites();
    }

    private void readSprites() {
        spriteReader.readRightSpriteSheet(gunningRightFrames, PlayerSpritesheetInfo.GUNNING_RIGHT_FRAMES_START_X, PlayerSpritesheetInfo.GUNNING_RIGHT_FRAMES_START_Y, PlayerSpritesheetInfo.GUNNING_WIDTH, PlayerSpritesheetInfo.GUNNING_HEIGHT, gunningRightFrames.length);
        spriteReader.readRightSpriteSheet(runningRightFrames, PlayerSpritesheetInfo.RUNNING_RIGHT_FRAMES_START_X, PlayerSpritesheetInfo.RUNNING_RIGHT_FRAMES_START_Y, PlayerSpritesheetInfo.RUNNING_WIDTH, PlayerSpritesheetInfo.RUNNING_HEIGHT, runningRightFrames.length);
        spriteReader.readRightSpriteSheet(jumpingRightFrames, PlayerSpritesheetInfo.RIGHT_JUMPING_FRAMES_START_X, PlayerSpritesheetInfo.RIGHT_JUMPING_FRAMES_START_Y, PlayerSpritesheetInfo.JUMPING_WIDTH, PlayerSpritesheetInfo.JUMPING_HEIGHT, jumpingRightFrames.length);

        spriteReader.readLeftSpriteSheet(gunningLeftFrames, PlayerSpritesheetInfo.GUNNING_LEFT_FRAMES_START_X, PlayerSpritesheetInfo.GUNNING_LEFT_FRAMES_START_Y, PlayerSpritesheetInfo.GUNNING_WIDTH, PlayerSpritesheetInfo.GUNNING_HEIGHT, gunningRightFrames.length);
        spriteReader.readLeftSpriteSheet(runningLeftFrames, PlayerSpritesheetInfo.RUNNING_LEFT_FRAMES_START_X, PlayerSpritesheetInfo.RUNNING_LEFT_FRAMES_START_Y, PlayerSpritesheetInfo.RUNNING_WIDTH, PlayerSpritesheetInfo.RUNNING_HEIGHT, runningRightFrames.length);
        spriteReader.readLeftSpriteSheet(jumpingLeftFrames, PlayerSpritesheetInfo.LEFT_JUMPING_FRAMES_START_X, PlayerSpritesheetInfo.LEFT_JUMPING_FRAMES_START_Y, PlayerSpritesheetInfo.JUMPING_WIDTH, PlayerSpritesheetInfo.JUMPING_HEIGHT, jumpingRightFrames.length);

        crouchRight = spriteReader.readSingleFrame(PlayerSpritesheetInfo.RIGHT_CROUCH_FRAME_START_X, PlayerSpritesheetInfo.RIGHT_CROUCH_FRAME_START_Y, PlayerSpritesheetInfo.CROUCH_WIDTH, PlayerSpritesheetInfo.CROUCH_HEIGHT);
        crouchLeft = spriteReader.readSingleFrame(PlayerSpritesheetInfo.LEFT_CROUCH_FRAME_START_X, PlayerSpritesheetInfo.LEFT_CROUCH_FRAME_START_Y, PlayerSpritesheetInfo.CROUCH_WIDTH, PlayerSpritesheetInfo.CROUCH_HEIGHT);

        deathSprite = spriteReader.readSingleFrame(PlayerSpritesheetInfo.DEATH_FRAME_START_X, PlayerSpritesheetInfo.DEATH_FRAME_START_Y, PlayerSpritesheetInfo.DEATH_WIDTH, PlayerSpritesheetInfo.DEATH_HEIGHT);
    }

    private void updateFireCooldown() {
        fireCooldown--;
        if (fireCooldown <=0) {
            fireCooldown = 0;
        }
    }

    private void updatePlayerSize() {
       if (isGunning()) {
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
        return gamePad.isCrouchPressed() && super.falling == false;
    }

    private boolean isRunning() {
        return getDirection() == Direction.LEFT || getDirection() == Direction.RIGHT;
    }

    private boolean isGunning() {
        return gamePad.isFirePressed();
    }

    private boolean isJumping() {
        return gamePad.isJumpPressed();
    }

    public int getNumberLives() {
        return numberLives;
    }
}
