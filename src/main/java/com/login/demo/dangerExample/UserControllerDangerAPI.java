package com.login.demo.dangerExample;

import com.login.demo.dto.UserDTO;
import com.login.demo.model.User;
import com.login.demo.services.AccountingService;
import com.login.demo.repository.UserRepository;
import com.login.demo.support.UserAlreadyExistsException;
import com.login.demo.support.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/home")
public class UserControllerDangerAPI {

    @Autowired
    UserRepository repository;

    @Autowired
    UserDangerDAO dangerDAO;
    @Autowired
    AccountingService service;

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login (@RequestBody UserDTO user){
        System.out.println(user.getUsername());
        if( !repository.existsByUsername(user.getUsername()) ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("There's no account associated with this username. Try another email address or create a new account.");
        }

        if(repository.existsByUsername(user.getUsername())) {
            List<User> users = dangerDAO.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Invalid Credentials!");
        }
    }


    /*@GetMapping(value = "/login1/{username}/{password}")
    public ResponseEntity<Object> login1 (@PathVariable(required = true, name="username") String username, @PathVariable(required = true, name="password")String password){
        System.out.println(username);
        if( repository.existsByUsername(username) ){
            List<User> user = dangerDAO.findByUsernameAndPassword(username, password);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("USER_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
    }*/

    @PostMapping(value = "/registration")
    public ResponseEntity<Object> registration (@RequestBody UserDTO user) throws UserAlreadyExistsException {
        if(repository.existsByUsername(user.getUsername()) ){
            return new ResponseEntity<>("EXISTS", HttpStatus.BAD_REQUEST);
        } else {
            User newUser = service.userSignUp(user.getUsername(), user.getPassword());
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
    }

    /*@GetMapping(value = "/registration1/{username}/{password}")
    public ResponseEntity<Object> registration1 (@PathVariable(required = true, name="username") String username, @PathVariable(required = true, name="password")String password) throws UserAlreadyExistsException {
        if( repository.existsByUsername(username) ){
            return new ResponseEntity<>("EXISTS", HttpStatus.BAD_REQUEST);
        } else {
            User newUser = service.userSignUp(username, password);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
    }*/


}
