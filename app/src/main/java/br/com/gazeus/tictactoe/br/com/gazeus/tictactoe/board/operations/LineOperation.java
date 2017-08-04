package br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations;

import java.util.List;

import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.Board;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.BoardPosition;

/**
 * Interface for line operations / verifications over a NxN Board.
 *
 * Lines can be:
 * 1) Rows
 * 2) Columns
 * 3) Diagonal
 * 4) Alternate Diagonal
 */
public interface LineOperation {

    /**
     * Searches for a complete line. If any, returns true and fills the comleteLineCoords parameter with the line coordinates.
     **/
    public boolean findCompleteLine(Board board, int playerId, int[][] completeLineCoords);

    /**
     * Searches for an almost complete line: a line marked only by the player and with just one empty position.
     *
     * If any, returns true and fills the emptyPosition parameter with the empty position coordinates.
     **/
    public boolean findAlmostCompleteLine(Board board, int playerId, BoardPosition emptyPosition);

    /**
     * Searches for lines marked only by the player and returns a list with the empty positions;
     */
    public List<BoardPosition> findLineStartedByPlayerEmptyPositions(Board board, int playerId);

    /**
     * Searches for lines that has not been marked.
     */
    public List<BoardPosition> findFreeLineEmptyPositions(Board board);
}
