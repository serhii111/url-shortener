package com.urlshortener.demo.controller;

import com.urlshortener.demo.service.UrlShortenerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/url_shortener")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @PostMapping
    public Mono<ResponseEntity<String>> shortenUrl(@RequestBody String url) {
        return urlShortenerService.shortenUrl(url).map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<String> getShortUrl(@PathVariable String id, ServerHttpResponse response) {
        return urlShortenerService.getOriginalUrl(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found")))
                .flatMap(originalUrl -> {
                    try {
                        URI redirectUri = UriComponentsBuilder.fromUriString(originalUrl).build().toUri();
                        response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
                        response.getHeaders().setLocation(redirectUri);
                        return Mono.empty();
                    } catch (Exception e) {
                        return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid URL: " + originalUrl, e));
                    }
                });
    }

}
