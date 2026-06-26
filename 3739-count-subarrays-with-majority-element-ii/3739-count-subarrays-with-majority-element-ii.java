import java.util.Arrays;

class Solution {
    public long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;
        int[] pref = new int[n + 1];
        
        for (int i = 0; i < n; i++) {
            int val = (nums[i] == target) ? 1 : -1;
            pref[i + 1] = pref[i] + val;
        }
        
        int[] sortedPref = pref.clone();
        Arrays.sort(sortedPref);
        
        int uniqueCount = 0;
        for (int i = 0; i < sortedPref.length; i++) {
            if (i == 0 || sortedPref[i] != sortedPref[i - 1]) {
                sortedPref[uniqueCount++] = sortedPref[i];
            }
        }
        
        int[] bit = new int[uniqueCount + 1];
        long ans = 0;
        
        for (int x : pref) {
            int rank = Arrays.binarySearch(sortedPref, 0, uniqueCount, x) + 1;
            
            for (int i = rank - 1; i > 0; i -= i & -i) {
                ans += bit[i];
            }
            
            for (int i = rank; i <= uniqueCount; i += i & -i) {
                bit[i]++;
            }
        }
        
        return ans;
    }
}