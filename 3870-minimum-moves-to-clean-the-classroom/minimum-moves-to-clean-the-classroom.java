class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        int[][] litterMap = new int[m][n];
        int startX = -1, startY = -1;
        int litterCount = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = classroom[i].charAt(j);
                if (c == 'S') {
                    startX = i;
                    startY = j;
                } else if (c == 'L') {
                    litterMap[i][j] = litterCount++;
                }
            }
        }
        
        if (litterCount == 0) return 0;
        
        int targetMask = (1 << litterCount) - 1;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{startX, startY, energy, 0, 0});
        
        boolean[][][][] visited = new boolean[m][n][energy + 1][1 << litterCount];
        visited[startX][startY][energy][0] = true;
        
        int[] dirs = {-1, 0, 1, 0, -1};
        
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int r = curr[0], c = curr[1], e = curr[2], mask = curr[3], steps = curr[4];
            
            for (int i = 0; i < 4; i++) {
                int nr = r + dirs[i];
                int nc = c + dirs[i + 1];
                
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && classroom[nr].charAt(nc) != 'X') {
                    int nxt_e = e - 1;
                    int nxt_mask = mask;
                    char nextCell = classroom[nr].charAt(nc);
                    
                    if (nextCell == 'L') {
                        nxt_mask |= (1 << litterMap[nr][nc]);
                    }
                    
                    if (nxt_mask == targetMask) {
                        return steps + 1;
                    }
                    
                    if (nextCell == 'R') {
                        nxt_e = energy;
                    }
                    
                    if (nxt_e == 0 && nextCell != 'R') continue;
                    
                    if (!visited[nr][nc][nxt_e][nxt_mask]) {
                        visited[nr][nc][nxt_e][nxt_mask] = true;
                        q.offer(new int[]{nr, nc, nxt_e, nxt_mask, steps + 1});
                    }
                }
            }
        }
        
        return -1;
    }
}