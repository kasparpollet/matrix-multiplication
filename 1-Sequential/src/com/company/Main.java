package com.company;

import java.util.Random;
import java.util.Scanner;

// TODO potential dataset: https://www.kaggle.com/datasets/fivethirtyeight/uber-pickups-in-new-york-city?select=uber-raw-data-jul14.csv
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the size for the two matrices, it will be used for the columns and rows");
        int size = in.nextInt();

        float[][] mat1 = generateMatrix(size);
        float[][] mat2 = generateMatrix(size);

        long start = System.currentTimeMillis();
        float[][] res = multiplyMatrices(mat1, mat2);
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

    private static float[][] multiplyMatrices(float[][] mat1, float[][] mat2) {
        float[][] res = new float[mat1.length][mat2[0].length];
        float sum;

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res.length; j++) {
                sum = 0;
                for (int k = 0; k < res.length; k++) {
                    sum += mat1[i][k] * mat2[k][j];
                }
                res[i][j] = sum;
            }
        }
        return res;
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
