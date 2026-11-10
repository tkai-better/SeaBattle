package SeaBattle.View;

public class View {
    public static final String RESET = "\u001B[0m";

    // Màu chữ cho nước và bắn hụt
    public static final String WATER = "\u001B[34m";    // Xanh dương cho ô nước (.)
    public static final String MISS = "\u001B[41m\u001B[97m";
    // Màu nền cho các trạng thái tàu (Nền màu + Chữ trắng/đen tương phản cao)
    public static final String SHIP = "\u001B[32m";     // Xanh lá cho tàu an toàn (S)


    // Các màu nền cho tàu đã chìm (Nền màu khác nhau + chữ dễ nhìn)
    public static final String TYPE_1 = "\u001B[43m\u001B[30m"; // Nền Vàng + Chữ Đen (Rất rõ)
    public static final String TYPE_2 = "\u001B[45m\u001B[97m"; // Nền Tím + Chữ Trắng sáng
    public static final String TYPE_3 = "\u001B[46m\u001B[30m"; // Nền Cyan (Xanh ngọc) + Chữ Đen
    public static final String TYPE_4 = "\u001B[44m\u001B[97m"; // Nền Xanh dương đậm + Chữ Trắng sáng
    public static final String TYPE_5 = "\u001B[47m\u001B[30m"; // Nền Trắng + Chữ Đen
    public View() {}

    public static void clearScreen() {
        try {
            String operatingSystem = System.getProperty("os.name");
            if (operatingSystem.contains("Windows")) {
                // Lệnh xóa màn hình chuẩn của Windows CMD/PowerShell
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Lệnh xóa màn hình cho Linux/macOS
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Trường hợp lỗi thì in vài dòng trống để đỡ rối mắt
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void delay(int time) {
        try {
            Thread.sleep(time-2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void showStartMenu() {
        System.out.println("===================================");
        System.out.println("    CHAO MUNG DEN VOI SEABATTLE   ");
        System.out.println("===================================");
        System.out.println("1. Play new Game - PvP (2 Nguoi choi)");
        System.out.println("2. Play new Game - PvE (Choi voi Bot)");
        System.out.println("OUT Game");
    }

    public static void showMessage(String str) {
        System.out.print(str);
    }
}