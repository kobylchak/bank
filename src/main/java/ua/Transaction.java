package ua;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "A_name_of_transaction")
    private String nameTransaction;
    @Column(name = "Who_id")
    private Long whoId;
    @Column(name = "Whom_id")
    private Long whomId;
    @Column(name = "Cash_name_from")
    private String fromCashName;
    @Column(name = "Cash_name_to")
    private String toCashName;
    @Column(name = "Money_from")
    private Double moneyFrom;
    @Column(name = "Money_to")
    private Double moneyTo;

    public Transaction() {
    }

    public Transaction(String nameTransaction, Long whoId, Long whomId, String fromCashName, String toCashName, Double moneyFrom, Double moneyTo) {
        this.nameTransaction = nameTransaction;
        this.whoId = whoId;
        this.whomId = whomId;
        this.fromCashName = fromCashName;
        this.toCashName = toCashName;
        this.moneyFrom = moneyFrom;
        this.moneyTo = moneyTo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameTransaction() {
        return nameTransaction;
    }

    public void setNameTransaction(String nameTransaction) {
        this.nameTransaction = nameTransaction;
    }

    public Long getWhoId() {
        return whoId;
    }

    public void setWhoId(Long whoId) {
        this.whoId = whoId;
    }

    public Long getWhomId() {
        return whomId;
    }

    public void setWhomId(Long whomId) {
        this.whomId = whomId;
    }

    public String getFromCashName() {
        return fromCashName;
    }

    public void setFromCashName(String fromCashName) {
        this.fromCashName = fromCashName;
    }

    public String getToCashName() {
        return toCashName;
    }

    public void setToCashName(String toCashName) {
        this.toCashName = toCashName;
    }

    public Double getMoneyFrom() {
        return moneyFrom;
    }

    public void setMoneyFrom(Double moneyFrom) {
        this.moneyFrom = moneyFrom;
    }

    public Double getMoneyTo() {
        return moneyTo;
    }

    public void setMoneyTo(Double moneyTo) {
        this.moneyTo = moneyTo;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", nameTransaction='" + nameTransaction + '\'' +
                ", whoId=" + whoId +
                ", whomId=" + whomId +
                ", fromCashName='" + fromCashName + '\'' +
                ", toCashName='" + toCashName + '\'' +
                ", moneyFrom=" + moneyFrom +
                ", moneyTo=" + moneyTo +
                '}';
    }
}
