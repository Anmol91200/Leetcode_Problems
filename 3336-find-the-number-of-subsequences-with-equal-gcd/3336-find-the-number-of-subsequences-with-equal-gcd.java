class Solution {
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public int subsequencePairCount(int[] nums) {
        int MOD = 1_000_000_007;
        int maxVal = 0;
        for (int x : nums) {
            maxVal = Math.max(maxVal, x);
        }

        int[][] dp = new int[maxVal + 1][maxVal + 1];
        dp[0][0] = 1;

        for (int x : nums) {
            int[][] nextDp = new int[maxVal + 1][maxVal + 1];
            for (int g1 = 0; g1 <= maxVal; g1++) {
                for (int g2 = 0; g2 <= maxVal; g2++) {
                    if (dp[g1][g2] == 0) continue;

                    nextDp[g1][g2] = (nextDp[g1][g2] + dp[g1][g2]) % MOD;

                    int ng1 = gcd(g1, x);
                    nextDp[ng1][g2] = (nextDp[ng1][g2] + dp[g1][g2]) % MOD;

                    int ng2 = gcd(g2, x);
                    nextDp[g1][ng2] = (nextDp[g1][ng2] + dp[g1][g2]) % MOD;
                }
            }
            dp = nextDp;
        }

        long ans = 0;
        for (int g = 1; g <= maxVal; g++) {
            ans = (ans + dp[g][g]) % MOD;
        }

        return (int) ans;
    }
}