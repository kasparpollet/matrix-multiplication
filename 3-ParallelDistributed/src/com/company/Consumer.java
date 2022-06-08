package com.company;

public class Consumer {
    public static void main(String[] args) {
        System.out.println("From Main2: starting consumer...");
        new DistributedParallelQueueConsumer().run();
    }
}
