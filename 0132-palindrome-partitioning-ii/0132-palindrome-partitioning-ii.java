class Solution {
    public int minCut(String s) {
        int n = s.length();
        if (n <= 1) return 0;

        boolean[][] isPal = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            isPal[i][i] = true;
        }
        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    if (length == 2 || isPal[i + 1][j - 1]) {
                        isPal[i][j] = true;
                    }
                }
            }
        }

        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            if (isPal[0][i]) {
                dp[i] = 0;
            } else {
                int minCuts = i; 
                for (int j = 0; j < i; j++) {
                    if (isPal[j + 1][i]) {
                        minCuts = Math.min(minCuts, dp[j] + 1);
                    }
                }
                dp[i] = minCuts;
            }
        }

        return dp[n - 1];
    }
}