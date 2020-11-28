package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.entity.UpdatableEntity;

import java.awt.*;

public class LeftBorder extends UpdatableEntity {

    private final Player player;

    public LeftBorder(Player player) {
        this.player = player;
        super.x = 0;
        super.setDimension(50, 600);
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void update() {
        if (!player.isCentered(this)) {
            centerBorder();
            Debuger.consoleLog(player.isCentered(this));
        }
    }

    @Override
    public void draw(Buffer buffer) {
        if (GameSettings.DEBUG_ENABLED) {
            buffer.drawRectangle(x, y, width, height, Color.BLUE);
        }
    }

    private void centerBorder() {
        for (; player.getX() - this.x > 400; ) {
            super.x++;
        }
    }
}
