package Tetris;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;

public final class MusicController {
    private static final Music themeA = TinySound.loadMusic("sounds/themeA.wav");
    private static final Music themeB = TinySound.loadMusic("sounds/themeB.wav");
    private static final Music themeC = TinySound.loadMusic("sounds/themeC.wav");
    private static Music currentMusic;
    private static boolean isPlaying = false;

    public static void playThemeA() {
        stop();
        currentMusic = themeA;
        play();
    }

    public static void playThemeB() {
        stop();
        currentMusic = themeB;
        play();
    }

    public static void playThemeC() {
        stop();
        currentMusic = themeC;
        play();
    }

    public static void stop() {
        if (isPlaying) {
            isPlaying = false;
            currentMusic.stop();
        }
    }

    public static void play() {
        if (!isPlaying && currentMusic != null) {
            isPlaying = true;
            currentMusic.play(true);
        }
    }
}
