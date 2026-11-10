package SeaBattle.Model;
import java.util.Random;

public class BotPlayer extends Player {
    private int[][] Stack; // Stack lưu các tọa độ ô có thuyền ĐÃ BỊ BẮN RỒI
    private int[][] prevDir;
    private int top;
    private boolean isReversed = false;

    public int[][] directions = new int[][]{
            {-1, 0}, // 0: Lên
            {1, 0},  // 1: Dưới
            {0, -1}, // 2: Trái
            {0, 1}   // 3: Phải
    };

    public BotPlayer(String name) {
        super(name);
        Stack = new int[100][2];
        prevDir = new int[20][20];
        top = 0;
    }

    public void push(int x, int y) {
        for (int i = 0; i < top; i++) {
            if (Stack[i][0] == x && Stack[i][1] == y) {
                return;
            }
        }
        if (top < 100) {
            Stack[top][0] = x;
            Stack[top][1] = y;
            top++;
        }
    }

    public void pop() {
        if (top > 0) {
            top--;
            isReversed = !isReversed;
        }
    }

    public int[] getNextShot(Board oppBoard) {
        int size = oppBoard.getSize();

        if (top > 0) {
            for (int i = top - 1; i >= 0; i--) {
                int x = Stack[i][0];
                int y = Stack[i][1];
                if (oppBoard.getCell(x, y).getShip().isSunk()) {
                    pop();
                    continue;
                }

                int dir = prevDir[x][y];

                int nx = x + directions[dir][0];
                int ny = y + directions[dir][1];

                if (oppBoard.isValid(nx, ny, true)) {
                    prevDir[nx][ny] = dir;
                    return new int[]{nx, ny};
                }

                for (int d = 0; d < directions.length; d++) {
                    nx = x + directions[d][0];
                    ny = y + directions[d][1];

                    if (oppBoard.isValid(nx, ny, true)) {
                        prevDir[nx][ny] = d;
                        return new int[]{nx, ny};
                    }
                }
/*
                int activeDir = isReversed ? ((dir+1)%5) : dir;
                int nx = x + directions[activeDir][0];
                int ny = y + directions[activeDir][1];

                while (nx > 0 && ny > 0 && nx <= size && ny <= size && isReversed) {
                    prevDir[nx][ny] = dir;
                    return new int[]{nx, ny};
                }

                if (!isReversed) {
                    isReversed = true;
                    return getNextShot(oppBoard);
                }

                for (int d = 0; d < directions.length; d++) {
                    nx = x + directions[d][0];
                    ny = y + directions[d][1];

                    if (oppBoard.isValid(nx, ny, true)) {
                        prevDir[nx][ny] = d;
                        return new int[]{nx, ny};
                    }
                }

 */
            }
        }

        while (true) {
            int x = random.nextInt(size) + 1;
            int y = random.nextInt(size) + 1;
            if (oppBoard.isValid(x, y, true)) {
                prevDir[x][y] = 0;
                return new int[]{x, y};
            }
        }
    }
}