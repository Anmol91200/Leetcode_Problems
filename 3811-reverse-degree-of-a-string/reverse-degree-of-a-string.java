class Solution {
    public int reverseDegree(String s) {
        int totalSum = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int revAlpha = 'z' - c + 1;
            int strIndex = i + 1;
            totalSum += revAlpha * strIndex;
        }
        return totalSum;
    }
}