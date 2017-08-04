package br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations;

import java.util.ArrayList;
import java.util.List;

import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.Board;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.BoardPosition;

public class AlternateDiagonalOperation implements LineOperation {

    @Override
    public boolean findCompleteLine(Board board, int playerId, int[][] completeLineCoords) {
        int col = 0;

        for (int row = board.getDimension() - 1; row >= 0; row--) {
            if (board.getValue(row, col) != playerId) {
                return false;
            }
            col++;
        }

        buildAlternateDiagonal(board, completeLineCoords);

        return true;
    }

    @Override
    public boolean findAlmostCompleteLine(Board board, int playerId, BoardPosition emptyPosition) {
        int elementsMarkedByPlayer = 0;
        List<BoardPosition> emptyBoardPositions = new ArrayList<BoardPosition>();

        int col = 0;

        for (int row = board.getDimension() - 1; row >= 0; row--) {

            if (board.getValue(row, col) == playerId) {
                elementsMarkedByPlayer++;
            } else if (board.isEmptyPosition(row, col)) {
                emptyBoardPositions.add(new BoardPosition(row, col));
            }

            col++;
        }

        if (elementsMarkedByPlayer == board.getDimension() - 1 &&
                emptyBoardPositions.size() == 1) {
            emptyPosition.setRow(emptyBoardPositions.get(0).getRow());
            emptyPosition.setCol(emptyBoardPositions.get(0).getCol());
            return true;
        }

        return false;
    }

    @Override
    public List<BoardPosition> findLineStartedByPlayerEmptyPositions(Board board, int playerId) {
        List<BoardPosition> emptyPositions = new ArrayList<BoardPosition>();

        int col = 0;
        boolean startedByPlayer = false;

        for (int row = board.getDimension() - 1; row >= 0; row--) {
            if (board.getValue(row, col) == board.getOpponentId(playerId)) {
                break;
            }

            if (board.getValue(row, col) == playerId) {
                startedByPlayer = true;
            }

            if (row == 0 && startedByPlayer) {
                emptyPositions.addAll(getAlternateDiagonalEmptyPositions(board));
            }

            col++;
        }

        return emptyPositions;
    }

    @Override
    public List<BoardPosition> findFreeLineEmptyPositions(Board board) {
        List<BoardPosition> emptyPositions = new ArrayList<BoardPosition>();

        int col = 0;
        for (int row = board.getDimension() - 1; row >= 0; row--) {
            if (board.getValue(row, col) != Board.EMPTY_POSITION_VALUE) {
                return emptyPositions;
            }
            col++;
        }

        return getAlternateDiagonalEmptyPositions(board);
    }

    private void buildAlternateDiagonal(Board board, int[][] completeLineCoords) {
        int col = 0;

        for (int row = board.getDimension() - 1; row >= 0; row--) {
            completeLineCoords[row][0] = row;
            completeLineCoords[row][1] = col;
            col++;
        }
    }

    private List<BoardPosition> getAlternateDiagonalEmptyPositions(Board board) {
        List<BoardPosition> emptyPositions = new ArrayList<BoardPosition>();

        int col = 0;
        for (int row = board.getDimension() - 1; row >= 0; row--) {
            if (board.getValue(row, col) == Board.EMPTY_POSITION_VALUE) {
                emptyPositions.add(new BoardPosition(row, col));
            }

            col++;
        }

        return emptyPositions;
    }
}
