/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int c = 0;

    public int averageOfSubtree(TreeNode root) {
        f(root);
        return c;
    }

    private int[] f(TreeNode n) {
        if (n == null) return new int[]{0, 0};
        
        int[] l = f(n.left);
        int[] r = f(n.right);
        
        int s = l[0] + r[0] + n.val;
        int k = l[1] + r[1] + 1;
        
        if (s / k == n.val) c++;
        
        return new int[]{s, k};
    }
}
