package server.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString(exclude = "readings")
@Entity
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;
    private String ip;
    private int port;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Reading> readings = new ArrayList<>();

    public void addReading(Reading reading) {
        readings.add(reading);
        reading.setSensor(this);
    }
}
