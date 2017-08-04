package br.com.gazeus.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.Board;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.BoardPosition;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.BoardUtils;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.RoundResult;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board.Score;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.player.Human;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.player.Player;
import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.player.SimpleCPU;

public class MainActivity extends AppCompatActivity {

    private static int BOARD_DIMENSION = 3;
    private static String DEFAULT_COLOR = "#999999";
    private static String WINNER_COLOR = "#006600";

    private Board board;
    private Map<Integer, Button> boardButtonsMap = new HashMap<Integer, Button>();
    private Button nextRoundButton;

    private Player player1;
    private Player player2;

    private Score score;

    private TextView feedback;

    private int lastPlayerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1 = new Human(getString(R.string.player_name), getString(R.string.player_symbol).charAt(0));
        player2 = new SimpleCPU(getString(R.string.cpu_name), getString(R.string.cpu_symbol).charAt(0));
        score = new Score(player1, player2);

        boardButtonsMap.put(0, (Button)findViewById(R.id.button0));
        boardButtonsMap.put(1, (Button)findViewById(R.id.button1));
        boardButtonsMap.put(2, (Button)findViewById(R.id.button2));
        boardButtonsMap.put(3, (Button)findViewById(R.id.button3));
        boardButtonsMap.put(4, (Button)findViewById(R.id.button4));
        boardButtonsMap.put(5, (Button)findViewById(R.id.button5));
        boardButtonsMap.put(6, (Button)findViewById(R.id.button6));
        boardButtonsMap.put(7, (Button)findViewById(R.id.button7));
        boardButtonsMap.put(8, (Button)findViewById(R.id.button8));

        for (int i = 0; i < 9; i++) {
            boardButtonsMap.get(i).setOnClickListener(new BoardButtonListener(i/BOARD_DIMENSION, i%BOARD_DIMENSION));
        }

        // initialize score
        ((TextView) findViewById(R.id.player1Name)).setText(player1.getName());
        ((TextView) findViewById(R.id.player2Name)).setText(player2.getName());
        ((TextView) findViewById(R.id.player1Score)).setText("0");
        ((TextView) findViewById(R.id.drawScore)).setText("0");
        ((TextView) findViewById(R.id.player2Score)).setText("0");

        nextRoundButton = (Button)findViewById(R.id.next_round);
        nextRoundButton.setOnClickListener(new NextRoundButtonListener());
        feedback = (TextView)findViewById(R.id.msg);

        resetBoard();
        startNewRound();
    }

    private void makeMove(int playerId, char symbol, int row, int col) {
        board.makePlayerMove(playerId, row, col);

        // Update View
        Button button = boardButtonsMap.get(row * BOARD_DIMENSION + col);
        button.setText(String.valueOf(symbol));
        button.setEnabled(false);
    }

    private void makeCPUMove() {
        BoardPosition cpuMove = ((SimpleCPU) player2).calcMove(board, player2.getId());
        makeMove(player2.getId(), player2.getSymbol(), cpuMove.getRow(), cpuMove.getCol());
    }

    private class BoardButtonListener implements View.OnClickListener {
        int row;
        int col;

        public BoardButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void onClick(View view) {
            makeMove(player1.getId(), player1.getSymbol(), row, col);
            if (handleGameOver(board)) {
                return;
            }

            makeCPUMove();
            if (handleGameOver(board)) {
                return;
            }
        }
    }

    private class NextRoundButtonListener implements View.OnClickListener {

        public void onClick(View view) {
            resetBoard();
            startNewRound();
        }
    }

    private void startNewRound() {
        if (lastPlayerId == player1.getId()) {
            lastPlayerId = player2.getId();
            makeCPUMove();
        } else {
            lastPlayerId = player1.getId();
        }
    }

    private boolean handleGameOver(Board board) {
        RoundResult result = new RoundResult();

        if (!BoardUtils.checkGameOver(board, result)) {
            return false;
        }

        if (!result.isDraw()) {
            markWinningLine(result.getCompleteLineCoords());
        }

        updateScore(result.getWinnerId());

        feedback.setText(getWinnerMessage(result.getWinnerId()));
        feedback.setVisibility(View.VISIBLE);
        nextRoundButton.setVisibility(View.VISIBLE);

        disableBoard();

        return true;
    }

    private void resetBoard() {
        for (Button button : boardButtonsMap.values()) {
            button.setText("");
            button.setEnabled(true);
            button.setTextColor(Color.parseColor(DEFAULT_COLOR));
        }

        board = new Board(player1.getId(), player2.getId(), BOARD_DIMENSION);

        feedback.setVisibility(View.INVISIBLE);
        nextRoundButton.setVisibility(View.INVISIBLE);
    }

    private void disableBoard() {
        for (Button button : boardButtonsMap.values()) {
            button.setEnabled(false);
        }
    }

    private void updateScore(int winnerId) {
        score.update(winnerId);
        ((TextView) findViewById(R.id.player1Score)).setText(String.valueOf(score.getPlayer1Score()));
        ((TextView) findViewById(R.id.drawScore)).setText(String.valueOf(score.getDraws()));
        ((TextView) findViewById(R.id.player2Score)).setText(String.valueOf(score.getPlayer2Score()));
    }

    private void markWinningLine(int[][] completeLineCoords) {
        for (int i = 0; i < completeLineCoords.length; i++) {
            int row = completeLineCoords[i][0];
            int col = completeLineCoords[i][1];
            Button button = boardButtonsMap.get(row * BOARD_DIMENSION + col);
            button.setTextColor(Color.parseColor(WINNER_COLOR));
        }
    }

    private String getWinnerMessage(int playerId) {
        if (playerId == Board.DRAW) {
            return getString(R.string.draw);
        }

        if (player1.getId() == playerId) {
            return player1.getName() + " " + getString(R.string.won);
        }

        return player2.getName() + " " + getString(R.string.won);
    }
}
