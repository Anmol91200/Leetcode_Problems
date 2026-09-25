import java.util.*;

class Solution {
    public List<String> braceExpansionII(String expression) {
        Stack<Set<String>> stack = new Stack<>();
        Stack<Character> ops = new Stack<>();
        
        int n = expression.length();
        for (int i = 0; i < n; i++) {
            char c = expression.charAt(i);
            
            if (c == '{') {
                if (i > 0 && (expression.charAt(i - 1) == '}' || Character.isLetter(expression.charAt(i - 1)))) {
                    while (!ops.isEmpty() && ops.peek() == '*') {
                        evaluate(stack, ops.pop());
                    }
                    ops.push('*');
                }
                ops.push('{');
            } else if (c == '}') {
                while (!ops.isEmpty() && ops.peek() != '{') {
                    evaluate(stack, ops.pop());
                }
                ops.pop();
            }
             else if (c == ',') {
                while (!ops.isEmpty() && ops.peek() != '{') {
                    evaluate(stack, ops.pop());
                }
                ops.push('+');
            } 
            else {
                if (i > 0 && (expression.charAt(i - 1) == '}' || Character.isLetter(expression.charAt(i - 1)))) {
                    while (!ops.isEmpty() && ops.peek() == '*') {
                        evaluate(stack, ops.pop());
                    }
                    ops.push('*');
                }
                
                Set<String> set = new HashSet<>();
                set.add(String.valueOf(c));
                stack.push(set);
            }
        }
        
        while (!ops.isEmpty()) {
            evaluate(stack, ops.pop());
        }
        
        List<String> result = new ArrayList<>(stack.pop());
        Collections.sort(result);
        return result;
    }

    private void evaluate(Stack<Set<String>> stack, char op) {
        Set<String> set2 = stack.pop();
        Set<String> set1 = stack.pop();
        Set<String> res = new HashSet<>();

        if (op == '+') {
            res.addAll(set1);
            res.addAll(set2);
        } else if (op == '*') {
            for (String s1 : set1) {
                for (String s2 : set2) {
                    res.add(s1 + s2);
                }
            }
        }
        
        stack.push(res);
    }
}