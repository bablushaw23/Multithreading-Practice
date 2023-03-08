package codes.deadlock11;

import java.util.Random;

public class SimpleWorker1 {
    private Account ac1= new Account();
    private Account ac2= new Account();
    public void firstThread() throws InterruptedException {
        Random r1= new Random();
        for(int i=0; i<1e5; i++){
            Account.transfer(ac1, ac2, r1.nextInt(100));
        }
    }

    public void secondThread() throws InterruptedException{
        Random r1= new Random();
        for(int i=0; i<1e5; i++){
            Account.transfer(ac2, ac1, r1.nextInt(100));
        }
    }

    public void finished(){
        System.out.println("Account 1 balance:"+ac1.getBalance());
        System.out.println("Account 2 balance:"+ac2.getBalance());
        System.out.println("Total balance:"+(ac1.getBalance()+ac2.getBalance()));
    }
}
