class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int[] minLen = new int[n];
        java.util.Arrays.fill(minLen, Integer.MAX_VALUE);
        
        int sum = 0;
        int left = 0;
        int shortest = Integer.MAX_VALUE;
        int ans = Integer.MAX_VALUE;
        
        for (int right = 0; right < n; right++) {
            sum += arr[right];
            while (sum > target) {
                sum -= arr[left++];
            }
            if (sum == target) {
                int len = right - left + 1;
                if (left > 0 && minLen[left - 1] != Integer.MAX_VALUE) {
                    ans = Math.min(ans, len + minLen[left - 1]);
                }
                shortest = Math.min(shortest, len);
            }
            minLen[right] = shortest;
        }
        
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}