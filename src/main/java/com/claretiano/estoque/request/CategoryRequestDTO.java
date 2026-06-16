package com.claretiano.estoque.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Add a description for your category")
    private String description;
}
