package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.user.UserProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserProfile, Long> {
    UserProfile findByEmail(String email);
    UserProfile findByName(String name);
    List<UserProfile> findAll();

}

