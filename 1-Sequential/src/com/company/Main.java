package com.company;

import java.util.Scanner;


// TODO use FloatingPoint
// TODO potential dataset: https://www.kaggle.com/datasets/fivethirtyeight/uber-pickups-in-new-york-city?select=uber-raw-data-jul14.csv
public class Main {

    public static void main(String[] args) {
        // variables to hold information of the rows and columns of the matrices
        int r1, r2, c1, c2, i, j, k, sum;
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the number of rows of matrix 1");
        r1 = input.nextInt();

        System.out.println("Enter the number columns of matrix 1");
        c1 = input.nextInt();

        System.out.println("Enter the number of rows of matrix 2");
        r2 = input.nextInt();

        System.out.println("Enter the number of columns of matrix 2");
        c2 = input.nextInt();

        if (c1 == r2) {
            int[][] mat1 = new int[r1][c1];
            int[][] mat2 = new int[r2][c2];
            int[][] res = new int[r1][c2];

            System.out.println("Enter the elements of matrix 1");
            for (i = 0; i < r1; i++) {
                for (j = 0; j < c1; j++) {
                    mat1[i][j] = input.nextInt();
                }
            }

            System.out.println("Enter the elements of matrix 2");
            for (i = 0; i < r2; i++) {
                for (j = 0; j < c2; j++) {
                    mat2[i][j] = input.nextInt();
                }
            }

            System.out.println("\nMatrix 1:");
            for (i = 0; i < r1; i++) {
                for (j = 0; j < c1; j++) {
                    System.out.print(mat1[i][j] + " ");
                }
                System.out.println();
            }

            System.out.println("\nMatrix 2:");
            for (i = 0; i < r2; i++) {
                for (j = 0; j < c2; j++) {
                    System.out.print(mat2[i][j] + " ");
                }
                System.out.println();
            }

            // Here matrix 1 and 2 are multiplied
            System.out.println("\nOutput matrix:");
            for (i = 0; i < r1; i++) {
                for (j = 0; j < c2; j++) {
                    sum = 0;
                    for (k = 0; k < r2; k++) {
                        sum += mat1[i][k] * mat2[k][j];
                    }
                    res[i][j] = sum;
                }
            }

            for (i = 0; i < r1; i++) {
                for (j = 0; j < c2; j++) {
                    System.out.print(res[i][j] + " ");
                }
                System.out.println();
            }
        } else System.out.print("multiplication does not exist ");
    }
}
