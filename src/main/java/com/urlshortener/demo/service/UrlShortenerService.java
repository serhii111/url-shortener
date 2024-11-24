package com.urlshortener.demo.service;


import com.urlshortener.demo.entity.UrlShortenerEntity;
import com.urlshortener.demo.repository.UrlShortenerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    @Value("${expiration.days}")
    private int expirationDays;

    private final UrlShortenerRepository urlShortenerRepository;

    public Mono<String> shortenUrl(String originalUrl) {
        return urlShortenerRepository.findByOriginalUrl(originalUrl)
                .filter(mapping -> mapping.getExpirationDate().isAfter(LocalDateTime.now()))
                .map(UrlShortenerEntity::getShortUrl)
                .switchIfEmpty(Mono.defer(() -> {
                    String trimmedUrl = originalUrl.trim();
                    UrlShortenerEntity mapping = new UrlShortenerEntity();
                    mapping.setOriginalUrl(trimmedUrl);
                    mapping.setShortUrl(generateShortUrl(trimmedUrl));
                    mapping.setCreationDate(LocalDateTime.now());
                    mapping.setExpirationDate(LocalDateTime.now().plusDays(expirationDays));

                    return urlShortenerRepository.save(mapping).map(UrlShortenerEntity::getShortUrl);
                }));
    }

    private String generateShortUrl(String originalUrl) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalUrl.getBytes(StandardCharsets.UTF_8));

            String base64Hash = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);

            return base64Hash.substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }

    public Mono<String> getOriginalUrl(String id) {
        return urlShortenerRepository.findByShortUrl(id)
                .filter(mapping -> mapping.getExpirationDate().isAfter(LocalDateTime.now()))
                .map(UrlShortenerEntity::getOriginalUrl)
                .switchIfEmpty(Mono.error(new RuntimeException("Short URL not found or expired")));
    }
}
