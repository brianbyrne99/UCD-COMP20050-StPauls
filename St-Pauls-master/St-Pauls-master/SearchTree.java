import java.util.LinkedList;

public class SearchTree {
    Node head;
    treeNode root;

    private class Node
    {
        String line;
        Node next;

        public Node(String s1)
        {
            line=s1;
            next=null;
        }
    }

    public static class treeNode
    {
        String line;
        treeNode left,right=null;

        public treeNode(String s1)
        {
            line=s1;
            left=right=null;
        }
    }

    public void push(String str){
        Node node = new Node(str);
        node.next=head;
        head=node;
    }

    treeNode convertList2Binary(treeNode node)
    {

        LinkedList<treeNode> list =
                new LinkedList<treeNode>();

        // Base Case
        if (head == null)
        {
            node = null;
            return null;
        }

        node = new treeNode(head.line);
        list.add(node);


        head = head.next;


        while (head != null)
        {

            treeNode parent = list.peek();
            treeNode pp = list.poll();

            treeNode left= null, right= null;
            left = new treeNode(head.line);
            list.add(left);
            head = head.next;
            if (head != null)
            {
                right= new treeNode(head.line);
                list.add(right);
                head = head.next;
            }

            parent.left = left;
            parent.right = right;
        }

        return node;
    }
}