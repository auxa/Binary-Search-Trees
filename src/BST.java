/*************************************************************************
 *  Binary Search Tree class.
 *  Adapted from Sedgewick and Wayne.
 *
 *  @version 3.0 1/11/15 16:49:42
 *
 *  @author TODO
 *
 *************************************************************************/
import java.lang.StringBuilder;
import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value> {
	private Node root;             // root of BST

	/**
	 * Private node class.
	 */
	private class Node {
		private Key key;           // sorted by key
		private Value val;         // associated data
		private Node left, right;  // left and right subtrees
		private int N;             // number of nodes in subtree

		public Node(Key key, Value val, int N) {
			this.key = key;
			this.val = val;
			this.N = N;
		}
	}

	// is the symbol table empty?
	public boolean isEmpty() {
		return size() == 0;
	}

	// return number of key-value pairs in BST
	public int size() { return size(root); }

	// return number of key-value pairs in BST rooted at x
	private int size(Node x) {
		if (x == null) return 0;
		else return x.N;
	}

	/**
	 *  Search BST for given key.
	 *  Does there exist a key-value pair with given key?
	 *
	 *  @param key the search key
	 *  @return true if key is found and false otherwise
	 */
	public boolean contains(Key key) {
		return get(key) != null;
	}

	/**
	 *  Search BST for given key.
	 *  What is the value associated with given key?
	 *
	 *  @param key the search key
	 *  @return value associated with the given key if found, or null if no such key exists.
	 */
	public Value get(Key key) { return get(root, key); }

	private Value get(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) return get(x.left, key);
		else if (cmp > 0) return get(x.right, key);
		else              return x.val;
	}

	/**
	 *  Insert key-value pair into BST.
	 *  If key already exists, update with new value.
	 *
	 *  @param key the key to insert
	 *  @param val the value associated with key
	 */
	public void put(Key key, Value val) {
		if (val == null) { delete(key); return; }
		root = put(root, key, val);
	}

	private Node put(Node x, Key key, Value val) {
		if (x == null) return new Node(key, val, 1);
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) x.left  = put(x.left,  key, val);
		else if (cmp > 0) x.right = put(x.right, key, val);
		else              x.val   = val;
		x.N = 1 + size(x.left) + size(x.right);
		return x;
	}

	/**
	 * Tree height.
	 *
	 * Asymptotic worst-case running time using Theta notation: TODO
	 *
	 * @return the number of links from the root to the deepest leaf.
	 *
	 * Example 1: for an empty tree this should return -1.
	 * Example 2: for a tree with only one node it should return 0.
	 * Example 3: for the following tree it should return 2.
	 *   B
	 *  / \
	 * A   C
	 *      \
	 *       D
	 */
	public int height() {
		int counter=-1;
		if(isEmpty()){
			return counter;
		}
		if(size()==1){
			return 0;
		}
		boolean temp =false;
		Node curNode = root;
		counter+=1;

		while(!temp){
			Node right=null;
			Node left=null;
			if(curNode.right!=null){
				right = curNode.right;
			}
			if(curNode.left != null){
				left = curNode.left;

			}
			if(right==null && left==null){
				return counter;
			}else if(right == null && left !=null){
				curNode=left;
				counter++;
			}else if(right !=null && left == null){
				curNode=right;
				counter++;
			}
			else if (left.N > right.N){
				curNode=left;
				counter++;
			}else if(left.N < right.N){
				curNode=right;
				counter++;
			}


		}

		return -1;
	}

	/**
	 * Median key.
	 * If the tree has N keys k1 < k2 < k3 < ... < kN, then their median key 
	 * is the element at position (N+1)/2 (where "/" here is integer division)
	 *
	 * @return the median key, or null if the tree is empty.
	 */
	public Key median() {
		if (isEmpty()) return null;

		int size= size();
		int median = (size-1)/2;
		Node temp2 = root;

		boolean notFound = true;
		while(notFound){
			if((size-temp2.left.N ) < median &(size-temp2.left.N )>0){
				size-=temp2.left.N;
				temp2=temp2.left;
				System.out.println("Help " + size);
			}
			if((size-temp2.right.N) < median & (size-temp2.right.N )>0) {
				size -=temp2.right.N;
				temp2=temp2.right;
				System.out.println("that" + size);

			}
			else{
				notFound=false;
			}
		}
		return temp2.key;

	}


	/**
	 * Print all keys of the tree in a sequence, in-order.
	 * That is, for each node, the keys in the left subtree should appear before the key in the node.
	 * Also, for each node, the keys in the right subtree should appear before the key in the node.
	 * For each subtree, its keys should appear within a parenthesis.
	 *
	 * Example 1: Empty tree -- output: "()"
	 * Example 2: Tree containing only "A" -- output: "(()A())"
	 * Example 3: Tree:
	 *   B
	 *  / \
	 * A   C
	 *      \
	 *       D
	 *
	 * output: "((()A())B(()C(()D())))"
	 *
	 * output of example in the assignment: (((()A(()C()))E((()H(()M()))R()))S(()X()))
	 *
	 * @return a String with all keys in the tree, in order, parenthesized.
	 */

	StringBuilder key =new StringBuilder();
	public String printKeysInOrder(){
		if (isEmpty()) return "()";

		key.append("(");
		key.append(printBrack(root));
		key.append(")");
		String temp2 = key.toString();
		key.setLength(0);
		return temp2;
	}
	private String printBrack(Node t){
		String temp = "";
		if(t.left==null&t.right==null){
			temp = "()"+t.val+"()";
		}
		else if(t.left==null){
			temp = "()" + t.val + "(" + printBrack(t.right) + ")";	
		}
		else if(t.right==null){
			temp = "(" + printBrack(t.left) + ")" + t.val + "()";
		}
		else{
			temp = "(" + printBrack(t.left) + ")"+t.val+ "(" + printBrack(t.right) + ")";
		}
		return temp;
	}


	/**
	 * Pretty Printing the tree. Each node is on one line -- see assignment for details.
	 *
	 * @return a multi-line string with the pretty ascii picture of the tree.
	 */
	StringBuilder pretty = new StringBuilder();
	public String prettyPrintKeys() {
		if(isEmpty()) return "-null\n";
		String output="";
		return miracleGroTree(root, output);
	}
	private String miracleGroTree(Node temp, String out) {
		String prettyTree="";
		if(temp==null)
		{
			prettyTree+=out+"-null\n";
			return prettyTree;
		}
		else{	
			String temp2=out+"-";
			prettyTree+=temp2+temp.key.toString()+"\n";
			String left = out+" |";
			prettyTree+=miracleGroTree(temp.left, left);
			String right = out+"  ";
			prettyTree+=miracleGroTree(temp.right, right);
		}
		return prettyTree;
	}


	/**
	 * Deteles a key from a tree (if the key is in the tree).
	 * Note that this method works symmetrically from the Hibbard deletion:
	 * If the node to be deleted has two child nodes, then it needs to be
	 * replaced with its predecessor (not its successor) node.
	 *
	 * @param key the key to delete
	 */

	public void delete(Key key)
	{ root = delete(root, key); }
	private Node delete(Node x, Key key) {
		if (x == null) return null;

		int howClose = key.compareTo(x.key);
		if (howClose < 0) {
			x.left = delete(x.left, key);
		}
		else if (howClose > 0){
			x.right = delete(x.right, key);
		}
		else {
			if (x.right == null) return x.left;
			if (x.left == null) return x.right;
			Node leftNode = x.left;
			Node rightNode = x.right;

			x = getPred(x);
			x.right= rightNode;
			put(x,leftNode.key, leftNode.val);
		}
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	} 	
	private Node deleteMin(Node x)
	{
		if (x.left == null) return x.right;
		x.left = deleteMin(x.left);
		x.N = 1 + size(x.left) + size(x.right);
		return x;
	}
	private Node getPred(Node x){
		Node temp25=x;
		while(temp25.left!=null){
			temp25=temp25.left;
		}
		temp25=deleteMin(temp25);
		//while(temp25.right!=null){
		//temp25=temp25.right;
		//	}

		return temp25;
	}

}