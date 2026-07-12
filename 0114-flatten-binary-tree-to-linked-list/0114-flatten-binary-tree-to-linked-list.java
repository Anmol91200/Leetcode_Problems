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
    public void flatten(TreeNode root) {
        TreeNode current = root;
        
        while (current != null) {
            if (current.left != null) {
                TreeNode runner = current.left;
                while (runner.right != null) {
                    runner = runner.right;
                }
                
                runner.right = current.right;
                current.right = current.left;
                current.left = null;
            }
            current = current.right;
        }
    }
}