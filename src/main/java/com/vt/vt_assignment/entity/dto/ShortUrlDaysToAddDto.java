package com.vt.vt_assignment.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlDaysToAddDto {

    @NotNull(message = "Short URL cannot be null")
    @NotBlank(message = "Short URL cannot be Blank")
    @Size(max = 30, message = "Short URL must not exceed 30 characters")
    private String shortUrl;

    private int daysToAdd;


}
