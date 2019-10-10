


public class PriorityQueue<AnyType extends Comparable<? super AnyType>>
{
    private AVLTree<AnyType> queue;
    private int length;

    public PriorityQueue() {
        this.queue = new AVLTree<AnyType>();
    }

    public void add(AnyType item) {
        queue.insert(item);
        length++;
    }

//    public AVLTree<AnyType> remove() {
//        AVLTree<AnyType> result = queue.remove(getFirst());
//        if (result != null) {
//            length--;
//        }
//        return result;
//    }

    public AnyType peek() {
        return getFirst();
    }

    private AnyType getFirst() {
        return queue.findMax();
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public int getSize() {
        return length;
    }

    public void clear() {
        queue.makeEmpty();
    }

    public void display() {
        queue.printTree("Print the Queue:");
    }

}