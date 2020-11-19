package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.controls.Direction;
import cegepst.engine.entity.MovableEntity;

import java.awt.*;

public class Bullet extends MovableEntity {

    private Direction playerDirection;

    public Bullet(Player player) {
        super.setSpeed(5);
        super.setDimensions(4, 4);
        playerDirection = player.getDirection();
        if (playerDirection == Direction.RIGHT) {
            super.teleport(player.getX() + player.getWidth() + 1, player.getY() + 15 - 2);
        } else if (playerDirection == Direction.LEFT) {
            super.teleport(player.getX() - 9, player.getY() + 15 - 2);
        } else if (player.isCrouching()) {
            super.teleport(player.getX() - 9, player.getY() + 15 - 2);
        }
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void update() {
        super.update();
        super.move(playerDirection);
        if (x >= 820 || y >= 620) {
            CollidableRepository.getInstance().unregisterEntity(this);
        }
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectangle(x, y, width, height, new Color(230, 230, 230));
    }
}
