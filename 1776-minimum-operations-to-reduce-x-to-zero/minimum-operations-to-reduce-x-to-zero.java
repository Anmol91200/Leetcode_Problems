class Solution {
    public int minOperations(int[] nums, int x) {
        int target = -x;
        for (int num : nums) {
            target += num;
        }
        
        if (target < 0) {
            return -1;
        }
        
        int n = nums.length;
        int maxLength = -1;
        int currentSum = 0;
        int left = 0;
        
        for (int right = 0; right < n; right++) {
            currentSum += nums[right];
            while (currentSum > target && left <= right) {
                currentSum -= nums[left];
                left++;
            }
            if (currentSum == target) {
                maxLength = Math.max(maxLength, right - left + 1);
            }
        }
        
        return maxLength == -1 ? -1 : n - maxLength;
    }
}