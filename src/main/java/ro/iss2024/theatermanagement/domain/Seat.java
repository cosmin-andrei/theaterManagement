package ro.iss2024.theatermanagement.domain;

public class Seat extends Entity<Long>{

    private int row;
    private int number;

    private SeatCategory category;

    public Seat(int row, int number, SeatCategory category) {
        this.row = row;
        this.number = number;
        this.category = category;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public SeatCategory getCategory() {
        return category;
    }

    public void setCategory(SeatCategory category) {
        this.category = category;
    }
}
