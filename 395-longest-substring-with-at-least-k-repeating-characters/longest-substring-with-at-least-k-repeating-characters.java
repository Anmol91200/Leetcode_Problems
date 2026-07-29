class Solution {
    public int longestSubstring(String s, int k) {
        int maxLen = 0;
        int maxUnique = getUniqueCharCount(s);

        for (int targetUnique = 1; targetUnique <= maxUnique; targetUnique++) {
            int[] charMap = new int[26];
            int windowStart = 0;
            int windowEnd = 0;
            int unique = 0;
            int countAtLeastK = 0;

            while (windowEnd < s.length()) {
                if (unique <= targetUnique) {
                    int idx = s.charAt(windowEnd) - 'a';
                    if (charMap[idx] == 0) unique++;
                    charMap[idx]++;
                    if (charMap[idx] == k) countAtLeastK++;
                    windowEnd++;
                } else {
                    int idx = s.charAt(windowStart) - 'a';
                    if (charMap[idx] == k) countAtLeastK--;
                    charMap[idx]--;
                    if (charMap[idx] == 0) unique--;
                    windowStart++;
                }

                if (unique == targetUnique && unique == countAtLeastK) {
                    maxLen = Math.max(maxLen, windowEnd - windowStart);
                }
            }
        }

        return maxLen;
    }

    private int getUniqueCharCount(String s) {
        boolean[] seen = new boolean[26];
        int count = 0;
        for (char c : s.toCharArray()) {
            if (!seen[c - 'a']) {
                seen[c - 'a'] = true;
                count++;
            }
        }
        return count;
    }
}