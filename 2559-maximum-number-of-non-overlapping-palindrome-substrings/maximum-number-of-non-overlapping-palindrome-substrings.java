class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        boolean[][] isPalindrome = new boolean[n][n];
        int[] dp = new int[n + 1];

        for (int length = 1; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    if (length <= 2 || isPalindrome[i + 1][j - 1]) {
                        isPalindrome[i][j] = true;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            dp[i + 1] = dp[i];
            for (int j = 0; j <= i; j++) {
                if (i - j + 1 >= k && isPalindrome[j][i]) {
                    dp[i + 1] = Math.max(dp[i + 1], dp[j] + 1);
                }
            }
        }

        return dp[n];
    }
}