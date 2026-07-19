package com.sema.librarymanagment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorRequestDto {

    @Schema(description = "Author name", example = "Nizami Ganjavi")
    @NotBlank(message = "Author name cannot be empty")
    @Size(min = 2, max = 50, message = "Author name must be between 2 and 50 characters")
    String name;

    @Schema(description = "Author email", example = "nizami@example.com")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    String email;
}
