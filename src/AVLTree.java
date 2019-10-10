// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AVLTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public AVLTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        root = remove( x, root );
    }


    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> remove( AnyType x, AvlNode<AnyType> t )
    {
        if( t == null )
            return t;   // Item not found; do nothing

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return balance( t );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin()
    {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMin( root ).element;
    }

    public void deleteMin(){

        root =  deleteMin(root);
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax()
    {
        if( isEmpty() )
            throw new RuntimeException();
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param search the item to search for.
     * @return true if x is found.
     */
    public boolean contains( AnyType search )
    {
        return contains( search, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( String label)
    {
        System.out.println(label);
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root,"" );
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    private AvlNode<AnyType> balance( AvlNode<AnyType> root )
    {
        if( root == null )
            return root;

        if( height( root.left ) - height( root.right ) > ALLOWED_IMBALANCE )
            if( height( root.left.left ) >= height( root.left.right ) )
                root = rightRotation( root );
            else
                root = doubleRightRotation( root );
        else
        if( height( root.right ) - height( root.left ) > ALLOWED_IMBALANCE )
            if( height( root.right.right ) >= height( root.right.left ) )
                root = leftRotation( root );
            else
                root = doubleLeftRotation( root );

        root.height = Math.max( height( root.left ), height( root.right ) ) + 1;
        return root;
    }

    public void checkBalance( )
    {
        checkBalance( root );
    }

    private int checkBalance( AvlNode<AnyType> root )
    {
        if( root == null )
            return -1;

        if( root != null )
        {
            int hl = checkBalance( root.left );
            int hr = checkBalance( root.right );
            if( Math.abs( height( root.left ) - height( root.right ) ) > 1 ||
                    height( root.left ) != hl || height( root.right ) != hr )
                System.out.println( "\n\n***********************OOPS!!" );
        }

        return height( root );
    }


    /**
     * Internal method to insert into a subtree.  Duplicates are allowed
     * @param node the item to insert.
     * @param root the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> insert( AnyType node, AvlNode<AnyType> root )
    {
        if( root == null )
            return new AvlNode<>( node, null, null );

        int compareResult = node.compareTo( root.element );

        if( compareResult < 0 )
            root.left = insert( node, root.left );
        else
            root.right = insert( node, root.right );

        return balance( root );
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param node the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<AnyType> findMin( AvlNode<AnyType> node )
    {
        if( node == null )
            return node;

        while( node.left != null )
            node = node.left;
        return node;
    }

    private AvlNode<AnyType> deleteMin( AvlNode<AnyType> root )
    {
        if (root == null) {
            return null;
        }

        if (root.left != null) {
            root.left = deleteMin(root.left);
        } else {
            if (root.right != null) {
                root = root.right;
            } else {
                root = null;
            }
        }
        return balance(root);
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param root the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<AnyType> findMax( AvlNode<AnyType> root )
    {
        if( root == null )
            return root;

        while( root.right != null )
            root = root.right;
        return root;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param search is item to search for.
     * @param root the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains( AnyType search, AvlNode<AnyType> root )
    {
        while( root != null )
        {
            int compareResult = search.compareTo( root.element );

            if( compareResult < 0 )
                root = root.left;
            else if( compareResult > 0 )
                root = root.right;
            else
                return true;    // Match
        }

        return false;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param root the node that roots the tree.
     */
    private void printTree( AvlNode<AnyType> root, String indent )
    {
        if( root != null )
        {
            printTree( root.right, indent+"   " );
            System.out.println( indent+ root.element + "("+ root.height  +")" );
            printTree( root.left, indent+"   " );
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height( AvlNode<AnyType> root )
    {   if (root==null) return -1;
        return root.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rightRotation(AvlNode<AnyType> root )
    {
        AvlNode<AnyType> theLeft = root.left;
        root.left = theLeft.right;
        theLeft.right = root;
        root.height = Math.max( height( root.left ), height( root.right ) ) + 1;
        theLeft.height = Math.max( height( theLeft.left ), root.height ) + 1;
        return theLeft;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> leftRotation(AvlNode<AnyType> t )
    {
        AvlNode<AnyType> theRight = t.right;
        t.right = theRight.left;
        theRight.left = t;
        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        theRight.height = Math.max( height( theRight.right ), t.height ) + 1;
        return theRight;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleRightRotation( AvlNode<AnyType> root )
    {
        root.left = leftRotation( root.left );
        return rightRotation( root );

    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleLeftRotation(AvlNode<AnyType> root )
    {
        root.right = rightRotation( root.right );
        return leftRotation( root );
    }

    private static class AvlNode<AnyType>
    {
        // Constructors
        AvlNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
        }

        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
    }

    /** The tree root. */
    private AvlNode<AnyType> root;


    // Test program
    public static void main( String [ ] args ) {
        AVLTree<Integer> t = new AVLTree<>();
        AVLTree<Dwarf> t2 = new AVLTree<>();

        String[] nameList = {"Snowflake", "Sneezy", "Doc", "Grumpy", "Bashful", "Dopey", "Happy", "Doc", "Grumpy", "Bashful", "Doc", "Grumpy", "Bashful"};
        for (int i=0; i < nameList.length; i++)
            t2.insert(new Dwarf(nameList[i]));

        t2.printTree( "The Tree" );

        t2.remove(new Dwarf("Bashful"));

        t2.printTree( "The Tree after delete Bashful" );
        for (int i=0; i < 8; i++) {
            t2.deleteMin();
            t2.printTree( "\n\n The Tree after deleteMin" );
        }
    }

}
