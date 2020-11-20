package cegepst;

import cegepst.engine.RenderingEngine;
import cegepst.engine.controls.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private int quitKey = KeyEvent.VK_Q;
    private int jumpKey = KeyEvent.VK_SPACE;
    private int fireKey = KeyEvent.VK_ENTER;
    private int crouchKey = KeyEvent.VK_SHIFT;

    public GamePad() {
        super.bindKey(quitKey);
        super.bindKey(jumpKey);
        super.bindKey(fireKey);
        super.bindKey(crouchKey);
        RenderingEngine.getInstance().addInputListener(this);
    }

    public boolean isJumpPressed() {
        return super.isKeyPressed(jumpKey);
    }

    public boolean isQuitPressed() {
        return super.isKeyPressed(quitKey);
    }

    public boolean isFirePressed() {
        return super.isKeyPressed(fireKey);
    }

    public boolean isCrouchPressed() {
        return super.isKeyPressed(crouchKey);
    }
}
