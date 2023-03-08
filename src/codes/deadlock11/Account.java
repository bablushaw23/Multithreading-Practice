package codes.deadlock11;

public class Account {
    private int balance=10_000;

    public void deposit(int money){         // without declaring synchronized, total balance may not be 20k
        balance+=money;
    }

    public void withdraw(int money){        // without declaring synchronized, total balance may not be 20k
        balance-=money;
    }

    public int getBalance(){
        return balance;
    }

    public static void transfer(Account ac1, Account ac2, int amount){
        ac1.withdraw(amount);
        ac2.deposit(amount);
    }
}
