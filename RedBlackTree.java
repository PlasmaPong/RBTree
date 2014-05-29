package RBTree;
import java.io.Serializable;
/*
 * THIS ASSIGNMENT
 * ONLY VALUES
 * NO GENERICS
 * ADD--LOOKUP--REMOVE
 * CAREFUL OF ADD/REMOVE CASES
 * 
 * 
 */
public class RedBlackTree implements BinaryTree, Serializable{
	private Node temp;
	private int size = 0;
	private Node root;
	public static final boolean RED = true;
	public static final boolean BLACK = false;

	public RedBlackTree(){
		root=null;
	}
	@Override
	public boolean lookup(int value) {
		if(root==null){
			return false; //DEFAULT RETURN
		}
		else if(root.value==value){
			root=null;
			return true;
		}
		else{
			while(temp.value!=value&&temp!=null){
				if(value<=temp.value)	temp=temp.leftChild;
				else					temp=temp.rightChild;
			}
			return true;
		}
	}

	@Override
	public int size() {
		return size;
	}
	@Override
	public void add(int value) {//check for key even existing........
		// TODO Auto-generated method stub
		// Tend to root case
		//Traverse down and add
		// traverse up the tree looking for cases to correct... only within the tree affected by the added node
		if(root==null){
			root.value=value;
		}
		else{//MAiN ADD
			temp = root;
			while(temp.rightChild!=null && temp.leftChild!=null){
				if(value<=temp.value){
					temp=temp.rightChild;
				}
				else{
					temp=temp.leftChild;
				}
			}
			placeNode(temp, value);//SHOULD ALWAYS BE RED
		}
		reorientAfterAdd(temp.rightChild);
		size++;
		root.color=BLACK;
	}

	private void reorientAfterAdd(Node temp) {
		//NOW BEGINS THE CASE CHECKING(ADD BASED CASES ONLY)
		/*
		 * this means...
		 * 
		 */
		if(temp.parent.color==RED){//RED PARENT AND CHILD----> Breaks black children rule.
			if(isRed(temp.getUncle())){//RED UNCLE------> Recolor and recursive call up the tree.
				temp.parent.color=BLACK;
				temp.getUncle().color=BLACK;
				temp.getGrandparent().color=RED;
				reorientAfterAdd(temp.parent.parent);//recursive call on grandparent.
			}
			//ROTATION CASES FOR SPECIFIC SIDES
			else if(temp.parent==temp.getGrandparent().leftChild){//LEFT SIDE CASE
				if(temp==temp.parent.rightChild){
					rotateLeft(temp=temp.parent);//Keep pointer at the pseudo-temp position for the grandparent call regardless of this case...
				}
				temp.getGrandparent().color=RED;//recolor as well to keep order
				temp.parent.color=BLACK;
				rotateRight(temp.getGrandparent());//the rotation should move the temp up one level.
			}else if(temp.parent==temp.getGrandparent().rightChild){
				if(temp==temp.parent.leftChild){//recolor as well
					rotateRight(temp=temp.parent);//Keep pointer at the pseudo-temp position
				}								  //for the grandparent call regardless of this case...
				temp.getGrandparent().color=RED;
				temp.parent.color=BLACK;
				rotateLeft(temp.getGrandparent());
			}
		}
	}
	public void rotateLeft(Node temp){
		// uses given node and the right Child of the node and rotates them left
	}
	private void rotateRight(Node temp) {
		// uses given node and the left Child of the node and rotates them to the right

	}
	private void placeNode(Node temp,int value) {
		Node tempNode = new Node(value, RED);
		if(value<=temp.value){
			temp.setRightChild(tempNode);
			temp=temp.rightChild;
		}
		else{
			temp.setLeftChild(tempNode);
			temp=temp.leftChild;
		}
	}
	public boolean remove(int value){//OLD REMOVE DIDNT WORK PROPERLY... REMAKE IT
		Node temp = root;
		if(root==null){
			return false; 
		}
		while(temp!=null&&temp.value!=value){
			if(value<=temp.value)
				temp=temp.leftChild;
			else
				temp=temp.rightChild;
		}
		if(temp==null){
			return false;
		}
		temp=getSuccessor(temp);
		Node child;
		if(temp.rightChild==null){
			child=temp.leftChild;
		}
		else{
			child=temp.rightChild;
		}
		if(temp==root){
			root=null;
		}
		else if(child!=null){
			if(temp==root){
				root=child;
			}
			else if(temp.parent.rightChild==temp){//short case replacement
				temp.parent.setRightChild(child);
			}
			else{
				temp.parent.setLeftChild(child);			
			}
			// now comes the case for black temp---->need to reorganize to compensate for loss of black
			if(temp.color==BLACK)
				reorientAfterRemove(child);
		}
		else{
			if(temp.color==BLACK)
				reorientAfterRemove(temp);
		}
		return true;
		//////////////////////////////////////////I COULDNT FINISH THIS.
	}

