package ua;

import com.google.gson.*;
import ua.privatBankJSON.*;

import javax.persistence.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            emf = Persistence.createEntityManagerFactory("JPABank");
            em = emf.createEntityManager();
            try {
                initExchangeRatePrivatBank();
                while (true) {
                    System.out.println("Enter 1 : add customer");
                    System.out.println("Enter 2 : refill account");
                    System.out.println("Enter 3 : transfer money");
                    System.out.println("Enter 4 : conversion currency");
                    System.out.println("Enter 5 : calculate total money in UAH");
                    System.out.println("Enter 6 : exit");
                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addCustomer(sc);
                            break;
                        case "2":
                            refillAccount(sc);
                            break;
                        case "3":
                            transferMoney(sc);
                            break;
                        case "4":
                            conversionCurrency(sc);
                            break;
                        case "5":
                            calculateTotalMoneyUAH(sc);
                            break;
                        case "6":
                            return;
                        default:
                            break;
                    }
                }
            } finally {
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String performRequest(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        StringBuilder sb = new StringBuilder();
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            char[] buf = new char[1000000];
            int r = 0;
            do {
                if ((r = br.read(buf)) > 0)
                    sb.append(new String(buf, 0, r));
            } while (r > 0);
        } finally {
            http.disconnect();
        }
        return sb.toString();
    }

    private static ExchangeRate initExchangeRatePrivatBank() {
        ExchangeRate exchangeRate = null;
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formDate = new SimpleDateFormat("dd.MM.yyyy");
            String request = "https://api.privatbank.ua/p24api/exchange_rates?json&date=" + formDate.format(calendar.getTime());
            String result = performRequest(request);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            BankJSON json = gson.fromJson(result, BankJSON.class);
            double saleRateUSD = json.getExchangeRate().get(16).getSaleRate();
            double purchaseRateUSD = json.getExchangeRate().get(16).getPurchaseRate();
            double saleRateEUR = json.getExchangeRate().get(22).getSaleRate();
            double purchaseRateEUR = json.getExchangeRate().get(22).getPurchaseRate();
            exchangeRate = new ExchangeRate(purchaseRateUSD, saleRateUSD, purchaseRateEUR, saleRateEUR);
            em.getTransaction().begin();
            try {
                em.persist(exchangeRate);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return exchangeRate;
    }

    private static void addCustomer(Scanner sc) {
        List<Cash> cashList = new ArrayList<>();
        Cash eur = new Cash("EUR", 0);
        Cash usd = new Cash("USD", 0);
        Cash uah = new Cash("UAH", 0);
        System.out.println("Enter your Id_Number:");
        String sId = sc.nextLine();
        Long id_number = Long.parseLong(sId);
        System.out.println("Enter your name: ");
        String name = sc.nextLine();
        System.out.println("Enter your surname: ");
        String surname = sc.nextLine();
        Customer customer = new Customer(id_number, name, surname, cashList);
        customer.addCash(eur);
        customer.addCash(usd);
        customer.addCash(uah);
        em.getTransaction().begin();
        try {
            em.persist(customer);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void refillAccount(Scanner sc) {
        System.out.println("Enter customer id:");
        String sId = sc.nextLine();
        Long id = Long.parseLong(sId);
        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            System.out.println("Client not found.");
            return;
        }
        Transaction transaction = null;
        System.out.println("Enter amount of money:");
        String sAmountMoney = sc.nextLine();
        double amountMoney = Double.parseDouble(sAmountMoney);
        System.out.println("Enter cash name (EUR, USD, UAH) :");
        String nameCash = sc.nextLine();
        em.getTransaction().begin();
        try {
            if (nameCash.equals("EUR") || nameCash.equals("eur")) {
                customer.getCashList().get(0).setMoney(customer.getCashList().get(0).getMoney() + amountMoney);
                transaction = new Transaction("refill account", id, id, null, "EUR", null, amountMoney);
            } else if (nameCash.equals("USD") || nameCash.equals("usd")) {
                customer.getCashList().get(1).setMoney(customer.getCashList().get(1).getMoney() + amountMoney);
                transaction = new Transaction("refill account", id, id, null, "USD", null, amountMoney);
            } else if (nameCash.equals("UAH") || nameCash.equals("uah")) {
                customer.getCashList().get(2).setMoney(customer.getCashList().get(2).getMoney() + amountMoney);
                transaction = new Transaction("refill account", id, id, null, "UAH", null, amountMoney);
            }
            em.persist(transaction);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void transferMoney(Scanner sc) {
        System.out.println("Enter sender id:");
        String sIdSender = sc.nextLine();
        Long idSender = Long.parseLong(sIdSender);
        System.out.println("Enter recipient id");
        String sIdRecipient = sc.nextLine();
        Long idRecipient = Long.parseLong(sIdRecipient);
        Customer customerSender = em.find(Customer.class, idSender);
        Customer customerRecipient = em.find(Customer.class, idRecipient);
        if (customerRecipient != null && customerSender != null) {
            System.out.println("Enter cash name (EUR, USD, UAH) :");
            String cashName = sc.nextLine();
            System.out.println("Enter amount of money:");
            String sMoney = sc.nextLine();
            Double money = Double.parseDouble(sMoney);
            if (cashName.equals("EUR") || cashName.equals("eur")) {
                transfer(customerSender, customerRecipient, money, 0, cashName);
            } else if (cashName.equals("USD") || cashName.equals("usd")) {
                transfer(customerSender, customerRecipient, money, 1, cashName);
            } else if (cashName.equals("UAH") || cashName.equals("uah")) {
                transfer(customerSender, customerRecipient, money, 2, cashName);
            }
        } else System.out.println("Error!");
    }

    private static void transfer(Customer sender, Customer recepient, double money, int index, String cashName) {
        if (sender.getCashList().get(index).getMoney() >= money) {
            em.getTransaction().begin();
            try {
                sender.getCashList().get(index).setMoney(sender.getCashList().get(index).getMoney() - money);
                recepient.getCashList().get(index).setMoney(recepient.getCashList().get(index).getMoney() + money);
                Transaction transaction = new Transaction("transfer money", sender.getIdNumber(), recepient.getIdNumber(), cashName, cashName, money, money);
                em.persist(transaction);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
            }
        } else if (sender.getCashList().get(index).getMoney() < money) {
            System.out.println("No enough money in the account.");
        }
    }

    private static void conversionCurrency(Scanner sc) {
        System.out.println("Enter customer id:");
        String sId = sc.nextLine();
        Long id = Long.parseLong(sId);
        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            System.out.println("Client not found.");
            return;
        }
        System.out.println("Enter cash name from (EUR, USD) : ");
        String cashName = sc.nextLine();
        System.out.println("Enter amount of money: ");
        String sAmount = sc.nextLine();
        Double amount = Double.parseDouble(sAmount);
        Double cashEURInUAH = amount * initExchangeRatePrivatBank().getPurchaseRateEUR();
        Double cashUSDInUAH = amount * initExchangeRatePrivatBank().getPurchaseRateUSD();
        if (customer.getCashList().get(0).getMoney() >= amount && cashName.equals("EUR")) {
            conversion(sc, customer, cashName, amount, cashEURInUAH, 0);
        } else if (cashName.equals("USD") && customer.getCashList().get(1).getMoney() >= amount) {
            conversion(sc, customer, cashName, amount, cashUSDInUAH, 1);
        }
    }

    private static void conversion(Scanner sc, Customer customer, String cashName, Double amount, Double cashEURInUAH, int index) {
        em.getTransaction().begin();
        try {
            Transaction transaction = new Transaction("conversion currency", customer.getIdNumber(), customer.getIdNumber(), cashName, "UAH", amount, cashEURInUAH);
            customer.getCashList().get(index).setMoney(customer.getCashList().get(index).getMoney() - amount);
            customer.getCashList().get(2).setMoney(customer.getCashList().get(2).getMoney() + cashEURInUAH);
            em.persist(customer);
            em.persist(transaction);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void calculateTotalMoneyUAH(Scanner sc) {
        System.out.println("Enter customer id: ");
        String sId = sc.nextLine();
        long id = Long.parseLong(sId);
        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            System.out.println("Client not found.");
            return;
        }
        Double cashEURinUAH = customer.getCashList().get(0).getMoney() * initExchangeRatePrivatBank().getPurchaseRateEUR();
        Double cashUSDinUAH = customer.getCashList().get(1).getMoney() * initExchangeRatePrivatBank().getPurchaseRateUSD();
        Double cashUAH = customer.getCashList().get(2).getMoney();
        Double totalMoneyInUAH = cashEURinUAH + cashUSDinUAH + cashUAH;
        System.out.println(totalMoneyInUAH);
        em.getTransaction().begin();
        try {
            Transaction transaction = new Transaction("calculate total money UAH", id, id, "from all cash", "UAH", null, totalMoneyInUAH);
            em.persist(transaction);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }
}