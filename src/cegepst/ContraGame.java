package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.Game;
import cegepst.engine.GameTime;
import cegepst.engine.RenderingEngine;

import java.util.ArrayList;

public class ContraGame extends Game {

    private final int CAMERA_STOP = -5915;

    private final Player player;
    private final GamePad gamePad;
    private final World level;
    private final LeftBorder leftBorder;
    private final Camera camera;
    private final HUD hud;
    private final AlienSpawner alienSpawner;

    private final CollisionDetector collisionDetector;
    private final ArrayList<Bullet> bullets;
    private final ArrayList<Alien> aliens;

    private SoundEffectPlayer soundEffectPlayer;
    private MusicPlayer musicPlayer;
    private AlienTextures alienTextures;
    private AlienQueen queen;
    private boolean isBossFight = false;
    private boolean isWinner = false;

    public ContraGame() {
        gamePad = new GamePad();
        level = new World();
        player = new Player(gamePad, this);
        camera = new Camera(0);
        hud = new HUD();
        leftBorder = new LeftBorder(player);
        alienSpawner = new AlienSpawner(player);
        bullets = new ArrayList<>();
        player.teleport(100, 0);
        aliens = alienSpawner.getAliensArray();
        collisionDetector = new CollisionDetector(this);
    }

    @Override
    public void initialize() {
        soundEffectPlayer = new SoundEffectPlayer();
        alienTextures = new AlienTextures();
        musicPlayer = new MusicPlayer();
        musicPlayer.start();
        RenderingEngine.getInstance().getScreen().hideCursor();
        RenderingEngine.getInstance().getScreen().fullScreen();
    }

    @Override
    public void conclude() {
        musicPlayer.stop();
        if (isWinner) {
            soundEffectPlayer.playSoundEffect("level_win.wav", "effects");
            GameTime.waitSeconds(6);
        }
        super.playing = false;
    }

    @Override
    public void update() {
        player.update();
        if (camera.getxOffset() >= CAMERA_STOP) {
            camera.update();
            leftBorder.update();
        }
        if (queen != null) {
            queen.update();
        }
        alienSpawner.update();
        manageKeyPresses();
        updateEntities();
        checkToSwitchToBossFight();
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.translate(camera.getxOffset());
        level.draw(buffer);
        leftBorder.draw(buffer);
        if (queen != null) {
            queen.draw(buffer);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(buffer);
        }
        for (Alien alien : aliens) {
            alien.draw(buffer);
        }
        buffer.translate(-camera.getxOffset());
        player.draw(buffer, camera.getxOffset());
        hud.draw(player, super.score, buffer);
    }

    private void updateEntities() {
        for (Bullet bullet : bullets) {
            bullet.update();
        }
        for (Alien alien : aliens) {
            alien.update();
            if (alien.collisionBoundIntersectWith(level.getEggs())) {
                alien.startJump();
            }
        }
        checkEntitiesCollisions();
    }

    private void checkEntitiesCollisions() {
        collisionDetector.checkCollisions(aliens, bullets);
        if (isBossFight) {
            ArrayList<AlienBullet> alienBullets = queen.getAlienBullets();
            collisionDetector.checkAlienBulletCollision(alienBullets, bullets, player);
            collisionDetector.checkQueenCollisions(bullets, queen, player);
        }
    }

    public boolean killPlayer() {
        if (player.isDead()) {
            return true;
        }
        player.setDead(true);
        soundEffectPlayer.playSoundEffect("player_death.wav", "player");
        player.decrementLives();
        return false;
    }

    private void manageKeyPresses() {
        if (gamePad.isQuitPressed() || player.getNumberLives() == 0) {
            musicPlayer.stop();
            soundEffectPlayer.playSoundEffect("game_over.wav", "effects");
            GameTime.waitSeconds(5);
            super.stop();
        }
        if (gamePad.isFirePressed() && player.canFire()) {
            bullets.add(player.fire());
            soundEffectPlayer.playSoundEffect("gun_shot.wav", "effects");
            super.incrementScore(15);
        }
        if (gamePad.isPausePressed()) {
            soundEffectPlayer.playSoundEffect("pause_sound.wav", "effects");
            super.pause(gamePad);
            soundEffectPlayer.playSoundEffect("pause_sound.wav", "effects");
        }
    }

    private void checkToSwitchToBossFight() {
        if (camera.getxOffset() <= CAMERA_STOP) {
            if (!isBossFight) {
                musicPlayer.playBossMusic();
                queen = new AlienQueen(leftBorder);
                queen.spawn();
            }
            isBossFight = true;
        }
    }

    public LeftBorder getLeftBorder() {
        return leftBorder;
    }

    public World getLevel() {
        return level;
    }

    public Player getPlayer() {
        return player;
    }

    public void isWinner() {
        this.isWinner = true;
    }

    public boolean isBossFight() {
        return isBossFight;
    }
}
