package ua.privatBankJSON;

import java.util.List;

public class BankJSON {
    private String date;
    private String bank;
    private int baseCurrency;
    private String baseCurrencyLit;
    private List<Valuta> exchangeRate;

    public BankJSON() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public int getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(int baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getBaseCurrencyLit() {
        return baseCurrencyLit;
    }

    public void setBaseCurrencyLit(String baseCurrencyLit) {
        this.baseCurrencyLit = baseCurrencyLit;
    }

    public List<Valuta> getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(List<Valuta> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
