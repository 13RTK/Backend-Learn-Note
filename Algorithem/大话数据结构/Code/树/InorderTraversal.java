import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class InorderTraversal {
   
    static List<Integer> res = new ArrayList<>();
    public static List<Integer> inorderTraversal(TreeNode root) {
        inorderTraverse(root);

        return res;
    }

    private static void inorderTraverse(TreeNode curNode) {
        if (curNode == null) {
            return;
        }

        inorderTraverse(curNode.left);
        res.add(curNode.val);
        inorderTraverse(curNode.right);
    }

    public static List<Integer> inorderStackTraverse(TreeNode curNode) {
        List<Integer> res = new ArrayList<>();
        if (curNode == null) {
            return res;
        }

        Stack<TreeNode> stack = new Stack<>();

        while (!stack.isEmpty() || curNode != null) {
            if (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            } else {
                curNode = stack.pop();
                res.add(curNode.val);
                curNode = curNode.right;
            }
        }

        return res;
    }
}