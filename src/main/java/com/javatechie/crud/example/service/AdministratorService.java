package com.javatechie.crud.example.service;


import com.javatechie.crud.example.entity.Administrator;
import com.javatechie.crud.example.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository adminRepository;

    public Administrator saveAdmin(Administrator administrator){
        return adminRepository.save(administrator);
    }
    public List<Administrator> getAdministrators(){
        return adminRepository.findAll();
    }
}
