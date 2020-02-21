package com.thouhtworks.workshop;

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

    public synchronized void withdraw(double amount) {
        if (amount <= 0) {
            return;
        }
        double oldBalance = balance;
        if (amount > balance) {
            System.out.println(WITHDRAWAL + "_FAILED" + " : " + amount);
            recordTransaction(oldBalance, amount, WITHDRAWAL + "_FAILED");
            return;
        }
        System.out.println(WITHDRAWAL + " : " + amount);
        // lock.lock();
        balance = balance - amount;
        recordTransaction(oldBalance, amount, WITHDRAWAL);
        // lock.unlock();
    }

    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            return;
        }
        double oldBalance = balance;
        System.out.println(DEPOSIT + " : " + amount);
        // lock.lock();
        balance = balance + amount;
        recordTransaction(oldBalance, amount, DEPOSIT);
        // lock.unlock();
    }


    private void recordTransaction(double oldBalance, double amount, String transactionType) {
        String transaction = " : Transaction : " + transactionType + ", Amount : " + amount + ", Old Balance : " + oldBalance + ", Current Balance : " + balance;
        statement.add("| " + seq++ + transaction);
    }

    public synchronized void listTransaction() {
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
        String padding = "";
        for (int i = 0; i < 100 - length; i++) {
            padding = padding + " ";

        }
        return padding + "|";
    }
}
