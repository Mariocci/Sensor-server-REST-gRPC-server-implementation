    package server.controller;

    import server.dto.SensorInfoResponse;
    import server.model.Sensor;
    import org.springframework.http.*;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.bind.annotation.RestController;
    import server.service.SensorService;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.web.bind.annotation.*;
    import java.net.URI;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/api/sensors")
    public class SensorController {
        private final SensorService sensorService;
        private static final Logger logger = LoggerFactory.getLogger(SensorController.class);


        public SensorController(SensorService sensorService) {
            this.sensorService = sensorService;
            logger.info("SensorController bean created");
        }

        @PostMapping("/register")
        public ResponseEntity<Map<String, Object>> registerSensor(@RequestBody Sensor sensor) {
            try {
                logger.info("Registering sensor: {}", sensor);
                Sensor saved = sensorService.registerSensor(sensor);
                Map<String, Object> response = new HashMap<>();
                response.put("id", saved.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        @GetMapping
        public ResponseEntity<List<SensorInfoResponse>> getAllSensors() {
            List<SensorInfoResponse> allSensors = sensorService.getAllSensors();
            return ResponseEntity.ok(allSensors);
        }

        @GetMapping("/{id}/nearest")
        public ResponseEntity<SensorInfoResponse> getNearestSensors(@PathVariable Long id) {
            try {
                logger.info("Getting nearest sensor for sensor with ID {}", id);
                SensorInfoResponse nearestSensor = sensorService.getNearest(id);
                if (nearestSensor == null) {
                    return ResponseEntity.noContent().build();
                }
                logger.info("Found nearest sensor: {}", nearestSensor);
                return ResponseEntity.ok(nearestSensor);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.notFound().build();
            }
        }
    }
