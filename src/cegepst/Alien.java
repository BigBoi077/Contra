package cegepst;

import cegepst.engine.entity.MovableEntity;

import java.awt.*;

abstract class Alien extends MovableEntity {

    protected Animator animator;
    protected Image[] mainFrames;
    protected Image[] attackFrames;
    protected Image[] deathFrames;
    protected int nbrLives;
    protected boolean isDead;

    public abstract void initFrames();

    public abstract void cycleFrames();

    public abstract boolean nearPlayer();

    public abstract void spawn();

    public abstract void decrementHealth();

    public abstract boolean deathCooldownFinished();

    public abstract void setIsDead(boolean b);
}
