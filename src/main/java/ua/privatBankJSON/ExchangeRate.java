package ua.privatBankJSON;

import javax.persistence.*;

@Entity
@Table(name = "exchangeRates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double purchaseRateUSD;
    private double saleRateUSD;
    private double purchaseRateEUR;
    private double saleRateEUR;

    public ExchangeRate() {
    }

    public ExchangeRate(double purchaseRateUSD, double saleRateUSD, double purchaseRateEUR, double saleRateEUR) {
        this.purchaseRateUSD = purchaseRateUSD;
        this.saleRateUSD = saleRateUSD;
        this.purchaseRateEUR = purchaseRateEUR;
        this.saleRateEUR = saleRateEUR;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPurchaseRateUSD() {
        return purchaseRateUSD;
    }

    public void setPurchaseRateUSD(double purchaseRateUSD) {
        this.purchaseRateUSD = purchaseRateUSD;
    }

    public double getSaleRateUSD() {
        return saleRateUSD;
    }

    public void setSaleRateUSD(double saleRateUSD) {
        this.saleRateUSD = saleRateUSD;
    }

    public double getPurchaseRateEUR() {
        return purchaseRateEUR;
    }

    public void setPurchaseRateEUR(double purchaseRateEUR) {
        this.purchaseRateEUR = purchaseRateEUR;
    }

    public double getSaleRateEUR() {
        return saleRateEUR;
    }

    public void setSaleRateEUR(double saleRateEUR) {
        this.saleRateEUR = saleRateEUR;
    }


    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", purchaseRateUSD=" + purchaseRateUSD +
                ", saleRateUSD=" + saleRateUSD +
                ", purchaseRateEUR=" + purchaseRateEUR +
                ", saleRateEUR=" + saleRateEUR +
                '}';
    }
}
