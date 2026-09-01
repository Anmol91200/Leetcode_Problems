import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        int sx = -1, sy = -1, numLitter = 0;
        int[][] litterIdx = new int[m][n];
        
        for (int i = 0; i < m; i++) {
            Arrays.fill(litterIdx[i], -1);
            for (int j = 0; j < n; j++) {
                char c = classroom[i].charAt(j);
                if (c == 'S') {
                    sx = i;
                    sy = j;
                } else if (c == 'L') {
                    litterIdx[i][j] = numLitter++;
                }
            }
        }
        
        if (numLitter == 0) return 0;
        
        int[][][] bestEnergy = new int[m][n][1 << numLitter];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(bestEnergy[i][j], -1);
            }
        }
        
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{sx, sy, 0, energy, 0});
        bestEnergy[sx][sy][0] = energy;
        
        int[] dirs = {-1, 0, 1, 0, -1};
        int targetMask = (1 << numLitter) - 1;
        
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int x = curr[0], y = curr[1], mask = curr[2], e = curr[3], steps = curr[4];
            
            if (mask == targetMask) {
                return steps;
            }
            
            for (int i = 0; i < 4; i++) {
                int nx = x + dirs[i];
                int ny = y + dirs[i + 1];
                
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    char nc = classroom[nx].charAt(ny);
                    if (nc == 'X') continue;
                    
                    int ne = e - 1;
                    if (ne < 0) continue;
                    
                    if (nc == 'R') {
                        ne = energy;
                    }
                    
                    int nmask = mask;
                    if (nc == 'L') {
                        nmask |= (1 << litterIdx[nx][ny]);
                    }
                    
                    if (ne > bestEnergy[nx][ny][nmask]) {
                        bestEnergy[nx][ny][nmask] = ne;
                        q.offer(new int[]{nx, ny, nmask, ne, steps + 1});
                    }
                }
            }
        }
        
        return -1;
    }
}