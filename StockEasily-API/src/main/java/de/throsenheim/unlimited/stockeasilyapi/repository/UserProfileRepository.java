package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.user.UserProfile;
import org.springframework.stereotype.Repository;

@Repository
public class UserProfileRepository {
        public UserProfile getUserProfile(){
            return new UserProfile();
        }
}
