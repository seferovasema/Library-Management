package com.sema.librarymanagment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequestDto {

    @Schema(description = "Book title", example = "The Little Prince")
    @NotBlank(message = "Title cannot be empty")
    String title;

    @Schema(description = "Book price", example = "25.50")
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than 0")
    BigDecimal price;

    @Schema(description = "Author ID", example = "1")
    @NotNull(message = "Author id cannot be null")
    Long authorId;

    @Schema(description = "Member ID", example = "2")
    @NotNull(message = "Member id cannot be null")
    Long memberId;
}
