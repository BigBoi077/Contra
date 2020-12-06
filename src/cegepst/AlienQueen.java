package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.GameTime;

public class AlienQueen extends Alien {

    private final SoundEffectPlayer soundEffectPlayer;
    private final Wings wings;
    private boolean spawning;

    public AlienQueen() {
        animator = new Animator(this);
        animator.setAnimationSpeed(10);
        wings = new Wings(this);
        soundEffectPlayer = new SoundEffectPlayer();
        nbrLives = 150;
        super.setDimension(AlienSpritesheetInfo.QUEEN_WIDTH, AlienSpritesheetInfo.QUEEN_HEIGHT);
        super.isGravityApplied = false;
        initFrames();
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void update() {
        super.update();
        cycleFrames();
        wings.update();
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
        GameTime.waitSeconds(8);
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
