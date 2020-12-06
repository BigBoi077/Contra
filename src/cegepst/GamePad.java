package cegepst;

import cegepst.engine.RenderingEngine;
import cegepst.engine.controls.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private final int quitKey = KeyEvent.VK_Q;
    private final int jumpKey = KeyEvent.VK_SPACE;
    private final int fireKey = KeyEvent.VK_ENTER;
    private final int crouchKey = KeyEvent.VK_SHIFT;
    private final int pauseKey = KeyEvent.VK_P;
    private final int resumeKey = KeyEvent.VK_R;

    public GamePad() {
        super.bindKey(quitKey);
        super.bindKey(jumpKey);
        super.bindKey(fireKey);
        super.bindKey(crouchKey);
        super.bindKey(pauseKey);
        super.bindKey(resumeKey);
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

    public boolean isPausePressed() {
        return super.isKeyPressed(pauseKey);
    }

    public boolean isCrouchPressed() {
        return super.isKeyPressed(crouchKey);
    }

    public boolean isResumePressed() {
        return super.isKeyPressed(resumeKey);
    }
}
