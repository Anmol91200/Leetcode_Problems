import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int catMouseGame(int[][] graph) {
        int n = graph.length;
        int[][][] color = new int[n][n][2];
        int[][][] degree = new int[n][n][2];

        for (int m = 0; m < n; m++) {
            for (int c = 0; c < n; c++) {
                degree[m][c][0] = graph[m].length;
                degree[m][c][1] = graph[c].length;
                for (int node : graph[c]) {
                    if (node == 0) {
                        degree[m][c][1]--;
                        break;
                    }
                }
            }
        }

        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int t = 0; t < 2; t++) {
                color[0][i][t] = 1;
                queue.offer(new int[]{0, i, t, 1});
                if (i > 0) {
                    color[i][i][t] = 2;
                    queue.offer(new int[]{i, i, t, 2});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int m = curr[0], c = curr[1], t = curr[2], res = curr[3];

            if (m == 1 && c == 2 && t == 0) {
                return res;
            }

            int pt = 1 - t;
            if (pt == 0) {
                for (int pm : graph[m]) {
                    if (color[pm][c][0] != 0) continue;
                    if (res == 1) {
                        color[pm][c][0] = 1;
                        queue.offer(new int[]{pm, c, 0, 1});
                    } else {
                        degree[pm][c][0]--;
                        if (degree[pm][c][0] == 0) {
                            color[pm][c][0] = 2;
                            queue.offer(new int[]{pm, c, 0, 2});
                        }
                    }
                }
            } else {
                for (int pc : graph[c]) {
                    if (pc == 0) continue;
                    if (color[m][pc][1] != 0) continue;
                    if (res == 2) {
                        color[m][pc][1] = 2;
                        queue.offer(new int[]{m, pc, 1, 2});
                    } else {
                        degree[m][pc][1]--;
                        if (degree[m][pc][1] == 0) {
                            color[m][pc][1] = 1;
                            queue.offer(new int[]{m, pc, 1, 1});
                        }
                    }
                }
            }
        }

        return color[1][2][0];
    }
}