package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.Game;
import cegepst.engine.entity.StaticEntity;

import java.util.ArrayList;

public class ContraGame extends Game {

    private Player player;
    private GamePad gamePad;
    private World level;
    private LeftBorder leftBorder;
    private Camera camera;
    private ArrayList<Bullet> bullets;

    public ContraGame() {
        gamePad = new GamePad();
        level = new World();
        player = new Player(gamePad);
        camera = new Camera(player, 0);
        bullets = new ArrayList<>();
        player.teleport(100, 0);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void conclude() {

    }

    @Override
    public void update() {
        camera.update();
        player.update();
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
        for (StaticEntity entity : bullets) {
            entity.draw(buffer);
        }
        buffer.translate(-camera.getxOffset());
        player.draw(buffer);
    }
}
