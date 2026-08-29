class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }
        
        int odd = -1;
        for (int i = 0; i < 26; i++) {
            if (counts[i] % 2 != 0) {
                if (odd != -1) return "";
                odd = i;
            }
            counts[i] /= 2;
        }

        int n = s.length();
        int m = n / 2;
        String targetHalf = target.substring(0, m);

        int[] required = new int[26];
        for (char c : targetHalf.toCharArray()) {
            required[c - 'a']++;
        }

        boolean matchHalf = true;
        for (int i = 0; i < 26; i++) {
            if (required[i] != counts[i]) {
                matchHalf = false;
                break;
            }
        }

        if (matchHalf) {
            StringBuilder sb = new StringBuilder(targetHalf);
            if (odd != -1) {
                sb.append((char) ('a' + odd));
            }
            for (int j = m - 1; j >= 0; j--) {
                sb.append(targetHalf.charAt(j));
            }
            String pal = sb.toString();
            if (pal.compareTo(target) > 0) {
                return pal;
            }
        }

        for (int i = m - 1; i >= 0; i--) {
            int[] rem = counts.clone();
            boolean possible = true;
            for (int j = 0; j < i; j++) {
                int c = target.charAt(j) - 'a';
                rem[c]--;
                if (rem[c] < 0) {
                    possible = false;
                    break;
                }
            }
            if (!possible) continue;

            int targetChar = target.charAt(i) - 'a';
            int placedC = -1;
            for (int c = targetChar + 1; c < 26; c++) {
                if (rem[c] > 0) {
                    placedC = c;
                    break;
                }
            }

            if (placedC != -1) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < i; j++) {
                    sb.append(target.charAt(j));
                }
                sb.append((char) ('a' + placedC));
                rem[placedC]--;

                for (int c = 0; c < 26; c++) {
                    while (rem[c] > 0) {
                        sb.append((char) ('a' + c));
                        rem[c]--;
                    }
                }

                String firstHalf = sb.toString();
                if (odd != -1) {
                    sb.append((char) ('a' + odd));
                }
                for (int j = m - 1; j >= 0; j--) {
                    sb.append(firstHalf.charAt(j));
                }
                return sb.toString();
            }
        }
        return "";
    }
}