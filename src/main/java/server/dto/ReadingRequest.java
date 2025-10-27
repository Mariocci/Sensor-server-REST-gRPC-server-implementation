package server.dto;

import lombok.Data;


@Data
public class ReadingRequest {
    private double temperature;
    private double pressure;
    private double humidity;
    private double co;
    private double so2;
}