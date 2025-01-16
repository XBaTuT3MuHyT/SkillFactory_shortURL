package org.example;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

class LinkManager {
    private Map<String, ShortenedLink> linkStorage = new HashMap<>();

    public ShortenedLink createShortenedLink(String longUrl, int limit, int lifespanInHours, String userUuid) {
        ShortenedLink shortenedLink = new ShortenedLink(longUrl, limit, lifespanInHours, userUuid);
        linkStorage.put(shortenedLink.getShortUrl(), shortenedLink);
        return shortenedLink;
    }

    public void redirectToLongUrl(String shortUrl) {
        ShortenedLink link = linkStorage.get(shortUrl);
        if (link != null) {
            if (!link.isExpired() && !link.isLimitReached()) {
                link.incrementClickCount();
                try {
                    // Открыть оригинальный URL в браузере
                    Desktop.getDesktop().browse(new URI(link.getLongUrl()));
                } catch (IOException | URISyntaxException e) {
                    System.out.println("Ошибка при открытии ссылки: " + e.getMessage());
                }
            } else {
                System.out.println("Ссылка недоступна. " + (link.isExpired() ? "Ссылка истекла." : "Лимит переходов достигнут."));
            }
        } else {
            System.out.println("Ссылка не найдена.");
        }
    }

    public void checkExpiredLinks() {
        linkStorage.values().removeIf(ShortenedLink::isExpired);
    }

    public void displayLinks() {
        if (linkStorage.isEmpty()) {
            System.out.println("Нет сохраненных ссылок.");
            return;
        }
        System.out.println("Сохраненные короткие ссылки:");
        for (ShortenedLink link : linkStorage.values()) {
            System.out.println("Короткая ссылка: " + link.getShortUrl() + " (длинная: " + link.getLongUrl() + ")");
        }
    }
}
