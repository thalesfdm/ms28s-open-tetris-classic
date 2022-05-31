package Tetris;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Tetris.Shape.Tetrominoes;

public class Board extends JPanel implements ActionListener {
    final int BoardWidth = 10;
    final int BoardHeight = 18;
    public Image[] box = new Image[8];
    Timer timer;
    boolean isFallingFinished = false;
    boolean isStarted = false;
    boolean isPaused = false;
    int numLinesRemoved = 0, linesTillNext = 10, linesComp = 0;
    int curX = 0;
    int curY = 0;
    public int level = 0;
    public int score = 0;
    JLabel statusbar;
    Shape curPiece, nextPiece;
    Tetrominoes[] board;
    public boolean first = false;

    public Board() {
        setBounds(80, 50, 400, 720);
        curPiece = new Shape();
        nextPiece = new Shape();
        timer = new Timer(400, this);
        String prefix = System.getProperty("user.dir") + "/Tetris/";

        box[1] = new ImageIcon(prefix + "graphics/pieces/1boxP1.png").getImage();
        box[2] = new ImageIcon(prefix + "graphics/pieces/2box.png").getImage();
        box[3] = new ImageIcon(prefix + "graphics/pieces/3box.png").getImage();
        box[4] = new ImageIcon(prefix + "graphics/pieces/4box.png").getImage();
        box[5] = new ImageIcon(prefix + "graphics/pieces/5box.png").getImage();
        box[6] = new ImageIcon(prefix + "graphics/pieces/6box.png").getImage();
        box[7] = new ImageIcon(prefix + "graphics/pieces/7box.png").getImage();

        board = new Tetrominoes[BoardWidth * BoardHeight];
        clearBoard();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!isStarted || Frame.tetrisPanel.lose) {
                startGame();
                Frame.tetrisPanel.lose = false;
            } else {
                if (!isPaused) {
                    pauseGame();
                } else {
                    resumeGame();
                }
            }
        }

        if (!isStarted || isPaused) {
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            TetrisPanel.move.play();
            tryMove(curPiece, curX - 1, curY);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            TetrisPanel.move.play();
            tryMove(curPiece, curX + 1, curY);
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            tryMove(curPiece.rotateRight(), curX, curY);
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            tryMove(curPiece.rotateLeft(), curX, curY);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            dropDown();
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            TetrisPanel.move.play();
            oneLineDown();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isFallingFinished) {
            isFallingFinished = false;
            newPiece();
        } else {
            oneLineDown();
        }
    }

    int squareWidth() {
        return (int) getSize().getWidth() / BoardWidth;
    }

    int squareHeight() {
        return (int) getSize().getHeight() / BoardHeight;
    }

    Tetrominoes shapeAt(int x, int y) {
        return board[(y * BoardWidth) + x];
    }

    public void startGame() {
        if (isPaused) {
            return;
        }

        resetPanel();
        first = true;
        newPiece();

        timer.start();
    }

    private void pauseGame() {
        if (!isStarted) {
            return;
        }
        isPaused = true;
        timer.stop();
    }

    private void resumeGame() {
        if (!isStarted) {
            return;
        }
        isPaused = false;
        timer.start();
//        statusbar.setText(String.valueOf(numLinesRemoved));
    }

    public void resetPanel() {
        isStarted = true;
        isFallingFinished = false;
        numLinesRemoved = 0;
        score = 0;
        level = 0;
        linesTillNext = 10;
        linesComp = 0;
        timer.setDelay(400);
        clearBoard();
    }

    @Override
    public void paint(Graphics g) {
        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();

        for (int i = 0; i < BoardHeight; ++i) {
            for (int j = 0; j < BoardWidth; ++j) {
                Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
                if (shape != Tetrominoes.NoShape) {
                    drawSquare(g, j * squareWidth() + 75,
                            boardTop + i * squareHeight(), shape);
                }

            }
        }

        if (curPiece.getShape() != Tetrominoes.NoShape) {
            for (int i = 0; i < 4; ++i) {
                int x = curX + curPiece.x(i);
                int y = curY - curPiece.y(i);
                drawSquare(g, x * squareWidth() + 75,
                        boardTop + (BoardHeight - y - 1) * squareHeight(),
                        curPiece.getShape());
            }
        }
    }

    private void dropDown() {
        int newY = curY;
        while (newY > 0) {
            if (!tryMove(curPiece, curX, newY - 1)) {
                break;
            }
            --newY;
        }
        pieceDropped();
    }

    private void oneLineDown() {
        if (!tryMove(curPiece, curX, curY - 1)) {
            pieceDropped();
        }
    }

    public final void clearBoard() {
        for (int i = 0; i < BoardHeight * BoardWidth; ++i) {
            board[i] = Tetrominoes.NoShape;
        }
    }

    private void pieceDropped() {
        TetrisPanel.drop.play();
        for (int i = 0; i < 4; ++i) {
            int x = curX + curPiece.x(i);
            int y = curY - curPiece.y(i);
            board[(y * BoardWidth) + x] = curPiece.getShape();
        }

        removeFullLines();

        if (!isFallingFinished) {
            newPiece();
        }
    }

    private void newPiece() {
        if (first) {
            nextPiece.setRandomShape();
            first = false;
        }

        curPiece.setShape(nextPiece.getShape());
        nextPiece.setRandomShape();
        Frame.tetrisPanel.nextPiece = nextPiece;
        curX = BoardWidth / 2 + 1;
        curY = BoardHeight - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {
            TetrisPanel.go1.play();
            curPiece.setShape(Tetrominoes.NoShape);
            timer.stop();
            isStarted = false;
            stopThemeMusic();
            Frame.tetrisPanel.lose();
        }
    }

    private void stopThemeMusic() {
        TetrisPanel.aTheme.stop();
        TetrisPanel.bTheme.stop();
        TetrisPanel.cTheme.stop();
    }

    private boolean tryMove(Shape newPiece, int newX, int newY) {
        for (int i = 0; i < 4; ++i) {
            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);
            if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight) {
                return false;
            }
            if (shapeAt(x, y) != Tetrominoes.NoShape) {
                return false;
            }
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;

        return true;
    }

    private void removeFullLines() {
        int numFullLines = 0;

        for (int i = BoardHeight - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < BoardWidth; ++j) {
                if (shapeAt(j, i) == Tetrominoes.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                ++numFullLines;

                //Blink
                for (int k = i; k < BoardHeight - 1; ++k) {
                    for (int j = 0; j < BoardWidth; ++j) {

                        board[(k * BoardWidth) + j] = shapeAt(j, k + 1);

                    }
                }

                if (numFullLines > 0) {

                    numLinesRemoved += numFullLines;
                    linesComp += numFullLines;
                    isFallingFinished = true;
                    scoring(numFullLines);
                    checkLevel();
                    curPiece.setShape(Tetrominoes.NoShape);
                }
            }
        }
    }

    public void checkLevel() {
        if (linesComp >= linesTillNext) {
            level++;
            linesComp = 0;
            linesTillNext += 10;
            timer.setDelay(timer.getDelay() - 25);
            TetrisPanel.newlevel.play();
        }
    }

    public void scoring(int numFullLines) {
        int levelMultiplier = level + 1;

        if (numFullLines == 1) {
            TetrisPanel.line.play();
            score += 40 * levelMultiplier;
        }
        if (numFullLines == 2) {
            TetrisPanel.line.play();
            score += 60 * levelMultiplier;
        }
        if (numFullLines == 3) {
            TetrisPanel.line.play();
            score += 160 * levelMultiplier;
        }
        if (numFullLines == 4) {
            TetrisPanel.linefour.play();
            score += 760 * levelMultiplier;
        }
    }

    private int getShapeIndex(Tetrominoes shape) {
        switch (shape) {
            case LineShape:
                return 1;
            case MirroredLShape:
                return 2;
            case LShape:
                return 3;
            case SquareShape:
                return 4;
            case SShape:
                return 5;
            case TShape:
                return 6;
            default:
                return 7;
        }
    }

    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
        g.drawImage(box[getShapeIndex(shape)], x, y, squareWidth(), squareHeight(), null);
    }
}
