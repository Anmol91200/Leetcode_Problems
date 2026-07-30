import java.util.Arrays;

class Solution {
    public int kthLargestValue(int[][] matrix, int k) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[] vals = new int[m * n];
        int[][] pref = new int[m + 1][n + 1];
        int idx = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                pref[i + 1][j + 1] = matrix[i][j] ^ pref[i][j + 1] ^ pref[i + 1][j] ^ pref[i][j];
                vals[idx++] = pref[i + 1][j + 1];
            }
        }

        Arrays.sort(vals);
        return vals[m * n - k];
    }
}