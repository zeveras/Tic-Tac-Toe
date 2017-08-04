package br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.operations;

import java.util.ArrayList;
import java.util.List;

import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.Board;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.BoardPosition;

public class DiagonalOperation implements LineOperation {

    @Override
    public boolean findCompleteLine(Board board, int playerId, int[][] completeLineCoords) {
        for (int i = 0; i < board.getDimension(); i++) {
            if (board.getValue(i, i) != playerId) {
                return false;
            }
        }

        buildDiagonal(board, completeLineCoords);

        return true;
    }

    @Override
    public boolean findAlmostCompleteLine(Board board, int playerId, BoardPosition emptyPosition) {
        int elementsMarkedByPlayer = 0;
        List<BoardPosition> emptyBoardPositions = new ArrayList<BoardPosition>();

        for (int i = 0; i < board.getDimension(); i++) {
            if (board.getValue(i, i) == playerId) {
                elementsMarkedByPlayer++;
            } else if (board.isEmptyPosition(i, i)) {
                emptyBoardPositions.add(new BoardPosition(i, i));
            }
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
        List<BoardPosition> boardPositions = new ArrayList<BoardPosition>();

        boolean startedByPlayer = false;

        for (int i = 0; i < board.getDimension(); i++) {
            if (board.getValue(i, i) == board.getOpponentId(playerId)) {
                break;
            }

            if (board.getValue(i, i) == playerId) {
                startedByPlayer = true;
            }

            if (i + 1 == board.getDimension() && startedByPlayer) {
                boardPositions.addAll(getDiagonalEmptyPositions(board));
            }
        }

        return boardPositions;
    }

    @Override
    public List<BoardPosition> findFreeLineEmptyPositions(Board board) {
        List<BoardPosition> emptyPositions = new ArrayList<BoardPosition>();

        for (int i = 0; i < board.getDimension(); i++) {
            if (board.getValue(i, i) != Board.EMPTY_POSITION_VALUE) {
                return emptyPositions;
            }
        }

        return getDiagonalEmptyPositions(board);
    }

    private void buildDiagonal(Board board, int[][] completeLineCoords) {
        for (int i = 0; i < board.getDimension(); i++) {
            completeLineCoords[i][0] = i;
            completeLineCoords[i][1] = i;
        }
    }

    private List<BoardPosition> getDiagonalEmptyPositions(Board board) {
        List<BoardPosition> emptyPositions = new ArrayList<BoardPosition>();

        for (int i = 0; i < board.getDimension(); i++) {
            if (board.getValue(i, i) == Board.EMPTY_POSITION_VALUE) {
                emptyPositions.add(new BoardPosition(i, i));
            }
        }

        return emptyPositions;
    }
}
