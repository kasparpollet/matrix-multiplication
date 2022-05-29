package com.company;

public class ParallelNaive {
    private final float[][] mat1;
    private final float[][] mat2;
    private float[][] res;
    private final int numberOfThreads;

    public ParallelNaive(float[][] mat1, float[][] mat2, int numberOfThreads) {
        this.mat1 = mat1;
        this.mat2 = mat2;
        this.numberOfThreads = numberOfThreads;
    }

    public float[][] multiplyMatrices() {
        Thread[] threads = new NaiveThread[numberOfThreads];
        res = new float[mat1.length][mat2[0].length];

        for (int h = 0; h < numberOfThreads; h++) {
            Thread thread = new NaiveThread(h);
            threads[h] = thread;
            thread.start();
        }

        // Wait for the matrix to be fully calculated
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    class NaiveThread extends Thread {
        private final int threadNumber;

        public NaiveThread(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {
            float sum;
            int i, j, k;

            for (i = this.threadNumber; i < res.length; i += numberOfThreads) {
                for (j = 0; j < res.length; j++) {
                    sum = 0;
                    for (k = 0; k < res[0].length; k++) {
                        sum += mat1[i][k] * mat2[j][k];
                    }
                    res[i][j] = sum;
                }
            }
        }
    }
}
