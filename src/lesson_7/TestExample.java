package lesson_7;

public class TestExample {

    @BeforeSuite
    public void begin() {
        System.out.println("BEFORE_SUITE");
    }

    @Test(priority = Priority.TWO)
    public void test1() {
        System.out.println("Test 1");
    }

    @Test(priority = Priority.ONE)
    public void test2() {
        System.out.println("Test 2");
    }

    @Test(priority = Priority.FOUR)
    public void test3() {
        System.out.println("Test 3");
    }

    @Test(priority = Priority.THREE)
    public void test4() {
        System.out.println("Test 4");
    }

    @Test()
    public void test5() {
        System.out.println("Test 5");
    }

    @Test(priority = Priority.SIX)
    public void test6() {
        System.out.println("Test 6");
    }

    @Test(priority = Priority.SEVEN)
    public void test7() {
        System.out.println("Test 7");
    }

    @Test(priority = Priority.EIGHT)
    public void test8() {
        System.out.println("Test 8");
    }

    @Test(priority = Priority.NINE)
    public void test9() {
        System.out.println("Test 9");
    }

    @Test(priority = Priority.TEN)
    public void test10() {
        System.out.println("Test 10");
    }

    @AfterSuite
    public void end() {
        System.out.println("AFTER_SUITE");
    }
}
