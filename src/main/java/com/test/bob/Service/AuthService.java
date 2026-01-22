package com.test.bob.Service;

import com.test.bob.Config.JwtUtil;
import com.test.bob.DTO.AuthResponseDto;
import com.test.bob.DTO.LoginDto;
import com.test.bob.DTO.RegisterDTO;
import com.test.bob.DTO.UserResponseDTO;
import com.test.bob.Entity.Uzytkownik;
import com.test.bob.Repository.RegisterRepository;
import com.test.bob.Repository.UzytkownikRepository;
import com.test.bob.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.test.bob.exception.UserAlreadyExistException;

@Service
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UzytkownikRepository repo;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;


    public AuthService(
            AuthenticationManager authManager,
            JwtUtil jwtUtil,
            UzytkownikRepository repo,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    // ======================
    // REJESTRACJA
    // ======================

    @Transactional
    public UserResponseDTO register(RegisterDTO dto) {
        if (repo.existByLogin(dto.getLogin())) {
            throw new UserAlreadyExistException("Login jest już zajęty");
        }
        if (repo.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistException("Ten email jest już zajęty");
        }

        Uzytkownik user = new Uzytkownik();
        user.setLogin(dto.getLogin());
        user.setEmail(dto.getEmail());
        user.setHaslo(passwordEncoder.encode(dto.getHaslo()));
        user.setImie(dto.getImie());
        user.setNazwisko(dto.getNazwisko());
        user.setStatus(dto.getStatus());
        user.setWiek(dto.getWiek());
//        user.setZdjecie(dto.getZdjecie());
        Uzytkownik saved = repo.save(user);

        return new UserResponseDTO(saved);
    }

    // ======================
    // LOGIN
    // ======================

    public AuthResponseDto login(LoginDto dto){
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getLogin(),
                        dto.getHaslo()
                )
        );

        Uzytkownik user = repo.findByLogin(dto.getLogin())
                .orElseThrow();

        String token = jwtUtil.generateToken(
                user.getLogin()
        );

        return new AuthResponseDto(
                token,
                user.getLogin(),
                user.getStatus()
        );
    }
}

