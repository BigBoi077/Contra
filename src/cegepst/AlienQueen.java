package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;

import java.util.ArrayList;
import java.util.Random;

public class AlienQueen extends Alien {

    private final SoundEffectPlayer soundEffectPlayer;
    private final Wings wings;
    private final LeftBorder leftBorder;
    private final ArrayList<AlienBullet> alienBullets;
    private final Random random;
    private int attackCooldown = 300;
    private int spawnCooldown = 480;

    public AlienQueen(LeftBorder leftBorder) {
        this.leftBorder = leftBorder;
        this.animator = new Animator(this);
        this.animator.setAnimationSpeed(10);
        this.wings = new Wings(this);
        this.soundEffectPlayer = new SoundEffectPlayer();
        this.alienBullets = new ArrayList<>();
        this.random = new Random();
        this.x = 6400;
        this.y = 200;
        this.nbrLives = 20;
        super.setDimension(AlienSpritesheetInfo.QUEEN_WIDTH, AlienSpritesheetInfo.QUEEN_HEIGHT);
        super.isGravityApplied = false;
        initFrames();
        CollidableRepository.getInstance().registerEntity(this);
    }

    private void attack() {
        soundEffectPlayer.playSoundEffect("alien_attack.wav", "enemy");
        for (int i = 0, lastY = this.y, lastX = this.x; i < 5; i++, lastY += 45, lastX += random.nextInt(20)) {
            alienBullets.add(new AlienBullet(this, lastX, lastY));
        }
    }

    @Override
    public void update() {
        super.update();
        cycleFrames();
        wings.update();
        updateAttack();
        if (isSpawning()) {
            playSpawnSequence();
        }
        for (AlienBullet bullet : alienBullets) {
            bullet.update();
        }
    }

    private void updateAttack() {
        attackCooldown--;
        if (attackCooldown <= 0) {
            attack();
            attackCooldown = new Random().nextInt(400);
        }
    }

    private void playSpawnSequence() {
        cycleFrames();
        super.update();
        moveDown();
        spawnCooldown--;
        if (spawnCooldown <= 5) {
            soundEffectPlayer.playSoundEffect("queen_roar.wav", "enemy");
        }
    }

    private boolean isSpawning() {
        return spawnCooldown >= 0;
    }

    @Override
    public void draw(Buffer buffer) {
        if (wings.animator.getCurrentFrameIndex() < 2) {
            wings.draw(buffer);
        }
        animator.drawCurrentAnimation(mainFrames, buffer, 0);
        if (wings.animator.getCurrentFrameIndex() >= 2) {
            wings.draw(buffer);
        }
        for (AlienBullet bullet : alienBullets) {
            bullet.draw(buffer);
        }
    }

    @Override
    public void initFrames() {
        mainFrames = AlienTextures.getAlienQueenFrames();
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
        teleport(6400, -200);
        wings.spawn();
        soundEffectPlayer.playSoundEffect("queen_spawn.wav", "enemy");
    }

    @Override
    public void decrementHealth() {
        this.nbrLives--;
        if (this.nbrLives == 0) {
            this.isDead = true;
        }
    }

    @Override
    public boolean deathCooldownFinished() {
        return true;
    }

    @Override
    public void setIsDead(boolean b) {

    }

    public ArrayList<AlienBullet> getAlienBullets() {
        return alienBullets;
    }

    public void playDeathSound() {
        soundEffectPlayer.playSoundEffect("queen_death.wav", "enemy");
    }
}
