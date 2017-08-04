package br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.board;

import br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.player.Player;

public class Score {

    private Player player1;
    private Player player2;

    private int player1Wins;
    private int player2Wins;
    private int draws;

    public Score(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void update(int winnerId) {
        if (winnerId == Board.DRAW) {
            draws++;
        }

        if (player1.getId() == winnerId) {
            this.player1Wins++;
        }

        if (player2.getId() == winnerId) {
            this.player2Wins++;
        }
    }

    public int getPlayer1Score() {
        return player1Wins;
    }

    public int getPlayer2Score() {
        return player2Wins;
    }

    public int getDraws() {
        return draws;
    }

}
