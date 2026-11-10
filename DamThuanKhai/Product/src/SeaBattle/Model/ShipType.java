package SeaBattle.Model;

public enum ShipType {
    DOUBLE("Double", 2),
    TRIPLE("Triple", 3),
    QUARDA("Quarda", 4),
    PENTA("Penta", 5);

    private final String name;
    private final int length;

    ShipType(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }
}