package com.thoughtworks.workshop.async;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    public static final String WITHDRAWAL = "WITHDRAWAL";
    private static final String DEPOSIT = "DEPOSIT";
    private static int seq = 0;
    ReentrantLock lock;
    private int accountNumber;
    private double balance;
    private List<String> statement = new LinkedList<>();


    public Account(int accountNumber) {
        this.accountNumber = accountNumber;
        lock = new ReentrantLock(true);
    }

    public String withdraw(double amount) {
        UUID uuid = UUID.randomUUID();
        System.out.println("Before : " + WITHDRAWAL + " : " + uuid + ", Balance : " + this.balance);
        if (amount <= 0) {
            return WITHDRAWAL + "_FAILED : " + uuid;
        }
        if (amount > balance) {
            System.out.println(uuid + " : " + WITHDRAWAL + "_FAILED" + " : " + amount);
            //recordTransaction(oldBalance, amount, WITHDRAWAL + "_FAILED");
            return WITHDRAWAL + "_FAILED : " + uuid;
        }

        System.out.println(uuid + " : " + WITHDRAWAL + " : " + amount);
        // lock.lock();
        balance = balance - amount;
        return WITHDRAWAL + " : " + uuid;
        // lock.unlock();
    }

    public String deposit(double amount) {
        UUID uuid = UUID.randomUUID();
        System.out.println("Before : " + DEPOSIT + " : " + uuid + ", Balance : " + this.balance);
        if (amount <= 0) {
            return DEPOSIT + " : " + uuid;
        }
        System.out.println(uuid + " : " + DEPOSIT + " : " + amount);

        balance = balance + amount;
        return DEPOSIT + " : " + uuid;

    }


    public void recordTransaction(String uuid) {
        String transaction = "After : " + uuid + ", Balance : " + this.balance;
        System.out.println(transaction);
        statement.add("| " + seq++ + transaction);
        //   listTransaction();
    }

    public void listTransaction() {
        System.out.println("======================= Start Statement for Account # " + accountNumber + "=============================================");
        if (statement.size() == 0) {
            System.out.println("======================= End Statement for Account # " + accountNumber + "==============================================");
            return;
        }
        // lock.lock();
        for (String stat : statement) {
            System.out.println(stat + getPadding(stat.length()));
        }
        //lock.lock();
        System.out.println("======================= End Statement for Account # " + accountNumber + "==============================================");
    }

    private String getPadding(int length) {
        return " ".repeat(Math.max(0, 100 - length)) + "|";
    }
}
