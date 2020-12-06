package cegepst;

import cegepst.engine.Buffer;

public class Wings extends Alien {

    private final Alien queen;

    public Wings(Alien queen) {
        this.queen = queen;
        animator = new Animator(this);
        initFrames();
    }

    @Override
    public void update() {
        cycleFrames();
    }

    @Override
    public void draw(Buffer buffer) {
        animator.drawCurrentAnimation(mainFrames, buffer, 0);
    }

    @Override
    public void initFrames() {
        mainFrames = AlienTextures.getAlienQueenWingFrames();
    }

    @Override
    public void cycleFrames() {
        animator.cycleFrames(mainFrames);
    }

    @Override
    public boolean nearPlayer() {
        return false;
    }

    @Override
    public void spawn() {
        teleport(queen.getX() + 100, queen.getY() - 200);
    }

    @Override
    public void decrementHealth() {

    }
}
