/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int first = -1;
        int last = -1;
        int count = 0;
        int idx = 1;
        int min = Integer.MAX_VALUE;
        ListNode prev = head;
        ListNode curr = head.next;
        while(curr.next != null) {
            if((curr.val > prev.val && curr.val > curr.next.val) || (curr.val < prev.val && curr.val < curr.next.val)) {
                if(first == -1) {
                    first = idx;
                    last = idx;
                }
                else {
                    min = Math.min(min,idx-last);
                    last = idx;
                }
            }
            prev = curr;
            curr = curr.next;
            idx++;
        }
        if(first == last) {
            return new int[]{-1,-1};
        }
        return new int[]{min,last-first};
    }
}