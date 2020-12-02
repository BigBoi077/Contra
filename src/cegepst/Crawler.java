package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;

public class Crawler extends Alien {

    private final Player player;

    public Crawler(Player player) {
        animator = new Animator(this);
        nbrLives = 3;
        super.setSpeed(4);
        super.setDimension(AlienSpritesheetInfo.CRAWLER_WIDTH, AlienSpritesheetInfo.CRAWLER_HEIGHT);
        super.isGravityApplied = true;
        this.player = player;
        initFrames();
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void update() {
        if (isDead) {
            return;
        }
        super.update();
        if (player.isJumping()) {
            super.startJump();
        }
        cycleFrames();
        moveLeft();
    }

    @Override
    public void draw(Buffer buffer) {
        if (nearPlayer() && player.isJumping()) {
            animator.drawCurrentAnimation(attackFrames, buffer, 0);
        } else {
            animator.drawCurrentAnimation(mainFrames, buffer, 0);
        }
    }

    @Override
    public void initFrames() {
        mainFrames = AlienTextures.getMainCrawlerFrames();
        attackFrames = AlienTextures.getAttackCrawlerFrames();
    }

    @Override
    public void cycleFrames() {
        if (nearPlayer() && player.isJumping()) {
            Debuger.consoleLog("Attack");
            animator.cycleFrames(attackFrames);
        } else {
            animator.cycleFrames(mainFrames);
        }
    }

    @Override
    public boolean nearPlayer() {
        return x - player.getX() < 100;
    }

    @Override
    public void spawn() {
        teleport(player.getX() + 550, 0);
    }

    public void setIsDead(boolean isDead) {
        super.isDead = isDead;
    }
}
