package cegepst.engine.entity;

import cegepst.engine.controls.MovementController;

public abstract class ControllableEntity extends MovableEntity {

    private MovementController controller;

    public ControllableEntity(MovementController controller) {
        this.controller = controller;
    }

    @Override
    public void update() {
        super.update();
        moveAccordingToHandler();
    }

    public void moveAccordingToHandler() {
        if (!controller.isMoving()) {
            return;
        }
        if (controller.isUpPressed()) {
            moveUp();
        } else if (controller.isDownPressed()) {
            moveDown();
        } else if (controller.isRightPressed()) {
            moveRight();
        } else if (controller.isLeftPressed()) {
            moveLeft();
        }
    }
}
