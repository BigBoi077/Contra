package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;

import java.awt.*;

public class Crawler extends Alien {

    public static final int WIDTH = 66;
    public static final int HEIGHT = 64;
    private final Player player;

    public Crawler(Player player) {
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
        if (player.isJumping()) {
            super.startJump();
        }
        cycleFrames();
        moveLeft();
    }

    @Override
    public void draw(Buffer buffer) {
        if (nearPlayer() && player.isJumping()) {
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
        mainFrames = new Image[4];
        attackFrames = new Image[2];
    }

    @Override
    public void readSprites() {
        spriteReader.readRightSpriteSheet(mainFrames, AlienSpritesheetInfo.CRAWLER_START_X, AlienSpritesheetInfo.CRAWLER_START_Y, WIDTH, HEIGHT, mainFrames.length);
        spriteReader.readRightSpriteSheet(attackFrames, AlienSpritesheetInfo.CRAWLER_ATTACK_START_X, AlienSpritesheetInfo.CRAWLER_ATTACK_START_Y, WIDTH, HEIGHT, attackFrames.length);
    }

    @Override
    public void cycleFrames() {
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
