
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucas Robertson
 * @version Program 5
 */
public class HashTableSCTests {

    public HashTableSCTests() {
    }

    @Test
    public void testCollisions() {
        System.out.println("Testing collisions...");
        HashTableSC instance = new HashTableSC(10);
        long expResult = 4;

        for (int i = 0; i < 15; i++) {
            instance.add(i);
        }

        Object result = instance.collisions();

        //instance._print();
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class HashTableSC.
     */
    @Test
    public void testAdd() {
        System.out.println("Testing add()...");
        String element = "john";
        HashTableSC<String> instance = new HashTableSC<>(100);
        boolean expResult = true;
        boolean result = instance.add(element);
        assertEquals(expResult, result);

        expResult = false;
        result = instance.add(element);
        assertEquals(expResult, result);
    }

    /**
     * Test of contains method, of class HashTableSC.
     */
    @Test
    public void testContains() {
        System.out.println("Testing contains()...");
        Object element = "john";
        HashTableSC instance = new HashTableSC(100);
        boolean expResult = false;
        boolean result = instance.contains("john");
        assertEquals(expResult, result);

        expResult = true;
        instance.add("john");
        result = instance.contains("john");
        assertEquals(expResult, result);

        instance.add("phil");
        instance.add("paul");
        instance.add("luke");
        instance.add("kristen");
        instance.add("sally");

        result = instance.contains("luke")
                && instance.contains("sally")
                && instance.contains("paul")
                && !instance.contains("mike");

        assertTrue(result);

        instance = new HashTableSC(200);
        for (int i = 0; i < 100; i++) {
            instance.add(i);
        }

        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            int value = r.nextInt(300);
            expResult = value < 100;
            result = instance.contains(value);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of isEmpty method, of class HashTableSC.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("Testing isEmpty()...");
        HashTableSC instance = new HashTableSC(100);
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);

        instance.add("3");
        instance.add("test");
        expResult = false;
        result = instance.isEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Test of loadFactor method, of class HashTableSC.
     */
    @Test
    public void testLoadFactor() {
        System.out.println("Testing loadFactor()...");
        HashTableSC instance = new HashTableSC(100);
        double expResult = 0.0;
        double result = instance.loadFactor();
        assertEquals(expResult, result, 0.0);

        for (int i = 0; i < 50; i++) {
            instance.add(i);
        }
        expResult = 0.495;
        result = instance.loadFactor();
        assertEquals(expResult, result, 0.01);

        for (int i = 0; i < 25; i++) {
            instance.remove(i);
        }

        expResult = 0.2475;
        result = instance.loadFactor();
        assertEquals(expResult, result, 0.01);
    }

    /**
     * Test of remove method, of class HashTableSC.
     */
    @Test
    public void testRemove() {
        System.out.println("Testing remove()...");
        Object element = "sam";
        HashTableSC instance = new HashTableSC(5);
        boolean expResult = false;
        boolean result = instance.remove(element);
        assertEquals(expResult, result);

        instance.add("jacob");
        instance.add("julie");
        instance.add("sarah");
        instance.add("sam");

        //instance._print();
        expResult = true;
        result = instance.remove(element);
        assertEquals(expResult, result);

        //instance._print();
        expResult = false;
        result = instance.remove(element);
        assertEquals(expResult, result);
    }

    /**
     * Test of size method, of class HashTableSC.
     */
    @Test(timeout = 1000)
    public void testSize() {
        System.out.println("Testing size()...");
        HashTableSC instance = new HashTableSC(100);
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);

        for (int i = 0; i < 5; i++) {
            instance.add(i);
        }
        expResult = 5;
        result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of tableSize method, of class HashTableSC.
     */
    @Test
    public void testTableSize() {
        System.out.println("Testing tableSize()...");
        HashTableSC instance = new HashTableSC(50);
        int expResult = 53;
        int result = instance.tableSize();
        assertEquals(expResult, result);

    }

}
