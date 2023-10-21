package com.login.demo.dangerExample;

import com.login.demo.model.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDangerDAO {
    @Autowired
    EntityManager entityManager;

    public List<User> findByUsernameAndPassword(String username, String password){
        //String jpql = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password";
        String jpql = "FROM User WHERE username = '" + username + "' and password = '" + password + "'";
        var query = entityManager.createQuery(jpql, User.class);
        //query.setParameter("username", username);
        //query.setParameter("password", password);
        if (query.getResultList().isEmpty())
            return null;
        return query.getResultList();
    }

    public List<User> findByUsername(String username){
        String jpql = "FROM User WHERE username = '" + username + "'";
        var query = entityManager.createQuery(jpql, User.class);
        if (query.getResultList().isEmpty())
            return null;
        return query.getResultList();
    }

}
