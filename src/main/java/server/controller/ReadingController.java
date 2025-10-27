package server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.dto.ReadingRequest;
import server.model.Reading;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.ReadingService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sensors/{sensorId}/readings")
public class ReadingController {
    private final ReadingService readingService;
    private static final Logger logger = LoggerFactory.getLogger(SensorController.class);
    public ReadingController(ReadingService readingService) {
        this.readingService = readingService;
    }

    @GetMapping
    public ResponseEntity<List<Reading>> getReadingsBySensorId(@PathVariable Long sensorId) {
        try {
            List<Reading> readings = readingService.getReadingsBySensorId(sensorId);
            return ResponseEntity.ok(readings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> addReading(@PathVariable Long sensorId, @RequestBody ReadingRequest reading) {
        try {
            logger.info("Adding reading for sensor with ID {}: {}", sensorId, reading);
            Long readingId = readingService.addReading(sensorId, reading);
            return ResponseEntity.created(URI.create("/api/sensors/" + sensorId + "/readings/" + readingId)).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
