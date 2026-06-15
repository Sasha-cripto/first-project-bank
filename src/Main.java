import java.util.Scanner;

import model.Client;
import service.BankService;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankService();
        int approvedCount = 0;
        int deniedCount = 0;
        int bankVault = 1000000;
        String[] lastClient = new String[3];
        int clientIndex = 0;
        while (true) {
            System.out.println("\n ==== Главное меню банка ====");
            System.out.println("1. Оформить новую заявку на кредит");
            System.out.println("2. Посмотреть статистику смены");
            System.out.println("3. Завершить смену");
            System.out.print("4. Выберите действие: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Ошибка. Введите правильное действие!");
                scanner.nextLine();
                System.out.print("Введите действие от 1 до 3: ");
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.println("\n --- Оформление клиента ---");
                    System.out.print("Введите свое имя: ");
                    String name = scanner.next();
                    if ("Александр".equalsIgnoreCase(name)) {
                        System.out.println(name + " находится в черном списке");
                        deniedCount++;
                        continue;
                    }
                    System.out.print("Введите свой возраст: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Ошибка. Введите возраст цифрами!");
                        scanner.next();
                        System.out.print("Введите свой возраст: ");
                    }
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Введите свой доход за последние три месяца: ");
                    int totalIncome = 0;
                    for (int m = 1; m <= 3; m++) {
                        System.out.print("Введите доход за " + m + " месяц: ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Ошибка. Введите свой доход цифрами!");
                            scanner.next();
                            System.out.print("Введите свой доход за " + m + " месяц: ");
                        }
                        totalIncome = totalIncome + scanner.nextInt();
                    }
                    int averageIncome = totalIncome / 3;
                    System.out.println("Ваш средний доход за последние три месяца равен: " + averageIncome);
                    System.out.print("Введите свой рейтинг кредитной истории: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Ошибка. Введите рейтинг цифрами!");
                        scanner.next();
                        System.out.print("Введите свой рейтинг кредитной истории: ");
                    }
                    int creditScore = scanner.nextInt();
                    scanner.nextLine();
                    if (creditScore == 1) {
                        System.out.println("Подозрительно низкий рейтинг, клиент заблокирован");
                        deniedCount++;
                        continue;
                    }
                    System.out.print("Введите катигорию клиента (М, Б, В): ");
                    String category = scanner.next().toUpperCase();
                    while (!"МБВ".contains(category)) {
                        System.out.print("Неверная категория. Введите М, Б или В: ");
                        category = scanner.next().toUpperCase();
                    }
                    String resalt1 = bankService.formatName(name);
                    Client client = new Client(1, resalt1, age, averageIncome, creditScore, category);
                    String resalt = bankService.checkLoanApproval(client);
                    System.out.println("Уважаемый " + resalt1 + " статус вашего кредита " + resalt);
                    if (resalt.startsWith("ОДОБРЕНО")) {
                        System.out.print("Введите желаемую сумму кредита: ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Ошибка. Введите сумму кредита цифрами!");
                            scanner.next();
                            System.out.print("Введите желаемую сумму кредита: ");
                        }
                        int loanAmount = scanner.nextInt();
                        scanner.nextLine();
                        if (loanAmount > bankVault) {
                            System.out.println("Отказано. В кассе банка не достаточно средств!");
                            System.out.println("Осталось всего " + bankVault);
                            deniedCount++;
                            continue;
                        }
                        System.out.print("Ввведите срок кредита в месяцах: ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Ошибка. Введите срок цифрами!");
                            scanner.next();
                            System.out.print("Введите срок кредита в месяцах: ");
                        }
                        int month = scanner.nextInt();
                        scanner.nextLine();
                        bankService.calculateLoanDetails(loanAmount, month, category);
                        bankVault -= loanAmount;
                        approvedCount++;
                        if (clientIndex < 3) {
                            lastClient[clientIndex] = resalt1;
                            clientIndex++;
                        } else {
                            lastClient[0] = lastClient[1];
                            lastClient[1] = lastClient[2];
                            lastClient[2] = resalt1;
                        }
                        System.out.println("Кредит успешно выдан, остаток в банке " + bankVault);
                    } else deniedCount++;

                }
                case 2 -> {
                    System.out.println("\n==== Текущая статистика ====");
                    System.out.println("Одобрено " + approvedCount);
                    System.out.println("Отказано " + deniedCount);
                    System.out.println("Последние одобренные клиенты: ");
                    for (int i = 0; i < clientIndex; i++) {
                        System.out.println((i + 1) + lastClient[i]);
                    }
                }
                case 3 -> {
                    System.out.println("\n==== СМЕНА ЗАКОНЧЕНА ====");
                    System.out.println("Одобрено: " + approvedCount);
                    System.out.println("Отказано: " + deniedCount);
                    System.out.println("Последние одобренные клиенты: ");
                    for (int i = 0; i < clientIndex; i++) {
                        System.out.println((i + 1) + lastClient[i]);
                    }
                    return;
                }
                default -> {
                    System.out.println("Неверный пункт меню. Повторите попытку!");
                }
            }
        }
    }
}