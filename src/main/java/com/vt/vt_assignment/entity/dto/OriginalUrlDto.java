package com.vt.vt_assignment.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OriginalUrlDto {
    @NotNull(message = "Original URL cannot be null")
    @Pattern(regexp = "^(http|https)://.*$", message = "Original URL must be a valid URL")
    String originalUrl;
}
