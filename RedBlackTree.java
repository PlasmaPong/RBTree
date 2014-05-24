import java.io.Serializable;

public class RedBlackTree<K extends Comparable<K>,V> 
							implements BinaryTree<K,V>,
							Serializable{
	private int size = 0;
	private Node root;
	
	public RedBlackTree(){
		root=null;
	}
	@Override
	public V lookup(K key) {
		Node temp = root;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public void add(K key, V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
	}
	private class Node{
		private K key;
		private V value;
		private Node rightChild;
		private Node leftChild;
		private Node parent;
		/*
		 * TODO
		 * Make methods specific to the kind of Node needed
		 */
	}
}
