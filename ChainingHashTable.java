package hashtables;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * An implementation of HashTable.
 * 
 * This implementation uses chaining to resolve collisions. Chaining means
 * the underlying array stores references to growable structures (like
 * LinkedLists) that we expect to remain small in size. When there is a
 * collision, the element is added to the end of the growable structure. It
 * must search the entire growable structure whenever checking membership
 * or removing elements.
 * 
 * This implementation maintains a capacity equal to 2^n - 1 for some positive
 * integer n. When the load factor exceeds 0.75, the next add() triggers a
 * resize by incrementing n (by one). For example, when n=3, then capacity=7.
 * When size=6, then load factor ~=0.86. The addition of the seventh item would
 * trigger a resize, increasing the capacity of the array to 15.
 */
public class ChainingHashTable<E> implements HashTable<E> {

    /**
     * Instantiate a new hash table. The initial capacity should be 7.
     */
    private LinkedList<E>[] newHashTable;
    private int size;
    private int n;

    public ChainingHashTable() {
        // TASK 1: Ah, the humble constructor. Set `n` to 3.
        // Initialize the table with just enough room for now — think of it as the
        // cozy starter home of hash tables.

        this.n = 3;
        int capacity = computeCapacity();
        newHashTable = new LinkedList[capacity];

        for (int i = 0; i < capacity; i++) {
            newHashTable[i] = new LinkedList<>();
        }

        this.size = 0;
    }

    /**
     * Instantiate a new hash table. The initial capacity should be
     * at least sufficient to hold n elements, but must be one less
     * than a power of two.
     */
    public ChainingHashTable(int n) {
        // TASK 2: This constructor requires some mathematical magic. Compute
        // `n` as if you were a sorcerer seeking the perfect table size.
        // The result? A capacity that’s *just right* for your element-hungry
        // table.

        this.n = 1;

        while (computeCapacity() < n) {
            this.n++;
        }

        int capacity = computeCapacity();
        newHashTable = new LinkedList[capacity];

        for (int i = 0; i < capacity; i++) {
            newHashTable[i] = new LinkedList<>();
        }

        this.size = 0;
    }

    @Override
    public int capacity() {
        // TASK 3: Here’s the easy one. Return the capacity like a proud parent
        // showing off their child's height chart.
        return computeCapacity();
    }

    @Override
    public int size() {
        // TASK 4: How big is your hash table? This method knows.
        // Return the size like you're counting jellybeans in a jar — except the
        // jar is your data structure.
        return size;
    }

    @Override
    public double loadFactor() {
        // TASK 5: Calculate the load factor like you're a weather forecaster,
        // predicting the chance of resizing.
        // Hint: if it’s above 0.75, it’s going to be a resize storm!
        return (double) size / computeCapacity();
    }

    public void enlarge() {
        n++;
        int newCapacity = computeCapacity();

        LinkedList<E>[] newTable = new LinkedList[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newTable[i] = new LinkedList<>();
        }

        for (LinkedList<E> bucket : newHashTable) {
            if (bucket != null) {
                for (E element : bucket) {
                    int newIndex = Math.abs(element.hashCode()) % newCapacity;
                    newTable[newIndex].add(element);
                }
            }
        }

        newHashTable = newTable;

    }

    @Override
    public boolean add(E e) {
        // TASK 6: Add an element to the hash table. If things get too crowded
        // (load factor > 0.75), sound the alarm and call `enlarge()`.
        // Think of it like adding chairs to a party—don't let guests (elements)
        // stand!

        if (loadFactor() > 0.75) {
            enlarge(); 
        }

        int index = Math.abs(e.hashCode()) % newHashTable.length;

        if (newHashTable[index] == null) {
            newHashTable[index] = new LinkedList<>();
        }

        if (newHashTable[index].contains(e)) {
            return false;
        }

        newHashTable[index].add(e);
        size++;

        return true;
    }

    @Override
    public boolean remove(E e) {
        // TASK 7: Attempt to remove an element. If it’s not there, shrug and
        // return false. If you find it, remove it like a piece of old code that
        // no longer sparks joy.
        int index = Math.abs(e.hashCode()) % newHashTable.length;

        if (newHashTable[index] == null) {
            return false;
        }

        if (newHashTable[index].contains(e)) {
            newHashTable[index].remove(e);
            size--;
            return true;
        }

        return false;
    }

    @Override
    public boolean contains(E e) {
        // TASK 8: Is your element in the table? Find out! If it's there,
        // celebrate with a triumphant return `true`. If not, return `false`
        // like a disappointed treasure hunter.
        int index = Math.abs(e.hashCode()) % newHashTable.length;

        if (newHashTable[index] == null) {
            return false;
        }

        if (newHashTable[index].contains(e)) {
            return true;
        }

        return false;
    }

    @Override
    public E get(E e) {
        // TASK 9: Retrieve the element from your hash table—like searching for
        // a needle in a haystack, if your haystack had neat little slots. If
        // it's there, return it with a flourish. If not, return `null` with a
        // sigh.
        int index = Math.abs(e.hashCode()) % newHashTable.length;

        if (newHashTable[index] != null) {
            for (E element : newHashTable[index]) {
                if (e.equals(element)) {
                    return element;
                }
            }
        }

        return null;
    }

    @Override
    public Iterator<E> iterator() {
        // TASK 10: Time for some iteration magic. Hand back an iterator that
        // will bravely traverse your hash table, item by item.
        return new Iterator<E>() {
            private int bucketIndex = 0;
            private Iterator<E> listIterator = null;

            {
                while (bucketIndex < newHashTable.length && newHashTable[bucketIndex].isEmpty()) {
                    bucketIndex++;
                }
                if (bucketIndex < newHashTable.length) {
                    listIterator = newHashTable[bucketIndex].iterator();
                }
            }

            @Override
            public boolean hasNext() {
                if (listIterator != null && listIterator.hasNext()) {
                    return true;
                }

                bucketIndex++;
                while (bucketIndex < newHashTable.length) {
                    if (!newHashTable[bucketIndex].isEmpty()) {
                        listIterator = newHashTable[bucketIndex].iterator();
                        return listIterator.hasNext();
                    }
                    bucketIndex++;
                }

                return false;
            }

            @Override
            public E next() {
                if (listIterator == null || !listIterator.hasNext()) {
                    
                }
                return listIterator.next();
            }
        };
    }

    /**
     * Computes the capacity of the hash table as 2^n - 1, where n is an integer
     * representing the growth factor of the table.
     * 
     * This method serves several key purposes:
     * 
     * 1. **Prime-Like Capacity**: By setting the capacity to 2^n - 1, the table
     * avoids even capacities, reducing the likelihood of hash collisions and
     * improving the distribution of elements across the buckets.
     * 
     * 2. **Controlled Resizing**: `computeCapacity` is used during resizing when
     * the load factor exceeds the threshold (0.75). Each time `n` increments,
     * the capacity approximately doubles, balancing memory usage and
     * performance by keeping the load factor within an optimal range.
     * 
     * 3. **Improved Hashing**: The computed capacity is used as the modulus in the
     * hash function, helping to distribute elements more evenly. Using a
     * non-even divisor reduces clustering in buckets, especially with keys
     * that have sequential or similar hash codes.
     * 
     * 4. **Dynamic Growth**: Setting the capacity to powers of two minus one
     * allows for efficient, consistent doubling of capacity, ensuring the
     * hash table scales smoothly with the number of elements added.
     * 
     * @return the computed capacity as 2^n - 1
     */
    private int computeCapacity() {
        return (int) (Math.pow(2, n) - 1);
    }

}