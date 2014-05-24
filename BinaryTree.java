
public interface BinaryTree<K extends Comparable<K>, V> {
	
	
	
	public V lookup(K key);
	
	public int size();
	
	public void add(K key, V value);
	 
	public V remove(K key);
}
