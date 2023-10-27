package com.login.demo.repository;

import com.login.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserByPassword(String password);
    @Query("select s from User s where s.username=?1 and s.password=?2")
    User findByUsernameAndPassword(String username,String password);
    List<User> findByUsername(String username);
    boolean existsByUsername(String username);
    void deleteUserByUsername(String username);

}//UtenteRepository
