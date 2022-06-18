import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

public class PreorderTraversal {
    
    static List<Integer> resList = new ArrayList<>();
    public static List<Integer> preOrderTraversal(TreeNode root) {
        preorderTraverse(root);
        return resList;
    }

    private static void preorderTraverse(TreeNode curNode) {
        if (curNode == null) {
            return;
        }

        resList.add(curNode.val);
        preorderTraverse(curNode.left);
        preorderTraverse(curNode.right);
    }


    public static List<Integer> preorderStackTraverse(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode popNode = stack.pop();
            res.add(popNode.val);

            if (popNode.right != null) {
                stack.push(popNode.right);
            }
            if (popNode.left != null) {
                stack.push(popNode.left);
            }
        }

        return res;
    }

}
