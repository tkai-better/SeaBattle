package SeaBattle.Model;
import java.util.*;

public class Player {
    protected String name;
    protected Board board;
    protected Ship[] ships;
    protected Random random;

    public Player(String name) {
        this.name = name;
        ships = new Ship[5];
        random = new Random();
        initShips();
    }

    public String getName() {
        return name;
    }

    public void setBoard(int size) {
        board = new Board(size);
    }

    public void initShips() {
        ships[0] = new Ship(ShipType.DOUBLE, 1);
        ships[1] = new Ship(ShipType.DOUBLE, 2);
        ships[2] = new Ship(ShipType.TRIPLE, 3);
        ships[3] = new Ship(ShipType.QUARDA, 4);
        ships[4] = new Ship(ShipType.PENTA, 5);
    }

    public void autoPlaceShips() {
        for (Ship ship : getShips()) {
            boolean isPlaced = false;
            while (!isPlaced) {
                int x = random.nextInt(getBoard().getSize()) + 1;
                int y = random.nextInt(getBoard().getSize()) + 1;
                boolean horizontal = random.nextBoolean();
                isPlaced = getBoard().placeShip(ship, x, y, horizontal);
            }
        }
    }

    public Ship[] getShips() {
        return ships;
    }

    public boolean isLose() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public Board getBoard() {
        return board;
    }
}