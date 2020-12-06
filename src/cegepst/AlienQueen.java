package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;

import java.util.ArrayList;

public class AlienQueen extends Alien {

    private final SoundEffectPlayer soundEffectPlayer;
    private final Wings wings;
    private final LeftBorder leftBorder;
    private final ArrayList<AlienBullet> alienBullets;
    private Player player;
    private boolean attacking;
    private boolean spawning;

    public AlienQueen(LeftBorder leftBorder, Player player) {
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
        attack();
    }

    private void attack() {
        for (int i = 0, lastY = this.y; i < 5; i++, lastY += 40) {
            alienBullets.add(new AlienBullet(this, this.x, lastY));
        }
    }

    @Override
    public void update() {
        super.update();
        cycleFrames();
        wings.update();
        for (AlienBullet bullet : alienBullets) {
            bullet.update();
        }
        for (AlienBullet bullet : alienBullets) {
            if (bullet.collisionBoundIntersectWith(leftBorder)) {
                alienBullets.remove(bullet);
            }
        }
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
        teleport(600, 200);
        wings.spawn();
        soundEffectPlayer.playlAlienSoundEffect("queen_spawn.wav");
        // GameTime.waitSeconds(8);
        soundEffectPlayer.playlAlienSoundEffect("queen_roar.wav");
    }

    @Override
    public void decrementHealth() {
        this.nbrLives--;
        if (this.nbrLives == 0) {
            this.isDead = true;
        }
    }
}
