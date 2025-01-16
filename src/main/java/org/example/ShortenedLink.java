package org.example;

import java.util.Date;
import java.util.UUID;

class ShortenedLink {
    private String longUrl;
    private String shortUrl;
    private int limit;
    private int clickCount;
    private long expirationTime;
    private String userUuid;

    public ShortenedLink(String longUrl, int limit, int lifespanInHours, String userUuid) {
        this.longUrl = longUrl;
        this.limit = limit;
        this.clickCount = 0;
        this.expirationTime = System.currentTimeMillis() + lifespanInHours * 3600 * 1000; // Время истечения
        this.shortUrl = generateShortUrl(longUrl, userUuid);
        this.userUuid = userUuid;
    }

    private String generateShortUrl(String longUrl, String userUuid) {
        // Генерация короткой ссылки (можно использовать более сложный алгоритм)
        String uniqueString = longUrl + userUuid;
        String shortHash = UUID.nameUUIDFromBytes(uniqueString.getBytes()).toString().substring(0, 6);
        return "https://short.url/" + shortHash;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public int getLimit() {
        return limit;
    }

    public int getClickCount() {
        return clickCount;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void incrementClickCount() {
        this.clickCount++;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }

    public boolean isLimitReached() {
        return clickCount >= limit;
    }
}

