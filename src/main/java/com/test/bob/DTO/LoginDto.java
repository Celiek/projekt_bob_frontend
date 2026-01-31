package com.test.bob.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "Login jest niepoprawny")
    private String login;
    @NotBlank(message = "Hasło jest wymagane")
    private String haslo;
}
