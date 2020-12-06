package cegepst;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffectPlayer {

    public void playSoundEffect(String soundEffectName) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream("sounds/effects/" + soundEffectName));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playlAlienSoundEffect(String soundEffectName) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream("sounds/ennemy/" + soundEffectName));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playPlayerSoundEffect(String soundEffectName) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream("sounds/player/" + soundEffectName));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
