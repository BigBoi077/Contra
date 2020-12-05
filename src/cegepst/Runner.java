package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;

public class Runner extends Alien {

    private final Player player;

    public Runner(Player player) {
        animator = new Animator(this);
        nbrLives = 1;
        super.setSpeed(4);
        super.setDimensions(AlienSpritesheetInfo.RUNNER_WIDTH, AlienSpritesheetInfo.RUNNER_HEIGHT);
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
        if (player.isCrouching()) {
            super.setSpeed(7);
        }
        cycleFrames();
        moveLeft();
    }

    @Override
    public void draw(Buffer buffer) {
        if (isDead) {
            animator.drawCurrentAnimation(deathFrames, buffer, 0);
            return;
        }
        if (nearPlayer() && player.isCrouching()) {
            animator.drawCurrentAnimation(attackFrames, buffer, 0);
        } else {
            animator.drawCurrentAnimation(mainFrames, buffer, 0);
        }
    }

    @Override
    public void initFrames() {
        mainFrames = AlienTextures.getMainRunnerFrames();
        attackFrames = AlienTextures.getAttackRunnerFrames();
        deathFrames = AlienTextures.getDeathRunnerFrames();
    }

    @Override
    public void cycleFrames() {
        if (isDead) {
            return;
        }
        if (nearPlayer() && player.isJumping()) {
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
    public void spawn(int leftRightRandom) {
        teleport(player.getX() + 1000, 200);
    }

    @Override
    public void decrementHealth() {
        this.nbrLives--;
        if (this.nbrLives == 0) {
            this.isDead = true;
        }
    }

    public void setIsDead(boolean isDead) {
        super.isDead = isDead;
    }
}
