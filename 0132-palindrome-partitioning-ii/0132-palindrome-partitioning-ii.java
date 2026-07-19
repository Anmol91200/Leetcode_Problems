class Solution {
    public int minCut(String s) {
        int n = s.length();
        if (n <= 1) return 0;

        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = i;
        }

        for (int i = 0; i < n; i++) {
            expand(s, i, i, dp);
            expand(s, i, i + 1, dp);
        }

        return dp[n - 1];
    }

    private void expand(String s, int left, int right, int[] dp) {
        int n = s.length();
        while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
            if (left == 0) {
                dp[right] = 0;
            } else {
                dp[right] = Math.min(dp[right], dp[left - 1] + 1);
            }
            left--;
            right++;
        }
    }
}