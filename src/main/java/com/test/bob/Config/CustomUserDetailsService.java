package com.test.bob.Config;

import com.test.bob.Entity.Uzytkownik;
import com.test.bob.Repository.UzytkownikRepository;
import com.test.bob.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UzytkownikRepository userRepo;

    private final UzytkownikRepository userRepository;

    public CustomUserDetailsService(UzytkownikRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        Uzytkownik user = userRepository.findByLogin(login)
                .orElseThrow(() ->
                        new UserNotFoundException("Nie znaleziono użytkownika: " + login)
                );

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getHaslo())
                .authorities("ROLE_" + user.getStatus())
                .build();
    }
}
