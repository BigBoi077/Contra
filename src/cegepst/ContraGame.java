package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.Game;
import cegepst.engine.GameTime;
import cegepst.engine.entity.StaticEntity;

import java.util.ArrayList;

public class ContraGame extends Game {

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

    public ContraGame() {
        gamePad = new GamePad();
        level = new World();
        player = new Player(gamePad);
        camera = new Camera(0);
        hud = new HUD();
        leftBorder = new LeftBorder(player);
        alienSpawner = new AlienSpawner(player);
        bullets = new ArrayList<>();
        player.teleport(100, 0);
        aliens = alienSpawner.getAliensArray();
        collisionDetector = new CollisionDetector();
    }

    @Override
    public void initialize() {
        soundEffectPlayer = new SoundEffectPlayer();
        alienTextures = new AlienTextures();
        musicPlayer = new MusicPlayer();
        musicPlayer.start();
    }

    @Override
    public void conclude() {

    }

    @Override
    public void update() {
        player.update();
        if (camera.getxOffset() >= -5920) {
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
        ArrayList<StaticEntity> killedElements = new ArrayList<>();
        ArrayList<AlienBullet> alienBullets = null;
        if (isBossFight) {
            alienBullets = queen.getAlienBullets();
        }
        for (Alien alien : aliens) {
            for (Bullet bullet : bullets) {
                if (bullet.needToDeleteBullet() || bullet.collisionBoundIntersectWith(level.getEggs()) || bullet.collisionBoundIntersectWith(leftBorder)) {
                    killedElements.add(bullet);
                }
                if (bullet.collisionBoundIntersectWith(alien)) {
                    killedElements.add(bullet);
                    alien.decrementHealth();
                    if (alien.nbrLives <= 0) {
                        alien.setIsDead(true);
                        CollidableRepository.getInstance().unregisterEntity(alien);
                    }
                    if (alien.deathCooldownFinished()) {
                        killedElements.add(alien);
                    }
                }
            }
            if (isBossFight) {
                for (AlienBullet alienBullet : alienBullets) {
                    if (alienBullet.collisionBoundIntersectWith(leftBorder)) {
                        killedElements.add(alienBullet);
                    }
                    if (alienBullet.collisionBoundIntersectWith(player)) {
                        if (killPlayer()) return;
                    }
                }
            }
            if (player.collisionBoundIntersectWith(alien) || player.collisionBoundIntersectWith(leftBorder)) {
                if (killPlayer()) return;
            }
        }
        killUnwantedEntities(killedElements);
    }

    private boolean killPlayer() {
        if (player.isDead()) {
            return true;
        }
        player.setDead(true);
        soundEffectPlayer.playPlayerSoundEffect("player_death.wav");
        player.decrementLives();
        return false;
    }

    private void killUnwantedEntities(ArrayList<StaticEntity> killedElements) {
        for (StaticEntity killedElement : killedElements) {
            if (killedElement instanceof Alien) {
                aliens.remove(killedElement);
                super.incrementScore(50);
            } else if (killedElement instanceof Bullet) {
                bullets.remove(killedElement);
            }
            CollidableRepository.getInstance().unregisterEntity(killedElement);
        }
    }

    private void manageKeyPresses() {
        if (gamePad.isQuitPressed() || player.getNumberLives() == 0) {
            musicPlayer.stop();
            soundEffectPlayer.playSoundEffect("game_over.wav");
            GameTime.waitSeconds(5);
            super.stop();
        }
        if (gamePad.isFirePressed() && player.canFire()) {
            bullets.add(player.fire());
            soundEffectPlayer.playSoundEffect("gun_shot.wav");
            super.incrementScore(15);
        }
        if (gamePad.isPausePressed()) {
            soundEffectPlayer.playSoundEffect("pause_sound.wav");
            super.pause(gamePad);
            soundEffectPlayer.playSoundEffect("pause_sound.wav");
        }
    }

    private void checkToSwitchToBossFight() {
        if (camera.getxOffset() <= -5920) {
            if (!isBossFight) {
                musicPlayer.playBossMusic();
                queen = new AlienQueen(leftBorder);
                queen.spawn();
            }
            isBossFight = true;
        }
    }
}
