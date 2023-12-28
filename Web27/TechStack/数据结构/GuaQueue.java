package demo.example;

import demo.Utils;

// 1. 入队列 enqueue
// 2. 出队列 dequeue
public class GuaQueue {
    Node head;
    Node tail;

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

    GuaQueue() {
        this.head = new Node();
        this.tail = head;
    }

    public void enqueue(Integer element) {
        /*
           head              tail
            # -----> 1 -----> 2

           head              tail         n
            # -----> 1 -----> 2 -----> element

           head                         tail
            # -----> 1 -----> 2 -----> element
        */
        Node n = new Node(element);
        // 尾结点的 next 指向新节点
        this.tail.next = n;
        // 尾结点就是新节点
        this.tail = n;
    }

    public Integer dequeue() {
        /*
          只有 1 个元素时
          head     tail
           # -----> 1

          head     node
           # -----> 1                   定位到第一个结点 命名为 node

          head
          tail                          node 的下一个结点 (空)成为第一个结点
           # ----->                     tail 就是 head


          不止 1 个元素时
           head              tail
            # -----> 1 -----> 2

           head     node     tail
            # -----> 1 -----> 2         定位到第一个结点 命名为 node

           head     tail
            # -----> 2                  node 的下一个结点成为第一个结点
        */
        // 分只有一个元素和两个以上的元素情况来讨论
        if (this.head.next.equals(this.tail)) {
            // 只有一个元素, 需要去改动尾结点
            Node node = this.head.next;
            this.head.next = null;
            this.tail = head;
            return node.element;
        } else {
            // 有两个以上元素, 不用去改尾结点
            Node node = this.head.next;
            this.head.next = node.next;
            return node.element;
        }
    }

    public static void testQueue() {
        GuaQueue q = new GuaQueue();
        q.enqueue(1);
        Utils.log("queue: %s", q);
        q.enqueue(2);
        Utils.log("queue: %s", q);
        q.enqueue(3);
        Utils.log("queue: %s", q);
        q.enqueue(4);

        Utils.log("queue: %s", q);
        Utils.ensure(q.dequeue().equals(1), "testQueue 1");
        Utils.log("queue: %s", q);
        Utils.ensure(q.dequeue().equals(2), "testQueue 2");
        Utils.log("queue: %s", q);
        Utils.ensure(q.dequeue().equals(3), "testQueue 3");
        Utils.log("queue: %s", q);
        Utils.ensure(q.dequeue().equals(4), "testQueue 4");
    }

    public static void main(String[] args) {
        testQueue();
    }
}
