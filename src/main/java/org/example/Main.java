package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LinkManager linkManager = new LinkManager();
        Scanner scanner = new Scanner(System.in);
        User admin = new User();
        String command;

        while (true) {
            System.out.println("Введите команду (создать, перейти, показать, удалить, выйти):");
            command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "создать":
                    System.out.print("Введите длинный URL: ");
                    String longUrl = scanner.nextLine();
                    System.out.print("Введите лимит переходов: ");
                    int limit = Integer.parseInt(scanner.nextLine());
                    System.out.print("Введите срок действия (в часах): ");
                    int lifespanInHours = Integer.parseInt(scanner.nextLine());
                    ShortenedLink shortenedLink = linkManager.createShortenedLink(longUrl, limit, lifespanInHours, admin.getUuid());
                    System.out.println("Создана короткая ссылка: " + shortenedLink.getShortUrl());
                    break;

                case "перейти":
                    System.out.print("Введите короткую ссылку: ");
                    String shortUrl = scanner.nextLine();
                    linkManager.redirectToLongUrl(shortUrl);
                    break;

                case "показать":
                    linkManager.displayLinks();
                    break;

                case "удалить":
                    linkManager.checkExpiredLinks();
                    System.out.println("Истекшие ссылки удалены.");
                    break;

                case "выйти":
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неизвестная команда. Пожалуйста, попробуйте снова.");
            }
        }
    }
}