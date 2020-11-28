package cegepst;

import cegepst.engine.entity.MovableEntity;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract class Alien extends MovableEntity {

    protected Animator animator;
    protected SpriteReader spriteReader;
    protected ImagesReader imagesReader;
    protected BufferedImage spritesheet;
    protected Image[] mainFrames;
    protected Image[] secondaryFrames;
    protected int nbrLives;

    public abstract void initSpritesheet();

    public abstract void initFrames();

    public abstract void readSprites();

    public abstract void cycleFrames();

    public abstract boolean nearPlayer();
}
