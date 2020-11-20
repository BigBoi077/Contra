package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.Game;
import cegepst.engine.entity.StaticEntity;

import java.util.ArrayList;

public class ContraGame extends Game {

    private Player player;
    private GamePad gamePad;
    private ArrayList<Brick> bricks;
    private ArrayList<Bullet> bullets;

    public ContraGame() {
        gamePad = new GamePad();
        player = new Player(gamePad);
        player.teleport(245, 10);
        bricks = new ArrayList<>();
        bullets = new ArrayList<>();
        bricks.add(new Brick(250, 250));
        bricks.add(new Brick(400, 250));
        bricks.add(new Brick(350, 450));
        bricks.add(new Brick(200, 400));
        bricks.add(new Brick(100, 350));
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
        if (gamePad.isQuitPressed()) {
            super.stop();
        }
        if (gamePad.isFirePressed()) {
            bullets.add(player.fire());
        }
    }

    @Override
    public void draw(Buffer buffer) {
        for (StaticEntity entity : bricks) {
            entity.draw(buffer);
        }
        player.draw(buffer);
    }
}
