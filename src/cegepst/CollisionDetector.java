package cegepst;

import cegepst.engine.entity.StaticEntity;

import java.util.ArrayList;

public class CollisionDetector {

    private final ArrayList<StaticEntity> killedElements;

    public CollisionDetector() {
        killedElements = new ArrayList<>();
    }

    public void checkCollisions(ArrayList<Alien> aliens, ArrayList<Bullet> bullets) {

        killUnwantedEntities();
    }

    public void killUnwantedEntities() {
    }
}
