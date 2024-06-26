package com.example.aka.repo;

import com.example.aka.entity.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrl,Integer> {


    @Query(value = "SELECT * FROM pre_generated_short_urls WHERE is_used = :isUsed LIMIT 1", nativeQuery = true)
    ShortenedUrl findOneByIsUsed(@Param("isUsed") int isUsed);


    @Query(value = "select * from pre_generated_short_urls where short_url = :shortRl", nativeQuery = true)
    ShortenedUrl getByShortUrl(@Param("shortRl") String shortUrl);
}
