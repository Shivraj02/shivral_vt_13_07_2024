package com.vt.vt_assignment.controller;

import com.opencsv.exceptions.CsvValidationException;
import com.vt.vt_assignment.entity.dao.Url;
import com.vt.vt_assignment.entity.dto.OriginalUrlDto;
import com.vt.vt_assignment.entity.dto.ShortUrlDaysToAddDto;
import com.vt.vt_assignment.entity.dto.ShortUrlDto;
import com.vt.vt_assignment.service.UrlService;
import com.vt.vt_assignment.utils.CSVReaderUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/url")
public class UrlController {
  @Autowired private UrlService urlService;
  @Autowired private ResourceLoader resourceLoader;

  @PostMapping("/shorten")
  public ResponseEntity<?> shortenUrl(@RequestBody @Valid OriginalUrlDto originalUrl) {
    String shortUrl = urlService.shortenUrl(originalUrl);
    return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
  }

  @GetMapping("/shortUrl")
  public ResponseEntity<?> redirectToOriginalUrl(@RequestBody @Valid ShortUrlDto shortUrl) {
    Optional<Url> url = urlService.getOriginalUrl(shortUrl);
    if (url.isPresent()) {
      if (url.get().getExpiryDate().isAfter(LocalDateTime.now())) {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .header("Location", url.get().getOriginalUrl())
            .build();
      } else {
        return ResponseEntity.status(HttpStatus.GONE).body("Url has expired");
      }
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{shortenString}")
  public void redirectToFullUrl(
      HttpServletResponse response,
      @PathVariable
          @Valid
          @NotNull(message = "Short URL cannot be null")
          @Size(max = 30, message = "Short URL must not exceed 30 characters")
          String shortenString)
      throws IOException {
    Resource resource = resourceLoader.getResource("classpath:urls.csv");
    InputStream inputStream = resource.getInputStream();
    try {
      Map<String, String> urlMap = (Map<String, String>) CSVReaderUtil.readCsv(inputStream);
      String fullUrl = urlMap.get(shortenString);

      if (fullUrl == null) {
        throw new NoSuchElementException("URL not found");
      }
      response.sendRedirect(fullUrl);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found", e);
    } catch (IOException | CsvValidationException e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Could not redirect to the Original url", e);
    }
  }

  @PostMapping("/update/{shortUrl}")
  public ResponseEntity<?> updateShortUrl(
      @PathVariable
          @Valid
          @NotNull(message = "Short URL cannot be null")
          @Size(max = 30, message = "Short URL must not exceed 30 characters")
          String shortUrl,
      @Valid @RequestParam("newOriginalUrl") String newOriginalUrl) {
    boolean updated = urlService.updateShortUrl(shortUrl, newOriginalUrl);
    if (updated) {
      return ResponseEntity.ok("URL updated successfully");
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/expiry")
  public ResponseEntity<?> updateExpiry(
      @RequestBody @Valid ShortUrlDaysToAddDto shortUrlDaysToAddDto) {

    boolean updated = urlService.updateExpiry(shortUrlDaysToAddDto);
    if (updated) {
      return ResponseEntity.ok("Expiry updated successfully");
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
