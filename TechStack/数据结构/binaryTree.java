package demo.example;

import demo.Utils;

import java.util.ArrayList;
import java.util.List;

class TreeNode {
    Integer element;
    TreeNode left;
    TreeNode right;

    TreeNode(Integer element) {
        this.element = element;
        this.left = null;
        this.right = null;
    }

    public void traversal() {
        /*
        树的遍历, 是一个递归操作
         */
        Utils.log("<%s>", this.element);

        if (this.left != null) {
            this.left.traversal();
        }

        if (this.right != null ) {
            this.right.traversal();
        }
    }
}

public class binaryTree {

    static public void invert(TreeNode tree) {
        TreeNode temp = tree.left;
        tree.left = tree.right;
        tree.right = temp;

        if (tree.left != null) {
            invert(tree.left);
        }

        if (tree.right != null) {
            invert(tree.right);
        }
    }

    static public boolean treeSearch(TreeNode tree, Integer element) {
        if (tree == null) {
            return false;
        } else {
            if (tree.element.equals(element)) {
                Utils.log("找到了 %s", tree.element);
                return true;
            } else {
                Utils.log("tree.left");
                boolean r = treeSearch(tree.left, element);
                if (r == false) {
                    Utils.log("tree.right");
                    return treeSearch(tree.right, element);
                } else {
                    return true;
                }
            }
        }
    }

    static public boolean bstSearch(TreeNode tree, Integer element) {
        if (tree == null) {
            return false;
        } else if (tree.element.equals(element)){
            return true;
        } else if (element < tree.element) {
            Utils.log("left");
            return bstSearch(tree.left, element);
        } else {
            Utils.log("right");
            return bstSearch(tree.right, element);
        }
    }

    static public void testTraversal() {
        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(2);
        TreeNode c = new TreeNode(3);
        TreeNode d = new TreeNode(4);
        TreeNode e = new TreeNode(5);
        TreeNode f = new TreeNode(6);
        TreeNode g = new TreeNode(7);

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        c.left = f;
        c.right = g;

        a.traversal();
    }


    static public void testInvert() {
        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(2);
        TreeNode c = new TreeNode(3);
        TreeNode d = new TreeNode(4);
        TreeNode e = new TreeNode(5);
        TreeNode f = new TreeNode(6);
        TreeNode g = new TreeNode(7);

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        c.left = f;
        c.right = g;

        Utils.log("test invert");
        a.traversal();
        invert(a);
        Utils.log("after invert");
        a.traversal();
    }

    static public void testSearch() {
        // 手动构建二叉树
        // 为什么手动这么麻烦呢, 因为一般都是自动生成的
        // 这里只需要掌握性质就好
        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(2);
        TreeNode c = new TreeNode(3);
        TreeNode d = new TreeNode(4);
        TreeNode e = new TreeNode(5);
        TreeNode f = new TreeNode(6);
        TreeNode g = new TreeNode(7);

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        c.left = f;
        c.right = g;

        a.traversal();

        Utils.ensure(treeSearch(a, 5) == true, "test 1");
        Utils.ensure(treeSearch(a, 3) == true, "test 2");
        Utils.ensure(treeSearch(a, 10) == false, "test 3");

        a = new TreeNode(1);
        b = new TreeNode(2);
        c = new TreeNode(3);
        d = new TreeNode(4);
        e = new TreeNode(5);
        f = new TreeNode(6);
        g = new TreeNode(7);

        d.left = b;
        d.right = f;
        b.left = a;
        b.right = c;
        f.left = e;
        f.right = g;

        d.traversal();

        Utils.ensure(bstSearch(a, 5) == true, "test 4");
        Utils.ensure(bstSearch(a, 3) == true, "test 5");
        Utils.ensure(bstSearch(a, 10) == false, "test 6");

    }


    static Integer binarySearch(Integer item, ArrayList<Integer> array, Integer begin, Integer end) {
        Utils.log("binarySearch item<%s>, %s to %s", item, begin, end);
        if (begin < end) {
            Integer index = begin + ((end - begin) / 2);
            Integer value = array.get(index);
            if (item.equals(value)) {
                return index;
            } else if (item > value) {
                return binarySearch(item, array, index + 1, end);
            } else {
                return binarySearch(item, array, begin, index);
            }
        } else {
            return null;
        }
    }

    static void testBinarySearch() {
        ArrayList<Integer> l = new ArrayList<>(List.of(3, 4, 5, 7, 9));

        Integer r1 = binarySearch(7, l, 0, l.size());
        Utils.ensure(r1.equals(3), "testBinarySearch 1");

        Integer r2 = binarySearch(10, l, 0, l.size());
        Utils.ensure(r2 == null, "testBinarySearch 2");
    }

    public static void main(String[] args) {
        testTraversal();
        // testInvert();
//         testSearch();
//        testBinarySearch();
    }

}
