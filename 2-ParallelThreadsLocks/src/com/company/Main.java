package com.company;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the size for the two matrices, it will be used for the columns and rows");
        int size = in.nextInt();
        System.out.println("Enter the number of threads to use");
        int numberOfThreads = in.nextInt();

        float[][] mat1 = generateMatrix(size);
        float[][] mat2 = generateMatrix(size);

        ParallelBlock parallelBlock = new ParallelBlock(mat1, mat2, numberOfThreads);
        long start = System.currentTimeMillis();
        parallelBlock.multiplyMatrices();
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.printf("\nBlock used %d threads and took: \n%d ms, \n%f sec\n", (int) Math.pow(parallelBlock.numberOfSides, 2), time, time / Math.pow(10, 3));

        ParallelNaive parallelNaive = new ParallelNaive(mat1, mat2, numberOfThreads);
        start = System.currentTimeMillis();
        parallelNaive.multiplyMatrices();
        end = System.currentTimeMillis();
        time = end - start;
        System.out.printf("\nNaive used %d threads and took: \n%d ms, \n%f sec\n", numberOfThreads, time, time / Math.pow(10, 3));
    }

    public static float[][] generateMatrix(int size) {
        Random r = new Random(32);
        float[][] matrix = new float[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
//                matrix[i][j] = r.nextInt(Integer.MAX_VALUE/1000000000) + r.nextFloat();
                matrix[i][j] = r.nextInt(20) + r.nextFloat();
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
