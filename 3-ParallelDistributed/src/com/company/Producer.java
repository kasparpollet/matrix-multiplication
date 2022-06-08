package com.company;

import java.util.Random;
import java.util.Scanner;

public class Producer {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the size for the two matrices, it will be used for the columns and rows");
        int size = in.nextInt();
        System.out.println("Enter the number of consumers to use");
        int numberOfConsumers = in.nextInt();

        // int[] sizes = { 1000, 3000, 5000 };
        // int[] threads = { 1, 2, 4, 8, 9, 12, 16 };

        // for (int size : sizes) {
        float[][] mat1 = generateMatrix(size);
        float[][] mat2 = generateMatrix(size);
        // for (int numberOfThreads : threads) {


        DistributedParallelQueueProducer parallelQueue = new DistributedParallelQueueProducer(mat1, mat2, numberOfConsumers);
        long start = System.currentTimeMillis();
        parallelQueue.multiplyMatrices();
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.printf("\nQueue used %d threads and took: \n%d ms, \n%f sec\n", numberOfConsumers, time,
                time / Math.pow(10, 3));
        // }
        // }
    }

    public static float[][] generateMatrix(int size) {
        Random r = new Random(32);
        float[][] matrix = new float[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // matrix[i][j] = r.nextInt(Integer.MAX_VALUE / 10_000_000);
                matrix[i][j] = r.nextFloat();
                // matrix[i][j] = r.nextInt(Integer.MAX_VALUE) + r.nextFloat();
                // matrix[i][j] = r.nextInt(9);
            }
        }
        return matrix;
    }

    public static void printMatrix(float[][] matrix) {
        for (float[] floats : matrix) {
            for (float aFloat : floats) {
                System.out.print(aFloat + " ");
            }
            System.out.println();
        }
    }
}
