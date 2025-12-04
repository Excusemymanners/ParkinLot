package org.example.parkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.parkinglot.common.CarDto;
import org.example.parkinglot.entities.Car;
import org.example.parkinglot.entities.User; // <--- IMPORT NOU NECESAR

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {
    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public void createCar(String licensePlate, String parkingSpot, Long userId) {
        LOG.info("createCar");

        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        // Găsim user-ul (proprietarul) în baza de date
        User user = entityManager.find(User.class, userId);

        // Dacă user-ul există, facem legătura
        if (user != null) {
            user.getCars().add(car); // Adăugăm mașina la lista userului
            car.setOwner(user);      // Setăm userul ca proprietar al mașinii
        }

        entityManager.persist(car); // Salvăm mașina în DB
    }

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> dtoList = new ArrayList<>();

        for (Car car : cars) {
            // Verificare pentru a evita NullPointerException dacă nu există owner
            String ownerName = (car.getOwner() != null) ? car.getOwner().getUsername() : "No Owner";

            CarDto dto = new CarDto(
                    car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    ownerName
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    public CarDto findById(Long id) {
        // Căutăm entitatea în baza de date
        Car car = entityManager.find(Car.class, id);

        if (car == null) {
            return null;
        }

        // Convertim Entitatea în DTO (pentru a o trimite la JSP)
        String ownerName = (car.getOwner() != null) ? car.getOwner().getUsername() : "";
        return new CarDto(car.getId(), car.getLicensePlate(), car.getParkingSpot(), ownerName);
}

    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long userId) {
        LOG.info("updateCar");

        // 1. Găsim mașina
        Car car = entityManager.find(Car.class, carId);

        // 2. Facem update la datele simple
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        // 3. Gestionăm schimbarea proprietarului
        // Găsim noul user
        User newUser = entityManager.find(User.class, userId);

        // Luăm vechiul user (doar pentru a fi siguri, deși nu e strict necesar să-l modificăm manual)
        User oldUser = car.getOwner();

        // Verificăm dacă proprietarul s-a schimbat
        if (oldUser != null && !oldUser.getId().equals(userId)) {
            // Aici era greșeala: NU apelăm oldUser.getCars().remove(car)
            // dacă User.java are orphanRemoval=true, pentru că șterge mașina!

            // Pur și simplu setăm noul owner.
            // JPA va detecta schimbarea și va face UPDATE car SET owner_id = ...
            car.setOwner(newUser);

            // Opțional: Putem adăuga mașina în lista noului user pentru consistență în memorie,
            // dar update-ul în bază e garantat de linia de mai sus.
            if (newUser != null) {
                newUser.getCars().add(car);
            }
        } else if (oldUser == null && newUser != null) {
            // Cazul în care nu avea proprietar înainte
            car.setOwner(newUser);
            newUser.getCars().add(car);
        }

        // Notă: Nu e nevoie de entityManager.persist(car) sau merge
        // pentru că 'car' este o entitate gestionată (managed),
        // modificările se propagă automat la sfârșitul tranzacției.
    }

    // În CarsBean.java
    public void deleteCarsByIds(List<Long> carIds) {
        LOG.info("deleteCarsByIds");
        for (Long id : carIds) {
            Car car = entityManager.find(Car.class, id);
            if (car != null) {
                // Dacă mașina are un proprietar, îl scoatem din listă înainte de ștergere
                // (pentru a evita probleme de chei străine, deși JPA ar trebui să se descurce)
                if (car.getOwner() != null) {
                    car.getOwner().getCars().remove(car);
                }
                entityManager.remove(car);
            }
        }
    }

}
