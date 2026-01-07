package com.test.bob.Service;

import com.test.bob.DTO.LoginDto;
import com.test.bob.DTO.RegisterDTO;
import com.test.bob.DTO.UserResponseDTO;
import com.test.bob.Entity.Uzytkownik;
import com.test.bob.Repository.RegisterRepository;
import com.test.bob.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.test.bob.exception.UserAlreadyExistException;

@Service
public class AuthService {
    @Autowired
    private final RegisterRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(RegisterRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // ======================
    // REJESTRACJA
    // ======================

    @Transactional
    public UserResponseDTO register(RegisterDTO dto) {
        if (repository.existsByLogin(dto.getLogin())) {
            throw new UserAlreadyExistException("Login jest już zajęty");
        }
        if (repository.existsByEmail(dto.getEmail())) {
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
        user.setZdjecie(dto.getZdjecie());
        Uzytkownik saved = repository.save(user);

        return new UserResponseDTO(saved);
    }

    // ======================
    // LOGIN
    // ======================

    public UserResponseDTO login(LoginDto dto){
        Uzytkownik user = repository.
                findByLoginOrEmail(dto.getLogin(), dto.getHaslo())
                .orElseThrow(() -> new UserNotFoundException("Nieprawidłowy login lub hasło"));
        if (!passwordEncoder.matches(dto.getHaslo(), user.getHaslo())){
            throw new UserNotFoundException("Nieproawidłowy login lub hasło");
        }
        return new UserResponseDTO(user);
    }
}

