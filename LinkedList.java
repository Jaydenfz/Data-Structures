/*
 * Copyright 2023 Marc Liberatore.
 */

package lists;

import java.util.Iterator;
import java.util.Objects;

public class LinkedList<E> implements List<E> {
    // Note: do not declare any additional instance variables
    Node<E> head;
    Node<E> tail;
    int size;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        Node<E> n = head;
        while (n != null) {
            result = prime * result + head.data.hashCode();
        }
        result = prime * result + size;
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof List))
            return false;
        List other = (List) obj;
        if (size != other.size())
            return false;
        // Part 1 List Data Type and Implementations
        // TASK: Complete the equals method to ensure all corresponding
        // elements in both lists are equal before confirming the lists as
        // identical.
        List<E> otherList = (List<E>) other;

        if (size != otherList.size()){
            return false;
        }

        Node<E> current = this.head;
        for (int i = 0; i < size; i++) {
            if (!current.data.equals(otherList.get(i))) { 
                return false;
            }
            current = current.next;
        }
        
        return true;
    }

    @Override
    public int size() {
        // Part 1 List Data Type and Implementations
        // TASK: Implement the size method to correctly return the current
        // number of elements in the list.
        return size;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        // Part 1 List Data Type and Implementations
        // TASK: Implement the get method to retrieve the element at the
        // specified index, checking for bounds and utilizing tail optimization
        // for the last element.

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    @Override
    public void add(E e) {
        // Part 1 List Data Type and Implementations
        // TASK: Implement the add method to append a new element to the end of
        // the list. Ensure the head and tail pointers are correctly managed in
        // cases where the list is initially empty or already contains elements.

        Node<E> newNode = new Node<>(e);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    @Override
    public void add(int index, E e) throws IndexOutOfBoundsException {
        // Part 1 List Data Type and Implementations
        // TASK: Implement the add method to insert an element at a specified
        // index within the list. Handle several cases:
        // 1. Index bounds: Throw IndexOutOfBoundsException if the index is out
        // of bounds.
        // 2. Append: If adding at the end of the list,
        // simply append the element.
        // 3. Empty list: If the list is empty, initialize both head and tail
        // properly.
        // 4. Insert at head: If adding at the start (index 0), update the head
        // properly.
        // 5. General insertion: If inserting elsewhere, navigate to the node
        // currently at the position before the desired index, insert the new
        // node, and adjust pointers accordingly. Ensure size is incremented
        // after insertion to reflect the new total number of elements in the
        // list.
        //
        // NOTE: In class we studied a version of this `add` method that threw
        // an exception when add(size of list, e) was called. In this version,
        // we are asking you to allow add(size of list, e) to work.

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> newNode = new Node<>(e);

        if (index == 0) { 
            newNode.next = head;
            head = newNode;
            if (size == 0) {
                tail = head;
            }
        } else {
            Node<E> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            newNode.next = current.next;
            current.next = newNode;

            if (newNode.next == null) {
                tail = newNode;   // took me a long time to fix
            }
        }
        size++;
}


    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        // Part 1 List Data Type and Implementations
        // TASK: Implement the remove method to properly handle the deletion of
        // an element at a given index. Consider the following:
        // 1. Validate the index to ensure it falls within acceptable bounds. If
        // not, throw an IndexOutOfBoundsException.
        // 2. Consider special cases based on the size and structure of the
        // list:
        // - Handle scenarios where the list is reduced to an empty state.
        // - Address removals from the beginning of the list, adjusting head
        // and potentially tail if needed.
        // - Manage removals from other positions within the list, ensuring
        // to maintain proper linkages and tail position if the last
        // element is removed.
        // 3. Always update the list size accordingly and return the data of the
        // removed element. This approach requires careful pointer
        // manipulation and consideration of list integrity at every step.
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        E removedStuff;

        if (index == 0) {
            removedStuff = head.data;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<E> preveNode = head;

            for (int i = 0; i < index - 1; i++) {
                preveNode = preveNode.next;
            }

            removedStuff = preveNode.next.data;
            preveNode.next = preveNode.next.next;

            if (preveNode.next == null) {
                tail = preveNode;
            }
        }

        size--; // since I removed decrease size
        return removedStuff;
    }

    @Override
    public E set(int index, E e) throws IndexOutOfBoundsException {
        // Part 1 List Data Type and Implementations
        // TASK: Implement the set method to update an element at a specified
        // index with a new value and return the old value. Consider the
        // following:
        // 1. Ensure the index is within the valid range of the list. If it is
        // out of bounds, throw an IndexOutOfBoundsException.
        // 2. Traverse the list to locate the node at the given index.
        // 3. Replace the existing data at that node with the new element and
        // return the old data. Careful consideration of list traversal and
        // data manipulation is required to maintain list integrity and
        // functionality.

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        E oldStuff = current.data;
        current.data = e;

        return oldStuff;
    }

    @Override
    public int indexOf(E e) {
        // Part 1 List Data Type and Implementations
        // TASK: Implement the indexOf method to find and return the index of
        // the first occurrence of the specified element in the list. If the
        // element is not found, return -1. Consider iterating through the list
        // from the head, checking each element for equality with the provided
        // value. Ensure the method handles cases where the list may be empty or
        // the element does not exist in the list.

        Node<E> current = head;
        int index = 0;

        while (current != null) {

            if (Objects.equals(current.data, e)) {
                return index;
            }

            current = current.next;
            index++;

        }

        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        // PART 2 Iterators
        // TASK: Implement the iterator method to return an Iterator for the
        // current collection. Ensure that the Iterator allows sequential access
        // to elements in the correct order.
        return new LinkedListIterator<>(head);
    }
}