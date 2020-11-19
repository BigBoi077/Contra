package cegepst.engine;

import cegepst.engine.entity.StaticEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollidableRepository implements Iterable<StaticEntity> {

    private static CollidableRepository instance;
    private final List<StaticEntity> registeredCollidableEntities;

    public static CollidableRepository getInstance() {
        if (instance == null) {
            instance = new CollidableRepository();
        }
        return instance;
    }

    public void registerEntity(StaticEntity entity) {
        registeredCollidableEntities.add(entity);
    }

    public void registerEntities(Collection<StaticEntity> entities) {
        registeredCollidableEntities.addAll(entities);
    }

    public void unregisterEntity(StaticEntity entity) {
        registeredCollidableEntities.remove(entity);
    }

    public int count() {
        return registeredCollidableEntities.size();
    }

    private CollidableRepository() {
        registeredCollidableEntities = new ArrayList<>();
    }

    @Override
    public Iterator<StaticEntity> iterator() {
        return registeredCollidableEntities.iterator();
    }
}
