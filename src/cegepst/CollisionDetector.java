package cegepst;

import cegepst.engine.CollidableRepository;
import cegepst.engine.entity.StaticEntity;

import java.util.ArrayList;

public class CollisionDetector {

    private final ContraGame contraGame;
    private final SoundEffectPlayer soundEffectPlayer;
    private final ArrayList<StaticEntity> killedElements;

    public CollisionDetector(ContraGame contraGame) {
        this.contraGame = contraGame;
        this.killedElements = new ArrayList<>();
        this.soundEffectPlayer = new SoundEffectPlayer();
    }

    public void checkCollisions(ArrayList<Alien> aliens, ArrayList<Bullet> bullets) {
        Blockade eggs = contraGame.getLevel().getEggs();
        LeftBorder leftBorder = contraGame.getLeftBorder();
        Player player = contraGame.getPlayer();
        for (Alien alien : aliens) {
            for (Bullet bullet : bullets) {
                if (bullet.needToDeleteBullet() || bullet.collisionBoundIntersectWith(eggs) || bullet.collisionBoundIntersectWith(leftBorder)) {
                    killedElements.add(bullet);
                }
                if (bullet.collisionBoundIntersectWith(alien)) {
                    killedElements.add(bullet);
                    if (alien instanceof Runner) {
                        soundEffectPlayer.playSoundEffect("runner_pre_death.wav", "enemy");
                    }
                    manageAlienDeath(alien);
                }
            }
            if (player.collisionBoundIntersectWith(leftBorder) || player.collisionBoundIntersectWith(alien)) {
                contraGame.killPlayer();
            }
        }
        killUnwantedEntities(aliens, bullets);
    }

    private void manageAlienDeath(Alien alien) {
        contraGame.incrementScore(50);
        alien.decrementHealth();
        if (alien.nbrLives <= 0) {
            if (alien instanceof Crawler) {
                soundEffectPlayer.playSoundEffect("crawler_death.wav", "enemy");
                killedElements.add(alien);
            }
            alien.setIsDead(true);
            CollidableRepository.getInstance().unregisterEntity(alien);
        }
        if (alien.deathCooldownFinished()) {
            if (alien instanceof Runner) {
                soundEffectPlayer.playSoundEffect("runner_death.wav", "enemy");
            }
            killedElements.add(alien);
        }
    }

    public void checkAlienBulletCollision(ArrayList<AlienBullet> alienBullets, ArrayList<Bullet> bullets, Player player) {
        LeftBorder leftBorder = contraGame.getLeftBorder();
        for (AlienBullet alienBullet : alienBullets) {
            for (Bullet bullet : bullets) {
                if (alienBullet.collisionBoundIntersectWith(leftBorder)) {
                    killedElements.add(alienBullet);
                }
                if (alienBullet.collisionBoundIntersectWith(player)) {
                    if (contraGame.killPlayer()) return;
                }
                if (alienBullet.collisionBoundIntersectWith(bullet) || alienBullet.collisionBoundIntersectWith(player)) {
                    killedElements.add(alienBullet);
                }
            }
        }
        killUnwantedEntities(alienBullets);
    }

    private void killUnwantedEntities(ArrayList<AlienBullet> alienBullets) {
        for (StaticEntity killedElement : killedElements) {
            if (killedElement instanceof AlienBullet) {
                alienBullets.remove(killedElement);
            }
            CollidableRepository.getInstance().unregisterEntity(killedElement);
        }
    }

    public void killUnwantedEntities(ArrayList<Alien> aliens, ArrayList<Bullet> bullets) {
        for (StaticEntity killedElement : killedElements) {
            if (aliens != null && killedElement instanceof Alien) {
                aliens.remove(killedElement);
            } else if (killedElement instanceof Bullet) {
                bullets.remove(killedElement);
            }
            CollidableRepository.getInstance().unregisterEntity(killedElement);
        }
    }

    public void checkQueenCollisions(ArrayList<Bullet> bullets, AlienQueen queen, Player player) {
        if (player.collisionBoundIntersectWith(queen)) {
            contraGame.killPlayer();
        }
        for (Bullet bullet : bullets) {
            if (bullet.collisionBoundIntersectWith(queen)) {
                killedElements.add(bullet);
                queen.decrementHealth();
                if (queen.nbrLives <= 0) {
                    queen.playDeathSound();
                    contraGame.isWinner();
                    contraGame.conclude();
                }
            }
        }
        killUnwantedEntities(null, bullets);
    }
}
