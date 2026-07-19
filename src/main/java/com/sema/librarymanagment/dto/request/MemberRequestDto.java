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
public class MemberRequestDto {

    @Schema(description = "Member full name", example = "Sema Seferova")
    @NotBlank(message = "Full name cannot be empty")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    String fullName;

    @Schema(description = "Member email", example = "sema@example.com")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    String email;

    @Schema(description = "Azerbaijan phone number", example = "+994501234567")
    @NotBlank(message = "Phone cannot be empty")
    @Pattern(
            regexp = "^(\\+994|994|0)(50|51|55|60|70|77|99)\\d{7}$",
            message = "Invalid Azerbaijan phone number"
    )
    String phone;
}
