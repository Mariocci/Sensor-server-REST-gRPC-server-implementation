package server.service;

import server.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.repository.SensorRepository;

import java.util.List;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


    public Sensor registerSensor(Sensor sensor) {
        System.out.println("Registering sensor: " + sensor);
        return sensorRepository.save(sensor);
    }


    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }


    public Sensor getNearest(Long referenceSensorId) {
        Sensor referenceSensor = sensorRepository.findById(referenceSensorId)
                .orElseThrow(() -> new IllegalArgumentException("Sensor with ID " + referenceSensorId + " not found"));

        List<Sensor> allSensors = sensorRepository.findAll();

        Sensor nearestSensor = null;
        double minimumDistanceInKm = Double.MAX_VALUE;

        for (Sensor currentSensor : allSensors) {
            if (currentSensor.getId().equals(referenceSensorId)) {
                continue;
            }

            double distanceBetweenSensors = calculateDistanceInKm(
                    referenceSensor.getLatitude(), referenceSensor.getLongitude(),
                    currentSensor.getLatitude(), currentSensor.getLongitude()
            );

            if (distanceBetweenSensors < minimumDistanceInKm) {
                minimumDistanceInKm = distanceBetweenSensors;
                nearestSensor = currentSensor;
            }
        }

        return nearestSensor;
    }

    private double calculateDistanceInKm(double latitude1, double longitude1, double latitude2, double longitude2) {
        final int EARTH_RADIUS_KM = 6371;

        double deltaLatitudeRadians = Math.toRadians(latitude2 - latitude1);
        double deltaLongitudeRadians = Math.toRadians(longitude2 - longitude1);

        double a = Math.sin(deltaLatitudeRadians / 2) * Math.sin(deltaLatitudeRadians / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(deltaLongitudeRadians / 2) * Math.sin(deltaLongitudeRadians / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}