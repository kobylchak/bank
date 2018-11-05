package ua;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@NamedQuery(name = "findCustomerId", query = "SELECT c FROM Customer c WHERE c.id = :id")
public class Customer {
    @Id
    private long idNumber;
    private String name;
    private String surname;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Cash> cashList = new ArrayList<>();

    public Customer() {
    }

    public Customer(long idNumber, String name, String surname, List<Cash> cashList) {
        this.idNumber = idNumber;
        this.name = name;
        this.surname = surname;
        this.cashList = cashList;
    }

    public long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Cash> getCashList() {
        return cashList;
    }

    public void setCashList(List<Cash> cashList) {
        this.cashList = cashList;
    }
    public void addCash(Cash cash){
        cash.setCustomer(this);
        cashList.add(cash);
    }
}
