package ua.privatBankJSON;

public class Valuta {
    private String baseCurrency;
    private String currency;
    private double saleRateNB;
    private double purchaseRateNB;
    private double saleRate;
    private double purchaseRate;

    public Valuta() {
    }

    public Valuta(String baseCurrency, double saleRateNB, double purchaseRateNB) {
        this.baseCurrency = baseCurrency;
        this.saleRateNB = saleRateNB;
        this.purchaseRateNB = purchaseRateNB;
    }

    public Valuta(String baseCurrency, String currency, double saleRateNB, double purchaseRateNB) {
        this.baseCurrency = baseCurrency;
        this.currency = currency;
        this.saleRateNB = saleRateNB;
        this.purchaseRateNB = purchaseRateNB;
    }

    public Valuta(String baseCurrency, String currency, double saleRateNB, double purchaseRateNB, double saleRate, double purchaseRate) {
        this.baseCurrency = baseCurrency;
        this.currency = currency;
        this.saleRateNB = saleRateNB;
        this.purchaseRateNB = purchaseRateNB;
        this.saleRate = saleRate;
        this.purchaseRate = purchaseRate;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getSaleRateNB() {
        return saleRateNB;
    }

    public void setSaleRateNB(double saleRateNB) {
        this.saleRateNB = saleRateNB;
    }

    public double getPurchaseRateNB() {
        return purchaseRateNB;
    }

    public void setPurchaseRateNB(double purchaseRateNB) {
        this.purchaseRateNB = purchaseRateNB;
    }

    public double getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(double saleRate) {
        this.saleRate = saleRate;
    }

    public double getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(double purchaseRate) {
        this.purchaseRate = purchaseRate;
    }
}