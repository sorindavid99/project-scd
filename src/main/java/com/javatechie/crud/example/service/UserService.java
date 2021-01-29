package com.javatechie.crud.example.service;

import com.javatechie.crud.example.dto.UserDetailsResponseDto;
import com.javatechie.crud.example.dto.UserExistingOrNotDto;
import com.javatechie.crud.example.dto.UserLoginDetailsResponse;
import com.javatechie.crud.example.entity.Dates;
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
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private LocationRepository locationRepository;
    public User saveUser(User user){
        return repository.save(user);
    }
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public  boolean checkEmail(String email){
        List<User> users = repository.findAll();
        boolean ok = true;
        for(User user : users){
            if(user.getEmail().equals(email)){
                ok=false;
            }
        }
        return ok;
    }
    public List<User> saveUsers(List<User> users){
        return repository.saveAll(users);
    }
    public User register(String firstName, String lastName, String email,String password){
        User user  = new User(firstName, lastName, email, password);
        return repository.save(user);
    }
    public List<User> getUsers(){
        return repository.findAll();
    }

    public User getUserById(int id){
        return repository.findById(id).orElse(null);
    }
    public User getUserByName(String firstName){
        return repository.findByfirstName(firstName);
    }
    public User updateUserById(User user, int id){
        User existingUser = repository.findById(id).orElse(null);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return repository.save(existingUser);
    }
    public String deleteUser(int id){
        repository.deleteById(id);
        return "User removed !! " + id;
    }

    public String addLocationToUser(String email,Location location) {
        User eUser = null;
        List<User> users = repository.findAll();
        for(User user : users){
            if(user.getEmail().equals(email)){
                eUser = user;
                break;
            }
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
        sdf.format(date);
        location.setData(date);
        eUser.getLocations().add(location);
        repository.save(eUser);
        return "S-a adaugat locatia: " + location + "la user-ul " + eUser.getFirstName();
    }
    public UserExistingOrNotDto checkUser(UserLoginDetailsResponse userLoginDetailsResponse){
        UserExistingOrNotDto userExistingOrNotDto = new UserExistingOrNotDto();
        userExistingOrNotDto.setOk(false);
        List<User> users=repository.findAll();
        System.out.println(userLoginDetailsResponse.getEmail());
        for(User user: users){
            if(userLoginDetailsResponse.getEmail().equals(user.getEmail())&&userLoginDetailsResponse.getPassword().equals(user.getPassword())){
                userExistingOrNotDto.setOk(true);
                userExistingOrNotDto.setFirstName(user.getFirstName() );
                break;
            }
        }
        return userExistingOrNotDto;
    }
    public boolean checkUserById(int id){
        List<User> users = repository.findAll();
        boolean ok = false;
        for(User user: users){
            if(user.getId()==id){
                ok = true;
            }
        }
        return ok;
    }
    public List<Location> getLocationsFromStartDateToEndDate(String email, Dates dat){
        List<Location> returner = new ArrayList<>();
        User eUser = null;
        List<User> users = repository.findAll();
        for(User user : users){
            if(user.getEmail().equals(email)){
                  eUser = user;
                  break;
            }
        }
        List<Location> lista = eUser.getLocations();
        for(Location l : lista){
            SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy");
            Date date = l.getData();
            sdf.format(date);
            sdf.format(dat.getStartDate());
            sdf.format(dat.getEndDate());
            if(date.compareTo(dat.getStartDate())>0 && date.compareTo(dat.getEndDate())<0){
                returner.add(l);
            }
        }
        return returner;
    }

}
