package cegepst;

import cegepst.engine.GameTime;

import java.util.ArrayList;
import java.util.Random;

public class AlienSpawner {

    private final Random random;
    private final Player player;
    private Crawler crawler;
    private Runner runner;
    private int coinToss;
    private int leftRightRandom;
    private final ArrayList<Alien> aliens;

    public AlienSpawner(Player player) {
        this.player = player;
        this.random = new Random();
        aliens = new ArrayList<>();
    }

    public void update() {
        updateSpawnCooldown();
    }

    private void spawnAlien() {
        coinToss = random.nextInt(2);
        leftRightRandom = random.nextInt(11);
        if (coinToss == 1) {
            crawler = new Crawler(player);
            aliens.add(crawler);
            crawler.spawn(leftRightRandom);
        } else {
            runner = new Runner(player);
            aliens.add(runner);
            runner.spawn(leftRightRandom);
        }
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
