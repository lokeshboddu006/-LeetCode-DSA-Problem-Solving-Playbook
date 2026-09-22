class Solution {
    public int totalFruit(int[] fruits) {
        int b = 2;
        int n = fruits.length;
        int l = 0 , h = 0, max = 1;
        Map<Integer, Integer> map = new HashMap<>();
        while(h < n){
            int c = fruits[h];
            map.put(c, map.getOrDefault(c, 0) + 1);
            while(map.size() > b){
                int cnt = fruits[l];
                if(map.get(cnt) == 1)
                    map.remove(cnt);
                else{
                    int freq = map.get(cnt);
                    map.put(cnt, freq - 1);
                } 
               l++; 
            }
            if(map.size() <= b)
                max = Math.max(max, h-l+1);
            h++;
        }
        return max;
    }
}