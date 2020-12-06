package cegepst;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class AlienTextures {

    private static BufferedImage alienBullet;
    private final String SPRITE_PATH = "images/AliensSpritesResized.png";

    private static Image[] mainCrawlerFrames;
    private static Image[] attackCrawlerFrames;
    private static Image[] mainRunnerFrames;
    private static Image[] attackRunnerFrames;
    private static Image[] deathRunnerFrames;
    private static Image[] alienQueenFrames;
    private static Image[] alienQueenWingFrames;
    private final String ALIEN_QUEEN_SPRITE_PATH = "images/AlienBoss.v1.png";

    private ImagesReader imagesReader;

    private BufferedImage alienSpriteSheet;
    private BufferedImage alienQueenSpriteSheet;

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

    public static Image[] getAlienQueenFrames() {
        return alienQueenFrames;
    }

    public static Image[] getAlienQueenWingFrames() {
        return alienQueenWingFrames;
    }

    public static BufferedImage getAlienBullet() {
        return alienBullet;
    }

    private void initFrames() {
        mainCrawlerFrames = new Image[4];
        attackCrawlerFrames = new Image[2];
        mainRunnerFrames = new Image[6];
        attackRunnerFrames = new Image[2];
        deathRunnerFrames = new Image[4];
        alienQueenFrames = new Image[3];
        alienQueenWingFrames = new Image[3];
        alienBullet = new BufferedImage(28, 28, TYPE_INT_RGB);
    }

    public void initSpritesheet() {
        imagesReader = new ImagesReader();
        alienSpriteSheet = imagesReader.readImage(SPRITE_PATH);
        alienQueenSpriteSheet = imagesReader.readImage(ALIEN_QUEEN_SPRITE_PATH);
    }

    public void readSprites() {
        readAlienSprites();
        readQueenSprites();
    }

    private void readQueenSprites() {
        SpriteReader spriteReader = new SpriteReader(alienQueenSpriteSheet);
        spriteReader.readRightSpriteSheet(alienQueenFrames, AlienSpritesheetInfo.QUEEN_START_X, AlienSpritesheetInfo.QUEEN_START_Y, AlienSpritesheetInfo.QUEEN_WIDTH, AlienSpritesheetInfo.QUEEN_HEIGHT, alienQueenFrames.length);
        spriteReader.readRightSpriteSheet(alienQueenWingFrames, AlienSpritesheetInfo.QUEEN_WINGS_START_X, AlienSpritesheetInfo.QUEEN_WINGS_START_Y, AlienSpritesheetInfo.QUEEN_WINGS_WIDTH, AlienSpritesheetInfo.QUEEN_WINGS_HEIGHT, alienQueenWingFrames.length);
    }

    private void readAlienSprites() {
        SpriteReader spriteReader = new SpriteReader(alienSpriteSheet);
        spriteReader.readRightSpriteSheet(mainCrawlerFrames, AlienSpritesheetInfo.CRAWLER_START_X, AlienSpritesheetInfo.CRAWLER_START_Y, AlienSpritesheetInfo.CRAWLER_WIDTH, AlienSpritesheetInfo.CRAWLER_HEIGHT, mainCrawlerFrames.length);
        spriteReader.readRightSpriteSheet(attackCrawlerFrames, AlienSpritesheetInfo.CRAWLER_ATTACK_START_X, AlienSpritesheetInfo.CRAWLER_ATTACK_START_Y, AlienSpritesheetInfo.CRAWLER_WIDTH, AlienSpritesheetInfo.CRAWLER_HEIGHT, attackCrawlerFrames.length);
        spriteReader.readRightSpriteSheet(mainRunnerFrames, AlienSpritesheetInfo.RUNNER_START_X, AlienSpritesheetInfo.RUNNER_START_Y, AlienSpritesheetInfo.RUNNER_WIDTH, AlienSpritesheetInfo.RUNNER_HEIGHT, mainRunnerFrames.length);
        spriteReader.readRightSpriteSheet(attackRunnerFrames, AlienSpritesheetInfo.RUNNER_ATTACK_START_X, AlienSpritesheetInfo.RUNNER_ATTACK_START_Y, AlienSpritesheetInfo.RUNNER_ATTACK_WIDTH, AlienSpritesheetInfo.RUNNER_HEIGHT, attackRunnerFrames.length);
        spriteReader.readRightSpriteSheet(deathRunnerFrames, AlienSpritesheetInfo.RUNNER_DEATH_START_X, AlienSpritesheetInfo.RUNNER_DEATH_START_Y, AlienSpritesheetInfo.RUNNER_DEATH_FRAMES_WIDTH, AlienSpritesheetInfo.RUNNER_DEATH_FRAMES_HEIGHT, deathRunnerFrames.length);
        alienBullet = spriteReader.readSingleFrame(731, 137, 28, 28);
    }
}