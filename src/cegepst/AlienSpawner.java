package cegepst;

import java.util.ArrayList;
import java.util.Random;

public class AlienSpawner {

    private final Random random;
    private final Player player;
    private Crawler crawler;
    private Runner runner;
    private int coinToss;
    private int spawnerCooldown;
    private final ArrayList<Alien> aliens;
    private boolean canSpawn = false;

    public AlienSpawner(Player player) {
        this.player = player;
        this.random = new Random();
        this.spawnerCooldown = 70;
        aliens = new ArrayList<>();
    }

    public void update() {
        if (!canSpawn) {
            spawnerCooldown--;
        }
        updateSpawnCooldown();
    }

    private void spawnAlien() {
        coinToss = random.nextInt(2);
        if (coinToss == 1) {
            crawler = new Crawler(player);
            aliens.add(crawler);
            crawler.spawn();
        } else {
            runner = new Runner(player);
            aliens.add(runner);
            runner.spawn();
        }
    }

    private void updateSpawnCooldown() {
        if (spawnerCooldown <= 0) {
            canSpawn = true;
            spawnerCooldown = 70;
        }
        if (canSpawn) {
            spawnAlien();
        }
        canSpawn = false;
    }

    public ArrayList<Alien> getAliensArray() {
        return aliens;
    }
}
