package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A mutable mathematical set of integers backed by an ArrayList.
 * No duplicates are permitted. All mutators modify this instance.
 */
public class IntegerSet {
    private List<Integer> set = new ArrayList<Integer>();

    /** Clears the internal representation of the set. */
    public void clear() {
        set.clear();
    }

    /** Returns the number of elements in the set. */
    public int length() {
        return set.size();
    }

    /**
     * Returns true if the 2 sets contain the same elements (order-independent).
     * This overrides Object.equals.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerSet)) return false;
        IntegerSet other = (IntegerSet) o;
        return this.set.size() == other.set.size()
                && this.set.containsAll(other.set)
                && other.set.containsAll(this.set);
    }

    /** Returns true if the set contains the value, otherwise false. */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /** Returns the largest item in the set (throws IllegalStateException if empty). */
    public int largest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        return Collections.max(set);
    }

    /** Returns the smallest item in the set (throws IllegalStateException if empty). */
    public int smallest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        return Collections.min(set);
    }

    /** Adds an item to the set or does nothing if already present. */
    public void add(int item) {
        if (!set.contains(item)) {
            set.add(item);
        }
    }

    /** Removes an item from the set or does nothing if not there. */
    public void remove(int item) {
        set.remove(Integer.valueOf(item));
    }

    /** Set union: modifies this to contain all unique elements in this or other. */
    public void union(IntegerSet other) {
        for (Integer v : other.set) {
            if (!this.set.contains(v)) {
                this.set.add(v);
            }
        }
    }

    /** Set intersection: modifies this to contain only elements in both sets. */
    public void intersect(IntegerSet other) {
        this.set.retainAll(other.set);
    }

    /** Set difference (this \ other): removes elements found in other from this. */
    public void diff(IntegerSet other) {
        this.set.removeAll(other.set);
    }

    /** Set complement: modifies this to become (other \ this). */
    public void complement(IntegerSet other) {
        List<Integer> result = new ArrayList<>(other.set);
        result.removeAll(this.set);
        this.set.clear();
        this.set.addAll(result);
    }

    /** Returns true if the set is empty, false otherwise. */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /** Returns a String representation like [1, 2, 3]; overrides Object.toString(). */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < set.size(); i++) {
            sb.append(set.get(i));
            if (i < set.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
