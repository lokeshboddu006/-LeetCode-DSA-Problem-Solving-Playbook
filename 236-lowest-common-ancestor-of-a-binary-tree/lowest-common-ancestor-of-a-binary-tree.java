/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==p || root==q|| root==null){
            return root;
        }
        TreeNode L=lowestCommonAncestor(root.left,p,q);
        TreeNode R=lowestCommonAncestor(root.right,p,q);
        if(L==null){
            return R;
        }if(R==null){
            return L;
        }
        return root;
    }
}