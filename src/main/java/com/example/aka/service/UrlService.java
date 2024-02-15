package com.example.aka.service;

import com.example.aka.entity.Url;
import com.example.aka.repo.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 8;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl) {
        // Check if the URL already exists in the database
        Url existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl != null) {
            return existingUrl.getShortUrl();
        }

        // Generate a unique short URL key
        String shortUrl = generateShortUrlKey();

        // Save the URL to the database
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        urlRepository.save(url);
        return shortUrl;
    }

    private String generateShortUrlKey() {
        StringBuilder key = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            key.append(CHARACTERS.charAt(index));
        }

        return key.toString();
    }

    public String getOriginalUrl(String shortUrl) {
        if (shortUrl !=null){
            Url url = urlRepository.findByShortUrl(shortUrl);
            if (url != null){
                return url.getOriginalUrl();
            }
        }
        return "Could not find original url.";
    }
}
