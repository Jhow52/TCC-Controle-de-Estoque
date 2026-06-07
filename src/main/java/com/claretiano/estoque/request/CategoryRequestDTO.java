package com.claretiano.estoque.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CategoryRequestDTO {
    @NotBlank(message = "O nome da categoria é obrigatorio")
    private String name;
    @NotBlank(message = "Adicione uma descrição para sua categoria")
    private String description;
}
