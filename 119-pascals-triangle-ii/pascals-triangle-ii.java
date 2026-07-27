class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        row.add(1);

        for (int i = 1; i <= rowIndex; i++) {
            long prev = row.get(i - 1);
            long next = prev * (rowIndex - i + 1) / i;
            row.add((int) next);
        }

        return row;
    }
}