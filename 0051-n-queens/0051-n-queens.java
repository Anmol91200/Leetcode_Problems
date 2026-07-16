import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        backtrack(res, board, 0, n, new boolean[n], new boolean[2 * n], new boolean[2 * n]);
        return res;
    }

    private void backtrack(List<List<String>> res, char[][] board, int row, int n, boolean[] cols, boolean[] d1, boolean[] d2) {
        if (row == n) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                list.add(new String(board[i]));
            }
            res.add(list);
            return;
        }

        for (int col = 0; col < n; col++) {
            int id1 = row - col + n;
            int id2 = row + col;
            
            if (cols[col] || d1[id1] || d2[id2]) {
                continue;
            }

            board[row][col] = 'Q';
            cols[col] = true;
            d1[id1] = true;
            d2[id2] = true;

            backtrack(res, board, row + 1, n, cols, d1, d2);

            board[row][col] = '.';
            cols[col] = false;
            d1[id1] = false;
            d2[id2] = false;
        }
    }
}