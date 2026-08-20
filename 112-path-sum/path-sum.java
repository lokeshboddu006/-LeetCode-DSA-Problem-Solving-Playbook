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
    public boolean hasPathSum(TreeNode r, int s) {
        if (r == null) return false;
        if (r.left == null && r.right == null) return s == r.val;
        return hasPathSum(r.left, s - r.val) || hasPathSum(r.right, s - r.val);
    }
}