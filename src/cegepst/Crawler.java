package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.controls.Direction;

import java.awt.*;

public class Crawler extends Alien {

    public static final int CRAWLER_WIDTH = 33;
    public static final int CRAWLER_HEIGHT = 32;
    private final String SPRITE_PATH = "images/AliensSprites.png";
    private final Player player;

    public Crawler(Player player) {
        animator = new Animator(this);
        nbrLives = 3;
        super.isGravityApplied = false;
        this.player = player;
        initSpritesheet();
        initFrames();
        readSprites();
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void update() {
        super.update();
        if (player.isJumping()) {
            super.startJump();
        }
        cycleFrames();
        move(Direction.LEFT);
    }

    @Override
    public void draw(Buffer buffer) {
        if (nearPlayer() && player.isJumping()) {
            animator.drawCurrentAnimation(secondaryFrames, buffer, 0);
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
        secondaryFrames = new Image[2];
    }

    @Override
    public void readSprites() {
        spriteReader.readRightSpriteSheet(mainFrames, AlienSpritesheetInfo.CRAWLER_START_X, AlienSpritesheetInfo.CRAWLER_START_Y, CRAWLER_WIDTH, CRAWLER_HEIGHT, mainFrames.length);
        spriteReader.readRightSpriteSheet(secondaryFrames, AlienSpritesheetInfo.CRAWLER_ATTACK_START_X, AlienSpritesheetInfo.CRAWLER_ATTACK_START_Y, CRAWLER_WIDTH, CRAWLER_HEIGHT, secondaryFrames.length);
    }

    @Override
    public void cycleFrames() {
        if (nearPlayer() && player.isJumping()) {
            Debuger.consoleLog("Attack");
            animator.cycleFrames(secondaryFrames);
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
}
