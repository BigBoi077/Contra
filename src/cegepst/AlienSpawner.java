package cegepst;

import java.util.Random;

public class AlienSpawner {

    private final Random random;
    private final Player player;
    private int spawnerCooldown = 20;
    private int coinToss;

    public AlienSpawner(Player player) {
        this.player = player;
        this.random = new Random();
    }

    public void update() {
        updateSpawnCooldown();
        spawnAlien();
    }

    private void spawnAlien() {
        coinToss = random.nextInt(2);
        if (coinToss == 1) {
            new Crawler(player).spawn();
        }
    }

    private void updateSpawnCooldown() {
        spawnerCooldown--;
        if (spawnerCooldown <= 0) {
            spawnerCooldown = 0;
        }
    }
}
