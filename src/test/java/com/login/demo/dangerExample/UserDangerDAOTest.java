package com.login.demo.dangerExample;

import com.login.demo.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

public class UserDangerDAOTest {

    @InjectMocks
    private UserDangerDAO userDangerDAO;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<User> query;

    @Test
    public void testFindByUsernameAndPassword_UserFound() {
        String username = "testUser";
        String password = "testPassword";

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(Collections.singletonList(new User()));

        List<User> users = userDangerDAO.findByUsernameAndPassword(username, password);

        //verifica che il metodo restituisca una lista non vuota
        org.junit.jupiter.api.Assertions.assertNotNull(users);
        org.junit.jupiter.api.Assertions.assertFalse(users.isEmpty());
    }

    @Test
    public void testFindByUsernameAndPassword_UserNotFound() {
        String username = "testUser";
        String password = "testPassword";

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(Collections.emptyList());

        List<User> users = userDangerDAO.findByUsernameAndPassword(username, password);

        //verifica che il metodo restituisca una lista vuota
        org.junit.jupiter.api.Assertions.assertNotNull(users);
        org.junit.jupiter.api.Assertions.assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByUsername_UserFound() {
        String username = "testUser";

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(Collections.singletonList(new User()));

        List<User> users = userDangerDAO.findByUsername(username);

        //verifica che il metodo restituisca una lista non vuota
        org.junit.jupiter.api.Assertions.assertNotNull(users);
        org.junit.jupiter.api.Assertions.assertFalse(users.isEmpty());
    }

    @Test
    public void testFindByUsername_UserNotFound() {
        String username = "testUser";

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(Collections.emptyList());

        List<User> users = userDangerDAO.findByUsername(username);

        //verifica che il metodo restituisca una lista vuota
        org.junit.jupiter.api.Assertions.assertNotNull(users);
        org.junit.jupiter.api.Assertions.assertTrue(users.isEmpty());
    }
}

