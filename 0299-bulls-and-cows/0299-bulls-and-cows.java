class Solution {
    public String getHint(String secret, String guess) {
        int bulls = 0;
        int cows = 0;
        int[] counts = new int[10];

        for (int i = 0; i < secret.length(); i++) {
            int s = secret.charAt(i) - '0';
            int g = guess.charAt(i) - '0';

            if (s == g) {
                bulls++;
            } else {
                if (counts[s] < 0) cows++;
                if (counts[g] > 0) cows++;
                counts[s]++;
                counts[g]--;
            }
        }

        return bulls + "A" + cows + "B";
    }
}