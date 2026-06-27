import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> result = new ArrayList<>();
        int n = buildings.length;
        int[][] points = new int[n * 2][2];
        
        int index = 0;
        for (int[] b : buildings) {
            points[index++] = new int[]{b[0], -b[2]};
            points[index++] = new int[]{b[1], b[2]};
        }
        
        Arrays.sort(points, (a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            }
            return a[1] - b[1];
        });
        
        TreeMap<Integer, Integer> heights = new TreeMap<>();
        heights.put(0, 1);
        int prevMaxHeight = 0;
        
        for (int[] point : points) {
            int x = point[0];
            int h = point[1];
            
            if (h < 0) {
                heights.put(-h, heights.getOrDefault(-h, 0) + 1);
            } else {
                int count = heights.get(h);
                if (count == 1) {
                    heights.remove(h);
                } else {
                    heights.put(h, count - 1);
                }
            }
            
            int currentMaxHeight = heights.lastKey();
            if (currentMaxHeight != prevMaxHeight) {
                result.add(Arrays.asList(x, currentMaxHeight));
                prevMaxHeight = currentMaxHeight;
            }
        }
        
        return result;
    }
}