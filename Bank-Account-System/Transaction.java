import java.util.Date;

class Transaction {
    private Date date;
    private String description;
    private double amount;
    private double balance;

    public Transaction(String description, double amount, double balance) {
        this.date = new Date();
        this.description = description;
        this.amount = amount;
        this.balance = balance;
    }

    public void display() {
        System.out.printf("%-25s %-20s %-10.2f %-10.2f\n",
                date.toString(), description, amount, balance);
    }
}