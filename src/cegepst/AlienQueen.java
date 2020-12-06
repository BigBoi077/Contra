package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;

public class AlienQueen extends Alien {

    private final SoundEffectPlayer soundEffectPlayer;
    private final Wings wings;

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
        animator.drawCurrentAnimation(mainFrames, buffer, 0);
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
