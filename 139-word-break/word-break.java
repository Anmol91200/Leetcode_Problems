import java.util.List;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        int maxLen = 0;
        for (String word : wordDict) {
            maxLen = Math.max(maxLen, word.length());
        }

        for (int i = 0; i < n; i++) {
            if (!dp[i]) continue;

            for (String word : wordDict) {
                int len = word.length();
                if (i + len <= n && !dp[i + len]) {
                    if (s.startsWith(word, i)) {
                        dp[i + len] = true;
                    }
                }
            }
        }

        return dp[n];
    }
}