class Solution {
    public String smallestSubsequence(String s) {
        int[] lastIndex = new int[26];
        int n = s.length();
        for (int i = 0; i < n; i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }
        
        boolean[] seen = new boolean[26];
        char[] stack = new char[26];
        int top = 0;
        
        for (int i = 0; i < n; i++) {
            char curr = s.charAt(i);
            int idx = curr - 'a';
            
            if (seen[idx]) continue;
            
            while (top > 0 && stack[top - 1] > curr && lastIndex[stack[top - 1] - 'a'] > i) {
                seen[stack[top - 1] - 'a'] = false;
                top--;
            }
            
            stack[top++] = curr;
            seen[idx] = true;
        }
        
        return new String(stack, 0, top);
    }
}