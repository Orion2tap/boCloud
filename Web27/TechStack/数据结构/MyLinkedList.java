package demo.example;

import demo.Utils;

class Node {
    Integer element;
    Node next;

    Node() {

    }

    Node(Integer element) {
        this.element = element;
        this.next = null;
    }

    @Override
    public String toString() {
        return String.format("%s", element);
    }
}


// 1. length
// 2. append

public class MyLinkedList {
    Node head;

    MyLinkedList() {
        // 设置头结点的值为 0
        this.head = new Node(0);
    }

    @Override
    public String toString() {
        Node current = this.head;
        StringBuilder s = new StringBuilder();
//        s.append("[");
        while (current != null) {
//            String m = String.format("(%s)", current);
//            s.append(m);
//            s.append(" > ");
            s.append(current.toString()).append(" > ");
            current = current.next;
        }
//        s.append("]");
        return s.toString();
    }

    public Boolean isEmpty() {
        return this.head.next == null;
    }

    public void append(Integer number) {
        Node n = new Node(number);

        // 从头结点开始游标后移, 直至到达末尾停止
        Node current = this.head;
        while (current.next != null) {

            current = current.next;
        }
        // 循环结束, current 变成最后一个节点, 最后一个节点的 next 属性是 null
        current.next = n;
    }

    public Integer length() {
        int l = 0;
        Node node = head.next;
        while (node != null) {
            l = l + 1;
            node = node.next;
        }
        return l;
    }

    public Node NodeAtIndex(Integer index) {
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    public Integer indexAtElement(int element) {
        int count = 0;
        Node current = this.head;
        while (current.next != null) {
            if (current.element == element) {
                break;
            }

            count++;
            current = current.next;
        }
        return count;
    }

    // 头结点的值
    public int firstElement() {
        // 头结点不存数据 返回的是第一个有数据的节点的 element
        return this.head.next.element;
    }

    // 尾结点的值
    public int lastElement() {
        Node current = this.head;
        while (current.next != null) {
            current = current.next;
        }
        return current.element;
    }

    /*
                       fresh
                        333
                 left    ↓   right
        0 >>>>>>> 111 >>>>>>> 222
                index-1      index

                 left        fresh       right
        0 >>>>>>> 111 >>>>>>> 333 >>>>>>> 222
                index-1      index
     */
    public void insertBeforeIndex(int index, int element) {
        Node fresh = new Node(element);
        Node left = NodeAtIndex(index - 1);
        Node right = NodeAtIndex(index);
        left.next = fresh;
        fresh.next = right;
    }

    public void insertAfterIndex(int index, int element) {
        Node fresh = new Node(element);
        Node left = NodeAtIndex(index);
        Node right = NodeAtIndex(index + 1);
        left.next = fresh;
        fresh.next = right;
    }

    public void insertAfterNode(Node node, int element) {
        Node fresh = new Node(element);
        Node left = node;
        Node right = node.next;
        left.next = fresh;
        fresh.next = right;
    }

    /*
                              删除
                               ↑
                 left        target       right
        0 >>>>>>> 111 >>>>>>> 333 >>>>>>> 222
                index-1      index       index+1

                 left        right
        0 >>>>>>> 111 >>>>>>> 222
                index-1      index
     */
    public void deleteAtIndex(int index) {
        Node left = NodeAtIndex(index - 1);
        Node right = NodeAtIndex(index + 1);
        left.next = right;
    }

    public void deleteByElement(int element) {
//        int index = indexAtElement (element);
//        deleteAtIndex(index);
        Node left = this.head;
        Node target = left.next;
        while (target.element != element) {
            left = left.next;
        }
        Node right = target.next;
        left.next = right;
    }

    public void deleteByNode(Node node) {
        deleteByElement(node.element);
    }

    // 工具函数
    public static void ensure(boolean condition, String message) {
        if (!condition) {
            System.out.println(message);
        } else {
            System.out.println("测试成功");
        }
    }

    public static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }
    
    // 测试
    public static void testMyLinkedList() {
        MyLinkedList l = new MyLinkedList();
        l.append(1);
        l.append(2);
        log("l %s %s", l, l.length());

        l.append(3);
        log("l %s %s", l, l.length());

        Node n = l.NodeAtIndex(1);
        log("n 1 %s", n);

        Node n3 = l.NodeAtIndex(3);
        log("n3 3 %s", n3);
    }

    public static void testfirstElement() {
        Node n1 = new Node(111);
        Node n2 = new Node(222);
        MyLinkedList l = new MyLinkedList();
        l.head.next = n1;
        n1.next = n2;
        int e = l.firstElement();
        ensure(e == 111, "testfirstElement");
    }

    public static void testlastElement() {
        Node n1 = new Node(111);
        Node n2 = new Node(222);
        MyLinkedList l = new MyLinkedList();
        l.head.next = n1;
        n1.next = n2;
        int e = l.lastElement();
        ensure(e == 222, "testlastElement");
    }

    public static void testInsert() {
        Node n1 = new Node(111);
        Node n2 = new Node(222);
        MyLinkedList l = new MyLinkedList();
        l.head.next = n1;
        n1.next = n2;

//        log("result1:%s", l.toString());
        l.insertBeforeIndex(2, 333);
//        log("result1:%s", l.toString());
        ensure(l.toString().equals("0 > 111 > 333 > 222 > "), "testInsert 1");

//        log("result2:%s", l.toString());
        l.insertAfterIndex(2, 444);
//        log("result2:%s", l.toString());
        ensure(l.toString().equals("0 > 111 > 333 > 444 > 222 > "), "testInsert 2");

//        log("result2:%s", l.toString());
        l.insertAfterNode(n2, 555);
//        log("result2:%s", l.toString());
        ensure(l.toString().equals("0 > 111 > 333 > 444 > 222 > 555 > "), "testInsert 3");

    }

    public static void testDelete() {
        Node n1 = new Node(111);
        Node n2 = new Node(222);
        Node n3 = new Node(333);
        MyLinkedList l = new MyLinkedList();
        l.head.next = n1;
        n1.next = n2;
        n2.next = n3;

        l.deleteAtIndex(1);
        ensure(l.toString().equals("0 > 222 > 333 > "), "testDelete 1");

//        log("result2:%s", l.toString());
        l.deleteByElement(222);
//        log("result2:%s", l.toString());
        ensure(l.toString().equals("0 > 333 > "), "testDelete 2");

//        log("result2:%s", l.toString());
        l.deleteByNode(n3);
//        log("result2:%s", l.toString());

        ensure(l.toString().equals("0 > "), "testDelete 3");
        ensure(l.length() == 0, "testDelete 4");

    }

    public static void main(String[] args) {
//        testMyLinkedList();
//        testfirstElement();
//        testlastElement();
//        testInsert();
        testDelete();
    }
}
