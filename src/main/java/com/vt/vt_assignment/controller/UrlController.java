package com.vt.vt_assignment.controller;

import com.vt.vt_assignment.entity.Url;
import com.vt.vt_assignment.service.UrlService;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
@Validated
public class UrlController {
  @Autowired private UrlService urlService;

  @PostMapping("/shorten")
  public ResponseEntity<?> shortenUrl(@Valid @RequestParam("originalUrl") String originalUrl) {
    String shortUrl = urlService.shortenUrl(originalUrl);
    return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
  }

  @GetMapping("/{shortenString}")
  public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortenString) {
    Optional<Url> url = urlService.getOriginalUrl(shortenString);
    if (url.isPresent()) {
      if (url.get().getExpiryDate().isAfter(LocalDateTime.now())) {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .header("Location", url.get().getOriginalUrl())
            .build();
      }else{
        return ResponseEntity.status(HttpStatus.GONE).body("Url has expired");
      }
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/update/{shortUrl}")
  public ResponseEntity<?> updateShortUrl(
      @PathVariable String shortUrl, @Valid @RequestParam("newOriginalUrl") String newOriginalUrl) {
    boolean updated = urlService.updateShortUrl(shortUrl, newOriginalUrl);
    if (updated) {
      return ResponseEntity.ok("URL updated successfully");
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/expiry/{shortUrl}")
  public ResponseEntity<?> updateExpiry(@PathVariable String shortUrl, @RequestParam int daysToAdd) {
    boolean updated = urlService.updateExpiry(shortUrl, daysToAdd);
    if (updated) {
      return ResponseEntity.ok("Expiry updated successfully");
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
