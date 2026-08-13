1/**
2 * Definition for a binary tree node.
3 * public class TreeNode {
4 *     int val;
5 *     TreeNode left;
6 *     TreeNode right;
7 *     TreeNode() {}
8 *     TreeNode(int val) { this.val = val; }
9 *     TreeNode(int val, TreeNode left, TreeNode right) {
10 *         this.val = val;
11 *         this.left = left;
12 *         this.right = right;
13 *     }
14 * }
15 */
16class Solution {
17    public void flatten(TreeNode root) {
18        if (root == null)
19            return;
20        List<TreeNode> nodes = new ArrayList<>();
21        preOrder(root, nodes);
22        for (int i = 0; i < nodes.size() - 1; i++) {
23            nodes.get(i).left = null;
24            nodes.get(i).right = nodes.get(i + 1);
25        }
26    }
27
28    private void preOrder(TreeNode node, List<TreeNode> nodes) {
29        if (node == null)return;
30        nodes.add(node);
31        preOrder(node.left, nodes);
32        preOrder(node.right, nodes);
33    }
34}
35
36