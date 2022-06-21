package Tetris;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;

public class MusicController {
    private static final Music themeA = TinySound.loadMusic("sounds/themeA.wav");
    private static final Music themeB = TinySound.loadMusic("sounds/themeB.wav");
    private static final Music themeC = TinySound.loadMusic("sounds/themeC.wav");
    private static MusicController musicController;
    private Music current;
    private boolean isPlaying = false;

    private MusicController() {
    }

    public static MusicController getMusicController() {
        if (musicController == null) {
            musicController = new MusicController();
        }
        return musicController;
    }

    public void playThemeA() {
        stop();
        current = themeA;
        play();
    }

    public void playThemeB() {
        stop();
        current = themeB;
        play();
    }

    public void playThemeC() {
        stop();
        current = themeC;
        play();
    }

    public void stop() {
        if (isPlaying) {
            isPlaying = false;
            current.stop();
        }
    }

    public void play() {
        if (!isPlaying && current != null) {
            isPlaying = true;
            current.play(true);
        }
    }
}
