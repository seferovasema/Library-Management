package com.sema.librarymanagment.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String BOOKS_CACHE = "books";
    public static final String BOOK_BY_ID_CACHE = "bookById";
    public static final String BOOKS_BY_AUTHOR_CACHE = "booksByAuthor";

    @Value("${cache.book.ttl-minutes:10}")
    private long bookCacheTtlMinutes;

    @Value("${cache.book.max-size:500}")
    private long bookCacheMaxSize;

    @Bean
    public CacheManager cacheManager() {

        CaffeineCacheManager cacheManager =
                new CaffeineCacheManager(
                        BOOKS_CACHE,
                        BOOK_BY_ID_CACHE,
                        BOOKS_BY_AUTHOR_CACHE
                );

        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(
                                bookCacheTtlMinutes,
                                TimeUnit.MINUTES
                        )
                        .maximumSize(bookCacheMaxSize)
        );

        return cacheManager;
    }
}
