import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestMatrixMultiplication {

    private float[][] mat1;
    private float[][] mat2;

    @BeforeEach
    public void generateMatrices() {
        mat1 = Producer.generateMatrix(20);
        mat2 = Producer.generateMatrix(20);
    }

    @Test
    public void testDistributedWithOneConsumer() {
        float[][] resultSequential = Sequential.multiplyMatrices(mat1, mat2);

        Thread thread = new Thread(new DistributedParallelQueueConsumer()::run);
        thread.start();
        DistributedParallelQueueProducer distributedParallelQueueProducer = new DistributedParallelQueueProducer(mat1, mat2, 1);
        float[][] result = distributedParallelQueueProducer.multiplyMatrices();

        assertEquals(resultSequential.length, result.length);
        assertEquals(resultSequential[0].length, result[0].length);

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                assertEquals(resultSequential[i][j], result[i][j]);
            }
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDistributedWithFourConsumers() {
        float[][] resultSequential = Sequential.multiplyMatrices(mat1, mat2);

        Thread[] threads = new Thread[4];
        for (int i = 0; i < threads.length; i++) {
            Thread thread = new Thread(new DistributedParallelQueueConsumer()::run);
            thread.start();
            threads[i] = thread;
        }

        DistributedParallelQueueProducer distributedParallelQueueProducer = new DistributedParallelQueueProducer(mat1, mat2, 4);
        float[][] result = distributedParallelQueueProducer.multiplyMatrices();

        assertEquals(resultSequential.length, result.length);
        assertEquals(resultSequential[0].length, result[0].length);

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                assertEquals(resultSequential[i][j], result[i][j]);
            }
        }

        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
