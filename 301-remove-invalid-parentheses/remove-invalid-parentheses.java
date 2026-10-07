class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();
        if (s == null) return res;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(s);
        visited.add(s);

        boolean found = false;

        while (!queue.isEmpty()) {
            int size = queue.size();
            Set<String> levelVisited = new HashSet<>();

            for (int i = 0; i < size; i++) {
                String curr = queue.poll();

                if (isValid(curr)) {
                    res.add(curr);
                    found = true;
                }

                if (found) continue;

                for (int j = 0; j < curr.length(); j++) {
                    if (curr.charAt(j) != '(' && curr.charAt(j) != ')') continue;

                    String next = curr.substring(0, j) + curr.substring(j + 1);
                    if (!visited.contains(next) && !levelVisited.contains(next)) {
                        levelVisited.add(next);
                        queue.add(next);
                    }
                }
            }

            if (found) break;
            visited.addAll(levelVisited);
        }

        return res;
    }

    private boolean isValid(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                count++;
            }
             else if (c == ')') {
                count--;
                if (count < 0) return false;
            }
        }
        return count == 0;
    }
}