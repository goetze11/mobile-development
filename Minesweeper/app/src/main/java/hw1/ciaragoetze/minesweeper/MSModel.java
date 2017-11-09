package hw1.ciaragoetze.minesweeper;

import java.util.Random;

/**
 * Created by ciaragoetze on 9/27/17.
 */

public class MSModel {
    private static MSModel MSModel = null;

    private MSModel() {
    }

    public static MSModel getInstance() {
        if (MSModel == null) {
            MSModel = new MSModel();
        }
        return MSModel;
    }

    public class Field {
        private boolean isMine;
        private boolean isFlag;
        private boolean hasBeenClicked;
        private int numMines;

        //constructor
        public Field() {
            isMine = false;
            isFlag = false;
            hasBeenClicked = false;
            numMines = 0;
        }

        //getters
        public boolean getIsMine() {
            return isMine;
        }

        public boolean getIsFlag() {
            return isFlag;
        }

        public boolean getHasBeenClicked() {
            return hasBeenClicked;
        }

        public int getNumMines() {
            return numMines;
        }

        //setters
        public void setMine(boolean mine) {
            isMine = mine;
        }

        public void setFlag(boolean flag) {
            isFlag = flag;
        }

        public void setHasBeenClicked(boolean hasBeenClicked) {
            this.hasBeenClicked = hasBeenClicked;
        }

        public void setNumMines(int numMines) {
            this.numMines = numMines;
        }
    }

    ;


    private Field[][] model = new Field[5][5];

    public Field getFieldContent(int x, int y) {
        return model[x][y];
    }

    public void setFlagContent(int x, int y, boolean componentIsFlag) {
        model[x][y].setFlag(componentIsFlag);
    }

    public void initializeGame() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                model[i][j] = new Field();
            }
        }
        placeMines();
        computeNumMines();
    }

    private void placeMines() {
        Random rand = new Random();
        int mineX, mineY;
        for (int i = 0; i < 3; i++) {
            mineX = rand.nextInt(5);
            mineY = rand.nextInt(5);
            while (model[mineX][mineY].getIsMine()) {
                mineX = rand.nextInt(5);
                mineY = rand.nextInt(5);
            }
            model[mineX][mineY].setMine(true);
        }
    }

    private void computeNumMines() {
        int numMines;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                numMines = 0;
                if ((i - 1) >= 0 && model[i - 1][j].getIsMine()) {numMines += 1;}
                if ((i + 1) <= 4 && model[i + 1][j].getIsMine()) {numMines += 1;}
                if ((j - 1) >= 0 && model[i][j - 1].getIsMine()) {numMines += 1;}
                if ((j + 1) <= 4 && model[i][j + 1].getIsMine()) {numMines += 1;}
                if ((i - 1) >= 0 && (j - 1) >= 0 && model[i - 1][j - 1].getIsMine()) {numMines += 1;}
                if ((i + 1) <= 4 && (j - 1) >= 0 && model[i + 1][j - 1].getIsMine()) {numMines += 1;}
                if ((i - 1) >= 0 && (j + 1) <= 4 && model[i - 1][j + 1].getIsMine()) {numMines += 1;}
                if ((i + 1) <= 4 && (j + 1) <= 4 && model[i + 1][j + 1].getIsMine()) {numMines += 1;}
                model[i][j].setNumMines(numMines);
            }
        }
    }
}
