package cegepst;

import cegepst.engine.RenderingEngine;
import cegepst.engine.controls.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private int quitKey = KeyEvent.VK_Q;
    private int jumpKey = KeyEvent.VK_SPACE;

    public GamePad() {
        super.bindKey(quitKey);
        super.bindKey(jumpKey);
        RenderingEngine.getInstance().addInputListener(this);
    }

    public boolean isJumpPressed() {
        return super.isKeyPressed(jumpKey);
    }

    public boolean isQuitPressed() {
        return super.isKeyPressed(quitKey);
    }
}
