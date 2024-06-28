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

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;


@Service
public class UrlService {

    Logger logger = LoggerFactory.getLogger(UrlService.class);

    private final UrlRepository urlRepository;

    @Autowired
    private ShortenedUrlRepository shortenedUrlRepository;

    @Autowired
    private PreGeneratedShortUrls preGeneratedShortUrls;

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


        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setCreationDate(LocalDateTime.now());
        urlRepository.save(url);

        //update the isUsed
        ShortenedUrl shortenedUrl = shortenedUrlRepository.getByShortUrl(shortUrl);
        shortenedUrl.setIsUsed(1);
        shortenedUrlRepository.save(shortenedUrl);
        String baseUrl = "https://Swft/";
        return baseUrl +shortUrl;
    }

    private String getShortUrl() {
        ShortenedUrl shortenedUrl = shortenedUrlRepository.findOneByIsUsed(0);
        if (shortenedUrl==null){
            preGeneratedShortUrls.generateAndSaveBatch();
            shortenedUrl = shortenedUrlRepository.findOneByIsUsed(0);
        }
        assert shortenedUrl != null;
        return shortenedUrl.getShortUrl();
    }


    @Transactional
    public String getOriginalUrl(String shortUrl) {
        if (shortUrl !=null){
            try {
                Url url = urlRepository.findByShortUrl(extractPath(shortUrl));
                url.incrementClickCount();
                urlRepository.save(url);
                return url.getOriginalUrl();
            }catch (URISyntaxException e){
                logger.error("URI EXTRACTION EXCEPTION: "+e.getLocalizedMessage());
            }
        }
        return null;
    }

    private String extractPath(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String path = uri.getPath();
        // Remove leading slash if present
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        logger.info("Extracted Path: "+path);
        return path;
    }
}
