package com.vt.vt_assignment.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrlDto {
  @NotNull(message = "Short URL cannot be null")
  @Size(max = 30, message = "Short URL must not exceed 30 characters")
  String shortUrl;
}