	public void reorientAfterRemove(Node node){
		
	}

	public Node getSuccessor(Node node) {
		/**
		 * this also copies the value from the successor
		 */
		Node temp = node;
		Node holder = node;
		if(temp.rightChild!=null){//in order successor
			temp=temp.rightChild;
			while(temp.leftChild!=null){
				temp=temp.leftChild;
			}
			holder.value=temp.value;
			return temp;
		}
		else
			return null;
	}

	/*private void swap(Node node1, Node node2) {//POINTLESS CODE REMOVE PLS
		Node temp = new Node(node1.value, node1.color);
		temp.parent=node1.parent;
		temp.setRightChild(node1.rightChild);
		temp.setLeftChild(node1.leftChild);
		if(node1.parent!=null){
			if(temp.parent.rightChild==temp)	node1.parent.rightChild=temp;//if temp is a rightChild
			else								node1.parent.leftChild=temp;
		}
		node1.setRightChild(node2.rightChild);
		node1.setLeftChild(node2.leftChild);
		node1.value=node2.value;
		if(node2.parent!=null){
			if(node1.parent.rightChild==node1)	node2.parent.rightChild=node1;//if node1 is a rightChild
			else								node2.parent.leftChild=node1;
		}
		node1.parent=node2.parent;
		node1.color=node2.color;
		if(temp.parent!=null){
			if(node2.parent.rightChild==node2)	temp.parent.rightChild=node2;//if node2 is a rightChild
			else								temp.parent.leftChild=node2;
		}
		node2.parent=temp.parent;
		node2.setRightChild(temp.rightChild);
		node2.setLeftChild(temp.leftChild);
		node2.value=temp.value;
		node2.color=temp.color;
	}*/
	public boolean isRed(Node node){//trying a new format for some if/else cases, this is really good for single line functions
		//If the node is null, then its automatically black
		if(node==null)		return BLACK;	
		else if(node.color)	return RED;
		else                return BLACK;
	}
	private class Node{
		private int value;
		private Node rightChild;
		private Node leftChild;
		private Node parent;
		private boolean color;
		/*
		 * TODO
		 * Make methods specific to the kind of Node needed
		 * getters/setters(if needed) meh nvm.
		 * any utility methods as needed.
		 */
		private Node(int value, boolean color){
			rightChild = null;
			leftChild=null;
			parent=null;
			value = value;
			this.color=color;
		}
		public Node getGrandparent() {
			return this.parent.parent;
		}
		private void setRightChild(Node node){
			this.rightChild=node;
			if(this.rightChild!=null)	this.rightChild.parent=this;
		}
		private void setLeftChild(Node node){
			this.leftChild=node;
			if(this.leftChild!=null)	this.leftChild.parent=this;
		}

		private Node getSibling(){
			if(parent.rightChild == this){
				return parent.leftChild;
			}
			else{
				return parent.rightChild;
			}
		}
		private Node getUncle(){
			if(parent!=null){
				if(parent.parent!=null){{
					if(parent.parent.rightChild==parent){
						return parent.parent.leftChild;
					}
					else{
						return parent.parent.rightChild;
					}
				}
				}
				else 
					return null;
			}
			else{
				return null;
			}
		}
	}
}

