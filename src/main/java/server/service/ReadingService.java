package server.service;

import server.dto.ReadingRequest;
import server.model.Reading;
import server.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.repository.ReadingRepository;
import server.repository.SensorRepository;

import java.util.List;

@Service
public class ReadingService {

    private final ReadingRepository readingRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public ReadingService(ReadingRepository readingRepository, SensorRepository sensorRepository) {
        this.readingRepository = readingRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Reading> getReadingsBySensorId(Long sensorId) {
        return readingRepository.findBySensorId(sensorId);
    }

    public Long addReading(Long sensorId, ReadingRequest readingreq) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found"));

        if (readingreq.getTemperature() < -100 || readingreq.getTemperature() > 100) {
            throw new IllegalArgumentException("Invalid temperature");
        }

        Reading reading = new Reading(
                readingreq.getTemperature(),
                readingreq.getPressure(),
                readingreq.getHumidity(),
                readingreq.getCo(),
                readingreq.getSo2()
        );

        reading.setSensor(sensor);

        return readingRepository.save(reading).getId();
    }
}