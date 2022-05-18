package com.company;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static float[][] mat1;
    private static float[][] mat2;
    private static float[][] res;
    private static int numberOfThreads;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the size for the two matrices, it will be used for the columns and rows");
        int size = in.nextInt();
        System.out.println("Enter the number of threads to use");
        numberOfThreads = in.nextInt();

        mat1 = generateMatrix(size);
        mat2 = generateMatrix(size);

        long start = System.currentTimeMillis();
        float[][] res = multiplyMatricesParallel();
        long end = System.currentTimeMillis();
        long time = end - start;

        System.out.printf("\nIt took: \n%d ms, \n%f sec", time, time / Math.pow(10, 3));
    }

    private static float[][] generateMatrix(int size) {
        Random r = new Random();
        float[][] matrix = new float[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = r.nextInt(Integer.MAX_VALUE) + r.nextFloat();
            }
        }
        return matrix;
    }

    private static float[][] multiplyMatricesParallel() {
        Thread[] threads = new Thread[numberOfThreads];
        res = new float[mat1.length][mat2[0].length];

        for (int h = 0; h < numberOfThreads; h++) {
            int finalH = h;
            Thread thread = new Thread(() -> threadRun(finalH));
            threads[h] = thread;
            thread.start();
        }

        // Wait for the matrix to be fully calculated
        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private static void threadRun(int h) {
        float sum;

        for (int i = h; i < res.length; i+=numberOfThreads)
            for (int j = 0; j < res.length; j++) {
                sum = 0;
                for (int k = 0; k < res.length; k++) {
                    sum += mat1[i][k] * mat2[k][j];
                }
                res[i][j] = sum;
            }
    }

    private static void printMatrix(float[][] matrix) {
        for (float[] floats : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(floats[j] + " ");
            }
            System.out.println();
        }
    }
}
