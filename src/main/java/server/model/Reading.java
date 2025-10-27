package server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@ToString(exclude = "sensor")
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperature;
    private double pressure;
    private double humidity;
    private double co;
    private double so2;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    @JsonBackReference
    private Sensor sensor;

    public Reading() {
    }

    public Reading(double temperature, double pressure, double humidity, double co, double so2) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.co = co;
        this.so2 = so2;
    }
}
