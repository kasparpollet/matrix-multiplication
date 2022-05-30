package com.company;

import java.util.ArrayList;
import java.util.List;

public class ParallelBlock {
    private final float[][] mat1;
    private final float[][] mat2;
    public float[][] res;
    private float[][][][] subRes;
    private final int maxNumberOfThreads;
    public final int numberOfSides;

    public ParallelBlock(float[][] mat1, float[][] mat2, int maxNumberOfThreads) {
        this.mat1 = mat1;
        this.mat2 = mat2;
        this.maxNumberOfThreads = maxNumberOfThreads;
        this.numberOfSides = this.getNumberOfSides();
    }

    public int getNumberOfSides() {
        int maxSides = (int) Math.floor(Math.sqrt(maxNumberOfThreads));
        if (mat1.length >= 4 && mat2[0].length >= 4) {
            for (int i = maxSides; i > 0; i--) {
                if (mat1.length % i == 0 && mat2[0].length % i == 0) {
                    return i;
                }
            }
        }
        return 1;
    }

    private float[][][][] createSubMatrices(float[][] matrix) {
        int subSize = mat1.length / numberOfSides;

        int size = (int) Math.pow(numberOfSides, 2);
        float[][][][] subMatrices = new float[numberOfSides][numberOfSides][subSize][subSize];

        for (int i = 0; i < size; i++) {
            float[][] sub = new float[subSize][subSize];
            int startRow = (int) (Math.floor((subSize * i) / matrix.length) * subSize);
            int startCol = (subSize * i) % matrix[0].length;

            for (int row = 0; row < subSize; row++) {
                System.arraycopy(matrix[startRow + row], startCol, sub[row], 0, subSize);
            }

            subMatrices[startRow / subSize][startCol / subSize] = sub;
        }
        return subMatrices;
    }

    private float[][] createMatrixFromSubMatrices(float[][][][] subMatrices) {
        res = new float[mat1.length][mat2.length];
        int row, col;

        for (int i = 0; i < subMatrices.length; i++) {
            for (int j = 0; j < subMatrices[0].length; j++) {
                for (int k = 0; k < subMatrices[0][0].length; k++) {
                    for (int l = 0; l < subMatrices[0][0][0].length; l++) {
                        row = i * subMatrices[0][0].length + k;
                        col = j * subMatrices[0][0][0].length + l;
                        res[row][col] = subMatrices[i][j][k][l];
                    }
                }
            }
        }
        return res;
    }

    // We gaan hier al uit dat mat1[row][col] is en mat2[col][row]
    public float[][] multiplyMatrices() {
        List<ParallelThread> threads = new ArrayList<>();

        float[][][][] subMat1 = createSubMatrices(mat1);
        float[][][][] subMat2 = createSubMatrices(mat2);
        subRes = new float[subMat1.length][subMat1.length][mat1.length][mat2[0].length];
        int i, j;

        for (i = 0; i < subRes.length; i++) {
            for (j = 0; j < subRes[0].length; j++) {
                ParallelThread thread = new ParallelThread(subMat1[i], subMat2[j], i, j);
                threads.add(thread);
                thread.start();
            }
        }

        // Wait for the matrix to be fully calculated
        for (ParallelThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return createMatrixFromSubMatrices(subRes);
    }

    private class ParallelThread extends Thread {
        float[][][] subMatrices1;
        float[][][] subMatrices2;
        float[][] result;
        int resRowPlace;
        int resColPlace;

        public ParallelThread(float[][][] subMatrices1, float[][][] subMatrices2, int resRowPlace, int resColPlace) {
            this.subMatrices1 = subMatrices1;
            this.subMatrices2 = subMatrices2;
            this.resRowPlace = resRowPlace;
            this.resColPlace = resColPlace;
        }

        @Override
        public void run() {
            result = new float[subMatrices1[0].length][subMatrices2[0][0].length];
            float sum;
            int i, j, k;

            for (int l = 0; l < subMatrices1.length; l++) {
                for (i = 0; i < result.length; i++) {
                    for (j = 0; j < result[0].length; j++) {
                        sum = 0;
                        for (k = 0; k < result.length; k++) {
                            sum += subMatrices1[l][i][k] * subMatrices2[l][j][k];
                        }
                        result[i][j] += sum;
                    }
                }
            }
            subRes[resRowPlace][resColPlace] = result;
        }
    }
}
