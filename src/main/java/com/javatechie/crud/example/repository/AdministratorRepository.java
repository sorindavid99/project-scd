package com.javatechie.crud.example.repository;

import com.javatechie.crud.example.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator,Integer> {
}
