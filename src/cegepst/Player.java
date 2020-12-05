package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.controls.Direction;
import cegepst.engine.entity.ControllableEntity;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class Player extends ControllableEntity {

    private static final String SPRITE_PATH = "images/PlayerSpritesResized.png";
    private final GamePad gamePad;
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
    private BufferedImage crouchRightFrame;
    private BufferedImage crouchLeftFrame;
    private BufferedImage deathFrame;
    private final int numberLives;
    private int fireCooldown;
    private int respawnCooldown;
    private boolean isDead;

    public Player(GamePad gamePad) {
        super(gamePad);
        super.isGravityApplied = true;
        this.gamePad = gamePad;
        this.numberLives = GameSettings.NUMBER_PLAYER_LIVES;
        super.setDimension(87, 102);
        super.setSpeed(2);
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

    public boolean isCrouching() {
        return gamePad.isCrouchPressed();
    }

    public int getNumberLives() {
        return numberLives;
    }

    public boolean isJumping() {
        return super.jumping;
    }

    @Override
    public void update() {
        if (isDead) {
            updateRespawnCooldown();
            return;
        }
        if (gamePad.isJumpPressed()) {
            if (!isCrouching()) {
                super.startJump();
            }
        }
        super.update();
        updateFireCooldown();
        moveAccordingToHandler();
        cycleFrames();
        lastDirection = getDirection();
        respawnCooldown = 60;
    }

    public void draw(Buffer buffer, int xOffset) {
        if (isDead) {
            animator.drawCurrentAnimation(deathFrame, buffer, xOffset);
            return;
        }
        if (isCrouching()) {
            if (isMoving(Direction.RIGHT)) {
                animator.drawCurrentAnimation(crouchRightFrame, buffer, xOffset);
            } else {
                animator.drawCurrentAnimation(crouchLeftFrame, buffer, xOffset);
            }
            return;
        }
        if (hasMoved()) {
            if (isInTheAir()) {
                if (isMoving(Direction.RIGHT)) {
                    animator.drawCurrentAnimation(jumpingRightFrames, buffer, xOffset);
                } else {
                    animator.drawCurrentAnimation(jumpingLeftFrames, buffer, xOffset);
                }
            } else if (isGunning()) {
                if (isMoving(Direction.RIGHT)) {
                    animator.drawCurrentAnimation(gunningRightFrames, buffer, xOffset);
                } else {
                    animator.drawCurrentAnimation(gunningLeftFrames, buffer, xOffset);
                }
            } else if (isRunning()) {
                if (isMoving(Direction.RIGHT)) {
                    animator.drawCurrentAnimation(runningRightFrames, buffer, xOffset);
                } else {
                    animator.drawCurrentAnimation(runningLeftFrames, buffer, xOffset);
                }
            }
        } else {
            if (isMoving(Direction.RIGHT) || lastDirection == Direction.DOWN) {
                animator.drawCurrentAnimation(gunningRightFrames, buffer, xOffset);
            } else {
                animator.drawCurrentAnimation(gunningLeftFrames, buffer, xOffset);
            }
        }
    }

    private void cycleFrames() {
        if (hasMoved()) {
            if (isRunning()) {
                animator.cycleFrames(runningRightFrames);
            } else if (isInTheAir()) {
                animator.cycleFrames(jumpingRightFrames);
            }
        } else {
            animator.cycleFrames(gunningRightFrames);
        }
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
        crouchRightFrame = new BufferedImage(17, 34, TYPE_INT_RGB);
        crouchLeftFrame = new BufferedImage(17, 34, TYPE_INT_RGB);
        deathFrame = new BufferedImage(11, 34, TYPE_INT_RGB);
        readSprites();
    }

    private void readSprites() {
        spriteReader.readRightSpriteSheet(gunningRightFrames, PlayerSpritesheetInfo.GUNNING_RIGHT_FRAMES_START_X, PlayerSpritesheetInfo.GUNNING_RIGHT_FRAMES_START_Y, PlayerSpritesheetInfo.GUNNING_WIDTH, PlayerSpritesheetInfo.GUNNING_HEIGHT, gunningRightFrames.length);
        spriteReader.readRightSpriteSheet(runningRightFrames, PlayerSpritesheetInfo.RUNNING_RIGHT_FRAMES_START_X, PlayerSpritesheetInfo.RUNNING_RIGHT_FRAMES_START_Y, PlayerSpritesheetInfo.RUNNING_WIDTH, PlayerSpritesheetInfo.RUNNING_HEIGHT, runningRightFrames.length);
        spriteReader.readRightSpriteSheet(jumpingRightFrames, PlayerSpritesheetInfo.RIGHT_JUMPING_FRAMES_START_X, PlayerSpritesheetInfo.RIGHT_JUMPING_FRAMES_START_Y, PlayerSpritesheetInfo.JUMPING_WIDTH, PlayerSpritesheetInfo.JUMPING_HEIGHT, jumpingRightFrames.length);
        spriteReader.readLeftSpriteSheet(gunningLeftFrames, PlayerSpritesheetInfo.GUNNING_LEFT_FRAMES_START_X, PlayerSpritesheetInfo.GUNNING_LEFT_FRAMES_START_Y, PlayerSpritesheetInfo.GUNNING_WIDTH, PlayerSpritesheetInfo.GUNNING_HEIGHT, gunningRightFrames.length);
        spriteReader.readLeftSpriteSheet(runningLeftFrames, PlayerSpritesheetInfo.RUNNING_LEFT_FRAMES_START_X, PlayerSpritesheetInfo.RUNNING_LEFT_FRAMES_START_Y, PlayerSpritesheetInfo.RUNNING_WIDTH, PlayerSpritesheetInfo.RUNNING_HEIGHT, runningRightFrames.length);
        spriteReader.readLeftSpriteSheet(jumpingLeftFrames, PlayerSpritesheetInfo.LEFT_JUMPING_FRAMES_START_X, PlayerSpritesheetInfo.LEFT_JUMPING_FRAMES_START_Y, PlayerSpritesheetInfo.JUMPING_WIDTH, PlayerSpritesheetInfo.JUMPING_HEIGHT, jumpingRightFrames.length);
        crouchRightFrame = spriteReader.readSingleFrame(PlayerSpritesheetInfo.RIGHT_CROUCH_FRAME_START_X, PlayerSpritesheetInfo.RIGHT_CROUCH_FRAME_START_Y, PlayerSpritesheetInfo.CROUCH_WIDTH, PlayerSpritesheetInfo.CROUCH_HEIGHT);
        crouchLeftFrame = spriteReader.readSingleFrame(PlayerSpritesheetInfo.LEFT_CROUCH_FRAME_START_X, PlayerSpritesheetInfo.LEFT_CROUCH_FRAME_START_Y, PlayerSpritesheetInfo.CROUCH_WIDTH, PlayerSpritesheetInfo.CROUCH_HEIGHT);
        deathFrame = spriteReader.readSingleFrame(PlayerSpritesheetInfo.DEATH_FRAME_START_X, PlayerSpritesheetInfo.DEATH_FRAME_START_Y, PlayerSpritesheetInfo.DEATH_WIDTH, PlayerSpritesheetInfo.DEATH_HEIGHT);
    }

    private void updateFireCooldown() {
        fireCooldown--;
        if (fireCooldown <= 0) {
            fireCooldown = 0;
        }
    }

    private void updateRespawnCooldown() {
        respawnCooldown--;
        if (respawnCooldown <= 0) {
            respawn();
            isDead = false;
        }
    }

    private void respawn() {
        super.teleport(lastX, 150);
    }

    private boolean isRunning() {
        return getDirection() == Direction.LEFT || getDirection() == Direction.RIGHT;
    }

    private boolean isGunning() {
        return gamePad.isFirePressed();
    }

    private boolean isInTheAir() {
        return super.falling || super.jumping || super.currentJumpMeter > super.jumpMaxHeight;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    @Override
    public void draw(Buffer buffer) {
        if (GameSettings.DEBUG_ENABLED) {
            drawHitBox(buffer);
        }
    }
}
