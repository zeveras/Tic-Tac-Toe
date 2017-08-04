package br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.Board;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.BoardPosition;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.BoardUtils;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations.AlternateDiagonalOperation;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations.ColumnOperation;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations.DiagonalOperation;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations.RowOperation;

/**
 * A simple AI based on the following rules (in order):
 *
 * 1) If It has a winning move, do it.
 * 2) If the opponent has a winning move, block it.
 * 3) If It is the first move of the game (the board is empty), choose a corner.
 * 4) Mark another position in a line previously started.
 * 5) Mark a position in a free line.
 * 6) Mark a random position.
 */
public class SimpleCPU extends Player {

    public SimpleCPU(String name, char symbol) {
        super(name, symbol);
    }

    public BoardPosition calcMove(Board board, int playerId) {
        BoardPosition boardPosition = new BoardPosition();

        if (board.isBoardEmpty()) {
            return calcFirstMove(board);
        }

        if (isPlayerAlmostWinning(board, playerId, boardPosition)) {
            return boardPosition;
        }

        if (isOpponentAlmostWinning(board, playerId, boardPosition)) {
            return boardPosition;
        }

        if (getRandomEmptyPositionFromLineStartedByPlayer(board, playerId, boardPosition)) {
            return boardPosition;
        }

        if (getRandomEmptyPositionFromFreeLine(board, boardPosition)) {
            return boardPosition;
        }

        return BoardUtils.getRandomEmptyPosition(board, playerId);
    }

    private boolean isOpponentAlmostWinning(Board board, int playerId, BoardPosition emptyPosition) {
        return isPlayerAlmostWinning(board, board.getOpponentId(playerId), emptyPosition);
    }

    private boolean isPlayerAlmostWinning(Board board, int playerId, BoardPosition emptyPosition) {
        if (new RowOperation().findAlmostCompleteLine(board, playerId, emptyPosition)) {
            return true;
        }

        if (new ColumnOperation().findAlmostCompleteLine(board, playerId, emptyPosition)) {
            return true;
        }

        if (new DiagonalOperation().findAlmostCompleteLine(board, playerId, emptyPosition)) {
            return true;
        }

        return new AlternateDiagonalOperation().findAlmostCompleteLine(board, playerId, emptyPosition);
    }

    private BoardPosition calcFirstMove(Board board) {
        List<BoardPosition> corners = new ArrayList<BoardPosition>();

        corners.add(new BoardPosition(0, 0));
        corners.add(new BoardPosition(0, board.getDimension() - 1));
        corners.add(new BoardPosition(board.getDimension() - 1, 0));
        corners.add(new BoardPosition(board.getDimension() - 1, board.getDimension() - 1));

        return getRandomPosition(corners);
    }

    private boolean getRandomEmptyPositionFromLineStartedByPlayer(Board board, int playerId, BoardPosition emptyPosition) {
        List<BoardPosition> emptyPositions = BoardUtils.getLinesStartedByPlayerEmptyPositions(board, playerId);

        if (emptyPositions.size() > 0) {
            BoardPosition randomPosition = getRandomPosition(emptyPositions);
            emptyPosition.setRow(randomPosition.getRow());
            emptyPosition.setCol(randomPosition.getCol());
            return true;
        }

        return false;
    }

    private boolean getRandomEmptyPositionFromFreeLine(Board board, BoardPosition emptyPosition) {
        List<BoardPosition> emptyPositions = BoardUtils.getFreeLineEmptyPositions(board);

        if (emptyPositions.size() > 0) {
            BoardPosition randomPosition = getRandomPosition(emptyPositions);
            emptyPosition.setRow(randomPosition.getRow());
            emptyPosition.setCol(randomPosition.getCol());
            return true;
        }

        return false;
    }

    private BoardPosition getRandomPosition(List<BoardPosition> positions) {
        return positions.get(new Random().nextInt(positions.size()));
    }

}
