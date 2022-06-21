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
    public static int delay;
    public BufferedImage font;
    public Shape nextPiece;
    public boolean lose = false;
    private static final long serialVersionUID = 1L;
    private Graphics2D g2d;

    public TetrisPanel() {
        TinySound.init();
        TinySound.setGlobalVolume(0.6);

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

    public void init() {
        // Just Start The Thread...
        thread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, 800, 720, null);
        Frame.board.paint(g);

        g2d = (Graphics2D) g.create();
        g2d.scale(5, 5);

        drawInfoElement(Frame.board.score, 105, 55, 23);
        drawInfoElement(Frame.board.level, 112, 40, 56);
        drawInfoElement(Frame.board.numLinesRemoved, 112, 40, 80);

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

    private void drawInfoElement(int element, int x1, int x2, int y) {
        List<BufferedImage> text = convert(String.valueOf(element));
        int x = (x1 + (x2 - (text.size() * 8)) / 2);
        for (BufferedImage img : text) {
            g2d.drawImage(img, x, y, this);
            x += img.getWidth();
        }
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
            repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        }
    }
}
