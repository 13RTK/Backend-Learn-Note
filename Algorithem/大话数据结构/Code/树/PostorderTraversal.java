import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class PostorderTraversal {
    static List<Integer> res = new ArrayList<>();
    public static List<Integer> postorderTraversal(TreeNode root) {
        postorderTraverse(root);

        return res;
    }

    private static void postorderTraverse(TreeNode curNode) {
        if (curNode == null) {
            return;
        }

        postorderTraverse(curNode.left);
        postorderTraverse(curNode.right);
        res.add(curNode.val);
    }


    public static List<Integer> postorderStackTraverse(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode popNode = stack.pop();
            res.add(popNode.val);

            if (popNode.left != null) {
                stack.push(popNode.left);
            }
            if (popNode.right != null) {
                stack.push(popNode.right);
            }
        }

        Collections.reverse(res);

        return res;
    }
}
