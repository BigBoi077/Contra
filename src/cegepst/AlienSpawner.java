package cegepst;

import cegepst.engine.GameTime;

import java.util.ArrayList;
import java.util.Random;

public class AlienSpawner {

    private final Random random;
    private final Player player;
    private final ArrayList<Alien> aliens;
    private int coinToss;
    private Crawler crawler;

    public AlienSpawner(Player player) {
        this.player = player;
        this.random = new Random();
        aliens = new ArrayList<>();
    }

    public void update() {
        updateSpawnCooldown();
    }

    private void spawnAlien() {
        crawler = new Crawler(player);
        aliens.add(crawler);
        crawler.spawn();
    }

    private void updateSpawnCooldown() {
        if (canSpawn()) {
            spawnAlien();
        }
    }

    private boolean canSpawn() {
        return GameTime.getElapsedTime() % 50 == 0;
    }

    public ArrayList<Alien> getAliensArray() {
        return aliens;
    }
}
