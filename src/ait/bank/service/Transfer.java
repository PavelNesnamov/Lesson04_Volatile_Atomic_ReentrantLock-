package ait.bank.service;
import ait.bank.model.Account;
import java.util.concurrent.locks.ReentrantLock;

public class Transfer implements Runnable {

    ReentrantLock locker; // add ReentrantLock lock as variable
    private Account accFrom;
    private Account accTo;
    private int sum;

    public Transfer(Account accFrom, Account accTo, int sum, ReentrantLock lock) {
        this.accFrom = accFrom;
        this.accTo = accTo;
        this.sum = sum;
        locker = lock;  // add ReentrantLock to constructor
    }


    @Override
    public void run() {

        locker.lock(); //blocking

        synchronized (accFrom) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {
                locker.unlock(); //unblocked
            }
            synchronized (accTo) {
                if (accFrom.getBalance() >= sum) {
                    accFrom.credit(sum);
                    accTo.debit(sum);

                }
            }
        }
    }
}