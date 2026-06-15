import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int approvedCount = 0;
        int deniedCount = 0;
        int bankVault = 1000000;
        String[] lastClient = new String[3];
        int clientIndex = 0;
        while (true) {
            System.out.println("\n ==== Главное менюменю банка ====");
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
                    String resalt1 = formatName(name);
                    String resalt = checkLoanApproval(age, averageIncome, creditScore, category);
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
                        calculateLoanDetails(loanAmount, month, category);
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

    public static String checkLoanApproval(int age, int income, int creditScore, String category) {
        switch (category) {
            case "В" -> {
                return "ОДОБРЕНО";
            }
            case "Б" -> {
                if (income > 50000)
                    return "ОДОБРЕНО";
                else return "ОТКАЗАНО, доход менее 50000 руб.";
            }
            case "М" -> {
                if (age < 21) {
                    return "ОТКАЗАНО, возраст менее 21 года";
                }
                if (income < 30000) {
                    return "ОТКАЗАНО, доход менее 30000 руб";
                }
                if (creditScore == 5) {
                    return "ОДОБРЕНО";
                }
                if (creditScore >= 3 && creditScore <= 4 && income > 70000) {
                    return "ОДОБРЕНО";
                }
                return "ОТКАЗАНО, низкий доход или низкая категория";
            }
        }
        return "Ошибка";
    }

    public static String formatName(String name) {
        String firstLatter = name.substring(0, 1).toUpperCase();
        String restOfName = name.substring(1).toLowerCase();
        return firstLatter + restOfName;
    }

    public static void calculateLoanDetails(int loanAmount, int month, String category) {
        int annualRate = switch (category) {
            case "В" -> 10;
            case "Б" -> 15;
            default -> 20;
        };
        int overpayment = loanAmount * annualRate / 100;
        int totalToPay = loanAmount + overpayment;
        int monthlyPayament = totalToPay / month;
        System.out.println("\n==== РАСЧЕТ КРЕДИТА ====");
        System.out.println("Ваша годовая ставка " + annualRate);
        System.out.println("Ежемесячный платеж " + monthlyPayament);
        System.out.println("Общая переплата " + overpayment);
        System.out.println("Всего к возврату в банк " + totalToPay);

    }
}