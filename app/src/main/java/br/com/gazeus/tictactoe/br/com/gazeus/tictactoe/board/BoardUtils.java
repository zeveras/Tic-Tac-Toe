package br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations.AlternateDiagonalOperation;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations.ColumnOperation;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations.DiagonalOperation;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations.RowOperation;

/**
 * Collect of NxN Board useful functions. Checks and verifications about the
 * current state of the game, considering its rules.
 *
 * Obs.: Line = Row, Column, Diagonal or Alternate Diagonal
 */
public class BoardUtils {

    public static boolean checkGameOver(Board board, RoundResult roundResult) {
        int[][] completeLineCoords = new int[board.getDimension()][2]; // x, y

        int player1Id = board.getPlayer1Id();
        int player2Id = board.getPlayer2Id();

        if (checkPlayerVictory(board, player1Id, completeLineCoords)) {
            roundResult.setWinnerId(player1Id);
            roundResult.setCompleteLineCoords(completeLineCoords);
            return true;
        }

        if (checkPlayerVictory(board, player2Id, completeLineCoords)) {
            roundResult.setWinnerId(player2Id);
            roundResult.setCompleteLineCoords(completeLineCoords);
            return true;
        }

        if (board.isBoardComplete()) {
            roundResult.setWinnerId(Board.DRAW);
            return true;
        }

        return false;
    }

    public static boolean checkPlayerVictory(Board board, int playerId, int[][] completeLineCoords) {
        if (new RowOperation().findCompleteLine(board, playerId, completeLineCoords)) {
            return true;
        }

        if (new ColumnOperation().findCompleteLine(board, playerId, completeLineCoords)) {
            return true;
        }

        return isAnyDiagonalComplete(board, playerId, completeLineCoords);
    }

    private static boolean isAnyDiagonalComplete(Board board, int playerId, int[][] completeLineCoords) {
        if (new DiagonalOperation().findCompleteLine(board, playerId, completeLineCoords)) {
            return true;
        }

        return new AlternateDiagonalOperation().findCompleteLine(board, playerId, completeLineCoords);
    }

    public static BoardPosition getRandomEmptyPosition(Board board, int playerId) {
        List<BoardPosition> emptyBoardPositions = new ArrayList<BoardPosition>();

        for (int row = 0; row < board.getDimension(); row++) {
            for (int col = 0; col < board.getDimension(); col++) {
                if (board.isEmptyPosition(row, col)) {
                    emptyBoardPositions.add(new BoardPosition(row, col));
                };
            }
        }

        if (emptyBoardPositions.size() > 0) {
            int rnd = new Random().nextInt(emptyBoardPositions.size());
            return emptyBoardPositions.get(rnd);
        }

        return null;
    }

    public static List<BoardPosition> getLinesStartedByPlayerEmptyPositions(Board board, int playerId) {
        List<BoardPosition> emptyPositions = new ArrayList<BoardPosition>();

        emptyPositions.addAll(new RowOperation().findLineStartedByPlayerEmptyPositions(board, playerId));
        emptyPositions.addAll(new ColumnOperation().findLineStartedByPlayerEmptyPositions(board, playerId));
        emptyPositions.addAll(new DiagonalOperation().findLineStartedByPlayerEmptyPositions(board, playerId));
        emptyPositions.addAll(new AlternateDiagonalOperation().findLineStartedByPlayerEmptyPositions(board, playerId));

        return emptyPositions;
    }

    public static List<BoardPosition> getFreeLineEmptyPositions(Board board) {
        List<BoardPosition> emptyPositions = new ArrayList<BoardPosition>();

        emptyPositions.addAll(new RowOperation().findFreeLineEmptyPositions(board));
        emptyPositions.addAll(new ColumnOperation().findFreeLineEmptyPositions(board));
        emptyPositions.addAll(new DiagonalOperation().findFreeLineEmptyPositions(board));
        emptyPositions.addAll(new AlternateDiagonalOperation().findFreeLineEmptyPositions(board));

        return emptyPositions;
    }

}
