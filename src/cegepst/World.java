package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;

import java.awt.*;
import java.util.ArrayList;

public class World {

    private static final String LEVEL_PATH = "images/AlienLevelResized.png";
    private Image background;
    private final ArrayList<Blockade> worldBorders;

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
        addBlockade(6720, 200, 0, 435);
        addBlockade(5, 650, 6600, 0);
        addBlockade(240, 150, 3000, 370);
    }

    private void addBlockade(int width, int height, int x, int y) {
        Blockade blockade = new Blockade();
        blockade.setDimension(width, height);
        blockade.teleport(x, y);
        worldBorders.add(blockade);
        CollidableRepository.getInstance().registerEntity(blockade);
    }

    private void loadBackground() {
        ImagesReader imagesReader = new ImagesReader();
        background = imagesReader.readImage(LEVEL_PATH);
    }
}
