package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.entity.UpdatableEntity;

public class Camera extends UpdatableEntity {

    private Player player;
    private int xOffset;

    public Camera(Player player, int xOffset) {
        this.player = player;
        this.xOffset = xOffset;
    }

    @Override
    public void update() {
        xOffset = -player.getX();
    }

    public int getxOffset() {
        return xOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    @Override
    public void draw(Buffer buffer) {
    }
}
