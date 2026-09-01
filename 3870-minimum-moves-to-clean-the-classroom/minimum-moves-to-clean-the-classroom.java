class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        int startX = -1, startY = -1;
        int litterCount = 0;
        int[][] litterIndex = new int[m][n];
        for (int[] row : litterIndex)
            Arrays.fill(row, -1);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = classroom[i].charAt(j);
                if (c == 'S') {
                    startX = i;
                    startY = j;
                } else if (c == 'L') {
                    litterIndex[i][j] = litterCount;
                    litterCount++;
                }
            }
        }
        if (litterCount == 0)
            return 0;
        int targetMask = (1 << litterCount) - 1;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[] { startX, startY, 0, energy, 0 });
        int[][][] bestEnergy = new int[m][n][1 << litterCount];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(bestEnergy[i][j], -1);
            }
        }
        bestEnergy[startX][startY][0] = energy;
        int[] dx = { -1, 1, 0, 0 };
        int[] dy = { 0, 0, -1, 1 };
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int x = curr[0], y = curr[1], mask = curr[2];
            int currE = curr[3], steps = curr[4];
            if (currE == 0)
                continue;
            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    char c = classroom[nx].charAt(ny);
                    if (c == 'X')
                        continue;
                    int nextE = currE - 1;
                    if (c == 'R')
                        nextE = energy;
                    if (nextE < 0)
                        continue;
                    int nextMask = mask;
                    if (c == 'L') {
                        nextMask |= (1 << litterIndex[nx][ny]);
                    }
                    if (nextMask == targetMask) {
                        return steps + 1;
                    }
                    if (nextE > bestEnergy[nx][ny][nextMask]) {
                        bestEnergy[nx][ny][nextMask] = nextE;
                        q.offer(new int[] { nx, ny, nextMask, nextE, steps + 1 });
                    }
                }
            }
        }
        return -1;
    }
}