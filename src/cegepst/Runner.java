package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;

public class Runner extends Alien {

    private final Player player;

    public Runner(Player player) {
        animator = new Animator(this);
        nbrLives = 3;
        super.setSpeed(4);
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
