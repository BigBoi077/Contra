package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.entity.MovableEntity;

import java.awt.image.BufferedImage;

public class AlienBullet extends MovableEntity {

    private final Alien queen;
    private final BufferedImage bulletFrame;

    public AlienBullet(Alien queen, int x, int y) {
        this.setDimension(20, 20);
        this.queen = queen;
        this.bulletFrame = AlienTextures.getAlienBullet();
        this.isGravityApplied = false;
        super.teleport(x, y);
        super.setSpeed(1);
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void update() {
        super.update();
        super.moveLeft();
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(bulletFrame, x, y);
    }
}
