package com.vt.vt_assignment.service;

import com.vt.vt_assignment.entity.Url;
import java.util.Optional;

public interface UrlService {
  public String shortenUrl(String originalUrl);

  Optional<Url> getOriginalUrl(String shortUrl);

  Boolean updateShortUrl(String shortUrl, String newLongUrl);

  Boolean updateExpiry(String shortUrl, int daysToAdd);
}
