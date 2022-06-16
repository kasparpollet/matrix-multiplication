public class Sequential {

    public static float[][] multiplyMatrices(float[][] mat1, float[][] mat2) {
        float[][] res = new float[mat1.length][mat2[0].length];
        float sum;

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res.length; j++) {
                sum = 0;
                for (int k = 0; k < res.length; k++) {
                    sum += mat1[i][k] * mat2[j][k];
                }
                res[i][j] = sum;
            }
        }
        return res;
    }
}
