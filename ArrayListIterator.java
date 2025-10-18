/*
 * Copyright 2023 Marc Liberatore.
 */

package lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ArrayListIterator<E> implements Iterator<E> {
    int currentIndex = 0;
    ArrayList<E> list;

    ArrayListIterator(ArrayList<E> list) {
        this.list = list;
    }
    // PART 2 Iterators and Comparators    
    // TASK: Complete the constructor and create and initialize the
    // ArrayListIterator fields, ensuring that the iterator starts at the
    // correct position (index
    // 0) and can track the size of the array.
    @Override
    public boolean hasNext() {
        // PART 2 Iterators
        // TASK: Implement the hasNext method to return true if there are more
        // elements in the collection to iterate over, based on the current
        // index and the size of the collection.

        return currentIndex < list.size();
    }

    @Override
    public E next() {
        // PART 2 Iterators
        // TASK: Implement the next method to return the next element in the
        // iteration. Make sure to throw a NoSuchElementException if there are
        // no more elements to return, and update the index accordingly.

        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return list.get(currentIndex++);
    }
}