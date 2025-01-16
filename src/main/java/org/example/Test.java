package org.example;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        LinkManager linkManager = new LinkManager();
        User user1 = new User();
        User user2 = new User();

        String longUrl = "https://example.com/some/long/url";

        ShortenedLink link1 = linkManager.createShortenedLink(longUrl, 5, 1, user1.getUuid());
        ShortenedLink link2 = linkManager.createShortenedLink(longUrl, 5, 1, user2.getUuid());
        System.out.println("Короткая ссылка 1: " + link1.getShortUrl());
        System.out.println("Короткая ссылка 1: " + link1.getShortUrl());
        System.out.println("Короткая ссылка 2: " + link2.getShortUrl());
// Ожидаемый результат: link1.getShortUrl() != link2.getShortUrl()

        ShortenedLink link = linkManager.createShortenedLink("https://example.com", 2, 1, user2.getUuid());
        linkManager.redirectToLongUrl(link.getShortUrl()); // 1 переход
        linkManager.redirectToLongUrl(link.getShortUrl()); // 2 переход
        linkManager.redirectToLongUrl(link.getShortUrl()); // Лимит достигнут
        // Ожидаемый результат: "Лимит переходов достигнут."

        // Тест на истечение срока жизни
        ShortenedLink expiringLink = linkManager.createShortenedLink("https://example.com", 5, 1, user2.getUuid()); // 1 час
        Thread.sleep(3600 * 1000); // Ждем 1 час
        linkManager.redirectToLongUrl(expiringLink.getShortUrl()); // Проверка после истечения
// Ожидаемый результат: "Ссылка недоступна. Ссылка истекла."
    }

}
