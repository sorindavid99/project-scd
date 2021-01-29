package com.javatechie.crud.example;

import com.javatechie.crud.example.entity.Location;
import com.javatechie.crud.example.entity.User;
import com.javatechie.crud.example.repository.LocationRepository;
import com.javatechie.crud.example.repository.UserRepository;
import com.javatechie.crud.example.service.LocationService;
import com.javatechie.crud.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCrudExampleApplication  {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    public static void main(String[] args) {

        SpringApplication.run(SpringBootCrudExampleApplication.class, args);

    }
  //*  @Override
  //  public void run(String... args) throws Exception{
  //      User user = userService.getUserById(1);
    //     Location location = new Location("AlbaIulia","27Martie2015");
   //     user.getLocations().add(location);
    //    this.userRepository.save(user);
//
   // }

}
