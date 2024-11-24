package com.urlshortener.demo;

import com.urlshortener.demo.service.UrlShortenerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlShortenerApplicationTests {

    private static final String ORIGINAL_URL = "https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html";

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Test
    void When_ShortUrlCreated_Then_RetrieveOriginalUrl() {
        String shortened = urlShortenerService.shortenUrl(ORIGINAL_URL).block();

        String savedUrl = urlShortenerService.getOriginalUrl(shortened).block();

        Assertions.assertEquals(ORIGINAL_URL, savedUrl);
    }

    @Test
    void When_AttemptCreatingExistingUrl_Then_RetrieveRetrieveSameShortenedUrl() {
        String shortenedFirst = urlShortenerService.shortenUrl(ORIGINAL_URL).block();

        String shortenedSecond = urlShortenerService.shortenUrl(ORIGINAL_URL).block();

        Assertions.assertEquals(shortenedFirst, shortenedSecond);
    }

}
