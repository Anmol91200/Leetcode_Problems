import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> result = new ArrayList<>();
        String digits = "123456789";
        
        int lowLen = String.valueOf(low).length();
        int highLen = String.valueOf(high).length();
        
        for (int length = lowLen; length <= highLen; length++) {
            for (int start = 0; start <= digits.length() - length; start++) {
                String sub = digits.substring(start, start + length);
                int num = Integer.parseInt(sub);
                
                if (num >= low && num <= high) {
                    result.add(num);
                }
            }
        }
        
        return result;
    }
}