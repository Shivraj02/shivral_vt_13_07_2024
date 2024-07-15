package com.vt.vt_assignment.service;

import com.vt.vt_assignment.entity.dao.Url;
import com.vt.vt_assignment.entity.dto.OriginalUrlDto;
import com.vt.vt_assignment.entity.dto.ShortUrlDaysToAddDto;
import com.vt.vt_assignment.entity.dto.ShortUrlDto;

import java.util.Optional;

public interface UrlService {
  public String shortenUrl(OriginalUrlDto originalUrl);

  Optional<Url> getOriginalUrl(ShortUrlDto shortUrl);

  Boolean updateShortUrl(String shortUrl, String newLongUrl);

  Boolean updateExpiry(ShortUrlDaysToAddDto shortUrlDaysToAddDto);
}
