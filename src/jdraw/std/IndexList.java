package jdraw.std;

import java.util.LinkedList;

public class IndexList<E> extends LinkedList<E> {
    
    /**
     * UID
     */
    private static final long serialVersionUID = -5139168563421418335L;

    /**
     * Sets position of given Element within the list.        
     * @param element Element to move.
     * @param pos New position of Element.
     * @throws IllegalArgumentException If object is null or not in list.
     * @throws IndexOutOfBoundsException Desired index does not fit the list.
     */
    public void setPosition(E element, int pos) throws IllegalArgumentException, IndexOutOfBoundsException{
        if(element == null || !this.contains(element))
            throw new IllegalArgumentException("Object null or no in list.");
                
        if(pos < 0 || size() <= pos)
            throw new IndexOutOfBoundsException("Invalid position.");
        
        if(indexOf(element) != pos && remove(element))
            add(pos, element);
    }
    
    /**
     * Moves element to front of list.
     * @param element Element to be moved.
     */
    public void moveToFront(E element){
        if(element == null || !this.contains(element))
            throw new IllegalArgumentException("Object null or no in list.");
        
        if(remove(element))
            addFirst(element);
    }
    
    /**
     * Moves element to back of list.
     * @param element Element to be moved.
     */
    public void moveToBack(E element){
        if(element == null || !this.contains(element))
            throw new IllegalArgumentException("Object null or no in list.");
        
        if(remove(element))
            addLast(element);
    }

}
