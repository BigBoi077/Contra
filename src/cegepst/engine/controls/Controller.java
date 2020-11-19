package cegepst.engine.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public abstract class Controller implements KeyListener {

    private final HashMap<Integer, Boolean> pressedKeys;

    public Controller() {
        pressedKeys = new HashMap<>();
    }

    protected void bindKeys(int[] keys) {
        for (int keycode : keys) {
            pressedKeys.put(keycode, false);
        }
    }

    protected void bindKey(int keycode) {
        pressedKeys.put(keycode, false);
    }

    protected void clearKeys() {
        pressedKeys.clear();
    }

    protected void removeKey(int keycode) {
        pressedKeys.remove(keycode);
    }

    protected boolean isKeyPressed(int keycode) {
        return pressedKeys.containsKey(keycode)
                && pressedKeys.get(keycode);
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (pressedKeys.containsKey(keycode)) {
            pressedKeys.put(keycode, true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (pressedKeys.containsKey(keycode)) {
            pressedKeys.put(keycode, false);
        }
    }
}
