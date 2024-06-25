package com.example.aka.controller;

import com.example.aka.service.PreGeneratedShortUrls;
import com.example.aka.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    @Autowired
    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }


    @Autowired
    private PreGeneratedShortUrls urlShortenerService;

    @GetMapping("/generate-batch")
    public List<String> generateBatch() {
        return urlShortenerService.generateAndSaveBatch();
    }



    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String originalUrl) {
        return urlService.shortenUrl(originalUrl);
    }

    @GetMapping("/{shortUrl}")
    public String getOriginalUrl(@PathVariable String shortUrl) {
        return urlService.getOriginalUrl(shortUrl);
    }
}
