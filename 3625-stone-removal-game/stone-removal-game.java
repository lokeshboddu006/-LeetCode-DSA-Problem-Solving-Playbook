class Solution {
    public boolean canAliceWin(int n) {
        int D=10;
        int c=0;
        while(n>=D) {
            c++;
            n-=D;
            D--;
        }
        return c%2==0?false:true;
    }
}