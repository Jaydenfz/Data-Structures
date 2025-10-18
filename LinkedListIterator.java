package lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListIterator<E> implements Iterator<E> {
  // PART 2 Iterators and Comparators
  // TASK: Initialize the Node reference for the iterator to track the current
  // position in the linked list.

  Node<E> current;

  
  

  LinkedListIterator(Node<E> head) {
    // PART 2 Iterators
    // TASK: Complete the LinkedListIterator constructor to initialize the
    // current node to the head of the linked list, allowing iteration to begin
    // at the start of the list.
    this.current = head;
  }

  @Override
  public boolean hasNext() {
    // PART 2 Iterators
    // TASK: Implement the hasNext method to return true if there are more nodes
    // to iterate over in the linked list. Check whether the current node has a
    // valid reference to continue the iteration.
    boolean hasNext = true;

    if(current == null){
      hasNext = false;
    }

    return hasNext;
  }

  @Override
  public E next() {
    // PART 2 Iterators
    // TASK: Implement the next method to return the current element in the
    // iteration and advance to the next node. Ensure that a
    // NoSuchElementException is thrown if there are no more elements to return.

    if (!hasNext()) {
      throw new NoSuchElementException();
    }

    E data = current.data;

    current = current.next;

    return data;
  }

}