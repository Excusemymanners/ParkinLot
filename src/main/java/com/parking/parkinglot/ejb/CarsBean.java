package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.CarPhotoDto;
import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.entities.CarPhoto;
import com.parking.parkinglot.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;


    public List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> dtos = new ArrayList<>();
        for (Car car : cars) {
            dtos.add(
                    new CarDto(car.getId(), car.getLicensePlate(), car.getParkingSpot(), car.getOwner().getUsername())
            );
        }
        return dtos;
    }

    public List<CarDto> findAllCars() {
        
        List<Car> cars = entityManager.createQuery("SELECT c FROM Car c ORDER BY c.owner.username", Car.class)
                .getResultList();
        return copyCarsToDto(cars);
    }

    public CarDto findById(Long carId) {
        LOG.info("findById");

        if (carId < 1) {
            carId = 1L;
        }

        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT p FROM Car p WHERE p.id = :id", Car.class).setParameter("id", carId);
            Car car = typedQuery.getSingleResult();
            return new CarDto(car.getId(), car.getLicensePlate(), car.getParkingSpot(), car.getOwner().getUsername());
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }


    public void createCar(String licensePlate, String parkingSpot, Long userId) {
        LOG.info("createCar");

        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);

        entityManager.persist(car);
    }

    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long userId) {
        LOG.info("Updating car with ID: " + carId);

        // 1. Gasim masina dupa carId (nu dupa userId!)
        Car car = entityManager.find(Car.class, carId);

        if (car != null) {
            car.setLicensePlate(licensePlate);
            car.setParkingSpot(parkingSpot);

            // 2. Gasim noul proprietar
            User newUser = entityManager.find(User.class, userId);

            // 3. Actualizam relatia
            if (car.getOwner() != null) {
                car.getOwner().getCars().remove(car);
            }
            car.setOwner(newUser);
            newUser.getCars().add(car);
        }
    }

    public void addPhotoToCar(Long carId, String filename, String fileType, byte[] fileContent) {
        LOG.info("addPhotoToCar");
        CarPhoto photo = new CarPhoto();
        photo.setFilename(filename);
        photo.setFileType(fileType);
        photo.setFileContent(fileContent);
        Car car = entityManager.find(Car.class, carId);
        if (car.getPhoto() != null) {
            entityManager.remove(car.getPhoto());
        }
        car.setPhoto(photo);

        photo.setCar(car);
        entityManager.persist(photo);
    }
    public CarPhotoDto findPhotoByCarId(Integer carId) {
        List<CarPhoto> photos = entityManager
                .createQuery("SELECT p FROM CarPhoto p where p.car.id = :id", CarPhoto.class)
                .setParameter("id", carId)
                .getResultList();
        if (photos.isEmpty()) {
            return null;
        }
        CarPhoto photo = photos.get(0); // the first element
        return new CarPhotoDto(photo.getId(), photo.getFilename(), photo.getFileType(),
                photo.getFileContent());
    }

    public void deleteCarsByIds(Collection<Long> carIds) {
        LOG.info("deleteCarsByIds");

        for (Long carId : carIds) {
            Car car = entityManager.find(Car.class, carId);
            entityManager.remove(car);
        }
    }
}
