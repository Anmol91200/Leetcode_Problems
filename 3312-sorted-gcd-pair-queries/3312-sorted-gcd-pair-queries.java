class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int maxVal = 0;
        for (int num : nums) {
            if (num > maxVal) {
                maxVal = num;
            }
        }

        long[] count = new long[maxVal + 1];
        for (int num : nums) {
            count[num]++;
        }

        long[] gcdPairsCount = new long[maxVal + 1];
        for (int i = maxVal; i >= 1; i--) {
            long totalDivisible = 0;
            for (int j = i; j <= maxVal; j += i) {
                totalDivisible += count[j];
            }
            
            long totalPairs = (totalDivisible * (totalDivisible - 1)) / 2;
            
            for (int j = 2 * i; j <= maxVal; j += i) {
                totalPairs -= gcdPairsCount[j];
            }
            gcdPairsCount[i] = totalPairs;
        }

        long[] prefixSum = new long[maxVal + 1];
        for (int i = 1; i <= maxVal; i++) {
            prefixSum[i] = prefixSum[i - 1] + gcdPairsCount[i];
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            long target = queries[i];
            int low = 1, high = maxVal;
            int res = maxVal;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (prefixSum[mid] > target) {
                    res = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            ans[i] = res;
        }

        return ans;
    }
}