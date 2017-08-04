package br.com.gazeus.tictactoe.br.com.gazeus.tictactoe.player;

public abstract class Player {

    private static int counter = 1;

    private int id;
    private String name;
    private char symbol;

    public Player(String name, char symbol) {
        this.id = counter;
        this.name = name;
        this.symbol = symbol;

        counter++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
