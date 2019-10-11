public class LinkedList<E extends Comparable> {
    private ListNode<E> head = new ListNode<>();
    private ListNode<E> tail;
    private int size;
    public int maxSize;

    public LinkedList() {
        this.size = 0;
    }

    public LinkedList(E o) {
        this.head.value = o;
        this.size++;
        this.maxSize++;
    }

    public E getHeadNode() {
        return head.value;
    }

    public ListNode<E> getHead() {return head;}

    public void add(E o) {
        ListNode<E> node = new ListNode<>(o);

        if (head == null) {
            head = node;
        }

        if (tail == null) {
            head.next = tail = node;
        }
        else {
            tail.next = node;
            tail = node;
        }

        this.size++;
        this.maxSize++;
    }

    public void insert(E o) {
        ListNode<E> node = new ListNode<>(o);
        ListNode<E> current = head.next;
        ListNode<E> previous = head;

        while (current != null && current.value.hashCode() != o.hashCode()) {
            previous = current;
            current = current.next;
        }

        previous.next = node;
        node.next = current;
        if (current == null) {
            tail = node;
        }

        this.size++;
        this.maxSize++;
    }

    public void remove(E o) {
        ListNode<E> node = head.next;
        ListNode<E> previous = head;

        while (node.next != null && node.value != o) {
            previous = node;
            node = node.next;
        }

        previous.next = node.next;
        if (previous.next == null) {
            tail = previous;
        }
        this.size--;
    }

    public void removeHeadNode() {
        if (head == null) {
            return;
        }
        head = head.next;
        this.size--;
    }

    public ListNode<E> getListNode(E o) {
        ListNode<E> node = head;
        boolean found = false;

        while (!found && node != null) {
            if (node.value.hashCode() == o.hashCode()) {
                found = true;
            }
            else {
                node = node.next;
            }
        }
        return node;
    }

    public boolean find(E o) {
        ListNode<E> node = head.next;
        boolean found = false;

        while (!found && node != null) {
            if (node.value == o) {
                found = true;
            }
            else {
                node = node.next;
            }
        }
        return found;
    }

    public boolean findHash(int hashcode) {
        ListNode<E> node = head;
        boolean found = false;

        while (!found && node != null) {
            if (node.value.hashCode() == hashcode) {
                found = true;
            }
            else {
                node = node.next;
            }
        }
        return found;
    }


    public ListNode<E> insertRec(ListNode<E> node, E val)
    {
        // If linked list is empty, create a
        // new node (Assuming newNode() allocates
        // a new node with given data)
        if (node == null)
            return new ListNode<>(val);

            // If we have not reached end, keep traversing
            // recursively.
        else
            node.next = insertRec(node.next, val);
        return node;
    }

    public boolean findRecur(ListNode<E> node, E val) {
        if(node == null) return false;
        if(node.value == val) return true;
        return findRecur(node.next, val);
    }

    public void display() {
        ListNode<E> current = head.next;

        System.out.println("--- List Contents ---");
        while (current != null) {
            System.out.println(current.value);
            current = current.next;
        }
    }

    public int getSize() {
        return this.size;
    }
    public boolean isEmpty() { return this.size == 0; }

    private class ListNode<E> {
        public E value;
        public ListNode<E> next;

        public ListNode() {
        }

        public ListNode(E o) {
            this.value = o;
        }
    }
}