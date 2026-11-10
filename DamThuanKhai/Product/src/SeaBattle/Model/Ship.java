package SeaBattle.Model;

public class Ship {
    private ShipType type;
    private int hitCount;
    private int id;
    private boolean announcedSunk = false; // Đánh dấu đã thông báo phá hủy tàu này chưa

    public Ship() {}

    public Ship(ShipType type, int id) {
        this.type = type;
        this.hitCount = 0;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isSunk() {
        return (type.getLength() == hitCount);
    }

    public String getName() {
        return type.getName();
    }

    public int getLength() {
        return type.getLength();
    }

    public void countHit() {
        hitCount++;
    }

    public boolean isAnnouncedSunk() {
        return announcedSunk;
    }

    public void setAnnouncedSunk(boolean announcedSunk) {
        this.announcedSunk = announcedSunk;
    }
}