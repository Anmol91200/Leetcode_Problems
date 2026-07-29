class Solution {
    public String smallestPalindrome(String s, int k) {
        int n = s.length();
        int[] freq = new int[26];
        for (int i = 0; i < n; i++) {
            freq[s.charAt(i) - 'a']++;
        }

        int oddChar = -1;
        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 != 0) {
                oddChar = i;
            }
        }

        int m = n / 2;
        int[] cnt = new int[26];
        for (int i = 0; i < 26; i++) {
            cnt[i] = freq[i] / 2;
        }

        long total = getPerms(cnt, m, k);
        if (total < k) {
            return "";
        }

        char[] half = new char[m];
        int remLen = m;

        for (int i = 0; i < m; i++) {
            for (int c = 0; c < 26; c++) {
                if (cnt[c] == 0) continue;

                cnt[c]--;
                long count = getPerms(cnt, remLen - 1, k);

                if (k <= count) {
                    half[i] = (char) ('a' + c);
                    remLen--;
                    break;
                } else {
                    k -= count;
                    cnt[c]++;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(new String(half));
        if (oddChar != -1) {
            sb.append((char) ('a' + oddChar));
        }
        for (int i = m - 1; i >= 0; i--) {
            sb.append(half[i]);
        }

        return sb.toString();
    }

    private long getPerms(int[] cnt, int remLen, long limit) {
        long ans = 1;
        int cur = remLen;
        for (int i = 0; i < 26; i++) {
            if (cnt[i] == 0) continue;
            long c = comb(cur, cnt[i], limit);
            ans = capMul(ans, c, limit);
            if (ans >= limit) return limit;
            cur -= cnt[i];
        }
        return ans;
    }

    private long comb(int n, int r, long limit) {
        if (r < 0 || r > n) return 0;
        if (r == 0 || r == n) return 1;
        if (r > n - r) r = n - r;
        long res = 1;
        for (int i = 1; i <= r; i++) {
            res = res * (n - i + 1) / i;
            if (res >= limit) return limit;
        }
        return res;
    }

    private long capMul(long a, long b, long limit) {
        if (a == 0 || b == 0) return 0;
        if (a > limit / b) return limit;
        return Math.min(a * b, limit);
    }
}