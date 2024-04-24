package ro.iss2024.theatermanagement.controller.spectator;

import ro.iss2024.theatermanagement.domain.Performance;
import ro.iss2024.theatermanagement.domain.Spectator;
import ro.iss2024.theatermanagement.service.Service;

import java.sql.SQLException;

public class SeatsController {
    
    private Spectator spectator;
    private Service service;
    private Performance performance;
    
    public void setService(Service service, Spectator spectator) throws SQLException {
        this.service = service;
        this.spectator = spectator;
        this.performance = service.getPlayOfDay();
        initModel();
        initialize();
        
    }

    private void initialize() {
    }

    private void initModel() {
        
    }


}
