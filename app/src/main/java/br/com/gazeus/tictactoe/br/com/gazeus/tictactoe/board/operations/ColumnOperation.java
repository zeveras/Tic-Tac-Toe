package br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations;

import java.util.ArrayList;
import java.util.List;

import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.Board;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.BoardPosition;

public class ColumnOperation implements LineOperation {

    @Override
    public boolean findCompleteLine(Board board, int playerId, int[][] completeLineCoords) {

        for (int col = 0; col < board.getDimension(); col++) {
            for (int row = 0; row < board.getDimension(); row++) {
                if (board.getValue(row, col) != playerId) {
                    break;
                }

                if (row + 1 == board.getDimension()) {
                    buildColumn(board, col, completeLineCoords);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean findAlmostCompleteLine(Board board, int playerId, BoardPosition emptyPosition) {
        for (int col = 0; col < board.getDimension(); col++) {
            int elementsMarkedByPlayer = 0;
            List<BoardPosition> emptyBoardPositions = new ArrayList<BoardPosition>();

            for (int row = 0; row < board.getDimension(); row++) {
                if (board.getValue(row, col) == playerId) {
                    elementsMarkedByPlayer++;
                } else if (board.isEmptyPosition(row, col)) {
                    emptyBoardPositions.add(new BoardPosition(row, col));
                }
            }

            if (elementsMarkedByPlayer == board.getDimension() - 1 &&
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

        for (int col = 0; col < board.getDimension(); col++) {
            boolean startedByPlayer = false;

            for (int row = 0; row < board.getDimension(); row++) {
                if (board.getValue(row, col) == board.getOpponentId(playerId)) {
                    break;
                }

                if (board.getValue(row, col) == playerId) {
                    startedByPlayer = true;
                }

                if (row + 1 == board.getDimension() && startedByPlayer) {
                    emptyPositions.addAll(getColumnEmptyPositions(board, col));
                }
            }
        }

        return emptyPositions;
    }

    @Override
    public List<BoardPosition> findFreeLineEmptyPositions(Board board) {
        List<BoardPosition> emptyPositions = new ArrayList<BoardPosition>();

        for (int col = 0; col < board.getDimension(); col++) {
            for (int row = 0; row < board.getDimension(); row++) {
                if (board.getValue(row, col) != Board.EMPTY_POSITION_VALUE) {
                    break;
                }

                if (row + 1 == board.getDimension()) {
                    emptyPositions.addAll(getColumnEmptyPositions(board, col));
                }
            }
        }

        return emptyPositions;
    }


    private void buildColumn(Board board, int column, int[][] completeLineCoords) {
        for (int row = 0; row < board.getDimension(); row++) {
            completeLineCoords[row][0] = row;
            completeLineCoords[row][1] = column;
        }
    }

    private List<BoardPosition> getColumnEmptyPositions(Board board, int col) {
        List<BoardPosition> emptyPositions = new ArrayList<BoardPosition>();

        for (int row = 0; row < board.getDimension(); row++) {
            if (board.getValue(row, col) == Board.EMPTY_POSITION_VALUE) {
                emptyPositions.add(new BoardPosition(row, col));
            }
        }

        return emptyPositions;
    }
}
