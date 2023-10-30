package com.login.demo.servises;
import com.login.demo.model.User;
import com.login.demo.repository.UserRepository;
import com.login.demo.services.AccountingService;
import com.login.demo.support.UserAlreadyExistsException;
import com.login.demo.support.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AccountingServiceTest {

    @InjectMocks
    private AccountingService accountingService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testUserSignIn_UserNotFound() {
        //test che permette di verificare che venga correttamente lanciata l'eccezione UserNotFoundException
        //nel momento in cui si prova ad effettuare un singIn con username non presente nel DB

        //simulaizone del comportamento del repository
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);

        try {
            //test del metodo userSignIn
            accountingService.userSignIn("testUser", "password");
        } catch (UserNotFoundException e) {
            //ci si aspetta che venga lanciata l'eccezione UserNotFoundException nel caso in cui l'utente non è presente
            return;
        }

        //il test fallisce se non viene lanciata l'eccezione
        org.junit.jupiter.api.Assertions.fail("Dovrebbe essere lanciata UserNotFoundException");
    }

    @Test
    public void testUserSignIn_UserFound() throws UserNotFoundException {
        //test che permette di verificare che venga correttamente effettuato il signIn qualora l'username sia presente nel DB

        //simulazione del comportamento del repository
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(true);
        Mockito.when(userRepository.findByUsernameAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(new User());

        //test del metodo userSignIn che restituisce l'utente una volta che viene effettuato correttamente il signIn
        User user = accountingService.userSignIn("lorena", "lola");

        //il test deve restituire l'utente che ha eseguito il signIn
        org.junit.jupiter.api.Assertions.assertNotNull(user);
    }

    @Test
    public void testUserSignUp_UserAlreadyExists() {
        //test che permette di verificare che venga correttamente lanciata l'eccezione UserNotFoundException
        //nel momento in cui si prova ad effettuare un singUp con username già presente nel DB

        //simulazione del comportamento del repository
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(true);

        try {
            //test del metodo userSignIn
            accountingService.userSignUp("testUser", "password");
        } catch (UserAlreadyExistsException e) {
            //ci si aspetta che venga lanciata l'eccezione UserNotFoundException nel caso in cui l'utente è già presente
            return;
        }

        //il test fallisce se non viene lanciata l'eccezione
        org.junit.jupiter.api.Assertions.fail("Dovrebbe essere lanciata UserAlreadyExistsException");
    }

    @Test
    public void testUserSignUp_UserNotExists() throws UserAlreadyExistsException {
        //test che permette di verificare che venga correttamente effettuato il signUp qualora l'username non sia presente nel DB

        //simulazione del comportamento del repository
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());

        //test del metodo userSignUp che restituisce l'utente una volta che viene effettuata correttamente la registrazione
        User user = accountingService.userSignUp("testUser", "password");

        //il test deve restituire l'utente che ha eseguito il signUp
        org.junit.jupiter.api.Assertions.assertNotNull(user);
    }

    @Test
    public void testDelete_UserNotFound() {
        //test che permette di verificare che venga correttamente lanciata l'eccezione UserNotFoundException
        //nel momento in cui si prova ad effettuare una delete con username non presente nel DB

        //simulazione dle comportamento del repository
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);

        try {
            //test del metodo delete
            accountingService.delete("testUser");
        } catch (UserNotFoundException e) {
            //ci si aspetta che venga lanciata l'eccezione UserNotFoundException nel caso in cui l'utente non è presente
            return;
        }

        //il test fallisce se non viene lanciata l'eccezione
        org.junit.jupiter.api.Assertions.fail("Dovrebbe essere lanciata UserNotFoundException");
    }

    @Test
    public void testDelete_UserFound() throws UserNotFoundException, UserAlreadyExistsException {
        //test che permette di verificare che venga correttamente effettuata la delete qualora l'username sia presente nel DB

        //simulazione del comportamento del repository
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(true);

        //test del metodo delete che restituisce l'username una volta che viene effettuata correttamente la delete
        String username = accountingService.delete("testUser");

        //il test deve restituire l'username dell'utente cancellato
        org.junit.jupiter.api.Assertions.assertEquals("testUser", username);
    }

    @Test
    public void testGetAllUsers() {
        //simulazione il comportamento del repository
        List<User> userList = List.of(new User(), new User());
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        //esecuzione del metodo getAllUsers che restituisce una lista di utenti
        List<User> users = accountingService.getAllUsers();

        //il test deve restituire l'username dell'utente cancellato
        org.junit.jupiter.api.Assertions.assertEquals(userList, users);
    }
}

