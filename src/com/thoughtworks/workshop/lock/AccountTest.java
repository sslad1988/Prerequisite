package com.thoughtworks.workshop.lock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountTest {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        Account a = new Account(123);
        Random rand = new Random();
        //rand.nextInt(10)
        ExecutorService executorService = Executors.newFixedThreadPool(10);


        Runnable deposit = () -> a.deposit(100);


        Runnable withdrawal = () -> a.withdraw(100);

        Runnable transaction = () -> {
            a.listTransaction();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 50; i++) {
            executorService.execute(deposit);
            executorService.execute(withdrawal);
            //executorService.execute(transaction);
        }

        executorService.shutdown();

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken " + (endTime - startTime));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a.listTransaction();
    }


}
