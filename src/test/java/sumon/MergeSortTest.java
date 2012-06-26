package sumon;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author exthulja
 */
public class MergeSortTest {

    List<Integer> list = Arrays.asList(1, 2, 1, 1, 2, 3, 4, 5, -1, 9999);

    MergeSort sort = new MergeSort();

    @Test
    public void testLeftSublist() {
        assertEquals(Arrays.asList(1, 2, 1, 1, 2), sort.leftSublist(list));
    }

    @Test
    public void testRightSublist() {
        assertEquals(Arrays.asList(3, 4, 5, -1, 9999), sort.rightSublist(list));
    }

    @Test
    public void testMerge() {
        assertEquals(Arrays.asList(1, 1, 2), sort.merge(Arrays.asList(1, 2), Arrays.asList(1)));
    }

    @Test
    public void testSimpleSort() {
        assertEquals(Arrays.asList(1, 1, 2), sort.sort(Arrays.asList(2, 1, 1)));
    }

    @Test
    public void testInverseSort() {
        assertEquals(Arrays.asList(-1, 1, 2), sort.sort(Arrays.asList(2, 1, -1)));
    }

    @Test
    public void testSingleSort() {
        assertEquals(Arrays.asList(1), sort.sort(Arrays.asList(1)));
    }

}
