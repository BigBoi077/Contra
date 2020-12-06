package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;

import java.util.ArrayList;

public class AlienQueen extends Alien {

    private final SoundEffectPlayer soundEffectPlayer;
    private final Wings wings;
    private final LeftBorder leftBorder;
    private final ArrayList<AlienBullet> alienBullets;
    private int attackCooldown = 300;
    private int spawnCooldown = 480;

    public AlienQueen(LeftBorder leftBorder) {
        this.leftBorder = leftBorder;
        this.animator = new Animator(this);
        this.animator.setAnimationSpeed(10);
        this.wings = new Wings(this);
        this.soundEffectPlayer = new SoundEffectPlayer();
        this.alienBullets = new ArrayList<>();
        this.x = 600;
        this.y = 200;
        this.nbrLives = 150;
        super.setDimension(AlienSpritesheetInfo.QUEEN_WIDTH, AlienSpritesheetInfo.QUEEN_HEIGHT);
        super.isGravityApplied = false;
        initFrames();
        CollidableRepository.getInstance().registerEntity(this);
    }

    private void attack() {
        soundEffectPlayer.playlAlienSoundEffect("alien_attack.wav");
        for (int i = 0, lastY = this.y, lastX = this.x; i < 5; i++, lastY += 40, lastX += 10) {
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
            attackCooldown = 300;
        }
    }

    private void playSpawnSequence() {
        cycleFrames();
        super.update();
        moveDown();
        spawnCooldown--;
        if (spawnCooldown <= 5) {
            soundEffectPlayer.playlAlienSoundEffect("queen_roar.wav");
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
        teleport(600, -200);
        wings.spawn();
        soundEffectPlayer.playlAlienSoundEffect("queen_spawn.wav");
    }

    @Override
    public void decrementHealth() {
        this.nbrLives--;
        if (this.nbrLives == 0) {
            this.isDead = true;
        }
    }

    public ArrayList<AlienBullet> getAlienBullets() {
        return alienBullets;
    }
}
