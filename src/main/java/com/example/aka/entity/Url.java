package com.example.aka.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_url")
    private String originalUrl;
    @Column(name = "short_url")
    private String shortUrl;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    // Constructors, getters, and setters

    // Constructors
    public Url() {
        // Default constructor for JPA
    }

    public Url(String originalUrl, String shortUrl, LocalDateTime creationDate) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.creationDate = creationDate;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}

