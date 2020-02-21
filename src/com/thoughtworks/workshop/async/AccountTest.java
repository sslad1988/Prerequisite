package com.thoughtworks.workshop.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class AccountTest {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        Account a = new Account(123);

        ExecutorService executorService = Executors.newFixedThreadPool(10);


        Supplier<Double> deposit = () -> a.deposit(100);


        Supplier<Double> withdrawal = () -> a.withdraw(100);


        for (int i = 0; i < 50; i++) {

            CompletableFuture<Double> depositFuture = CompletableFuture.supplyAsync(deposit, executorService);
            depositFuture.thenAccept(a::recordTransaction);
            CompletableFuture<Double> withdrawalFuture = CompletableFuture.supplyAsync(withdrawal, executorService);
            withdrawalFuture.thenAccept(a::recordTransaction);

        }

        executorService.shutdown();
        //   a.listTransaction();

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken " + (endTime - startTime));
    }


}
