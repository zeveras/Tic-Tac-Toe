package br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board;

/**
 * Generic NxN Board.
 *
 * Stores playerIds at the specified positions.
 * Accepts only 2 players.
 * Agnostic about the rules.
 */
public class Board {

    public static final int EMPTY_POSITION_VALUE = -1;
    public static final int DRAW = -1;

    private int[][] board;
    private int dimension;
    private int player1Id;
    private int player2Id;
    private int[][] completeLineCoords;
    private int winnerId;

    private boolean isBoardEmpty;

    public Board(int player1Id, int player2Id, int dimension) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;

        this.dimension = dimension;

        board = new int[dimension][dimension];

        // reset board
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board[i][j] = EMPTY_POSITION_VALUE;
            }
        }

        this.isBoardEmpty = true;
    }

    public boolean isEmptyPosition(int row, int col) {
        return board[row][col] == EMPTY_POSITION_VALUE;
    }

    public int getDimension() {
        return this.dimension;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public int getPlayer2Id() {
        return player2Id;
    }

    public int getValue(int row, int col) {
        return board[row][col];
    }

    public int getOpponentId(int playerId) {
        checkPlayerId(playerId);

        if (playerId == player1Id) return player2Id;

        return player1Id;
    }

    public void makePlayerMove(int playerId, int row, int col) {
        checkPlayerId(playerId);
        checkMove(row, col);
        board[row][col] = playerId;
        isBoardEmpty = false;
    }

    private void checkPlayerId(int playerId) {
        if (player1Id != playerId && player2Id != player2Id) {
            throw new IllegalArgumentException("Player not found!");
        }
    }

    private void checkMove(int row, int col) {
        if (board[row][col] != EMPTY_POSITION_VALUE) {
            throw new IllegalStateException("Cannot make this move!");
        }
    }

    public boolean isBoardComplete() {
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (board[row][col] == Board.EMPTY_POSITION_VALUE) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isBoardEmpty() {
        return this.isBoardEmpty;
    }

}
