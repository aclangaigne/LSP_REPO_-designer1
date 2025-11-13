package org.howard.edu.lsp.assignment6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for IntegerSet.
 */
class IntegerSetTest {
    private IntegerSet a;
    private IntegerSet b;

    @BeforeEach
    void setup() {
        a = new IntegerSet();
        b = new IntegerSet();
        a.add(1); a.add(2); a.add(3);
        b.add(3); b.add(4); b.add(5);
    }

    @Test
    void testClearLengthIsEmpty() {
        assertEquals(3, a.length());
        assertFalse(a.isEmpty());
        a.clear();
        assertTrue(a.isEmpty());
        assertEquals(0, a.length());
    }

    @Test
    void testEquals() {
        IntegerSet c = new IntegerSet();
        c.add(3); c.add(2); c.add(1);
        assertTrue(a.equals(c));
        c.add(99);
        assertFalse(a.equals(c));
    }

    @Test
    void testContains() {
        assertTrue(a.contains(2));
        assertFalse(a.contains(99));
    }

    @Test
    void testLargestSmallest() {
        assertEquals(3, a.largest());
        assertEquals(1, a.smallest());
        IntegerSet empty = new IntegerSet();
        assertThrows(IllegalStateException.class, empty::largest);
        assertThrows(IllegalStateException.class, empty::smallest);
    }

    @Test
    void testAddRemove() {
        a.add(2); // duplicate
        assertEquals(3, a.length());
        a.add(99);
        assertTrue(a.contains(99));
        a.remove(99);
        assertFalse(a.contains(99));
    }

    @Test
    void testUnion() {
        a.union(b);
        assertTrue(a.contains(1) && a.contains(2) && a.contains(3)
                && a.contains(4) && a.contains(5));
    }

    @Test
    void testIntersect() {
        a.intersect(b);
        assertEquals(1, a.length());
        assertTrue(a.contains(3));
    }

    @Test
    void testDiff() {
        a.diff(b);
        assertTrue(a.contains(1) && a.contains(2));
        assertFalse(a.contains(3));
    }

    @Test
    void testComplement() {
        a.complement(b);
        assertTrue(a.contains(4) && a.contains(5));
        assertEquals(2, a.length());
    }

    @Test
    void testToString() {
        String s = a.toString();
        assertTrue(s.startsWith("[") && s.endsWith("]"));
    }
}
