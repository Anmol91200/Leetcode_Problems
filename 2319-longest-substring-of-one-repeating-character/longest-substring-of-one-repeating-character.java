class Solution {
    private class Node {
        char leftChar, rightChar;
        int length, maxPrefix, maxSuffix, maxLen;

        Node(char c) {
            leftChar = c;
            rightChar = c;
            length = 1;
            maxPrefix = 1;
            maxSuffix = 1;
            maxLen = 1;
        }

        Node() {}
    }

    private Node[] tree;

    private Node merge(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;

        Node res = new Node();
        res.leftChar = left.leftChar;
        res.rightChar = right.rightChar;
        res.length = left.length + right.length;

        res.maxPrefix = left.maxPrefix;
        if (left.leftChar == right.leftChar && left.maxPrefix == left.length) {
            res.maxPrefix = left.length + right.maxPrefix;
        }

        res.maxSuffix = right.maxSuffix;
        if (left.rightChar == right.rightChar && right.maxSuffix == right.length) {
            res.maxSuffix = right.length + left.maxSuffix;
        }

        res.maxLen = Math.max(left.maxLen, right.maxLen);
        if (left.rightChar == right.leftChar) {
            res.maxLen = Math.max(res.maxLen, left.maxSuffix + right.maxPrefix);
        }

        return res;
    }

    private void build(String s, int node, int start, int end) {
        if (start == end) {
            tree[node] = new Node(s.charAt(start));
            return;
        }
        int mid = start + (end - start) / 2;
        build(s, 2 * node, start, mid);
        build(s, 2 * node + 1, mid + 1, end);
        tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
    }

    private void update(int node, int start, int end, int idx, char c) {
        if (start == end) {
            tree[node] = new Node(c);
            return;
        }
        int mid = start + (end - start) / 2;
        if (idx <= mid) {
            update(2 * node, start, mid, idx, c);
        } else {
            update(2 * node + 1, mid + 1, end, idx, c);
        }
        tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
    }

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        int k = queryIndices.length;
        tree = new Node[4 * n];

        build(s, 1, 0, n - 1);

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            update(1, 0, n - 1, queryIndices[i], queryCharacters.charAt(i));
            result[i] = tree[1].maxLen;
        }

        return result;
    }
}