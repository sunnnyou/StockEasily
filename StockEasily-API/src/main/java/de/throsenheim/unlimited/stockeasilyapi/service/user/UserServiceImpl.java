package de.throsenheim.unlimited.stockeasilyapi.service.user;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.UserRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.model.User;
import de.throsenheim.unlimited.stockeasilyapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User edit(UserRequestDto request) {
        // TODO test if it overwrites with null if values are null or empty
        return this.userRepository.save(new User(request));
    }
}
