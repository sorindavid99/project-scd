package com.javatechie.crud.example.repository;

import com.javatechie.crud.example.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Integer> {
}
