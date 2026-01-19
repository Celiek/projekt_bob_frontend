package com.test.bob.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank(message = "Login nie może być pusty")
    @Size(min = 4, max = 30, message = "Login musi mieć między 4 a 30 znaków")
    private String login;
    @NotBlank(message = "Hasło nie może być puste")
    @Size(min = 8, message = "Hasło musi mieć min. 8 znaków")
    private String haslo;
    @NotBlank(message = "Email nie może być pusty")
    @Email(message = "Email jest niepoprawny")
    private String email;
    @NotBlank(message = "Imię jest wymagane")
    private String imie;
    @NotBlank(message = "Nazwisko jest wymagane")
    private String nazwisko;
    private String status;
    @NotNull(message = "Wiek jest wymagany")
    @Min(value = 18, message = "Minimalny wiek to 18")
    @Max(value = 120, message = "Niepoprawny wiek")
    private Integer wiek;
    // czy na pewno potrzebne jest zdjęcie ?
//    private byte[] zdjecie;
}
