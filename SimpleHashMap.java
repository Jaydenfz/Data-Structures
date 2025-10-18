/*
 * Copyright 2023 Marc Libnnratore.
 */
package hashmaps;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;

import hashtables.ChainingHashTable;

/**
 * An implementation of a SimpleMap, built using the ChainingHashTable and
 * SimpleMapEntry classes. This class should behave similarly to the built-in
 * java.util.HashMap, though it is much simpler!
 */
public class SimpleHashMap<K, V> implements SimpleMap<K, V> {

    private ChainingHashTable<SimpleMapEntry<K, V>> newHashTable;
    private int size;

    public SimpleHashMap() {
        // TASK 1: Constructor for SimpleHashMap. Just like any good story, this
        // constructor sets the stage by initializing our ChainingHashTable.
        this.newHashTable = new ChainingHashTable<>();
        this.size = 0;
    }

    @Override
    public int size() {
        // TASK 2: Time to count how many key-value pairs are partying in your
        // SimpleHashMap. Return the current guest count!
        return size;
    }

    @Override
    public void put(K k, V v) {
        // TASK 3: Add a new key-value pair to the map, or update it if the
        // key’s already there. Think of it like updating a name tag at a
        // conference—swap the old value out for the new one!

        SimpleMapEntry<K, V> dummy = new SimpleMapEntry<>(k, null);
        SimpleMapEntry<K, V> existing = newHashTable.get(dummy);
        if (existing != null) {
            newHashTable.remove(existing);
        } else {
            size++;
        }

        SimpleMapEntry<K, V> newEntry = new SimpleMapEntry<>(k, v);
        newHashTable.add(newEntry);

    }

    @Override
    public V get(K k) {
        // TASK 4: Retrieve the value for the given key. If it's not there,
        // return `null` like a disappointed parent searching for their missing
        // car keys.
        SimpleMapEntry<K, V> entry = new SimpleMapEntry<>(k, null);
        SimpleMapEntry<K, V> foundEntry = newHashTable.get(entry);

        if (foundEntry != null) {
            return foundEntry.v;
        }

        return null;
    }

    @Override
    public V getOrDefault(K k, V defaultValue) {
        // TASK 5: A close cousin to `get()`, but with a safety net! If the key
        // isn't there, return the `defaultValue`. It’s like having a fallback
        // playlist when your favorite song isn’t on Spotify.
        SimpleMapEntry<K, V> entry = new SimpleMapEntry<>(k, null);
        SimpleMapEntry<K, V> foundEntry = newHashTable.get(entry);

        if (foundEntry != null) {
            return foundEntry.v;
        }

        return defaultValue;
    }

    @Override
    public V remove(K k) {
        // TASK 6: Remove the key-value pair from the map like deleting an
        // embarrassing old tweet. Return the value if successful, otherwise
        // return `null`—no harm, no foul.
        SimpleMapEntry<K, V> entry = new SimpleMapEntry<>(k, null);
        SimpleMapEntry<K, V> foundEntry = newHashTable.get(entry);

        if (foundEntry != null) {
            newHashTable.remove(entry);
            size--;
            return foundEntry.v;
        }

        return null;
    }

    @Override
    public Set<K> keys() {
        // TASK 7: Collect all the keys like you're on a treasure hunt. Store
        // them in a Set, because duplicates are for amateurs. Return your
        // shiny key collection!
        if (size == 0) {
            return new HashSet<>(); 
        }
        Set<K> keysSet = new HashSet<>();
        for (SimpleMapEntry<K, V> entry : newHashTable) {
            if (entry != null) {
                keysSet.add(entry.k);
            }
        }
        return keysSet;
    }

}