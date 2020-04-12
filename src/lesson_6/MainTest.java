package lesson_6;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MainTest {

    private final int[] newArray;
    private final int[] oldArray;
    private Main main;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {new int[]{3, 7}, new int[]{1, 2, 4, 4, 2, 3, 4, 3, 7}},
            {new int[]{2, 3, 5, 3, 7}, new int[]{1, 2, 4, 4, 2, 3, 5, 3, 7}},
            {new int[]{2, 5, 9, 2, 3, 8, 3, 7}, new int[]{4, 2, 5, 9, 2, 3, 8, 3, 7}},
            {new int[]{}, new int[]{4, 2, 4, 4, 2, 3, 4, 1, 4}}
        });
    }

    public MainTest(int[] newArray, int[] oldArray) {
        this.newArray = newArray;
        this.oldArray = oldArray;
    }

    @Before
    public void startTest() {
        main = new Main();
    }

    @Test
    public void testNewArray() {
        Assert.assertArrayEquals(newArray, main.newArray(oldArray));
    }

    @Test (expected = RuntimeException.class)
    public void testNewArrayException() {
        int[] array = new int[]{1, 2, 2, 3, 5, 1, 7};
        main.newArray(array);
    }

    @Test
    public void testIsArrayContain4Or1True() {
        Assert.assertTrue(main.isArrayContain4Or1(oldArray));
    }

    @Test
    public void testIsArrayContain4Or1False() {
        Assert.assertFalse(main.isArrayContain4Or1(newArray));
    }
}
