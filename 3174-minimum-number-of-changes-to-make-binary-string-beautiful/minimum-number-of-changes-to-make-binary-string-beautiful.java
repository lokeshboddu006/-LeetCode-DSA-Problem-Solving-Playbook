class Solution {
    public int minChanges(String s) {
        return (int) IntStream.range(0, s.length() / 2).filter(i -> s.charAt(2 * i) != s.charAt(2 * i + 1)).count();
    }
}