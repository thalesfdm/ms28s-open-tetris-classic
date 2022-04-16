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

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static TetrisPanel panel = new TetrisPanel();
    public static Image icon;
    public static Board board = new Board();

    final JRadioButtonMenuItem volume0;
    final JRadioButtonMenuItem volume20;
    final JRadioButtonMenuItem volume40;
    final JRadioButtonMenuItem volume60;
    final JRadioButtonMenuItem volume80;
    final JRadioButtonMenuItem volume100;

    public Frame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        icon = new ImageIcon("graphics/pieces/6.png").getImage();
        final ControlsWindow cont = new ControlsWindow();
        JMenuBar menuBar;
        JMenu menu, sounds, music, volume, about;
        JMenuItem menuItem, pauseItem, exitItem, controls, aboutItem;
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
        aTheme.setSelected(true);

        bTheme = new JRadioButtonMenuItem("B Theme");
        cTheme = new JRadioButtonMenuItem("C Theme");

        volume0 = new JRadioButtonMenuItem("0%");
        volume20 = new JRadioButtonMenuItem("20%");
        volume40 = new JRadioButtonMenuItem("40%");
        volume60 = new JRadioButtonMenuItem("60%");
        volume60.setSelected(true);
        volume80 = new JRadioButtonMenuItem("80%");
        volume100 = new JRadioButtonMenuItem("100%");

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
        menuItem = new JMenuItem("New Game",
                KeyEvent.VK_T);
        pauseItem = new JMenuItem("Pause Game",
                KeyEvent.VK_T);
        exitItem = new JMenuItem("Exit Game",
                KeyEvent.VK_T);
        controls = new JMenuItem("Controls...",
                KeyEvent.VK_T);
        sounds.addSeparator();

        aboutItem = new JMenuItem("About...");

        controls.addActionListener((ActionEvent e) -> {
            TetrisPanel.turn.play();
            cont.setVisible(true);
        });

        aTheme.addActionListener((ActionEvent e) -> {
            bTheme.setSelected(false);
            cTheme.setSelected(false);
            aTheme.setSelected(true);
            TetrisPanel.bTheme.stop();
            TetrisPanel.cTheme.stop();
            TetrisPanel.aTheme.play(true);
            TetrisPanel.turn.play();
        });

        bTheme.addActionListener((ActionEvent e) -> {
            aTheme.setSelected(false);
            cTheme.setSelected(false);
            bTheme.setSelected(true);
            TetrisPanel.aTheme.stop();
            TetrisPanel.cTheme.stop();
            TetrisPanel.bTheme.play(true);
            TetrisPanel.turn.play();
        });

        cTheme.addActionListener((ActionEvent e) -> {
            aTheme.setSelected(false);
            bTheme.setSelected(false);
            cTheme.setSelected(true);
            TetrisPanel.aTheme.stop();
            TetrisPanel.bTheme.stop();
            TetrisPanel.cTheme.play(true);
            TetrisPanel.turn.play();
        });
        exitItem.addActionListener((ActionEvent e) -> {
            TetrisPanel.turn.play();
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

        pauseItem.addActionListener((ActionEvent e) -> {
            TetrisPanel.turn.play();
            if (!panel.lose) {
                if (false == TetrisPanel.pause) {
                    TetrisPanel.pause = true;
                    board.timer.stop();
                } else {
                    TetrisPanel.pause = false;
                    board.timer.start();
                }
            }
        });

        menuItem.addActionListener((ActionEvent e) -> {
            TetrisPanel.turn.play();

            if (aTheme.isSelected() == true) {
                TetrisPanel.aTheme.play(true);
            }
            if (bTheme.isSelected() == true) {
                TetrisPanel.bTheme.play(true);
            }
            if (cTheme.isSelected() == true) {
                TetrisPanel.cTheme.play(true);
            }
            panel.lose = false;
            board.clearBoard();
            board.startGame();
        });

        sounds.add(controls);
        menu.add(menuItem);
        menu.add(pauseItem);
        menu.addSeparator();
        menu.add(exitItem);
        about.add(aboutItem);

        setJMenuBar(menuBar);
        setLayout(null);
        setIconImage(icon);
        setFocusable(true);
        setSize(new Dimension(800, 770));
        setTitle("Tetris");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.init(this);
        add(panel);
        panel.setBounds(0, 0, 800, 770);
        add(board);
        board.setBounds(0, 50, 400, 720);
        setLocationRelativeTo(null);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

                panel.keyboardEvent(e);
                board.keyPressed(e);

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        aboutItem.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "OpenTetris Classic v1.0-dev 08/10/2021\n\nA full open-source recreation of the 1989 hit game.\n\nOriginally developed by Kyle Bredenkamp.\n\nRefactored and improved by:\nPedro G. K. Bertella\nJailson L. Panizzon\nArthur R. P. So.\n\nGitHub\nhttps://github.com/pedrobertella/OpenTetrisClassic\n", "About OpenTetris Classic", JOptionPane.INFORMATION_MESSAGE);
        });

    }

    public void setVolumeFromMenu(double val) {
        volume40.setSelected(false);
        volume60.setSelected(false);
        volume80.setSelected(false);
        volume20.setSelected(false);
        volume100.setSelected(false);
        volume0.setSelected(false);
        TinySound.setGlobalVolume(val);
    }

    public static void main(String[] args) {

        Frame frame = new Frame();

        frame.setVisible(true);

    }

}
