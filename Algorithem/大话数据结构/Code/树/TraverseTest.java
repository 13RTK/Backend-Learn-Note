import java.util.List;

public class TraverseTest {
    public static void main(String[] args) {
        TreeNode testRoot = new TreeNode(9, new TreeNode(10), new TreeNode(28));
        List<Integer> res = PostorderTraversal.postorderTraversal(testRoot);

        res.forEach(System.out::println);
    }
}
