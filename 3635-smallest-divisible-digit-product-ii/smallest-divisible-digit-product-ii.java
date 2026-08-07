import java.util.Arrays;

class Solution {
    public String smallestNumber(String num, long t) {
        long tempT = t;
        int c2 = 0, c3 = 0, c5 = 0, c7 = 0;

        while (tempT % 2 == 0) { tempT /= 2; c2++; }
        while (tempT % 3 == 0) { tempT /= 3; c3++; }
        while (tempT % 5 == 0) { tempT /= 5; c5++; }
        while (tempT % 7 == 0) { tempT /= 7; c7++; }

        if (tempT > 1) return "-1";

        int n = num.length();

        int[] pref2 = new int[n + 1];
        int[] pref3 = new int[n + 1];
        int[] pref5 = new int[n + 1];
        int[] pref7 = new int[n + 1];

        int zeroPos = -1;
        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';
            if (d == 0) {
                if (zeroPos == -1) zeroPos = i;
                pref2[i + 1] = pref2[i];
                pref3[i + 1] = pref3[i];
                pref5[i + 1] = pref5[i];
                pref7[i + 1] = pref7[i];
            } else {
                pref2[i + 1] = pref2[i] + countFactor(d, 2);
                pref3[i + 1] = pref3[i] + countFactor(d, 3);
                pref5[i + 1] = pref5[i] + countFactor(d, 5);
                pref7[i + 1] = pref7[i] + countFactor(d, 7);
            }
        }

        if (zeroPos == -1 && pref2[n] >= c2 && pref3[n] >= c3 && pref5[n] >= c5 && pref7[n] >= c7) {
            return num;
        }

        int maxPrefix = (zeroPos == -1) ? n - 1 : zeroPos;

        for (int i = maxPrefix; i >= 0; i--) {
            int cur2 = pref2[i];
            int cur3 = pref3[i];
            int cur5 = pref5[i];
            int cur7 = pref7[i];

            int startDigit = num.charAt(i) - '0' + 1;
            for (int d = startDigit; d <= 9; d++) {
                int rem2 = Math.max(0, c2 - cur2 - countFactor(d, 2));
                int rem3 = Math.max(0, c3 - cur3 - countFactor(d, 3));
                int rem5 = Math.max(0, c5 - cur5 - countFactor(d, 5));
                int rem7 = Math.max(0, c7 - cur7 - countFactor(d, 7));

                int lenNeeded = minDigitsNeeded(rem2, rem3, rem5, rem7);
                int remLen = n - 1 - i;

                if (lenNeeded <= remLen) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num.substring(0, i));
                    sb.append(d);

                    String suffix = fillSuffix(remLen, rem2, rem3, rem5, rem7);
                    sb.append(suffix);
                    return sb.toString();
                }
            }
        }

        int targetLen = Math.max(n + 1, minDigitsNeeded(c2, c3, c5, c7));
        return fillSuffix(targetLen, c2, c3, c5, c7);
    }

    private static int countFactor(int d, int prime) {
        int cnt = 0;
        while (d > 0 && d % prime == 0) {
            cnt++;
            d /= prime;
        }
        return cnt;
    }

    private static int minDigitsNeeded(int c2, int c3, int c5, int c7) {
        int min23 = Integer.MAX_VALUE;
        // Brute force optimal combination of digits 8, 9, 6, 4, 3, 2
        for (int count9 = 0; count9 <= (c3 + 1) / 2; count9++) {
            int rem3After9 = Math.max(0, c3 - 2 * count9);
            for (int count8 = 0; count8 <= (c2 + 2) / 3; count8++) {
                int rem2After8 = Math.max(0, c2 - 3 * count8);
                
                // Can use '6' to cover one 2 and one 3
                for (int count6 = 0; count6 <= Math.min(rem2After8, rem3After9); count6++) {
                    int rem2 = Math.max(0, rem2After8 - count6);
                    int rem3 = Math.max(0, rem3After9 - count6);

                    int count4 = rem2 / 2;
                    int count2 = rem2 % 2;
                    int count3 = rem3; // Each remaining 3 needs a digit '3'

                    int totalDigits = count9 + count8 + count6 + count4 + count2 + count3;
                    min23 = Math.min(min23, totalDigits);
                }
            }
        }
        return min23 + c5 + c7;
    }

    private static String fillSuffix(int len, int c2, int c3, int c5, int c7) {
        StringBuilder sb = new StringBuilder();
        int rem2 = c2, rem3 = c3, rem5 = c5, rem7 = c7;

        for (int i = 0; i < len; i++) {
            int remPos = len - 1 - i;
            for (int d = 1; d <= 9; d++) {
                int next2 = Math.max(0, rem2 - countFactor(d, 2));
                int next3 = Math.max(0, rem3 - countFactor(d, 3));
                int next5 = Math.max(0, rem5 - countFactor(d, 5));
                int next7 = Math.max(0, rem7 - countFactor(d, 7));

                if (minDigitsNeeded(next2, next3, next5, next7) <= remPos) {
                    sb.append(d);
                    rem2 = next2;
                    rem3 = next3;
                    rem5 = next5;
                    rem7 = next7;
                    break;
                }
            }
        }
        return sb.toString();
    }
}