class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];
        long totalK = (long) k1 + k2;
        long totalDiffSum = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            totalDiffSum += diff[i];
        }

        if (totalDiffSum <= totalK) {
            return 0;
        }

        int maxDiff = 0;
        for (int d : diff) {
            maxDiff = Math.max(maxDiff, d);
        }

        int[] count = new int[maxDiff + 1];
        for (int d : diff) {
            count[d]++;
        }

        long remainingK = totalK;
        for (int d = maxDiff; d > 0 && remainingK > 0; d--) {
            if (count[d] == 0) continue;
            
            long take = Math.min((long) count[d], remainingK);
            count[d] -= take;
            count[d - 1] += (int) take;
            remainingK -= take;
        }

        long ans = 0;
        for (int d = 0; d <= maxDiff; d++) {
            if (count[d] > 0) {
                ans += (long) count[d] * d * d;
            }
        }

        return ans;
    }
}