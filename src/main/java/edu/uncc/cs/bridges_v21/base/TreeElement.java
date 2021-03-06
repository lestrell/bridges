package bridges.base;

/**
 * @author Mihai Mehedint, K.R. Subramanian
 * This class can be used to create tree element objects
 * with left and right pointers
 *
 * Modified (KRS) - 6/22/16. Generaized to hold any number of children
 */
import java.util.Vector; 

public class TreeElement<E> extends Element<E> {

						// holds all children of the node
	private Vector<TreeElement<E>> children; 
	
	/** 
	 *
	 *	Constructs an empty TreeElement with first two children
	 *	set to null. 
	 *
	 **/
	public TreeElement(){
		super();
		children = new Vector<TreeElement <E>> (2);
	}
	
	/** 
	 *
	 *	Constructs a TreeElement holding an object "e" with first two children
	 *	set to null. 
	 *
	 * @param e the generic object that TreeElement will hold 
	 *
	 **/
	public TreeElement (E e){
		super(e);
		children = new Vector<TreeElement <E>> (2);
	}
	
	/** 
	 *	Constructs a TreeElement with label set to "label", holding an 
	 *		object "e". 
	 * 	@param label the label of TreeElement that shows up on the Bridges 
	 *		visualization
	 * 	@param e the generic object that TreeElement will hold 
	 * 
	 **/
	public TreeElement (String label, E e){
		super(label, e);
		children = new Vector<TreeElement <E>> (2);
	}
	
	/** 
	 *
	 *	Constructs an empty TreeElement left pointer pointing to child 0  
	 *	and right pointer pointing to child 1.
	 *
	 * 	@param left the TreeElement to be assigned to the child 0 of 
	 *		this TreeElement
	 * 	@param right the TreeElement to be assigned to the child 1 
	 *		of this TreeElement
	 * 
	 **/
	public TreeElement(TreeElement<E> left, TreeElement<E> right) {
		super();
		children = new Vector<TreeElement <E>> (2);
		children.add(left);
		children.add(right);
	}
	
	/** 
	 *
	 *	Constructs a TreeElement holding the object "e", left pointer 
	 *	pointing to first child  and right pointer pointing to second child
	 *
	 * @param e the generic object that TreeElement will hold 
	 * @param left the TreeElement to be assigned to the first child of 
	 *		this TreeElement
	 * @param right the TreeElement to be assigned to the second child 
	 *		of this TreeElement
	 * 
	 **/
	public TreeElement(E e, TreeElement<E> left, TreeElement<E> right) {
		super(e);
		children = new Vector<TreeElement <E>> (2);
		children.add(left);
		children.add(right);
	}

	/**
	 *	This method gets the data structure type
	 *
	 *	@return  The date structure type as a string
	 **/
	public String getDataStructType() {
		return "Tree";
	}

	/**
	 *  Adds a child to the node
	 *
	 */
	public void addChild(TreeElement<E> child) {
		children.add(child);	
	}

	/**
	 *
	 * Returns the number of children at this node
	 *
	 * @return  number of children
	 *
	 **/
	public int getNumberOfChildren() {
		return children.size();
	}

	/**
	 *	adds a child to the node - will be added at the next open position
	 *
	 * @param  child to be added
	 *
	 * @return none
	 *
	 **/
	public void setChild (int index, TreeElement<E> child) {
		if (index < children.size())
			children.set(index, child);
	}

	/**
	 *	gets a child at a particular index 
	 *
	 * @param  index into the list of children
	 *
	 * @return child to be returned
	 *
	 **/
	public TreeElement<E> getChild(int index) {
		if (index < children.size()) 
			return  children.get(index);
		else return null;
	}
}
