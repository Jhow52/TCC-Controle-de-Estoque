package com.claretiano.estoque.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "O email é obrigatorio")
    private String email;
    @NotBlank(message = "A senha é obrigatoria")
    private String password;
}
