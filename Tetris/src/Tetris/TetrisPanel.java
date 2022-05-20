package Tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kuusisto.tinysound.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Tetris.Shape.Tetrominoes;

public class TetrisPanel extends JPanel implements Runnable {

    public Thread thread = new Thread(this);
    public static Image bg;
    public static Image go;
    public static Image ps;
    public static Image we;
    public static Image[] box;
    public static Music aTheme;
    public static Music bTheme;
    public static Music cTheme;
    public static Sound turn, move, drop, line, go1, go2, linefour, newlevel;
    public static int delay;
    public static boolean losing = false;
    public BufferedImage font;
    public Shape nextPiece;
    public boolean lose = false;
    private static final long serialVersionUID = 1L;

    public TetrisPanel() {
        TinySound.init();

        nextPiece = new Shape();
        delay = 0;

        String prefix = System.getProperty("user.dir") + "/Tetris/";

        bg = new ImageIcon(prefix + "graphics/gamebackgroundgamea.png").getImage();
        go = new ImageIcon(prefix + "graphics/gameover.png").getImage();
        ps = new ImageIcon(prefix + "graphics/pause.png").getImage();
        we = new ImageIcon(prefix + "graphics/welcome.png").getImage();

        box = new Image[8];

        box[1] = new ImageIcon(prefix + "graphics/pieces/1.png").getImage();
        box[2] = new ImageIcon(prefix + "graphics/pieces/2.png").getImage();
        box[3] = new ImageIcon(prefix + "graphics/pieces/3.png").getImage();
        box[4] = new ImageIcon(prefix + "graphics/pieces/4.png").getImage();
        box[5] = new ImageIcon(prefix + "graphics/pieces/5.png").getImage();
        box[6] = new ImageIcon(prefix + "graphics/pieces/6.png").getImage();
        box[7] = new ImageIcon(prefix + "graphics/pieces/7.png").getImage();

        turn = TinySound.loadSound("sounds/turn.wav");
        move = TinySound.loadSound("sounds/move.wav");
        drop = TinySound.loadSound("sounds/blockfall.wav");
        line = TinySound.loadSound("sounds/lineclear.wav");
        go1 = TinySound.loadSound("sounds/gamemover1.wav");
        go2 = TinySound.loadSound("sounds/gamemover2.wav");
        linefour = TinySound.loadSound("sounds/4lineclear.wav");
        newlevel = TinySound.loadSound("sounds/newlevel.wav");
        aTheme = TinySound.loadMusic("sounds/themeA.wav");
        bTheme = TinySound.loadMusic("sounds/themeB.wav");
        cTheme = TinySound.loadMusic("sounds/themeC.wav");
        aTheme.play(true);
        TinySound.setGlobalVolume(0.6);

        try {
            font = ImageIO.read(Objects.requireNonNull(getClass().getResource("/graphics/font.png")));
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

        this.setBackground(new Color(100, 100, 100));
    }

    public List<BufferedImage> convert(String text) {
        List<BufferedImage> images = new ArrayList<>(25);

        for (char c : text.toCharArray()) {
            c = Character.toUpperCase(c);
            int smudge = 1;
            int offset = -1;
            if (c >= 48 && c <= 57) {
                offset = c - 48;
            } else if (c >= 65 && c <= 90) {
                offset = c - 65 + 10;
            } else if (c == 32) {
                offset = 48;
                smudge = 2;
            }

            if (offset >= 0) {
                BufferedImage sub = font.getSubimage((offset * 8) + smudge, 0, 8 - smudge, 8);
                images.add(sub);
            }
        }

        return images;
    }

    public void lose() {
        lose = true;
//        delay = 200;
    }

    public void init(Frame arg0) {
        // Just Start The Thread...
        thread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, 800, 720, null);
        Frame.board.paint(g);

        Graphics2D g2d = (Graphics2D) g.create();
        List<BufferedImage> text = convert(String.valueOf(Frame.board.score));
        int x = (105 + (55 - (text.size() * 8)) / 2);
        int y = (23);
        g2d.scale(5, 5);
        for (BufferedImage img : text) {

            g2d.drawImage(img, x, y, this);
            x += img.getWidth();
        }

        List<BufferedImage> text2 = convert(String.valueOf(Frame.board.level));
        int x2 = (112 + (40 - (text2.size() * 8)) / 2);
        int y2 = (56);
        for (BufferedImage img2 : text2) {

            g2d.drawImage(img2, x2, y2, this);
            x2 += img2.getWidth();
        }

        List<BufferedImage> text3 = convert(String.valueOf(Frame.board.numLinesRemoved));
        int x3 = (112 + (40 - (text3.size() * 8)) / 2);
        int y3 = (80);
        for (BufferedImage img3 : text3) {

            g2d.drawImage(img3, x3, y3, this);
            x3 += img3.getWidth();
        }

        g2d.dispose();

        if (Frame.board.isPaused) {
            g.drawImage(ps, 75, 0, 400, 720, null);
        }

        if (lose) {
            g.drawImage(go, 75, 0, 400, 720, null);
        }

        if (!lose && !Frame.board.isPaused && !Frame.board.isStarted) {
            g.drawImage(we, 75, 0, 400, 720, null);
        }

        paintPiece(g, box[1], Tetrominoes.LineShape);
        paintPiece(g, box[2], Tetrominoes.MirroredLShape);
        paintPiece(g, box[3], Tetrominoes.LShape);
        paintPiece(g, box[4], Tetrominoes.SquareShape);
        paintPiece(g, box[5], Tetrominoes.SShape);
        paintPiece(g, box[6], Tetrominoes.TShape);
        paintPiece(g, box[7], Tetrominoes.ZShape);
    }

    private void paintPiece(Graphics g, Image img, Tetrominoes shape) {
        if (!lose && nextPiece.getShape() == shape) {
            g.drawImage(img, (595 + ((170 - (img.getWidth(null) * 5)) / 2)),
                    (515 + ((170 - (img.getHeight(null) * 5)) / 2)), img.getWidth(null) * 5,
                    img.getHeight(null) * 5, null);
        }
    }

    @Override
    public void run() {
        while (true) {
//            if (losing) {
//                if (delay > 0) {
//                    delay--;
//                } else {
//                    go2.play();
//                    lose = true;
//                    losing = false;
//                }
//            }

            repaint();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        }
    }
}
