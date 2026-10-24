class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int as = toDay(arriveAlice, days);
        int aed = toDay(leaveAlice, days);
        int bst = toDay(arriveBob, days);
        int bed = toDay(leaveBob, days);
        int st = Math.max(as, bst);
        int ed = Math.min(aed, bed);
        return st <= ed ? ed - st + 1 : 0;
    }
    private int toDay(String s, int[] days) {
        int m = (s.charAt(0) - '0') * 10 + (s.charAt(1) - '0');
        int d = (s.charAt(3) - '0') * 10 + (s.charAt(4) - '0');
        int res = d;
        for (int i = 0; i < m - 1; i++) res += days[i];
        return res;
    }
}