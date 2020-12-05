package cegepst.engine;

import java.awt.event.KeyListener;

public abstract class Game {

    private final RenderingEngine renderingEngine;
    private GameTime gameTime;
    protected boolean playing = true;
    protected int score;

    public Game() {
        renderingEngine = RenderingEngine.getInstance();
    }

    public abstract void initialize();

    public abstract void conclude();

    public abstract void update();

    public abstract void draw(Buffer buffer);

    public void start() {
        initialize();
        run();
        conclude();
    }

    public void stop() {
        playing = false;
    }

    public void addKeyListener(KeyListener listener) {
        renderingEngine.addInputListener(listener);
    }

    private void run() {
        renderingEngine.start();
        gameTime = new GameTime();
        while (playing) {
            update();
            draw(renderingEngine.getRenderingBuffer());
            renderingEngine.renderBufferOnScreen();
            gameTime.synchronize();
        }
        renderingEngine.stop();
    }

    protected void incrementScore(int scoreAdd) {
        this.score += scoreAdd;
    }
}
