package com.javatechie.crud.example.service;

import com.javatechie.crud.example.entity.Location;
import com.javatechie.crud.example.entity.User;
import com.javatechie.crud.example.repository.LocationRepository;
import com.javatechie.crud.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;
    public List<Location> showLocations(){
        return locationRepository.findAll();
    }

}
