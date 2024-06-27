package com.example.aka.service;

import com.example.aka.entity.DTO.UrlDTO;
import com.example.aka.entity.ShortenedUrl;
import com.example.aka.entity.Url;
import com.example.aka.repo.ShortenedUrlRepository;
import com.example.aka.repo.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class UrlService {

    Logger logger = LoggerFactory.getLogger(UrlService.class);

    private final UrlRepository urlRepository;

    @Autowired
    private ShortenedUrlRepository shortenedUrlRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl) {

        Url existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl != null) {
            return existingUrl.getOriginalUrl();
        }

        // Generate a unique short URL key
        String shortUrl = getShortUrl();
        logger.info("my short url is : "+shortUrl);


        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setCreationDate(LocalDateTime.now());
        urlRepository.save(url);

        //update the isUsed
        ShortenedUrl shortenedUrl = shortenedUrlRepository.getByShortUrl(shortUrl);
        shortenedUrl.setIsUsed(1);
        shortenedUrlRepository.save(shortenedUrl);
        return shortUrl;
    }

    private String getShortUrl() {
        ShortenedUrl shortenedUrl = shortenedUrlRepository.findOneByIsUsed(0);
        if (shortenedUrl==null){
            logger.info("Call Batch Service Here");
        }
        assert shortenedUrl != null;
        return shortenedUrl.getShortUrl();
    }


    public UrlDTO getOriginalUrl(String shortUrl) {
        UrlDTO dto = new UrlDTO();
        if (shortUrl !=null){
            Url url = urlRepository.findByShortUrl(shortUrl);
            if (url != null){
                dto.setShortUrl(url.getShortUrl());
                dto.setOriginalUrl(url.getOriginalUrl());
                dto.setCreationDate(url.getCreationDate());
            }
        }
        return dto;
    }
}
