class Solution {
    public int largestPalindrome(int n) {
        if (n == 1) {
            return 9;
        }

        long max = (long) Math.pow(10, n) - 1;
        long min = (long) Math.pow(10, n - 1);

        for (long left = max; left >= min; left--) {
            long palindrome = left;
            for (long temp = left; temp > 0; temp /= 10) {
                palindrome = palindrome * 10 + (temp % 10);
            }

            for (long j = max; j * j >= palindrome; j--) {
                if (palindrome % j == 0) {
                    return (int) (palindrome % 1337);
                }
            }
        }

        return 0;
    }
}