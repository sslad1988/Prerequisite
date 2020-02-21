package com.thoughtworks.workshop.future;

import java.util.LinkedList;
import java.util.List;
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

    public double withdraw(double amount) {
        if (amount <= 0) {
            return balance;
        }
        if (amount > balance) {
            System.out.println(WITHDRAWAL + "_FAILED" + " : " + amount);

            return balance;
        }
        System.out.println(WITHDRAWAL + " : " + amount);
        // lock.lock();
        balance = balance - amount;
        return balance;
        // lock.unlock();
    }

    public double deposit(double amount) {
        if (amount <= 0) {
            return balance;
        }
        System.out.println(DEPOSIT + " : " + amount);
        // lock.lock();
        balance = balance + amount;
        return balance;
    }


    public void recordTransaction(double balance) {
        String transaction = " Current Balance : " + balance;
        statement.add("| " + seq++ + transaction);
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
