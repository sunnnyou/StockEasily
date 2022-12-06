package de.throsenheim.unlimited.stockeasilyapi.service.user;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.UserRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.model.User;

public interface UserService {
    User edit(UserRequestDto request);
}
