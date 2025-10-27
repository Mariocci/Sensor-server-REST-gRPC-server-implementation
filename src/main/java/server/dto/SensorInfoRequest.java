package server.dto;

import lombok.Data;


@Data
public class SensorInfoRequest {
    private double latitude;
    private double longitude;
    private String ip;
    private int port;
}