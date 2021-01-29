package com.javatechie.crud.example.controller;

import com.javatechie.crud.example.config.exception.MyAppException;
import com.javatechie.crud.example.dto.*;
import com.javatechie.crud.example.entity.Administrator;
import com.javatechie.crud.example.entity.Dates;
import com.javatechie.crud.example.entity.Location;
import com.javatechie.crud.example.entity.User;
import com.javatechie.crud.example.repository.UserRepository;
import com.javatechie.crud.example.service.AdministratorService;
import com.javatechie.crud.example.service.LocationService;
import com.javatechie.crud.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private LocationService lservice;
    @Autowired
    private AdministratorService administratorService;

    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody final User user) throws MyAppException{
        if(user.getFirstName() == null) {
            throw new MyAppException(400, "First name is mandatory");
        }
        if(user.getLastName() == null) {
            throw new MyAppException(400, "Last name is mandatory");
        }
        if(user.getEmail() == null) {
            throw new MyAppException(400, "Email is mandatory");
        }
        if(user.getPassword() == null) {
            throw new MyAppException(400, "Password is mandatory");
        }
        if(!service.isValid(user.getEmail())){
            throw new MyAppException(400, "Invalid email format");
        }
        if(!service.checkEmail(user.getEmail())){
            throw new MyAppException(409, "Email is already used by a different user");
        }
        service.saveUser(user);
        int createdUserId = user.getId();
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()// http://localhost:8080/users
                .path("/{id}") // http://localhost:8080/users/{id}
                .buildAndExpand(createdUserId) // http://localhost:8080/users/1
                .toUri();

        return ResponseEntity.created(location).build();
    }
    @GetMapping("/users")
    public List<User> findAllUsers(){
        return service.getUsers();
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> findUserById(@PathVariable int id) throws  MyAppException{
        User user = service.getUserById(id);
        if(user==null){
            throw new MyAppException(404, "User not found");
        }
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) throws MyAppException{
        boolean ok = service.checkUserById(id);
        if(!ok){
            throw new MyAppException(404,"User not found");
        }
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user,@PathVariable int id) throws MyAppException{
        if(user.getFirstName() == null) {
            throw new MyAppException(400, "First name is mandatory");
        }
        if(user.getLastName() == null) {
            throw new MyAppException(400, "Last name is mandatory");
        }
        if(user.getEmail() == null) {
            throw new MyAppException(400, "Email is mandatory");
        }
        if(user.getPassword() == null) {
            throw new MyAppException(400, "Password is mandatory");
        }
        if(!service.isValid(user.getEmail())){
            throw new MyAppException(400, "Invalid email format");
        }
        if(!service.checkEmail(user.getEmail())){
            throw new MyAppException(409, "Email is already used by a different user");
        }
        User u = service.getUserById(id);
        if(user==null){
            throw new MyAppException(404, "User not found");
        }
        User s = service.updateUserById(user,id);
        return ResponseEntity.ok(s);
    }
    @GetMapping("/user/{name}")
    public User findUserByName(@PathVariable String name){
        return service.getUserByName(name);
    }

    @PostMapping("/locations/{email}")
    public ResponseEntity<Location> addLocationToUser(@PathVariable String email, @RequestBody Location loc) {
         service.addLocationToUser(email, loc);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()//
                .path("/{email}") //
                .buildAndExpand(email)//
                .toUri();

        return ResponseEntity.created(location).build();
    }
    @RequestMapping("/getLocationsFiltred")
    @GetMapping
    public List<Location> getLocationsFiltred(
            @RequestParam(value = "email") final String email,
            @RequestParam(value = "startDate") @DateTimeFormat(pattern="yyyy-MM-dd") final Date startDate,
            @RequestParam(value = "endDate") @DateTimeFormat(pattern="yyyy-MM-dd") final Date endDate) throws MyAppException {
        Dates dates = new Dates();
        dates.setStartDate(startDate);
        dates.setEndDate(endDate);
        User eUser = null;
        List<User> users = service.getUsers();
        for(User user : users){
            if(user.getEmail().equals(email)){
                eUser = user;
                break;
            }
        }
        if(service.checkEmail(eUser.getEmail())){
            throw new MyAppException(404, "This email is not registered");
        }
        return service.getLocationsFromStartDateToEndDate(email,dates);
    }
    @GetMapping("/locations")
    public List<Location> getLocations(){
        return lservice.showLocations();
    }
    @PostMapping("/addAdmin")
    public Administrator addAdmin(@RequestBody Administrator administrator){
        return administratorService.saveAdmin(administrator);
    }
    @GetMapping("/administrators")
    public List<Administrator> getAdmin(){
        return administratorService.getAdministrators();
    }

    @RequestMapping("/checkUser")
    @PostMapping
    public UserExistingOrNotDto findUserAmongUsers(@RequestBody UserLoginDetailsResponse userLoginDetailsResponse){
            return service.checkUser(userLoginDetailsResponse);
    }
    @GetMapping("/users/pagination")
    public ResponseEntity<PaginatedUserDetailsResponseDto> filterUsersWithPagination(
            @RequestParam(name = "firstName", required = false) final String firstName,
            @RequestParam(name = "lastName", required = false) final String lastName,
            @RequestParam(name = "page", required = false, defaultValue = "1") final Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "15") final Integer pageSize
    ) {
        List<User> users = service.getUsers();
        final PaginatedUserDetailsResponseDto responseData = new PaginatedUserDetailsResponseDto();
        responseData.setPage(page);
        responseData.setPageSize(pageSize);
        responseData.setTotalPages(100);
        responseData.setTotalCount(1000);
        responseData.setData(users);
        return ResponseEntity.ok(responseData);
    }
    }
