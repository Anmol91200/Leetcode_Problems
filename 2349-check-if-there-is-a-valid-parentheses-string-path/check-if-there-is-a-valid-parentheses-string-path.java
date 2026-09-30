class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        if ((m + n - 1) % 2 != 0 || grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }
        
        int maxLen = (m + n - 1) / 2;
        boolean[][][] visited = new boolean[m][n][maxLen + 1];
        
        return dfs(grid, 0, 0, 0, maxLen, visited);
    }
    
    private boolean dfs(char[][] grid, int r, int c, int count, int maxLen, boolean[][][] visited) {
        int m = grid.length;
        int n = grid[0].length;
        
        if (grid[r][c] == '(') {
            count++;
        } else {
            count--;
        }
        
        if (count < 0 || count > maxLen) {
            return false;
        }
        
        if (r == m - 1 && c == n - 1) {
            return count == 0;
        }
        
        if (visited[r][c][count]) {
            return false;
        }
        
        visited[r][c][count] = true;
        
        if (r + 1 < m && dfs(grid, r + 1, c, count, maxLen, visited)) {
            return true;
        }
        
        if (c + 1 < n && dfs(grid, r + 1 - 1, c + 1, count, maxLen, visited)) {
            return true;
        }
        
        return false;
    }
}