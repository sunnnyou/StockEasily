package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.repository.UserRepository;
import de.throsenheim.unlimited.stockeasilyapi.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserProfile findUserByEmail(String email) {
        return (UserProfile) userRepository;
    }

    public UserProfile findUserByName(String name) {
        return (UserProfile) userRepository;
    }
}