import java.util.Arrays;

class Solution {
    public int[] smallestTrimmedNumbers(String[] nums, int[][] queries) {
        int n = nums.length;
        int q = queries.length;
        int[] ans = new int[q];

        for (int i = 0; i < q; i++) {
            int k = queries[i][0];
            int trim = queries[i][1];

            Integer[] indices = new Integer[n];
            for (int j = 0; j < n; j++) {
                indices[j] = j;
            }

            int startPos = nums[0].length() - trim;

            Arrays.sort(indices, (a, b) -> {
                String subA = nums[a].substring(startPos);
                String subB = nums[b].substring(startPos);
                int cmp = subA.compareTo(subB);
                if (cmp != 0) {
                    return cmp;
                }
                return Integer.compare(a, b);
            });

            ans[i] = indices[k - 1];
        }

        return ans;
    }
}