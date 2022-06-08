package com.company;

public class Main2 {
    public static void main(String[] args) {
        System.out.println("From Main2: starting consumer...");
        new JMSConsumer().run();
    }
}
