package ro.iss2024.theatermanagement.domain;

import java.sql.Timestamp;

public class Performance extends Entity<Long> {

    private String titlePlay;
    private int durationPlay;
    private String descriptionPlay;
    private Timestamp date;

    public Performance(String titlePlay, int durationPlay, String descriptionPlay, Timestamp date) {
        this.titlePlay = titlePlay;
        this.durationPlay = durationPlay;
        this.descriptionPlay = descriptionPlay;
        this.date = date;
    }

    public String getTitlePlay() {
        return titlePlay;
    }

    public void setTitlePlay(String titlePlay) {
        this.titlePlay = titlePlay;
    }

    public int getDurationPlay() {
        return durationPlay;
    }

    public void setDurationPlay(int durationPlay) {
        this.durationPlay = durationPlay;
    }

    public String getDescriptionPlay() {
        return descriptionPlay;
    }

    public void setDescriptionPlay(String descriptionPlay) {
        this.descriptionPlay = descriptionPlay;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
