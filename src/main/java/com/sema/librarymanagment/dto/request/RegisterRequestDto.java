package com.sema.librarymanagment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class RegisterRequestDto {

    @Schema(description = "Username", example = "sema")
    @NotBlank(message = "Username cannot be empty")
    String username;

    @Schema(description = "User email", example = "sema@example.com")
    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be empty")
    String email;

    @Schema(description = "User password", example = "Password123")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    String password;

}
