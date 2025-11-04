class Solution {
    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] answer = new int[n - k + 1];
        for (int i = 0; i <= n - k; i++) {
            Map<Integer, Integer> freq = new HashMap<>();
            for (int j = i; j < i + k; j++) {
                int num = nums[j];
                freq.put(num, freq.getOrDefault(num, 0) + 1);
            }
            List<int[]> list = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
                list.add(new int[]{entry.getKey(), entry.getValue()});
            }
            list.sort((a, b) -> {
                if (a[1] != b[1]) {
                    return Integer.compare(b[1], a[1]);
                }
                return Integer.compare(b[0], a[0]);
            });
            int sum = 0;
            int limit = Math.min(x, list.size());
            for (int p = 0; p < limit; p++) {
                int val = list.get(p)[0];
                int cnt = list.get(p)[1];
                sum += val * cnt;
            }
            answer[i] = sum;
        }
        return answer;
    }
}
