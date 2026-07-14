import java.util.*;

class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) {
            return res;
        }
        
        Map<String, List<String>> adj = new HashMap<>();
        Map<String, Integer> visited = new HashMap<>();
        
        Queue<String> q = new LinkedList<>();
        q.offer(beginWord);
        visited.put(beginWord, 0);
        
        boolean found = false;
        
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String curr = q.poll();
                int steps = visited.get(curr);
                
                if (curr.equals(endWord)) {
                    found = true;
                    break;
                }
                
                char[] chars = curr.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    char original = chars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        chars[j] = c;
                        String next = new String(chars);
                        
                        if (dict.contains(next)) {
                            if (!visited.containsKey(next)) {
                                visited.put(next, steps + 1);
                                q.offer(next);
                                adj.computeIfAbsent(next, k -> new ArrayList<>()).add(curr);
                            } else if (visited.get(next) == steps + 1) {
                                adj.computeIfAbsent(next, k -> new ArrayList<>()).add(curr);
                            }
                        }
                    }
                    chars[j] = original;
                }
            }
            if (found) break;
        }
        
        if (found) {
            List<String> path = new ArrayList<>();
            path.add(endWord);
            backtrack(endWord, beginWord, adj, path, res);
        }
        
        return res;
    }
    
    private void backtrack(String curr, String beginWord, Map<String, List<String>> adj, List<String> path, List<List<String>> res) {
        if (curr.equals(beginWord)) {
            List<String> validPath = new ArrayList<>(path);
            Collections.reverse(validPath);
            res.add(validPath);
            return;
        }
        
        if (!adj.containsKey(curr)) return;
        
        for (String prev : adj.get(curr)) {
            path.add(prev);
            backtrack(prev, beginWord, adj, path, res);
            path.remove(path.size() - 1);
        }
    }
}