package ro.iss2024.theatermanagement.domain;

public class SeatCategory extends Entity<Long> {
    private String name;
    private double price;

    public SeatCategory(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
