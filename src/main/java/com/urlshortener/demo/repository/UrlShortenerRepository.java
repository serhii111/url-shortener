package com.urlshortener.demo.repository;

import com.urlshortener.demo.entity.UrlShortenerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UrlShortenerRepository extends ReactiveCrudRepository<UrlShortenerEntity, String> {
    Mono<UrlShortenerEntity> findByOriginalUrl(String shortUrl);

    Mono<UrlShortenerEntity> findByShortUrl(String originalUrl);
}
