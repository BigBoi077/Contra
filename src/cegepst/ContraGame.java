package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.Game;
import cegepst.engine.entity.StaticEntity;

import java.util.ArrayList;

public class ContraGame extends Game {

    private final Player player;
    private final GamePad gamePad;
    private final World level;
    private final LeftBorder leftBorder;
    private final Camera camera;
    private final AlienSpawner alienSpawner;
    private final ArrayList<Bullet> bullets;
    private final ArrayList<Alien> aliens;
    private AlienTextures alienTextures;

    public ContraGame() {
        gamePad = new GamePad();
        level = new World();
        player = new Player(gamePad);
        camera = new Camera(player, 0);
        leftBorder = new LeftBorder(player);
        alienSpawner = new AlienSpawner(player);
        bullets = new ArrayList<>();
        player.teleport(100, 0);
        aliens = alienSpawner.getAliensArray();
    }

    @Override
    public void initialize() {
        alienTextures = new AlienTextures();
    }

    @Override
    public void conclude() {

    }

    @Override
    public void update() {
        player.update();
        leftBorder.update();
        alienSpawner.update();
        // alienSpawner.update();
        camera.update();
        if (gamePad.isQuitPressed()) {
            super.stop();
        }
        if (gamePad.isFirePressed() && player.canFire()) {
            bullets.add(player.fire());
        }
        updateEnnemies();
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
    }

    private void updateEnnemies() {
        ArrayList<StaticEntity> killedElements = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.update();
            for (Alien alien : aliens) {
                alien.update();
                if (bullet.collisionBoundIntersectWith(alien)) {
                    killedElements.add(bullet);
                    alien.decrementHealth();
                    if (alien.isDead) {
                        killedElements.add(alien);
                    }
                }
            }
        }
        for (StaticEntity killedElement : killedElements) {
            if (killedElement instanceof Alien) {
                aliens.remove(killedElement);
            } else if (killedElement instanceof Bullet) {
                bullets.remove(killedElement);
            }
            CollidableRepository.getInstance().unregisterEntity(killedElement);
        }
    }
}
