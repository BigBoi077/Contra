package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.entity.ControllableEntity;
import cegepst.engine.entity.UpdatableEntity;

public class Camera extends UpdatableEntity {

    private final ControllableEntity entity;
    private int xOffset;

    public Camera(ControllableEntity entity, int xOffset) {
        this.entity = entity;
        this.xOffset = xOffset;
    }

    @Override
    public void update() {
        xOffset = -entity.getX();
    }

    public int getxOffset() {
        return xOffset;
    }

    @Override
    public void draw(Buffer buffer) {
    }
}
