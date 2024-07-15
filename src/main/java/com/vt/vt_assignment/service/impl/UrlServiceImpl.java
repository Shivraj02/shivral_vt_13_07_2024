package com.vt.vt_assignment.service.impl;

import com.vt.vt_assignment.entity.dao.Url;
import com.vt.vt_assignment.entity.dto.OriginalUrlDto;
import com.vt.vt_assignment.entity.dto.ShortUrlDaysToAddDto;
import com.vt.vt_assignment.entity.dto.ShortUrlDto;
import com.vt.vt_assignment.repository.UrlRepository;
import com.vt.vt_assignment.service.UrlService;
import com.vt.vt_assignment.utils.Base62;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {

  @Autowired private UrlRepository UrlRepository;

  private final String BASE_Url = "http://localhost:8080/";

  @Override
  public String shortenUrl(OriginalUrlDto originalUrl) {
    SecureRandom secureRandom = new SecureRandom();
    long id = Math.abs(secureRandom.nextLong());
    String shortUrl = Base62.encode(id);

    Url Url = new Url();
    Url.setOriginalUrl(originalUrl.getOriginalUrl());
    Url.setShortUrl(shortUrl);
    Url.setExpiryDate(LocalDateTime.now().plusMonths(10));
    UrlRepository.save(Url);

    return BASE_Url + "url/shortUrl/" + shortUrl;
  }

  @Override
  public Optional<Url> getOriginalUrl(ShortUrlDto shortUrl) {
    return UrlRepository.findByShortUrl(shortUrl.getShortUrl());
  }

  @Override
  public Boolean updateShortUrl(String shortUrl, String newOriginalUrl) {
    Optional<Url> UrlOptional = UrlRepository.findByShortUrl(shortUrl);
    if (UrlOptional.isPresent()) {
      Url Url = UrlOptional.get();
      Url.setOriginalUrl(newOriginalUrl);
      UrlRepository.save(Url);
      return true;
    }
    return false;
  }

  @Override
  public Boolean updateExpiry(ShortUrlDaysToAddDto shortUrlDaysToAddDto) {
    Optional<Url> UrlOptional = UrlRepository.findByShortUrl(shortUrlDaysToAddDto.getShortUrl());
    if (UrlOptional.isPresent()) {
      Url url = UrlOptional.get();
      url.setExpiryDate(url.getExpiryDate().plusDays(shortUrlDaysToAddDto.getDaysToAdd()));
      UrlRepository.save(url);
      return true;
    }
    return false;
  }
}
