package service;

import model.Client;

public class BankService {
    public static String checkLoanApproval(Client client) {
        switch (client.getCategory()) {
            case "В" -> {
                return "ОДОБРЕНО";
            }
            case "Б" -> {
                if (client.getAverageIncome() > 50000)
                    return "ОДОБРЕНО";
                else return "ОТКАЗАНО, доход менее 50000 руб.";
            }
            case "М" -> {
                if (client.getAge() < 21) {
                    return "ОТКАЗАНО, возраст менее 21 года";
                }
                if (client.getAverageIncome() < 30000) {
                    return "ОТКАЗАНО, доход менее 30000 руб";
                }
                if (client.getCreditScore() == 5) {
                    return "ОДОБРЕНО";
                }
                if (client.getCreditScore() >= 3 && client.getCreditScore() <= 4 && client.getAverageIncome() > 70000) {
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

