package com.example.aka.service;

import com.example.aka.entity.Url;
import com.example.aka.repo.UrlRepository;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;

@Service
public class UrlService {

    Logger logger = LoggerFactory.getLogger(UrlService.class);

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
        String shortUrl = generateShortUrlKey(originalUrl);

        // Save the URL to the database
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        urlRepository.save(url);
        return shortUrl;
    }

    private String generateShortUrlKey(String originalUrl) {
        StringBuilder key = new StringBuilder();
        Random random = new SecureRandom();

        // Add prefix (optional)
        key.append("dispatch.to/");

        // Generate random string
        for (int i = 0; i < 4; i++) {
            key.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }


        String hashedUrl = Hashing.sha256()
                .hashString(originalUrl, StandardCharsets.UTF_8)
                .toString();


        logger.info("Hashed : " +hashedUrl);

        // Extract 4 characters from hash
        key.append(hashedUrl.substring(0, 4));

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
