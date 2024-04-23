package ro.iss2024.theatermanagement.domain;

public class Reservation extends Entity<Long> {

    private Spectator spectator;
    private Performance performance;

    public Reservation(Spectator spectator, Performance performance) {
        this.spectator = spectator;
        this.performance = performance;
    }

    public Spectator getSpectator() {
        return spectator;
    }

    public void setSpectator(Spectator spectator) {
        this.spectator = spectator;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }
}
