public class Consumer {
    public static void main(String[] args) {
        System.out.println("Starting consumer...");
        new DistributedParallelQueueConsumer().run();
    }
}
