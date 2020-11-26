package cegepst.engine.controls;

import java.awt.event.KeyEvent;

public class MovementController extends Controller {

    private int upKey = KeyEvent.VK_W;
    private int downKey = KeyEvent.VK_S;
    private int rightKey = KeyEvent.VK_D;
    private int leftKey = KeyEvent.VK_A;
    private final int crouchKey = KeyEvent.VK_SHIFT;

    public MovementController() {
        int[] pressedKeys = {upKey, downKey, rightKey, leftKey, crouchKey};
        bindKeys(pressedKeys);
    }

    public boolean isLeftPressed() {
        return super.isKeyPressed(leftKey);
    }

    public boolean isRightPressed() {
        return super.isKeyPressed(rightKey);
    }

    public boolean isUpPressed() {
        return super.isKeyPressed(upKey);
    }

    public boolean isDownPressed() {
        return super.isKeyPressed(downKey);
    }

    public boolean isCrouchPressed() {
        return super.isKeyPressed(crouchKey);
    }

    public boolean isMoving() {
        return isLeftPressed() || isRightPressed()
                || isUpPressed() || isDownPressed();
    }

    public void setUpKey(int upKey) {
        super.removeKey(this.upKey);
        super.bindKey(upKey);
        this.upKey = upKey;
    }

    public void setDownKey(int downKey) {
        super.removeKey(this.downKey);
        super.bindKey(downKey);
        this.downKey = downKey;
    }

    public void setRightKey(int rightKey) {
        super.removeKey(this.rightKey);
        super.bindKey(rightKey);
        this.rightKey = rightKey;
    }

    public void setLeftKey(int leftKey) {
        super.removeKey(this.leftKey);
        super.bindKey(leftKey);
        this.leftKey = leftKey;
    }
}
