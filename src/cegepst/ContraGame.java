package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.Game;

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

    private final Alien crawler;

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

        crawler = new Crawler(player);
        crawler.spawn();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void conclude() {

    }

    @Override
    public void update() {
        player.update();
        leftBorder.update();
        crawler.update();
        // alienSpawner.update();
        camera.update();
        if (gamePad.isQuitPressed()) {
            super.stop();
        }
        if (gamePad.isFirePressed() && player.canFire()) {
            bullets.add(player.fire());
        }
        for (Bullet bullet : bullets) {
            bullet.update();
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
        crawler.draw(buffer);

        buffer.translate(-camera.getxOffset());
        player.draw(buffer, camera.getxOffset());
    }
}
