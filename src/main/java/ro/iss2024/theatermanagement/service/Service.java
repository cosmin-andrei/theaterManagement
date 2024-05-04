package ro.iss2024.theatermanagement.service;

import ro.iss2024.theatermanagement.controller.utils.SeatDTO;
import ro.iss2024.theatermanagement.domain.*;
import ro.iss2024.theatermanagement.observer.Observable;
import ro.iss2024.theatermanagement.observer.Observer;
import ro.iss2024.theatermanagement.repository.IRepository;
import ro.iss2024.theatermanagement.repository.irepository.IAdminRepo;
import ro.iss2024.theatermanagement.repository.irepository.ISpectatorRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Service implements IService, Observable {

    private final IAdminRepo adminRepo;
    private final ISpectatorRepo spectatorRepo;
    private final IRepository<Long, Performance> performanceRepo;
    private final IRepository<Long, SeatCategory> seatCategoryRepo;
    private final IRepository<Long, Reservation> reservationRepo;
    private final IRepository<Long, Seat> seatRepo;

    private final IRepository<Long, SeatReserved> seatReservedRepo;


    List<Observer> observers = new ArrayList<>();

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
        Admin admin = adminRepo.findByUsername(user.getUsername()).orElse(null);
        if (admin != null) {
            return admin.getPassword().equals(user.getPassword());
        }
        return false;
    }

    @Override
    public boolean login_spectator(User user) {
        Spectator spectator = spectatorRepo.findByUsername(user.getUsername()).orElse(null);
        if (spectator != null) {
            return spectator.getPassword().equals(user.getPassword());
        }
        return false;
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
            notifyAllObservers();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePerformance(Performance performance) {
        try {
            performanceRepo.update(performance);
            notifyAllObservers();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePerformance(Performance performance) {
        try {
            performanceRepo.delete(performance.getId());
            notifyAllObservers();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updateSeatCategory(SeatCategory seatCategory) {
        try {
            seatCategoryRepo.update(seatCategory);
            notifyAllObservers();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }

    private List<Seat> getAllSeats() throws SQLException {
        List<Seat> seats = new ArrayList<>();
        for (Seat seat : seatRepo.findAll()) {
            SeatCategory seatCategory = seatCategoryRepo.findOne(seat.getCategory().getId()).orElse(null);
            seat.setCategory(seatCategory);
            seats.add(seat);
        }
        return seats;
    }

    @Override
    public List<SeatDTO> getSeats(Performance performance) throws SQLException {
        List<SeatReserved> seatReserved = (List<SeatReserved>) seatReservedRepo.findAll();
        List<SeatDTO> seatDTOS = new ArrayList<>();
        for (Seat seat : seatRepo.findAll()) {
            boolean reserved = false;

            SeatCategory seatCategory = seatCategoryRepo.findOne(seat.getCategory().getId()).orElse(null);
            seat.setCategory(seatCategory);

            for(SeatReserved seatRes : seatReserved){
               Reservation reservation = reservationRepo.findOne(seatRes.getReservation().getId()).orElse(null);
               seatRes.setReservation(reservation);
            }

            for (SeatReserved seatRes : seatReserved) {
                if (seatRes.getSeat().getId().equals(seat.getId()) && seatRes.getReservation().getPerformance().getId().equals(performance.getId())) {
                    reserved = true;
                    break;
                }
            }
            if (!reserved) {
                seatDTOS.add(new SeatDTO(seat.getCategory().getName(), seat.getRow(), seat.getNumber(), seat.getCategory().getPrice(), "free"));
            } else {
                seatDTOS.add(new SeatDTO(seat.getCategory().getName(), seat.getRow(), seat.getNumber(), seat.getCategory().getPrice(), "reserved"));
            }

        }
        return sortSeats(seatDTOS);
    }


    private List<SeatDTO> sortSeats(List<SeatDTO> seatDTOS) {
        List<SeatDTO> freeSeats = seatDTOS.stream()
                .filter(seat -> seat.getStatus().equals("free"))
                .collect(Collectors.toList());
        List<SeatDTO> reservedSeats = seatDTOS.stream()
                .filter(seat -> seat.getStatus().equals("reserved"))
                .collect(Collectors.toList());

        Comparator<SeatDTO> comparator = Comparator.comparing(SeatDTO::getStatus)
                .thenComparing(SeatDTO::getLodge)
                .thenComparing(SeatDTO::getRow)
                .thenComparing(SeatDTO::getNumber);
        freeSeats.sort(comparator);
        reservedSeats.sort(Comparator.comparing(SeatDTO::getStatus));

        List<SeatDTO> sortedSeats = new ArrayList<>(freeSeats);
        sortedSeats.addAll(reservedSeats);

        return sortedSeats;
    }



    @Override
    public void addReservation(Reservation reservation, SeatDTO seat) {
        try {
            UUID uuid = UUID.randomUUID();
            long id = uuid.getMostSignificantBits() & Long.MAX_VALUE;
            reservation.setId(id);
            reservationRepo.save(reservation);
            Seat s = getSeat(seat);
            SeatReserved seatReserved = new SeatReserved(s, reservation);
            UUID uuid1 = UUID.randomUUID();
            long id1 = uuid1.getMostSignificantBits() & Long.MAX_VALUE;
            seatReserved.setId(id1);
            seatReservedRepo.save(seatReserved);
            notifyAllObservers();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Performance> getAllPerformances() throws SQLException {
        LocalDate today = LocalDate.now();
        List<Performance> allPerformances = (List<Performance>) performanceRepo.findAll();

        List<Performance> performancesStartingToday = allPerformances.stream()
                .filter(performance -> performance.getDate().toLocalDateTime().toLocalDate().isAfter(today.minusDays(1)))
                .sorted(Comparator.comparing(Performance::getDate))
                .collect(Collectors.toList());

        return performancesStartingToday;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyAllObservers() throws SQLException {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public Performance getPlayOfDay() throws SQLException {
        List<Performance> performances = getAllPerformances();
        LocalDate currentDate = LocalDate.now();
        for (Performance performance : performances) {
            LocalDate performanceDate = performance.getDate().toLocalDateTime().toLocalDate();
            if (performanceDate.equals(currentDate)) {
                return performance;
            }
        }
        return null;
    }

    @Override
    public Seat getSeat(SeatDTO seatDTO) throws SQLException {
        Seat s = null;
        for (Seat seat: seatRepo.findAll()) {
            if(seat.getRow() == seatDTO.getRow() && seat.getNumber() == seatDTO.getNumber()){
                s=seat;
                break;
            }
        }

        assert s != null;
        s.setCategory(seatCategoryRepo.findOne(s.getCategory().getId()).orElse(null));
        return s;

    }

    public List<SeatCategory> getSeatsCategory() throws SQLException {
        return (List<SeatCategory>) seatCategoryRepo.findAll();
    }
}
