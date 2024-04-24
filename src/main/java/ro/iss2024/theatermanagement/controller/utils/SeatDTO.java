package ro.iss2024.theatermanagement.controller.utils;

public class SeatDTO {

    private String lodge;
    private int row;
    private int number;
    private double price;
    private String status;

    public String getLodge() {
        return lodge;
    }

    public void setLodge(String lodge) {
        this.lodge = lodge;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SeatDTO(String lodge, int row, int number, double price, String status) {
        this.lodge = lodge;
        this.row = row;
        this.number = number;
        this.price = price;
        this.status = status;
    }
}
