package SeaBattle.Model;

public class Cell {
    private int x, y;
    private char symbol;
    private boolean hasShip, isHit;
    private Ship ship;

    public Cell() {}

    public Cell(int x, int y, char symbol) {
        this.symbol = symbol;
        this.x = x;
        this.y = y;
        this.hasShip = false;
        this.isHit = false;
    }

    public boolean isHasShip() {
        return hasShip;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit() {
        isHit = true;
        if (!hasShip) symbol = 'X';
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.hasShip = true;
        symbol = 'S';
        this.ship = ship;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}