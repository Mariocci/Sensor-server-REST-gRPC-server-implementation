package server.dto;

import lombok.Data;


@Data
public class SensorInfoResponse {
    private Long id;
    private double latitude;
    private double longitude;
    private String ip;
    private int port;

}