package de.throsenheim.unlimited.stockeasilyapi.service.user;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.UserRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.model.User;
import de.throsenheim.unlimited.stockeasilyapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User edit(UserRequestDto request, long id) {
        // TODO move validation to repository so that it doesnt update
        UserRequestDto userRequestDto = new UserRequestDto();
        if (request.getEmailAddress() != null) {
            userRequestDto.setEmailAddress(request.getEmailAddress());
        } else {
            throw new ValidationException("Email address cannot be null");
        }
        if (request.getPassword() != null) {
            userRequestDto.setPassword(request.getPassword());
        } else {
            throw new ValidationException("Password cannot be null");
        }
        userRequestDto.setNotified(request.isNotified());
        if (request.getLoginDate() != null) {
            userRequestDto.setLoginDate(request.getLoginDate());
        } else {
            throw new ValidationException("Login date cannot be null");
        }
        return this.userRepository.update(new User(userRequestDto), id);
    }

    @Override
    public User find(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
}
