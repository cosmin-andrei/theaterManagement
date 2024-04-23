package ro.iss2024.theatermanagement.service;

import ro.iss2024.theatermanagement.domain.*;
import ro.iss2024.theatermanagement.repository.IRepository;
import ro.iss2024.theatermanagement.repository.irepository.IAdminRepo;
import ro.iss2024.theatermanagement.repository.irepository.ISpectatorRepo;

import java.util.UUID;

public class Service implements IService {

    private final IAdminRepo adminRepo;
    private final ISpectatorRepo spectatorRepo;
    private final IRepository<Long, Performance> performanceRepo;
    private final IRepository<Long, SeatCategory> seatCategoryRepo;
    private final IRepository<Long, Reservation> reservationRepo;
    private final IRepository<Long, Seat> seatRepo;

    private final IRepository<Long, SeatReserved> seatReservedRepo;

    public Service(IAdminRepo adminRepo, ISpectatorRepo spectatorRepo, IRepository<Long, Performance> performanceRepo, IRepository<Long, SeatCategory> seatCategoryRepo, IRepository<Long, Reservation> reservationRepo, IRepository<Long, Seat> seatRepo, IRepository<Long, SeatReserved> seatReservedRepo) {
        this.adminRepo = adminRepo;
        this.spectatorRepo = spectatorRepo;
        this.performanceRepo = performanceRepo;
        this.seatCategoryRepo = seatCategoryRepo;
        this.reservationRepo = reservationRepo;
        this.seatRepo = seatRepo;
        this.seatReservedRepo = seatReservedRepo;
    }


    @Override
    public boolean login_admin(User user) {
        return adminRepo.findByUsername(user.getUsername()).isPresent();
    }

    @Override
    public boolean login_spectator(User user) {
        return spectatorRepo.findByUsername(user.getUsername()).isPresent();
    }

    @Override
    public Spectator findSpectator(String username) {
        return spectatorRepo.findByUsername(username).orElse(null);
    }


    @Override
    public void addPerformance(Performance performance) {
        try {
            UUID uuid = UUID.randomUUID();
            long id = uuid.getMostSignificantBits() & Long.MAX_VALUE;
            performance.setId(id);
            performanceRepo.save(performance);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePerformance(Performance performance) {
        try {
            performanceRepo.update(performance);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePerformance(Performance performance) {
        try {
            performanceRepo.delete(performance.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updateSeatCategory(SeatCategory seatCategory) {
        try{
            seatCategoryRepo.update(seatCategory);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }

    @Override
    public void addReservation(Reservation reservation) {
        try{
            UUID uuid = UUID.randomUUID();
            long id = uuid.getMostSignificantBits() & Long.MAX_VALUE;
            reservation.setId(id);
            reservationRepo.save(reservation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addSeatReserved(SeatReserved seatReserved) {
        try{
            UUID uuid = UUID.randomUUID();
            long id = uuid.getMostSignificantBits() & Long.MAX_VALUE;
            seatReserved.setId(id);
            seatReservedRepo.save(seatReserved);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
