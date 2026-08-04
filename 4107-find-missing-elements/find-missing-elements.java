class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        int min = nums[0];
        int max = nums[0];

        for (int num : nums) {
            if (num < min) min = num;
            if (num > max) max = num;
        }

        boolean[] present = new boolean[max - min + 1];
        for (int num : nums) {
            present[num - min] = true;
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < present.length; i++) {
            if (!present[i]) {
                result.add(min + i);
            }
        }

        return result;
    }
}