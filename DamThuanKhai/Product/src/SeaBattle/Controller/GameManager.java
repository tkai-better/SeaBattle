package SeaBattle.Controller;

import SeaBattle.Model.*;
import SeaBattle.View.*;
import java.io.*;

public class GameManager {
    private Player p1, p2;

    public void startGame() {
        while (true) {
            p1 = null;
            p2 = null;

            View.clearScreen();
            View.showStartMenu();
            selectMode();

            if (p1 == null || p2 == null) {
                break;
            }

            View.clearScreen();

            System.out.print("\n--- GIAI DOAN CHIEN DAU ---\n");
            playTurn(p1, p2);

            System.out.println("\n-----------------------------------");
            System.out.println("Nhan Enter de xem lai Board");
            Input.getLine();

            System.out.println("\n------ BANG CUA " + p1.getName() + " ------\n");
            p1.getBoard().showBoard(false);

            System.out.println("\n------ BANG CUA " + p2.getName() + " ------\n");
            p2.getBoard().showBoard(false);

            System.out.println("\n-----------------------------------");
            System.out.println("Nhan Enter de quay tro lai menu chinh...");
            Input.getLine();
        }
    }

    public void selectMode() {
        int mode = Input.getInt("\nChon che do choi (1/2): ");
        Input.getLine();

        switch (mode) {
            case 1:
                p1 = new Player(Input.getString("\nNhap ten nguoi choi 1: "));
                p2 = new Player(Input.getString("Nhap ten nguoi choi 2: "));
                setupPhase(false);
                break;

            case 2:
                p1 = new Player(Input.getString("\nNhap ten nguoi choi: "));
                p2 = new BotPlayer("Bot AI");
                setupPhase(true);
                break;

            default:
                View.clearScreen();
                View.showMessage("\n\nCam on ban da choi game, vui long danh gia 5* nhe!\n");;
        }
    }

    public void setupPhase(boolean isPVE) {
        View.clearScreen();
        System.out.println("\n--- GIAI DOAN CHUAN BI DAT TAU ---");

        int size;
        while (true) {
            size = Input.getInt("\nNhap kich co bang NxN (N >= 8): ");
            Input.getLine();
            if (size >= 8) break;
            else {
                System.out.println("Sai kich co roi, nhap lai di");
            }
        }

        p1.setBoard(size);
        p2.setBoard(size);
        setupPlayerShips(p1);

        if(!isPVE) {
            System.out.println("\nNhan Enter de chuyen sang luot dat tau cua nguoi choi " + p2.getName() + "...");
            Input.getLine();
            View.clearScreen();
            setupPlayerShips(p2);
        }

        else {
            p2.autoPlaceShips();
            System.out.println("\nNhan Enter de xem bang cua Bot...");
            Input.getLine();
            View.clearScreen();
            View.showMessage("Bot AI da tu dong dat xong tau!\n");
            View.delay(2000);
            p2.getBoard().showBoard(false);
        }

        System.out.println("\nNhan Enter de bat dau vao tran dau...");
        Input.getLine();
    }

    private void setupPlayerShips(Player player) {
        View.clearScreen();
        System.out.println("\nLuot dat tau cua " + player.getName());
        System.out.println("1. Tu dat tau thu cong");
        System.out.println("2. Dat tau ngau nhien");
        int choice = Input.getInt("Chon cach dat tau (1-2): ");
        Input.getLine();

        if (choice == 2) {
            player.autoPlaceShips();
            System.out.println("\n" + player.getName() + " da tu dong dat xong tau ngau nhien!");
            System.out.println("Nhan Enter de xem bang tau...");
            Input.getLine();

            View.delay(2000);
            player.getBoard().showBoard(false);
            return;
        }

        for (Ship ship : player.getShips()) {
            View.delay(3000);
            System.out.println("\nNhan Enter de tiep tuc dat tau...");
            Input.getLine();
            View.clearScreen();

            player.getBoard().showBoard(false);

            boolean isplaced = false;
            System.out.println("Dat tau: " + ship.getName() + " (Do dai: " + ship.getLength() + ")");
            System.out.println("Buoc 1: nhap 2 so la toa do (X, Y)");
            System.out.println("Buoc 2: nhap 1 so nguyen Z bieu dien chieu dat tau, 1 - ngang, 2 - doc\n");

            while (!isplaced) {
                int X = Input.getInt("X = ");
                int Y = Input.getInt("Y = ");
                int Z = Input.getInt("Z = ");
                System.out.println();
                Input.getLine();

                boolean horizontal = (Z != 2);

                if ((Z == 1 || Z == 2) && player.getBoard().placeShip(ship, X, Y, horizontal)) {
                    isplaced = true;
                }

                else {
                    System.out.println("Vui long dat lai vi tri tau ");
                }
            }
        }
        System.out.println("\nDa dat xong tat ca cac tau! Nhan Enter de xem lai bang cua ban...");
        Input.getLine();
        View.clearScreen();
        player.getBoard().showBoard(false);
    }

    public void playTurn(Player attacker, Player defender) {
        while (true) {
            int X, Y;
            View.delay(2000);
            View.clearScreen();

            defender.getBoard().showBoard(true);
            boolean shootOnTarget = false;

            System.out.println("Luot ban cua nguoi choi " + attacker.getName());

            if (attacker instanceof BotPlayer) {
                View.delay(3000);

                int[] shot = ((BotPlayer) attacker).getNextShot(defender.getBoard());
                X = shot[0];
                Y = shot[1];
                System.out.println("\n[Bot AI] ban vao toa do: (" + X + ", " + Y + ")\n");

                View.delay(3000);
            }

            else {
                System.out.println("Nhap toa do ban (X,Y)");

                while (true) {
                    X = Input.getInt("X = ");
                    Y = Input.getInt("Y = ");

                    System.out.println();
                    Input.getLine();

                    if (defender.getBoard().isValid(X, Y, true)) {
                        break;
                    }
                    System.out.println("Vui long nhap lai toa do ban hop le (X,Y)");
                }
            }

            shootOnTarget = defender.getBoard().receiveHit(X, Y);
            View.delay(3000);

            if (shootOnTarget) {
                Ship hitShip = defender.getBoard().getCell(X, Y).getShip();

                if (hitShip.isSunk() && !hitShip.isAnnouncedSunk()) {
                    hitShip.setAnnouncedSunk(true);

                    System.out.println("Da pha huy thanh cong tau "
                            + hitShip.getName() + " cua doi thu\n");
                }

                else if (!hitShip.isSunk()) {
                    System.out.println("Ban trung roi ne, ban tiep thoi\n");

                    if (attacker instanceof BotPlayer) {
                        ((BotPlayer) attacker).push(X, Y);
                    }
                }

                if (defender.isLose()) {
                    defender.getBoard().showBoard(true);
                    endGame(attacker);
                    return;
                }
            }

            else {
                defender.getBoard().showBoard(true);
                System.out.println("Ban truot roi, mat luot\n");

                Player temp = attacker;
                attacker = defender;
                defender = temp;
            }

            System.out.println("\nNhan Enter de chuyen sang luot choi tiep theo...");
            Input.getLine();

        }
    }

    public void endGame(Player player) {
        View.delay(3000);
        System.out.println("\n🎉 Chuc mung nguoi choi " + player.getName() + " da gianh chien thang!");
    }
}