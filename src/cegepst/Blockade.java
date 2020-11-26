package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.entity.StaticEntity;

import java.awt.*;

public class Blockade extends StaticEntity {

    @Override
    public void draw(Buffer buffer) {
        if (GameSettings.DEBUG_ENABLED) {
            buffer.drawRectangle(x, y, width, height, new Color(255, 0, 0, 100));
        }
    }
}
