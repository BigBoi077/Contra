package cegepst;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AlienTextures {

    private static Image[] mainCrawlerFrames;
    private static Image[] attackCrawlerFrames;
    private static Image[] mainRunnerFrames;
    private static Image[] attackRunnerFrames;
    private static Image[] deathRunnerFrames;
    private final String SPRITE_PATH = "images/AliensSpritesResized.png";
    private SpriteReader spriteReader;
    private ImagesReader imagesReader;
    private BufferedImage spritesheet;

    public AlienTextures() {
        initSpritesheet();
        initFrames();
        readSprites();
    }

    public static Image[] getMainCrawlerFrames() {
        return mainCrawlerFrames;
    }

    public static Image[] getAttackCrawlerFrames() {
        return attackCrawlerFrames;
    }

    public static Image[] getMainRunnerFrames() {
        return mainRunnerFrames;
    }

    public static Image[] getAttackRunnerFrames() {
        return attackRunnerFrames;
    }

    public static Image[] getDeathRunnerFrames() {
        return deathRunnerFrames;
    }

    private void initFrames() {
        mainCrawlerFrames = new Image[4];
        attackCrawlerFrames = new Image[2];
        mainRunnerFrames = new Image[6];
        attackRunnerFrames = new Image[2];
        deathRunnerFrames = new Image[4];
    }

    public void initSpritesheet() {
        imagesReader = new ImagesReader();
        spritesheet = imagesReader.readImage(SPRITE_PATH);
        this.spriteReader = new SpriteReader(spritesheet);
    }

    public void readSprites() {
        spriteReader.readRightSpriteSheet(mainCrawlerFrames, AlienSpritesheetInfo.CRAWLER_START_X, AlienSpritesheetInfo.CRAWLER_START_Y, AlienSpritesheetInfo.CRAWLER_WIDTH, AlienSpritesheetInfo.CRAWLER_HEIGHT, mainCrawlerFrames.length);
        spriteReader.readRightSpriteSheet(attackCrawlerFrames, AlienSpritesheetInfo.CRAWLER_ATTACK_START_X, AlienSpritesheetInfo.CRAWLER_ATTACK_START_Y, AlienSpritesheetInfo.CRAWLER_WIDTH, AlienSpritesheetInfo.CRAWLER_HEIGHT, attackCrawlerFrames.length);
        spriteReader.readRightSpriteSheet(mainRunnerFrames, AlienSpritesheetInfo.RUNNER_START_X, AlienSpritesheetInfo.RUNNER_START_Y, AlienSpritesheetInfo.RUNNER_WIDTH, AlienSpritesheetInfo.RUNNER_HEIGHT, mainRunnerFrames.length);
        spriteReader.readRightSpriteSheet(attackRunnerFrames, AlienSpritesheetInfo.RUNNER_ATTACK_START_X, AlienSpritesheetInfo.RUNNER_ATTACK_START_Y, AlienSpritesheetInfo.RUNNER_ATTACK_WIDTH, AlienSpritesheetInfo.RUNNER_HEIGHT, attackRunnerFrames.length);
        spriteReader.readRightSpriteSheet(deathRunnerFrames, AlienSpritesheetInfo.RUNNER_DEATH_START_X, AlienSpritesheetInfo.RUNNER_DEATH_START_Y, AlienSpritesheetInfo.RUNNER_DEATH_FRAMES_WIDTH, AlienSpritesheetInfo.RUNNER_DEATH_FRAMES_HEIGHT, deathRunnerFrames.length);
    }
}