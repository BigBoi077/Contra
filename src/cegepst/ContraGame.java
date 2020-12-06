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
    private final ArrayList<Bullet> bullets;
    private final ArrayList<Alien> aliens;
    private MusicPlayer musicPlayer;
    private AlienTextures alienTextures;
    private final SoundEffectPlayer soundsRepository;
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
        soundsRepository = new SoundEffectPlayer();
        player.teleport(100, 0);
        aliens = alienSpawner.getAliensArray();
    }

    @Override
    public void initialize() {
        bullets.add(new Bullet(player));
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
        alienSpawner.update();
        manageKeyPresses();
        updateEntities();
        checkToSwitchMusic();
    }

    private void manageKeyPresses() {
        if (gamePad.isQuitPressed() || player.getNumberLives() == 0) {
            musicPlayer.stop();
            soundsRepository.playSoundEffect("game_over.wav");
            GameTime.waitSeconds(5);
            super.stop();
        }
        if (gamePad.isFirePressed() && player.canFire()) {
            bullets.add(player.fire());
            soundsRepository.playSoundEffect("gun_shot.wav");
            super.incrementScore(15);
        }
        if (gamePad.isPausePressed()) {
            soundsRepository.playSoundEffect("pause_sound.wav");
            super.pause(gamePad);
            soundsRepository.playSoundEffect("pause_sound.wav");
        }
    }

    private void checkToSwitchMusic() {
        if (camera.getxOffset() <= -5920) {
            if (!isBossFight) {
                musicPlayer.playBossMusic();
            }
            isBossFight = true;
        }
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.translate(camera.getxOffset());
        level.draw(buffer);
        leftBorder.draw(buffer);
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
        }
        ArrayList<StaticEntity> killedElements = new ArrayList<>();
        checkEntitiesCollisions(killedElements);
        killUnwantedEntities(killedElements);
    }

    private void checkEntitiesCollisions(ArrayList<StaticEntity> killedElements) {
        for (Alien alien : aliens) {
            for (Bullet bullet : bullets) {
                if (bullet.needToDeleteBullet() || bullet.collisionBoundIntersectWith(level.getEggs())) {
                    killedElements.add(bullet);
                }
                if (bullet.collisionBoundIntersectWith(alien)) {
                    killedElements.add(bullet);
                    alien.decrementHealth();
                    if (alien.isDead) {
                        killedElements.add(alien);
                    }
                }
            }
            if (player.collisionBoundIntersectWith(alien) || player.collisionBoundIntersectWith(leftBorder)) {
                player.setDead(true);
            }
        }
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
}
