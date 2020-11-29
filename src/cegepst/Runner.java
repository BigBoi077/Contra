package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;

import java.awt.*;

public class Runner extends Alien {

    public static final int WIDTH = 66;
    public static final int HEIGHT = 90;
    private final Player player;

    public Runner(Player player) {
        animator = new Animator(this);
        nbrLives = 3;
        super.setSpeed(4);
        super.isGravityApplied = true;
        this.player = player;
        initSpritesheet();
        initFrames();
        readSprites();
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void update() {
        if (isDead) {
            return;
        }
        super.update();
        if (player.isCrouching()) {
            super.setSpeed(7);
        }
        cycleFrames();
        moveLeft();
    }

    @Override
    public void draw(Buffer buffer) {
        if (isDead) {
            animator.drawCurrentAnimation(deathFrames, buffer, 0);
            return;
        }
        if (nearPlayer() && player.isCrouching()) {
            animator.drawCurrentAnimation(attackFrames, buffer, 0);
        } else {
            animator.drawCurrentAnimation(mainFrames, buffer, 0);
        }
    }

    @Override
    public void initSpritesheet() {
        imagesReader = new ImagesReader();
        spritesheet = imagesReader.readImage(SPRITE_PATH);
        this.spriteReader = new SpriteReader(spritesheet);
    }

    @Override
    public void initFrames() {
        mainFrames = new Image[6];
        attackFrames = new Image[2];
        deathFrames = new Image[4];
    }

    @Override
    public void readSprites() {
        spriteReader.readRightSpriteSheet(mainFrames, AlienSpritesheetInfo.RUNNER_START_X, AlienSpritesheetInfo.RUNNER_START_Y, WIDTH, HEIGHT, mainFrames.length);
        spriteReader.readRightSpriteSheet(attackFrames, AlienSpritesheetInfo.RUNNER_ATTACK_START_X, AlienSpritesheetInfo.RUNNER_ATTACK_START_Y, AlienSpritesheetInfo.RUNNER_ATTACK_WIDTH, HEIGHT, attackFrames.length);
        spriteReader.readRightSpriteSheet(deathFrames, AlienSpritesheetInfo.RUNNER_DEATH_START_X, AlienSpritesheetInfo.RUNNER_DEATH_START_Y, AlienSpritesheetInfo.RUNNER_DEATH_FRAMES_WIDTH, AlienSpritesheetInfo.RUNNER_DEATH_FRAMES_HEIGHT, deathFrames.length);
    }

    @Override
    public void cycleFrames() {
        if (isDead) {
            return;
        }
        if (nearPlayer() && player.isJumping()) {
            Debuger.consoleLog("Attack");
            animator.cycleFrames(attackFrames);
        } else {
            animator.cycleFrames(mainFrames);
        }
    }

    @Override
    public boolean nearPlayer() {
        return x - player.getX() < 100;
    }

    @Override
    public void spawn() {
        teleport(player.getX() + 550, 0);
    }

    public void setIsDead(boolean isDead) {
        super.isDead = isDead;
    }
}
