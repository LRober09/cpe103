/**
 * JUnit tests for Quadratic sorts lab assignment.
 *
 * @author Paul Hatalsky
 * @version 2/18/2016 Developed for CPE 103 Lab 10 
 */
import static org.junit.Assert.*;
import org.junit.*;
import java.lang.reflect.*;
import java.util.Random;
import java.util.Arrays;
import org.junit.runners.MethodSorters;
import org.junit.rules.*;
import org.junit.runner.Description;
import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShellSortsSampleTests
{
   @Test
   public void test01_shell_sort() 
   {
      Integer[] array = new Integer[] {10, 5};
      ShellSorts.shell(array);
      assertEquals(array[0], new Integer(5));
      assertEquals(array[1], new Integer(10));
   }
   @Test
   public void test02_hibbard_sort() 
   {
      String[] array = new String[] {"World", "Hello"};
      ShellSorts.hibbard(array);
      assertEquals(array[0], "Hello");
      assertEquals(array[1], "World");
   }
   @Test
   public void test03_sedgewick_sort() 
   {
      Integer[] array = new Integer[] {10, 5};
      ShellSorts.sedgewick(array);
      assertEquals(array[0], new Integer(5));
      assertEquals(array[1], new Integer(10));
   }
   @Test
   public void test04_heapSort_sort() 
   {
      Integer[] array = new Integer[] {10, 5};
      ShellSorts.heapSort(array);
      assertEquals(array[0], new Integer(5));
      assertEquals(array[1], new Integer(10));
   }
} 