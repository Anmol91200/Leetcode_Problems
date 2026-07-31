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
import java.util.HashMap;
import java.util.Map;

class Solution {
    private Map<Integer, Integer> map = new HashMap<>();
    private int postIdx;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        postIdx = postorder.length - 1;
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return helper(postorder, 0, inorder.length - 1);
    }

    private TreeNode helper(int[] postorder, int inStart, int inEnd) {
        if (inStart > inEnd) {
            return null;
        }

        int val = postorder[postIdx--];
        TreeNode root = new TreeNode(val);
        int inIdx = map.get(val);

        root.right = helper(postorder, inIdx + 1, inEnd);
        root.left = helper(postorder, inStart, inIdx - 1);

        return root;
    }
}