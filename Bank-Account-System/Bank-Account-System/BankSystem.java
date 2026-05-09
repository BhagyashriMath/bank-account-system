public class BankSystem {
    public static void main(String[] args) {

        BankAccount acc1 = new BankAccount("Alice", 101);
        BankAccount acc2 = new BankAccount("Bob", 102);

        acc1.deposit(5000);
        acc1.withdraw(1000);
        acc1.transfer(acc2, 1500);

        acc2.deposit(2000);

        acc1.showStatement();
        acc2.showStatement();
    }
}