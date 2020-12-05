package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.entity.UpdatableEntity;

public class Camera extends UpdatableEntity {

    private int xOffset;

    public Camera(int xOffset) {
        this.xOffset = xOffset;
    }

    @Override
    public void update() {
        xOffset--;
    }

    public int getxOffset() {
        return xOffset;
    }

    @Override
    public void draw(Buffer buffer) {
    }
}
