package com.test.bob.Config;

import com.test.bob.Entity.Uzytkownik;
import com.test.bob.Repository.UzytkownikRepository;
import com.test.bob.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UzytkownikRepository userRepository;

    public CustomUserDetailsService(UzytkownikRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        Uzytkownik user = userRepository.findByLogin(login)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Nie znaleziono użytkownika"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getHaslo()) // ← HASH z DB
                .roles(user.getStatus())   // np USER / ADMIN
                .build();
    }
}
