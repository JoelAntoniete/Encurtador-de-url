package com.example.EncurtadorUrl.repository;

import com.example.EncurtadorUrl.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByUrl(String shortUrl);
}
