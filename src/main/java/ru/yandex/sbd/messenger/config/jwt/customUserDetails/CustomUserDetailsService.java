package ru.yandex.sbd.messenger.config.jwt.customUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.yandex.sbd.messenger.entities.User;
import ru.yandex.sbd.messenger.service.impl.UserService;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findUserByLogin(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No login like this " + username);
        }
        return CustomUserDetails.fromUserToCustomUserDetails(user.get());
    }
}