package Tetris;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public final class SoundController {
    private static final Sound blockFall = TinySound.loadSound("sounds/blockfall.wav");
    private static final Sound combo = TinySound.loadSound("sounds/combo.wav");
    private static final Sound gameOver1 = TinySound.loadSound("sounds/gameover1.wav");
    private static final Sound gameOver2 = TinySound.loadSound("sounds/gameover2.wav");
    private static final Sound lineClear = TinySound.loadSound("sounds/lineclear.wav");
    private static final Sound move = TinySound.loadSound("sounds/move.wav");
    private static final Sound newLevel = TinySound.loadSound("sounds/newlevel.wav");
    private static final Sound turn = TinySound.loadSound("sounds/turn.wav");

    public static void playBlockFall() {
        SoundController.blockFall.play();
    }

    public static void playCombo() {
        SoundController.combo.play();
    }

    public static void playGameOver1() {
        SoundController.gameOver1.play();
    }

    public static void playGameOver2() {
        SoundController.gameOver2.play();
    }

    public static void playLineClear() {
        SoundController.lineClear.play();
    }

    public static void playMove() {
        SoundController.move.play();
    }

    public static void playNewLevel() {
        SoundController.newLevel.play();
    }

    public static void playTurn() {
        SoundController.turn.play();
    }
}
