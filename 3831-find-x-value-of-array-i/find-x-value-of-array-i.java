class Solution {
    public long[] resultArray(int[] nums, long k) {
        int n = nums.length;
        long[] result = new long[(int) k];
        long[] dp = new long[(int) k];

        for (int num : nums) {
            long[] nextDp = new long[(int) k];
            int val = (int) (num % k);
            nextDp[val]++;

            for (int r = 0; r < k; r++) {
                if (dp[r] > 0) {
                    int nextR = (int) ((r * (long) val) % k);
                    nextDp[nextR] += dp[r];
                }
            }

            for (int r = 0; r < k; r++) {
                dp[r] = nextDp[r];
                result[r] += dp[r];
            }
        }

        return result;
    }
}