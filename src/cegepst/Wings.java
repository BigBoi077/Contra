package cegepst;

import cegepst.engine.Buffer;

public class Wings extends Alien {

    private final Alien queen;

    public Wings(Alien queen) {
        this.queen = queen;
        animator = new Animator(this);
        animator.setAnimationSpeed(15);
        initFrames();
    }

    @Override
    public void update() {
        teleport(queen.getX() + AlienSpritesheetInfo.QUEEN_WIDTH - 60, queen.getY() - 24);
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
        animator.cycleStaticFrames(mainFrames);
    }

    @Override
    public boolean nearPlayer() {
        return false;
    }

    @Override
    public void spawn() {
        teleport(queen.getX() + AlienSpritesheetInfo.QUEEN_WIDTH - 60, queen.getY() - 24);
    }

    @Override
    public void decrementHealth() {

    }

    @Override
    public boolean deathCooldownFinished() {
        return true;
    }

    @Override
    public void setIsDead(boolean b) {

    }
}
