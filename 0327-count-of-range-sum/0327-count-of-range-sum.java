class Solution {
    public int countRangeSum(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        return mergeSort(prefix, 0, n, lower, upper);
    }

    private int mergeSort(long[] prefix, int left, int right, int lower, int upper) {
        if (right <= left) {
            return 0;
        }
        int mid = left + (right - left) / 2;
        int count = mergeSort(prefix, left, mid, lower, upper) + mergeSort(prefix, mid + 1, right, lower, upper);

        int j = mid + 1, k = mid + 1;
        for (int i = left; i <= mid; i++) {
            while (j <= right && prefix[j] - prefix[i] < lower) {
                j++;
            }
            while (k <= right && prefix[k] - prefix[i] <= upper) {
                k++;
            }
            count += k - j;
        }

        long[] sorted = new long[right - left + 1];
        int p1 = left, p2 = mid + 1, idx = 0;
        while (p1 <= mid && p2 <= right) {
            if (prefix[p1] <= prefix[p2]) {
                sorted[idx++] = prefix[p1++];
            } else {
                sorted[idx++] = prefix[p2++];
            }
        }
        while (p1 <= mid) {
            sorted[idx++] = prefix[p1++];
        }
        while (p2 <= right) {
            sorted[idx++] = prefix[p2++];
        }

        System.arraycopy(sorted, 0, prefix, left, sorted.length);
        return count;
    }
}