package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.controls.Direction;
import cegepst.engine.entity.MovableEntity;

import java.awt.*;

public class Bullet extends MovableEntity {

    private final Direction playerDirection;
    private final Player player;

    public Bullet(Player player) {
        this.player = player;
        super.setSpeed(15);
        super.isGravityApplied = false;
        super.setDimensions(4, 4);
        playerDirection = player.getDirection();
        if (player.isCrouching() && playerDirection == Direction.RIGHT) {
            super.teleport(player.getX() + player.getWidth() + 1, player.getY() + player.getWidth() / 2 + 28);
        } else if (player.isCrouching() && playerDirection == Direction.LEFT) {
            super.teleport(player.getX() - 9, player.getY() + player.getWidth() / 2 - 25);
        } else if (playerDirection == Direction.RIGHT) {
            super.teleport(player.getX() + player.getWidth() + 1, player.getY() + player.getWidth() / 2 - 9);
        } else if (playerDirection == Direction.LEFT) {
            super.teleport(player.getX() - 9, player.getY() + player.getWidth() / 2 - 9);
        }
        CollidableRepository.getInstance().registerEntity(this);
    }

    public boolean needToDeleteBullet() {
        return x > player.getX() + 1000 || x < player.getX() - 1000;
    }

    @Override
    public void update() {
        super.update();
        super.move(playerDirection);
        if (needToDeleteBullet()) {
            CollidableRepository.getInstance().unregisterEntity(this);
        }
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectangle(x + 2, y, 2, 2, new Color(224, 224, 209));
        buffer.drawRectangle(x, y - 2, 2, 2, new Color(224, 224, 209));
        buffer.drawRectangle(x, y, 2, 2, new Color(224, 224, 209));
        buffer.drawRectangle(x - 2, y, 2, 2, new Color(224, 224, 209));
        buffer.drawRectangle(x, y + 2, 2, 2, new Color(224, 224, 209));
    }
}
