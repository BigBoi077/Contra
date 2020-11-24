package cegepst;

import cegepst.engine.Buffer;

import java.awt.*;
import java.util.ArrayList;

public class World {

    private static final String LEVEL_PATH = "images/AlienLevelResized.png";
    private Image background;
    private ArrayList<Blockade> worldBorders;

    public World() {
        loadBackground();
        worldBorders = new ArrayList<>();
        createBorders();
    }

    public void draw(Buffer buffer) {
        buffer.drawImage(background, 0, 0);
        for (Blockade blockade : worldBorders) {
            blockade.draw(buffer);
        }
    }

    private void createBorders() {
        addBlockade(5, 645, 0, 0);
        addBlockade(6720, 200, 0, 435);
        addBlockade(5, 645, 6700, 0);
    }

    private void addBlockade(int width, int height, int x, int y) {
        Blockade blockade = new Blockade();
        blockade.setDimension(width, height);
        blockade.teleport(x, y);
        worldBorders.add(blockade);
    }

    private void loadBackground() {
        ImagesReader imagesReader = new ImagesReader();
        background = imagesReader.readImage(LEVEL_PATH);
    }

    public ArrayList<Blockade> getWorldBorders() {
        return worldBorders;
    }
}
