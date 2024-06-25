package com.example.aka.repo;

import com.example.aka.entity.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrl,Integer> {


}
