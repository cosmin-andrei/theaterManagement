package ro.iss2024.theatermanagement.domain;

public class SeatReserved extends Entity<Long>{

    private Seat seat;
    private Reservation reservation;

    public SeatReserved(Seat seat, Reservation reservation) {
        this.seat = seat;
        this.reservation = reservation;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
