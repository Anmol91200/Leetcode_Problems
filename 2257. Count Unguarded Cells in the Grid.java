class Solution {
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        char [][] grid=new char[m][n];

        for(char[] row:grid)
        Arrays.fill(row, '.');
        
        for(int[] g: guards)
        grid[g[0]][g[1]]='B';

        for(int []w: walls)
        grid[w[0]][w[1]]='B';

        int[][] dir={{-1,0},{1,0},{0,-1},{0,1}};

        for(int[] g: guards){
            int x=g[0];
            int y=g[1];
            for(int[] d:dir){
                int dx=d[0];
                int dy=d[1];
                int nx=x+dx;
                int ny=y+dy;
                while (nx>=0 && nx<m && ny>=0 && ny<n) {
                    if(grid[nx][ny]=='B')
                    break;

                    if(grid[nx][ny]=='.')
                    grid[nx][ny]='X';

                    nx+=dx;
                    ny+=dy;


                    
                }
            }
        }

        int count=0;
        for(int i=0;i<m;i++)
        for(int j=0;j<n;j++){
            if(grid[i][j]=='.') count++;
        }
        return count;

    }
}
