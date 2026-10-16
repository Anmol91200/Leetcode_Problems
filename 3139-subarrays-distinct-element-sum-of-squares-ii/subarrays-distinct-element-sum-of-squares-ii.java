
class Solution {
    private static final int MOD = 1_000_000_007;

    private long[] sum;
    private long[] lazy;
    private int n;

    public int sumCounts(int[] nums) {
        n = nums.length;
        sum = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        long current = 0;
        long answer = 0;

        for (int i = 0; i < n; i++) {
            int previous = last[nums[i]];

            long added = queryAndAdd(
                1, 0, n - 1, previous + 1, i
            );

            current = (current + 2 * added + (i - previous)) % MOD;
            answer = (answer + current) % MOD;

            last[nums[i]] = i;
        }

        return (int) answer;
    }

    private void apply(int node, int left, int right, long value) {
        sum[node] = (sum[node] + (right - left + 1L) * value) % MOD;
        lazy[node] = (lazy[node] + value) % MOD;
    }

    private void push(int node, int left, int right) {
        if (lazy[node] == 0 || left == right) {
            return;
        }

        int mid = left + (right - left) / 2;

        apply(node * 2, left, mid, lazy[node]);
        apply(node * 2 + 1, mid + 1, right, lazy[node]);

        lazy[node] = 0;
    }

    private long queryAndAdd(
        int node, int left, int right, int ql, int qr
    ) {
        if (ql <= left && right <= qr) {
            long result = sum[node];
            apply(node, left, right, 1);
            return result;
        }

        push(node, left, right);

        int mid = left + (right - left) / 2;
        long result = 0;

        if (ql <= mid) {
            result += queryAndAdd(
                node * 2, left, mid, ql, qr
            );
        }

        if (qr > mid) {
            result += queryAndAdd(
                node * 2 + 1, mid + 1, right, ql, qr
            );
        }

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

        return result % MOD;
    }
}
