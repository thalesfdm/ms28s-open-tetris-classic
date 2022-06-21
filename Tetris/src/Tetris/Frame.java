package Tetris;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

import javax.swing.*;

import kuusisto.tinysound.TinySound;

public class Frame extends JFrame {
    public static TetrisPanel tetrisPanel = new TetrisPanel();
    public static Image icon;
    public static Board board = new Board();

    final JRadioButtonMenuItem volume0;
    final JRadioButtonMenuItem volume20;
    final JRadioButtonMenuItem volume40;
    final JRadioButtonMenuItem volume60;
    final JRadioButtonMenuItem volume80;
    final JRadioButtonMenuItem volume100;

    private static final long serialVersionUID = 1L;

    public Frame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
        }

        icon = new ImageIcon("graphics/pieces/6.png").getImage();
        final ControlsWindow cont = new ControlsWindow();
        JMenuBar menuBar;
        JMenu menu, sounds, music, volume, about;
        JMenuItem newGameMenuItem, pauseGameMenuItem, exitGameMenuItem, controlsMenuItem, aboutMenuItem;
        final JRadioButtonMenuItem aTheme;
        final JRadioButtonMenuItem bTheme;
        final JRadioButtonMenuItem cTheme;

        JCheckBoxMenuItem snd, msc;

        menuBar = new JMenuBar();
        menu = new JMenu("Game");
        sounds = new JMenu("Options");
        music = new JMenu("Music");
        volume = new JMenu("Volume");
        about = new JMenu("About");
        menuBar.add(menu);
        menuBar.add(sounds);
        menuBar.add(about);
        sounds.add(music);
        sounds.add(volume);

        aTheme = new JRadioButtonMenuItem("A Theme");
        bTheme = new JRadioButtonMenuItem("B Theme");
        cTheme = new JRadioButtonMenuItem("C Theme");

        // Default music theme
        aTheme.setSelected(true);
        MusicController.playThemeA();

        volume0 = new JRadioButtonMenuItem("0%");
        volume20 = new JRadioButtonMenuItem("20%");
        volume40 = new JRadioButtonMenuItem("40%");
        volume60 = new JRadioButtonMenuItem("60%");
        volume80 = new JRadioButtonMenuItem("80%");
        volume100 = new JRadioButtonMenuItem("100%");

        // Default volume
        volume60.setSelected(true);

        music.add(aTheme);
        music.add(bTheme);
        music.add(cTheme);

        volume.add(volume0);
        volume.add(volume20);
        volume.add(volume40);
        volume.add(volume60);
        volume.add(volume80);
        volume.add(volume100);

        snd = new JCheckBoxMenuItem("Sounds");
        snd.setSelected(true);
        msc = new JCheckBoxMenuItem("Music");
        msc.setSelected(true);
        snd.setEnabled(false);
        msc.setEnabled(false);

        sounds.add(snd);
        sounds.add(msc);

        newGameMenuItem = new JMenuItem("New Game", KeyEvent.VK_T);
        pauseGameMenuItem = new JMenuItem("Pause Game", KeyEvent.VK_T);
        exitGameMenuItem = new JMenuItem("Exit Game", KeyEvent.VK_T);
        controlsMenuItem = new JMenuItem("Controls...", KeyEvent.VK_T);

        sounds.addSeparator();

        aboutMenuItem = new JMenuItem("About...");

        controlsMenuItem.addActionListener((ActionEvent e) -> {
            SoundController.playTurn();
            cont.setVisible(true);
        });

        aTheme.addActionListener((ActionEvent e) -> {
            aTheme.setSelected(true);
            bTheme.setSelected(false);
            cTheme.setSelected(false);
            MusicController.playThemeA();
            SoundController.playTurn();
        });

        bTheme.addActionListener((ActionEvent e) -> {
            aTheme.setSelected(false);
            bTheme.setSelected(true);
            cTheme.setSelected(false);
            MusicController.playThemeB();
            SoundController.playTurn();
        });

        cTheme.addActionListener((ActionEvent e) -> {
            aTheme.setSelected(false);
            bTheme.setSelected(false);
            cTheme.setSelected(true);
            MusicController.playThemeC();
            SoundController.playTurn();
        });

        exitGameMenuItem.addActionListener((ActionEvent e) -> {
            SoundController.playTurn();
            System.exit(1);
        });

        volume0.addActionListener((ActionEvent e) -> {
            setVolumeFromMenu(0);
            volume0.setSelected(true);
        });

        volume20.addActionListener((ActionEvent e) -> {
            setVolumeFromMenu(0.2);
            volume20.setSelected(true);
        });

        volume40.addActionListener((ActionEvent e) -> {
            setVolumeFromMenu(0.4);
            volume40.setSelected(true);
        });

        volume60.addActionListener((ActionEvent e) -> {
            setVolumeFromMenu(0.6);
            volume60.setSelected(true);
        });

        volume80.addActionListener((ActionEvent e) -> {
            setVolumeFromMenu(0.8);
            volume80.setSelected(true);
        });

        volume100.addActionListener((ActionEvent e) -> {
            setVolumeFromMenu(1.0);
            volume100.setSelected(true);
        });

        pauseGameMenuItem.addActionListener((ActionEvent e) -> {
            SoundController.playTurn();
            if (!tetrisPanel.lose) {
                if (!board.isPaused) {
                    board.isPaused = true;
                    board.timer.stop();
                } else {
                    board.isPaused = false;
                    board.timer.start();
                }
            }
        });

        newGameMenuItem.addActionListener((ActionEvent e) -> {
            SoundController.playTurn();
            MusicController.play();
            tetrisPanel.lose = false;
            board.clearBoard();
            board.startGame();
        });

        sounds.add(controlsMenuItem);
        menu.add(newGameMenuItem);
        menu.add(pauseGameMenuItem);
        menu.addSeparator();
        menu.add(exitGameMenuItem);
        about.add(aboutMenuItem);

        setJMenuBar(menuBar);
        setLayout(null);
        setIconImage(icon);
        setFocusable(true);
        setSize(new Dimension(800, 770));
        setTitle("Tetris");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        tetrisPanel.init();
        add(tetrisPanel);
        tetrisPanel.setBounds(0, 0, 800, 770);
        add(board);
        board.setBounds(0, 50, 400, 720);
        setLocationRelativeTo(null);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                board.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        aboutMenuItem.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(
                    this,
                    "OpenTetris Classic v1.0-dev 08/10/2021\n\n" +
                            "A full open-source recreation of the 1989 hit game.\n\n" +
                            "Originally developed by Kyle Bredenkamp.\n\n" +
                            "Refactored and improved by:\nPedro G. K. Bertella\nJailson L. Panizzon\nArthur R. P. So.\n\n" +
                            "GitHub:\nhttps://github.com/pedrobertella/OpenTetrisClassic\n\n" +
                            "Refactored and improved (again) by:\nThales F. Dal Molim\nJose O. Bremm\nLuis V. da S. S. Dutra.\n\n" +
                            "GitHub:\nhttps://github.com/thalesfdm/ms28s-open-tetris-classic\n",
                    "About OpenTetris Classic",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    public void setVolumeFromMenu(double val) {
        volume0.setSelected(false);
        volume20.setSelected(false);
        volume40.setSelected(false);
        volume60.setSelected(false);
        volume80.setSelected(false);
        volume100.setSelected(false);
        TinySound.setGlobalVolume(val);
    }

    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setVisible(true);
    }
}
