package de.lucabecker.tictactoe;

public class Move {
    int row;
    int col;

    public Move() {
        this.row = -1;
        this.col = -1;
    }

    public void setMove(int row, int col) {
        this.row = row;
        this.col = col;
    }
}