package com.vt.vt_assignment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Url {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Original URL cannot be null")
  @Pattern(regexp = "^(http|https)://.*$", message = "Original URL must be a valid URL")
  private String originalUrl;

  @NotNull(message = "Short URL cannot be null")
  @Pattern(
      regexp = "^[a-zA-Z0-9]{1,30}$",
      message = "Short URL must be alphanumeric and up to 30 characters")
  private String shortUrl;

  @NotNull(message = "Expiry date cannot be null")
  private LocalDateTime expiryDate;
}
