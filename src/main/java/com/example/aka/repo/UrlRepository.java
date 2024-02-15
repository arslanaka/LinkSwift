package com.example.aka.repo;

import com.example.aka.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url,Long> {

    Url findByShortUrl(String shortUrl);

    Url findByOriginalUrl(String originalUrl);
}
