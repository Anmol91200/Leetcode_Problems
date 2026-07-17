import java.util.*;

class Solution {
    private Map<String, List<String>> memo = new HashMap<>();

    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        return backtrack(s, wordSet);
    }

    private List<String> backtrack(String s, Set<String> wordSet) {
        // if (memo.containsKey(s)) {
            // return memo.get(s);
        // }
        
        List<String> res = new ArrayList<>();
        if (s.isEmpty()) {
            res.add("");
            return res;
        }

        for (int i = 1; i <= s.length(); i++) {
            String prefix = s.substring(0, i);
            if (wordSet.contains(prefix)) {
                String suffix = s.substring(i);
                List<String> suffixSubstrings = backtrack(suffix, wordSet);
                for (String substring : suffixSubstrings) {
                    res.add(prefix + (substring.isEmpty() ? "" : " ") + substring);
                }
            }
        }

        memo.put(s, res);
        return res;
    }
}