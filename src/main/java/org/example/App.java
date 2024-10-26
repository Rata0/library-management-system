package org.example;

import org.example.manager.BookManager;
import org.example.manager.DatabaseManager;
import org.example.manager.ReaderManager;

import java.sql.Connection;
import java.util.Scanner;

public class App {
    private static BookManager bookManager;
    private static ReaderManager readerManager;

    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect();

        Connection connection = databaseManager.getConnect();

        bookManager = new BookManager(connection);
        readerManager = new ReaderManager(connection);

        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Добро пожаловать в библиотеку!\n");
        printHelp();

        while (true) {
            System.out.println("> ");
            command = scanner.nextLine().trim();

            switch (command) {
                case "1":
                    addBook(scanner);
                    break;

                case "2":
                    getAllBooks();
                    break;

                case "3":
                    findBookByTitle(scanner);
                    break;

                case "4":
                    deleteBook(scanner);
                    break;

                case "5":
                    addReader(scanner);
                    break;

                case "6":
                    getAllReaders();
                    break;

                case "7":
                    findReaderByEmail(scanner);
                    break;

                case "8":
                    deleteReader(scanner);
                    break;

                case "exit":
                    System.out.println("Завершение работы...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверная команда. Попробуйте снова.");
                    break;
            }
        }
    }

    private static void printHelp() {
        System.out.println("Доступные команды:");
        System.out.println("1 - Добавить книгу");
        System.out.println("2 - Список всех книг");
        System.out.println("3 - Найти книгу по названию");
        System.out.println("4 - Удалить книгу по ID");
        System.out.println("5 - Добавить читателя");
        System.out.println("6 - Список всех читателей");
        System.out.println("7 - Найти читателя по email");
        System.out.println("8 - Удалить читателя по ID");
        System.out.println("exit - Заверешение сеанса");
    }

    private static void addBook(Scanner scanner) {
        System.out.print("Введите название книги: \n");
        String title = scanner.nextLine();
        System.out.print("Введите автора книги: \n");
        String author = scanner.nextLine();
        System.out.print("Введите дату публикации (YYYY-MM-DD): \n");
        String publishedDate = scanner.nextLine();
        System.out.print("Введите ISBN книги: \n");
        String isbn = scanner.nextLine();
        bookManager.addBook(title, author, java.sql.Date.valueOf(publishedDate), isbn);
    }

    private static void getAllBooks() {
        bookManager.getAllBooks()
                .forEach(book -> System.out.println(book.toString()));
    }

    private static void findBookByTitle(Scanner scanner) {
        System.out.print("Введите название книги: \n");
        String title = scanner.nextLine();
        System.out.println(bookManager.findBookByTitle(title));
    }

    private static void deleteBook(Scanner scanner) {
        System.out.print("Введите ID книги для удаления: \n");
        String id = scanner.nextLine();
        bookManager.deleteBook(Integer.parseInt(id));
    }

    private static void addReader(Scanner scanner) {
        System.out.print("Введите имя: \n");
        String name = scanner.nextLine();
        System.out.print("Введите email: \n");
        String email = scanner.nextLine();
        readerManager.addReader(name, email);
    }

    private static void getAllReaders() {
        readerManager.getAllReaders()
                .forEach(book -> System.out.println(book.toString()));
    }

    private static void findReaderByEmail(Scanner scanner) {
        System.out.print("Введите email пользователя: \n");
        String email = scanner.nextLine();
        System.out.println(readerManager.findReaderByEmail(email));
    }

    private static void deleteReader(Scanner scanner) {
        System.out.print("Введите ID читателя для удаления: \n");
        String id = scanner.nextLine();
        readerManager.deleteReader(Integer.parseInt(id));
    }
}
