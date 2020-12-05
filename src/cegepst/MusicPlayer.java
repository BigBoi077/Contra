package cegepst;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;

public class MusicPlayer {

    private static final String ALIEN_MUSIC_PATH = "resources/sounds/songs/alien_zone_music.mp3";
    private static final String BOSS_MUSIC_PATH = "resources/sounds/songs/final_boss_fight.mp3";
    private Player player;

    public void start() {
        loadMusic(ALIEN_MUSIC_PATH);
    }

    public void playBossMusic() {
        player.close();
        loadMusic(BOSS_MUSIC_PATH);
    }

    private void loadMusic(String musicPath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(musicPath);
            player = new Player(fileInputStream);
            startSong();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSong() {
        Runnable runnable = () -> {
            try {
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
