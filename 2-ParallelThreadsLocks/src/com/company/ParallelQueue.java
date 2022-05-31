package com.company;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ParallelQueue {
    private final float[][] mat1; // float[row][col]
    private final float[][] mat2; // float[col][row]
    public float[][] res;
    private final int numberOfThreads;
    public ConcurrentLinkedQueue<ToCalculate> toBeCalculatedQueue = new ConcurrentLinkedQueue<>();

    public ParallelQueue(float[][] mat1, float[][] mat2, int numberOfThreads) {
        this.mat1 = mat1;
        this.mat2 = mat2;
        this.numberOfThreads = numberOfThreads;
        res = new float[mat1.length][mat2.length];
    }

    public void fillQueue() {
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res.length; j++) {
                toBeCalculatedQueue.add(new ToCalculate(mat1[i], mat2[j], i, j));
            }
        }
    }

    public float[][] multiplyMatrices() {
        QueueThread[] threads = new QueueThread[numberOfThreads];
        fillQueue();

        for (int h = 0; h < numberOfThreads; h++) {
            QueueThread thread = new QueueThread();
            threads[h] = thread;
            thread.start();
        }

        // Wait for the matrix to be fully calculated
        for (QueueThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public class ToCalculate {
        private final float[] col;
        private final float[] row;
        private final int i;
        private final int j;

        public ToCalculate(float[] col, float[] row, int i, int j) {
            this.col = col;
            this.row = row;
            this.i = i;
            this.j = j;
        }

        private void calculate() {
            float sum = 0;
            for (int k = 0; k < res.length; k++) {
                sum += col[k] * row[k];
            }
            res[i][j] = sum;
        }
    }

    class QueueThread extends Thread {

        @Override
        public void run() {
            while (!toBeCalculatedQueue.isEmpty()) {
                ToCalculate object = toBeCalculatedQueue.poll();
                if (object != null)
                    object.calculate();
            }
        }
    }
}
