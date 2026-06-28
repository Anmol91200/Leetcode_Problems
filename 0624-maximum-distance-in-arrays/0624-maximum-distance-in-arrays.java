import java.util.List;

class Solution {
    public int maxDistance(List<List<Integer>> arrays) {
        int res = 0;
        int minVal = arrays.get(0).get(0);
        int maxVal = arrays.get(0).get(arrays.get(0).size() - 1);
        
        for (int i = 1; i < arrays.size(); i++) {
            List<Integer> current = arrays.get(i);
            int currentMin = current.get(0);
            int currentMax = current.get(current.size() - 1);
            
            res = Math.max(res, Math.abs(currentMax - minVal));
            res = Math.max(res, Math.abs(maxVal - currentMin));
            
            minVal = Math.min(minVal, currentMin);
            maxVal = Math.max(maxVal, currentMax);
        }
        
        return res;
    }
}