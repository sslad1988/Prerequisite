package com.thoughtworks.workshop.future;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class AccountTest {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        Account a = new Account(123);
        Random rand = new Random();
        //rand.nextInt(10)
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<Double>> resultList = new ArrayList<>();

        Callable<Double> deposit = () -> a.deposit(100);

        Callable<Double> withdrawal = () -> a.withdraw(100);

        Runnable transaction = () -> {
            a.listTransaction();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 50; i++) {
            Future<Double> depositResult = executorService.submit(deposit);
            resultList.add(depositResult);
            Future<Double> withdrawalResult = executorService.submit(withdrawal);
            resultList.add(withdrawalResult);

        }


        for (Future<Double> future : resultList) {
            try {
                a.recordTransaction(future.get());
                System.out.println("Future result is - " + " - " + future.get() + "; And Task done is " + future.isDone());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.execute(transaction);
        executorService.shutdown();

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken " + (endTime - startTime));
    }


}
