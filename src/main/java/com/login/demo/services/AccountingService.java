package com.login.demo.services;

import com.login.demo.model.User;
import com.login.demo.repository.UserRepository;
import com.login.demo.support.UserAlreadyExistsException;
import com.login.demo.support.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountingService {

    @Autowired
    private UserRepository userRepository;

    //accesso di un utente trasmite credeziali
    @Transactional(readOnly = true)
    public User userSignIn(String username, String password) throws UserNotFoundException {
        if ( userRepository.existsByUsername(username)) {
            throw new UserNotFoundException();
        }
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User userSignUp(String username, String password) throws UserAlreadyExistsException {
        if ( userRepository.existsByUsername(username) ) {
            throw new UserAlreadyExistsException();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
