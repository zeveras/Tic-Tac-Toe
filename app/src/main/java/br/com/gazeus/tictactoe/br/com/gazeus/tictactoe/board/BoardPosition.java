package br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board;

public class BoardPosition {
    int row;
    int col;

    public BoardPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public BoardPosition() {

    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
