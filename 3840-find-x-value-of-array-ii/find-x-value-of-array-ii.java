class Solution {
    class Node {
        int[] count;
        int prod;

        Node(int k) {
            count = new int[k];
            prod = 1 % (k == 0 ? 1 : k);
        }
    }

    private int kVal;
    private Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.kVal = k;
        int n = nums.length;
        tree = new Node[4 * n];
        for (int i = 0; i < 4 * n; i++) {
            tree[i] = new Node(k);
        }

        build(0, 0, n - 1, nums);

        int qCount = queries.length;
        int[] ans = new int[qCount];

        for (int i = 0; i < qCount; i++) {
            int idx = queries[i][0];
            int val = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            update(0, 0, n - 1, idx, val);

            Node res = query(0, 0, n - 1, start, n - 1);
            ans[i] = res.count[x];
        }

        return ans;
    }

    private void build(int node, int l, int r, int[] nums) {
        if (l == r) {
            tree[node].prod = kVal == 0 ? 0 : nums[l] % kVal;
            java.util.Arrays.fill(tree[node].count, 0);
            if (kVal > 0) {
                tree[node].count[tree[node].prod]++;
            }
            return;
        }
        int mid = l + (r - l) / 2;
        build(2 * node + 1, l, mid, nums);
        build(2 * node + 2, mid + 1, r, nums);
        merge(node, 2 * node + 1, 2 * node + 2);
    }

    private void update(int node, int l, int r, int idx, int val) {
        if (l == r) {
            tree[node].prod = kVal == 0 ? 0 : val % kVal;
            java.util.Arrays.fill(tree[node].count, 0);
            if (kVal > 0) {
                tree[node].count[tree[node].prod]++;
            }
            return;
        }
        int mid = l + (r - l) / 2;
        if (idx <= mid) {
            update(2 * node + 1, l, mid, idx, val);
        } else {
            update(2 * node + 2, mid + 1, r, idx, val);
        }
        merge(node, 2 * node + 1, 2 * node + 2);
    }

    private Node query(int node, int l, int r, int ql, int qr) {
        if (ql > r || qr < l) {
            Node emptyNode = new Node(kVal);
            emptyNode.prod = kVal == 0 ? 0 : 1 % kVal;
            return emptyNode;
        }
        if (ql <= l && r <= qr) {
            return tree[node];
        }
        int mid = l + (r - l) / 2;
        Node leftNode = query(2 * node + 1, l, mid, ql, qr);
        Node rightNode = query(2 * node + 2, mid + 1, r, ql, qr);

        Node res = new Node(kVal);
        if (kVal == 0) {
            res.prod = 0;
            return res;
        }
        res.prod = (leftNode.prod * rightNode.prod) % kVal;
        
        int[] tempCount = new int[kVal];
        for (int i = 0; i < kVal; i++) {
            tempCount[i] += leftNode.count[i];
        }
        for (int i = 0; i < kVal; i++) {
            if (rightNode.count[i] > 0) {
                int combinedRem = (leftNode.prod * i) % kVal;
                tempCount[combinedRem] += rightNode.count[i];
            }
        }
        res.count = tempCount;
        return res;
    }

    private void merge(int parent, int left, int right) {
        if (kVal == 0) {
            tree[parent].prod = 0;
            return;
        }
        tree[parent].prod = (tree[left].prod * tree[right].prod) % kVal;
        java.util.Arrays.fill(tree[parent].count, 0);
        for (int i = 0; i < kVal; i++) {
            tree[parent].count[i] += tree[left].count[i];
        }
        for (int i = 0; i < kVal; i++) {
            if (tree[right].count[i] > 0) {
                int combinedRem = (tree[left].prod * i) % kVal;
                tree[parent].count[combinedRem] += tree[right].count[i];
            }
        }
    }
}