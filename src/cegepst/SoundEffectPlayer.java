package cegepst;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Random;

public class SoundEffectPlayer {

    private final String[] respawnQuotes = {"come_get_some.wav", "lets_attack_aggressively.wav", "lets_party.wav", "locked_and_loaded.wav"};

    public void playSoundEffect(String soundEffectName, String repertory) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream("sounds/" + repertory + "/" + soundEffectName));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playRandomRespawnSound() {
        playSoundEffect(respawnQuotes[new Random().nextInt(respawnQuotes.length)], "player");
    }
}
