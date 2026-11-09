class Solution {
    record Job(int at, int server) {} 
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        PriorityQueue<Integer> free = new PriorityQueue<>(); 
        for (int x = 0; x < k; x++) free.add(x); 
        PriorityQueue<Job> busy = new PriorityQueue<>(Comparator.comparingInt(Job::at));
        int[] handled = new int[k];
        for (int i = 0; i < arrival.length; i++) {
            while (!busy.isEmpty() && busy.peek().at() <= arrival[i]) {  
                int x = busy.poll().server();
                free.add(i + Math.floorMod(x - i, k));  
            }
            if (free.isEmpty()) continue;
            int x = free.poll() % k;
            busy.add(new Job(arrival[i] + load[i], x));
            handled[x]++;
        }
        int most = Arrays.stream(handled).max().getAsInt();
        return IntStream.range(0, k).filter(x -> handled[x] == most).boxed().toList();
    }
}