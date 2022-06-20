package Tetris;

import java.util.Random;
import java.lang.Math;

public class Shape {

    enum Tetrominoes {
        NoShape, ZShape, SShape,SquareShape ,
        TShape, LineShape, LShape, MirroredLShape
    }

    private Tetrominoes pieceShape;
    private int[][] coords;

    public Shape() {

        coords = new int[4][2];
        setShape(Tetrominoes.NoShape);

    }

    public void setShape(Tetrominoes shape) {

        int[][][] coordsTable = new int[][][]{
                //NoShape
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
                //ZShape
                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
                //SShape
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
                //SquareShape
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
                //TShape
                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
                //LineShape
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
                //LShape
                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
                //MirroredLShape
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
        };

        for (int i = 0; i < 4; i++) {
            System.arraycopy(coordsTable[shape.ordinal()][i], 0, coords[i], 0, 2);
        }
        pieceShape = shape;

    }

    private void setX(int index, int x) {
        coords[index][0] = x;
    }

    private void setY(int index, int y) {
        coords[index][1] = y;
    }

    public int x(int index) {
        return coords[index][0];
    }

    public int y(int index) {
        return coords[index][1];
    }

    public Tetrominoes getShape() {
        return pieceShape;
    }

    public void setRandomShape() {
        int r = new Random().nextInt((7 - 1) + 1) + 1;
        Tetrominoes[] values = Tetrominoes.values();
        setShape(values[r]);

    }

    public void setFirstRandomShape() {
        int r = new Random().nextInt((7 - 4) + 1) + 4;
        Tetrominoes[] values = Tetrominoes.values();
        setShape(values[r]);

    }


    public int minY() {
        int m = coords[0][1];
        for (int i = 0; i < 4; i++) {
            m = Math.min(m, coords[i][1]);
        }
        return m;
    }

    public Shape rotateLeft() {
        TetrisPanel.turn.play();
        if (pieceShape == Tetrominoes.SquareShape) {
            return this;
        }

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, y(i));
            result.setY(i, -x(i));
        }
        return result;
    }

    public Shape rotateRight() {
        TetrisPanel.turn.play();
        if (pieceShape == Tetrominoes.SquareShape) {
            return this;
        }

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, -y(i));
            result.setY(i, x(i));
        }
        return result;
    }
}
