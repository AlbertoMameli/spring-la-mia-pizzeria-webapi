package org.lessons.wdpt6.pizzeria.la_mia_pizzeria.security;

import java.util.Optional;

import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.model.User;
import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return new DataBaseUserDetails(user.get());
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
