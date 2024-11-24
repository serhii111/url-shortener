package com.urlshortener.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("url_shortener")
public class UrlShortenerEntity {

    @Column
    private String shortUrl;

    @Column
    private String originalUrl;

    @Column
    private LocalDateTime creationDate;

    @Column
    private LocalDateTime expirationDate;
}
