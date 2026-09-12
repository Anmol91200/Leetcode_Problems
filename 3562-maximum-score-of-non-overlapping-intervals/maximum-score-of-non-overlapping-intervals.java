class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        int[][] sorted = new int[n][4];
        for (int i = 0; i < n; i++) {
            List<Integer> interval = intervals.get(i);
            sorted[i][0] = interval.get(0);
            sorted[i][1] = interval.get(1);
            sorted[i][2] = interval.get(2);
            sorted[i][3] = i;
        }

        Arrays.sort(sorted, (a, b) -> Integer.compare(a[1], b[1]));

        long[][] dp = new long[5][n + 1];
        List<Integer>[][] bestIndices = new ArrayList[5][n + 1];

        for (int k = 0; k <= 4; k++) {
            for (int i = 0; i <= n; i++) {
                bestIndices[k][i] = new ArrayList<>();
            }
        }

        int[] p = new int[n];
        for (int i = 0; i < n; i++) {
            int l = 0, r = i - 1;
            int ans = -1;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (sorted[mid][1] < sorted[i][0]) {
                    ans = mid;
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            p[i] = ans;
        }

        for (int k = 1; k <= 4; k++) {
            for (int i = 1; i <= n; i++) {
                dp[k][i] = dp[k][i - 1];
                bestIndices[k][i] = bestIndices[k][i - 1];

                int prevIdx = p[i - 1];
                long takeScore = sorted[i - 1][2] + (prevIdx != -1 ? dp[k - 1][prevIdx + 1] : 0);
                List<Integer> takeList = new ArrayList<>(prevIdx != -1 ? bestIndices[k - 1][prevIdx + 1] : Collections.emptyList());
                takeList.add(sorted[i - 1][3]);
                Collections.sort(takeList);

                if (takeScore > dp[k][i]) {
                    dp[k][i] = takeScore;
                    bestIndices[k][i] = takeList;
                } else if (takeScore == dp[k][i]) {
                    if (compareLists(takeList, bestIndices[k][i]) < 0) {
                        bestIndices[k][i] = takeList;
                    }
                }
            }
        }

        long maxScore = -1;
        List<Integer> result = new ArrayList<>();

        for (int k = 1; k <= 4; k++) {
            if (dp[k][n] > maxScore) {
                maxScore = dp[k][n];
                result = bestIndices[k][n];
            } else if (dp[k][n] == maxScore && dp[k][n] > 0) {
                if (compareLists(bestIndices[k][n], result) < 0) {
                    result = bestIndices[k][n];
                }
            }
        }

        int[] res = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            res[i] = result.get(i);
        }
        return res;
    }

    private int compareLists(List<Integer> a, List<Integer> b) {
        if (a.isEmpty() && b.isEmpty()) return 0;
        if (a.isEmpty()) return 1;
        if (b.isEmpty()) return -1;
        int minSize = Math.min(a.size(), b.size());
        for (int i = 0; i < minSize; i++) {
            int cmp = Integer.compare(a.get(i), b.get(i));
            if (cmp != 0) return cmp;
        }
        return Integer.compare(a.size(), b.size());
    }
}