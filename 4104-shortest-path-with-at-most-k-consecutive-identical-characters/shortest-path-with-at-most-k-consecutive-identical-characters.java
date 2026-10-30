class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        int [][] m=edges;
        List<int[]>[] a = new ArrayList[n];
        for(int i=0;i<n;i++){
            a[i]= new ArrayList<>();
        }
        for(int[] b: edges){
            a[b[0]].add(new int[]{b[1],b[2]});
        }
        long[][] b = new long[n][k+1];
        for(int i=0;i<n;i++){
            Arrays.fill(b[i],Long.MAX_VALUE);
        }
        PriorityQueue<long[]> c =new PriorityQueue<>((x, y) -> Long.compare(x[0], y[0]));
        b[0][1]=0;
        c.offer(new long[]{0, 0, 1});
        while (!c.isEmpty()) {
            long[] d = c.poll();
            long cost = d[0];
            int node = (int) d[1];
            int streak = (int) d[2];
            if (cost != b[node][streak]) {
                continue;
            }
            if (node == n - 1) {
                return (int) cost;
            }
            for (int[] e : a[node]) {
                int next = e[0];
                int wt = e[1];
                int f;
                if (labels.charAt(node) == labels.charAt(next)) {
                    f = streak + 1;
                } else {
                    f = 1;
                }
                if (f > k) {
                    continue;
                }
                long g = cost + wt;
                if (g < b[next][f]) {
                    b[next][f] = g;
                    c.offer(new long[]{g, next, f});
                }
            }
        }
        return -1;
    }
}