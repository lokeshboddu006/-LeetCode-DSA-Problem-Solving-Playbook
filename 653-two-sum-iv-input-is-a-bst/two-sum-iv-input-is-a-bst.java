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
    class Inorder {
        Stack<TreeNode> stack = new Stack<>();
        int val;

        Inorder(TreeNode root) {
            pushAll(root);
            getNext();
        }

        Inorder getNext() {
            if (!stack.isEmpty()) {
                TreeNode node = stack.pop();
                val = node.val;
                pushAll(node.right);
            }
            return this;
        }

        void pushAll(TreeNode node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }

    class RevInorder {
        Stack<TreeNode> stack = new Stack<>();
        int val;

        RevInorder(TreeNode root) {
            pushAll(root);
            getnext();
        }

        RevInorder getnext() {
            if (!stack.isEmpty()) {
                TreeNode node = stack.pop();
                val = node.val;
                pushAll(node.left);
            }
            return this;
        }

        void pushAll(TreeNode node) {
            while (node != null) {
                stack.push(node);
                node = node.right;
            }
        }
    }

    public boolean findTarget(TreeNode root, int k) {
        if (root == null) return false;

        Inorder p1 = new Inorder(root);
        RevInorder p2 = new RevInorder(root);

        while (p1.val < p2.val) {
            if (p1.val + p2.val == k) {
                return true;
            }
            if (p1.val + p2.val > k) {
                p2 = p2.getnext();
            } else {
                p1 = p1.getNext();
            }
        }
        return false;
    }
}