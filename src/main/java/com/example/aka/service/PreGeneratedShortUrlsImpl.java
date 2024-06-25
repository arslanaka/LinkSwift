package com.example.aka.service;

import com.example.aka.entity.ShortenedUrl;
import com.example.aka.repo.ShortenedUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PreGeneratedShortUrlsImpl implements PreGeneratedShortUrls{


    Logger logger = LoggerFactory.getLogger(PreGeneratedShortUrls.class);

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 5;
    private static final int BATCH_SIZE = 500;
    private final SecureRandom random = new SecureRandom();


    @Autowired
    private ShortenedUrlRepository shortenedUrlRepository;




    @Override
    public List<String> generateAndSaveBatch() {
        Set<String> uniqueShortUrls = new HashSet<>();

        while (uniqueShortUrls.size() < BATCH_SIZE) {
            String shortUrl = generateShortUrl();
            uniqueShortUrls.add(shortUrl);
        }

        List<ShortenedUrl> shortenedUrls = uniqueShortUrls.stream()
                .map(url-> {
                    ShortenedUrl shortenedUrl = new ShortenedUrl();
                    shortenedUrl.setShortUrl(url);
                    return shortenedUrl;
                }).toList();

        return saveBatch(shortenedUrls);
    }

    @Transactional
    private List<String> saveBatch(List<ShortenedUrl> shortenedUrls) {
        try{
            List<ShortenedUrl> savedUrls = shortenedUrlRepository.saveAll(shortenedUrls);
            return savedUrls.stream()
                    .map(ShortenedUrl::getShortUrl)
                    .collect(Collectors.toList());
        }catch (NullPointerException exception){
            logger.error(exception.toString());
        }
        return null;
    }

    @Override
    public String generateShortUrl() {
        return IntStream.range(0, SHORT_URL_LENGTH)
                .mapToObj(i -> String.valueOf(CHARACTERS.charAt(random.nextInt(CHARACTERS.length()))))
                .collect(Collectors.joining());
    }
}
