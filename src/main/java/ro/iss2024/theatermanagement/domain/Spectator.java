package ro.iss2024.theatermanagement.domain;

public class Spectator extends User{

    private String name;

    public Spectator(String username, String email, String password, String name) {
        super(username, email, password);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
