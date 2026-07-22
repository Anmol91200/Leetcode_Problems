import java.util.*;

class Solution {
    private static class Group {
        int start;
        int length;

        Group(int start, int length) {
            this.start = start;
            this.length = length;
        }
    }

    private static class SparseTable {
        private int[][] st;

        public SparseTable(int[] nums) {
            int n = nums.length;
            if (n == 0) return;
            int k = bitLength(n);
            st = new int[k][n];
            for (int i = 0; i < n; i++) {
                st[0][i] = nums[i];
            }
            for (int i = 1; i < k; i++) {
                for (int j = 0; j + (1 << i) <= n; j++) {
                    st[i][j] = Math.max(st[i - 1][j], st[i - 1][j + (1 << (i - 1))]);
                }
            }
        }

        public int query(int l, int r) {
            if (l > r) return 0;
            int i = bitLength(r - l + 1) - 1;
            return Math.max(st[i][l], st[i][r - (1 << i) + 1]);
        }

        private int bitLength(int n) {
            return 32 - Integer.numberOfLeadingZeros(n);
        }
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int totalOnes = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                totalOnes++;
            }
        }

        List<Group> zeroGroups = new ArrayList<>();
        int[] zeroGroupIndex = new int[n];
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                if (i > 0 && s.charAt(i - 1) == '0') {
                    zeroGroups.get(zeroGroups.size() - 1).length++;
                } else {
                    zeroGroups.add(new Group(i, 1));
                }
                zeroGroupIndex[i] = zeroGroups.size() - 1;
            } else {
                zeroGroupIndex[i] = -1;
            }
        }

        int m = zeroGroups.size();
        if (m == 0) {
            List<Integer> ans = new ArrayList<>();
            for (int i = 0; i < queries.length; i++) {
                ans.add(totalOnes);
            }
            return ans;
        }

        int[] adjacentSums = new int[Math.max(0, m - 1)];
        for (int i = 0; i < m - 1; i++) {
            adjacentSums[i] = zeroGroups.get(i).length + zeroGroups.get(i + 1).length;
        }

        SparseTable st = new SparseTable(adjacentSums);
        List<Integer> ans = new ArrayList<>();

        for (int[] query : queries) {
            int l = query[0];
            int r = query[1];

            int gL = findGroupForL(zeroGroups, zeroGroupIndex, l);
            int gR = findGroupForR(zeroGroups, zeroGroupIndex, r);

            int leftLen = getLeftTruncatedLen(zeroGroups, s, l, gL);
            int rightLen = getRightTruncatedLen(zeroGroups, s, r, gR);

            int maxActive = totalOnes;

            if (gL != -1 && gR != -1) {
                if (gL == gR && s.charAt(l) == '0' && s.charAt(r) == '0') {
                    // Single group segment inside query range
                } else if (gL + 1 == gR && s.charAt(l) == '0' && s.charAt(r) == '0') {
                    maxActive = Math.max(maxActive, totalOnes + leftLen + rightLen);
                }
            }

            int startAdj = (s.charAt(l) == '0') ? gL + 1 : gL;
            int endAdj = (s.charAt(r) == '0') ? gR - 2 : gR - 1;

            if (startAdj <= endAdj) {
                maxActive = Math.max(maxActive, totalOnes + st.query(startAdj, endAdj));
            }

            if (s.charAt(l) == '0' && gL + 1 < m && gL + 1 <= (s.charAt(r) == '0' ? gR - 1 : gR)) {
                maxActive = Math.max(maxActive, totalOnes + leftLen + zeroGroups.get(gL + 1).length);
            }

            if (s.charAt(r) == '0' && gR - 1 >= 0 && gR - 1 >= (s.charAt(l) == '0' ? gL + 1 : gL)) {
                maxActive = Math.max(maxActive, totalOnes + rightLen + zeroGroups.get(gR - 1).length);
            }

            ans.add(maxActive);
        }

        return ans;
    }

    private int findGroupForL(List<Group> zeroGroups, int[] zeroGroupIndex, int l) {
        if (zeroGroupIndex[l] != -1) return zeroGroupIndex[l];
        int low = 0, high = zeroGroups.size() - 1, res = zeroGroups.size();
        while (low <= high) {
            int mid = (low + high) / 2;
            if (zeroGroups.get(mid).start > l) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    private int findGroupForR(List<Group> zeroGroups, int[] zeroGroupIndex, int r) {
        if (zeroGroupIndex[r] != -1) return zeroGroupIndex[r];
        int low = 0, high = zeroGroups.size() - 1, res = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (zeroGroups.get(mid).start < r) {
                res = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return res;
    }

    private int getLeftTruncatedLen(List<Group> zeroGroups, String s, int l, int gL) {
        if (s.charAt(l) != '0' || gL < 0 || gL >= zeroGroups.size()) return 0;
        Group g = zeroGroups.get(gL);
        return g.length - (l - g.start);
    }

    private int getRightTruncatedLen(List<Group> zeroGroups, String s, int r, int gR) {
        if (s.charAt(r) != '0' || gR < 0 || gR >= zeroGroups.size()) return 0;
        Group g = zeroGroups.get(gR);
        return r - g.start + 1;
    }
}