import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maximumLength(int[] nums) {
        Map<Long, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put((long) num, countMap.getOrDefault((long) num, 0) + 1);
        }

        int maxLength = 1;

        if (countMap.containsKey(1L)) {
            int onesCount = countMap.get(1L);
            if (onesCount % 2 == 0) {
                maxLength = Math.max(maxLength, onesCount - 1);
            } else {
                maxLength = Math.max(maxLength, onesCount);
            }
        }

        for (long x : countMap.keySet()) {
            if (x == 1) continue;

            int currentLength = 0;
            long current = x;

            while (countMap.containsKey(current)) {
                if (countMap.get(current) >= 2) {
                    maxLength = Math.max(maxLength, currentLength + 1);
                    currentLength += 2;
                    
                    if (current > 100000) { 
                        break;
                    }
                    current = current * current;
                } else {
                    maxLength = Math.max(maxLength, currentLength + 1);
                    break;
                }
            }
        }

        return maxLength;
    }
}