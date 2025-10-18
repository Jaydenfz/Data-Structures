/*
 * Copyright 2023 Marc Liberatore.
 */
package hashtables;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * An implementation of HashTable.
 * 
 * This implementation uses "open addressing" to resolve collisions. Open
 * addressing means that the underlying array stores references to elements
 * (*not* ArrayLists of references!). when there is a collision, the hash table
 * searches forward linearly in the array until the element being sought is
 * found, or until an empty cell is found.
 * 
 * This implementation maintains a capacity equal to 2^n - 1 for some positive
 * integer n. When the load factor exceeds 0.75, the next add() triggers a
 * resize by incrementing n (by one). For example, when n=3, then capacity=7.
 * When size=6, then load factor ~=0.86. The addition of the seventh item would
 * trigger a resize, increasing the capacity of the array to 15.
 */
public class PrivateOpenHashTable<E> implements HashTable<E> {

            private E[] newHashTable;
            private int size; 
            private int n;
    
    /**
     * Instantiate a new hash table. The initial capacity should be 7.
     */
    public PrivateOpenHashTable() {
        this.n = 3;
        int capacity = (int) (Math.pow(2, n) - 1);
        newHashTable = (E[]) new Object[capacity];
        this.size = 0;
    }

    /**
     * Instantiate a new hash table. The initial capacity should be 
     * at least sufficient to hold n elements, but must be one less
     * than a power of two.
     */
    public PrivateOpenHashTable(int n) {

        this.n = 1;

        while ((int) (Math.pow(2, this.n) - 1) < n) {
            this.n++;
        }

        int capacity = (int) (Math.pow(2, n) - 1); 
        newHashTable = (E[]) new Object[capacity]; 
        this.size = 0; 
    }

    @Override
    public int capacity() {
        return (int)(Math.pow(2, n) - 1);
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public double loadFactor() {
        return loadFactor();
    }

    @Override
    public boolean add(E e) {
        /* 
        if (loadFactor() > 0.75) {
            enlarge();
        }
    */
        int index = Math.abs(e.hashCode()) % newHashTable.length;
    
        while (newHashTable[index] != null) {
            if (newHashTable[index].equals(e)) { 
                return false;
            }
            index = (index + 1) % newHashTable.length; 
        }
    
        newHashTable[index] = e;
        size++;

        return true;
    }

    @Override
    public boolean remove(E e) {
        return false;
    }

    @Override
    public E get(E e) {
        return null;
    }

    @Override
    public boolean contains(E e) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}