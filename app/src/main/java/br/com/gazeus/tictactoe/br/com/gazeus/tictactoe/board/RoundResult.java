package br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board;

public class RoundResult {

    private int[][] completeLineCoords;
    private int winnerId;

    public RoundResult(int[][] completeLineCoords, int winnerId) {
        this.completeLineCoords = completeLineCoords;
        this.winnerId = winnerId;
    }

    public RoundResult() {

    }

    public int[][] getCompleteLineCoords() {
        return completeLineCoords;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public void setCompleteLineCoords(int[][] completeLineCoords) {
        this.completeLineCoords = completeLineCoords;
    }

    public boolean isDraw() {
        return winnerId == Board.DRAW;
    }
}
