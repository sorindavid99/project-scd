package com.javatechie.crud.example.dto;

import java.util.Date;

public class LocationDetailsResponseDto {
    private String longitude;
    private String latitude;
    private Date data;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }


}
