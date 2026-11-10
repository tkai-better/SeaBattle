package SeaBattle.Model;
import SeaBattle.View.*;

public class Board {
    private Cell[][] grid;
    public static int size;

    public Board() {}

    public Board(int sizee) {
        this.size = sizee;
        this.grid = new Cell[size+1][size+1];
        initBoard();
    }

    public int getSize() {
        return size;
    }

    public void initBoard() {
        for (int i=0; i<=size; ++i) {
            for (int j=0; j<=size; ++j) {
                grid[i][j] = new Cell(i, j, '.');
            }
        }
    }

    public void showBoard(boolean fog) {
        for (int i = 0; i <= size; ++i) {
            for (int j = 0; j <= size; ++j) {
                if (i == 0) {
                    if (j > 0) {
                        System.out.printf("%3d", grid[i][j].getY());
                        continue;
                    }
                }

                if (j == 0) {
                    if (i > 0) {
                        System.out.printf("%2d ", grid[i][j].getX());
                    }
                    else {
                        System.out.print("  ");
                    }
                    continue;
                }

                Cell cell = grid[i][j];

                if (!fog) {
                    if (cell.isHasShip()) {
                        if (cell.isHit()) {
                            if (cell.getShip() != null && cell.getShip().isSunk()) {
                                String color = getShipColor(cell.getShip());
                                System.out.print(color + " S " + View.RESET);
                            }
                            else {
                                System.out.print(View.SHIP + " S ");
                            }
                        }
                        else {
                            String color = getShipColor(cell.getShip());
                            System.out.print(color + " S " + View.RESET);
                        }
                    }
                    else {
                        if (cell.isHit()) {
                            System.out.print(View.MISS + " X " + View.RESET);
                        }
                        else {
                            System.out.print(View.WATER + " . " + View.RESET);
                        }
                    }
                }
                else {
                    if (cell.isHit()) {
                        if (cell.isHasShip()) {
                            if (cell.getShip() != null && cell.getShip().isSunk()) {
                                String color = getShipColor(cell.getShip());
                                System.out.print(color + " S " + View.RESET);
                            }
                            else {
                                System.out.print(View.SHIP + " S ");
                            }
                        }
                        else {
                            System.out.print(View.MISS + " X " + View.RESET);
                        }
                    }
                    else {
                        System.out.print(View.WATER + " . " + View.RESET);
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private String getShipColor(Ship ship) {
        if (ship == null) return View.TYPE_1;

        int len = ship.getId();
        switch (len) {
            case 2:  return View.TYPE_1;
            case 3:  return View.TYPE_2;
            case 4:  return View.TYPE_3;
            case 5:  return View.TYPE_4;
            default: return View.TYPE_5;
        }
    }

    public boolean isValid(int x, int y, boolean ingame) {
        if (x<=0 || x>size || y<=0 || y>size) return false;
        if (!ingame && grid[x][y].isHasShip()) return false;
        if (ingame && grid[x][y].isHit()) return false;

        return true;
    }

    public boolean placeShip(Ship ship, int x1, int y1, boolean horizontal) {
        int x2 = x1, y2 = y1;
        int length = ship.getLength();

        if (horizontal) y2 += length-1;
        else x2 += length-1;

        for (int i = x1; i <= x2; ++i) {
            for (int j = y1; j <= y2; ++j) {
                if (!isValid(i, j, false)) {
                    return false;
                }
            }
        }

        for (int i=x1; i<=x2; ++i) {
            for (int j=y1; j<=y2; ++j) {
                grid[i][j].setShip(ship);
            }
        }

        return true;
    }

    public boolean receiveHit(int x, int y) {
        if (!grid[x][y].isHit()) {
            grid[x][y].setHit();

            if (grid[x][y].isHasShip()) {
                grid[x][y].getShip().countHit();
                return true;
            }
        }
        return false;
    }

    public Cell getCell(int x, int y) {
        return grid[x][y];
    }
}