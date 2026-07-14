package model;

public class Client {
    private int id;
    private String name;
    private int age;
    private int averageIncome;
    private int creditScore;
    private  ClientCategory category;

    public Client(int id, String name, int age, int averageIncome, int creditScore,  ClientCategory category) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.averageIncome = averageIncome;
        this.creditScore = creditScore;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getAverageIncome() {
        return averageIncome;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public ClientCategory getCategory() {
        return category;
    }
}
