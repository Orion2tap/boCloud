package demo.example;// 1. push, 往栈里面放一个元素
// 2. pop, 从栈里面拿出一个元素
// 3. top, 得到栈顶元素的值, 但是不弹出栈顶元素

import demo.Utils;

public class GuaStack {
    Node head;

    @Override
    public String toString() {
        Node n = this.head;
        StringBuilder s = new StringBuilder();
        s.append("[");
        while (n != null) {
            String m = String.format("(%s)", n);
            s.append(m);
            s.append(" > ");
            n = n.next;
        }
        s.append("]");
        return s.toString();
    }

    GuaStack() {
        this.head = new Node();
    }

    public void push(Integer element) {
        // head -> node2 -> node3
        // node 入栈
        // node -> node2 -> node3
        // head -> node
        // head -> node -> node2 -> node3

        /*
                  node
                   ↓
            head -----> node2 -----> node3

                  node -----> node2 -----> node3          组装尾部 node.next = head.next;
            head -----> node -----> node2 -----> node3    组装头部 head.next = node;
        */
        Node node = new Node(element);
        // 把新插入的节点, 放在 head 节点之后, 永远都是第一个节点
        node.next = head.next;
        head.next = node;
    }

    public Integer pop() {
        // head -> node -> node2 -> node3
        // node 出栈
        // head -> node2 -> node3
        Node node = this.head.next;
        this.head.next = node.next;
        return node.element;

    }

    public Integer top() {
        return this.head.next.element;
    }

    public static void testStack() {
        GuaStack s = new GuaStack();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);

        Utils.log("%s", s);

        Utils.ensure(s.pop().equals(4), "testStack 1");
        Utils.log("%s", s);
        Utils.log("top %s l(%s)", s.top(), s);
        Utils.ensure(s.pop().equals(3), "testStack 2");
        Utils.log("%s", s);
        Utils.ensure(s.pop().equals(2), "testStack 3");
        Utils.log("%s", s);
        Utils.ensure(s.pop().equals(1), "testStack 4");
    }

    public static void main(String[] args) {
        testStack();
    }
}
