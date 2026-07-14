package model;

public enum ClientCategory {
    M(20), B(15), V(10);
    private final int annualRate;
    ClientCategory(int annualRate){
        this.annualRate = annualRate;
    }

    public int getAnnualRate() {
        return annualRate;
    }
}