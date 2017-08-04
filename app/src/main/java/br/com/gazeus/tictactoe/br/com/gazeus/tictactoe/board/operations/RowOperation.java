package br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations;

import java.util.ArrayList;
import java.util.List;

import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.Board;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.BoardPosition;

public class RowOperation implements LineOperation {

    @Override
    public boolean findCompleteLine(Board board, int id, int[][] completeLineCoords) {
        for (int row = 0; row < board.getDimension(); row++) {
            for (int col = 0; col < board.getDimension(); col++) {
                if (board.getValue(row, col) != id) {
                    break;
                }

                if (col + 1 == board.getDimension()) {
                    buildRow(board, row, completeLineCoords);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean findAlmostCompleteLine(Board board, int playerId, BoardPosition emptyPosition) {
        for (int row = 0; row < board.getDimension(); row++) {
            int elementsMarkedByPlayer = 0;
            List<BoardPosition> emptyBoardPositions = new ArrayList<BoardPosition>();

            for (int col = 0; col < board.getDimension(); col++) {
                if (board.getValue(row, col) == playerId) {
                    elementsMarkedByPlayer++;
                } else if (board.isEmptyPosition(row, col)) {
                    emptyBoardPositions.add(new BoardPosition(row, col));
                }
            }

            if (elementsMarkedByPlayer == board.getDimension() -1 &&
                    emptyBoardPositions.size() == 1) {
                emptyPosition.setRow(emptyBoardPositions.get(0).getRow());
                emptyPosition.setCol(emptyBoardPositions.get(0).getCol());

                return true;
            }
        }

        return false;
    }

    @Override
    public List<BoardPosition> findLineStartedByPlayerEmptyPositions(Board board, int playerId) {
        List<BoardPosition> emptyPositions = new ArrayList<BoardPosition>();

        for (int row = 0; row < board.getDimension(); row++) {
            boolean startedByPlayer = false;

            for (int col = 0; col < board.getDimension(); col++) {
                if (board.getValue(row, col) == board.getOpponentId(playerId)) {
                    break;
                }

                if (board.getValue(row, col) == playerId) {
                    startedByPlayer = true;
                }

                if (col + 1 == board.getDimension() && startedByPlayer) {
                    emptyPositions.addAll(getRowEmptyPositions(board, row));
                }
            }
        }

        return emptyPositions;
    }

    @Override
    public List<BoardPosition> findFreeLineEmptyPositions(Board board) {
        List<BoardPosition> emptyPositions = new ArrayList<BoardPosition>();

        for (int row = 0; row < board.getDimension(); row++) {
            for (int col = 0; col < board.getDimension(); col++) {
                if (board.getValue(row, col) != Board.EMPTY_POSITION_VALUE) {
                    break;
                }

                if (col + 1 == board.getDimension()) {
                    emptyPositions.addAll(getRowEmptyPositions(board, row));
                }
            }
        }

        return emptyPositions;
    }

    private List<BoardPosition> getRowEmptyPositions(Board board, int row) {
        List<BoardPosition> emptyPositions = new ArrayList<BoardPosition>();

        for (int col = 0; col < board.getDimension(); col++) {
            if (board.getValue(row, col) == Board.EMPTY_POSITION_VALUE) {
                emptyPositions.add(new BoardPosition(row, col));
            }
        }

        return emptyPositions;
    }

    private void buildRow(Board board, int row, int[][] completeLineCoords) {
        for (int col = 0; col < board.getDimension(); col++) {
            completeLineCoords[col][0] = row;
            completeLineCoords[col][1] = col;
        }
    }
}
