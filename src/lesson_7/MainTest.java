package lesson_7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MainTest {

    public static void main(String[] args) {
        start(TestExample.class);
    }

    public static void start(Class<?> clazz) {
        try {
            Object instance = clazz.newInstance();
            Method[] methods = clazz.getMethods();

            executeBeforeSuite(instance, methods);
            executeTests(instance, methods);
            executeAfterSuite(instance, methods);

        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void executeAfterSuite(Object instance, Method[] methods) throws IllegalAccessException, InvocationTargetException {
        for (Method method : methods) {
            if (method.getAnnotation(AfterSuite.class) != null) {
                method.invoke(instance);
            }
        }
    }

    private static void executeTests(Object instance, Method[] methods) throws IllegalAccessException, InvocationTargetException {
        List<Method> testMethods = new ArrayList<>();
        for (Method method : methods) {
            if (method.getAnnotation(Test.class) != null) {
                testMethods.add(method);
            }
        }
        testMethods.sort(MainTest::sortMethods);
        for (Method method : testMethods) {
            method.invoke(instance);
        }
    }

    private static int sortMethods(Method m1, Method m2) {
        int priorityM1 = m1.getAnnotation(Test.class).priority().getPriority();
        int priorityM2 = m2.getAnnotation(Test.class).priority().getPriority();
        if (priorityM1 == priorityM2) {
            return 0;
        }
        return priorityM1 < priorityM2 ? -1 : 1;
    }

    private static void executeBeforeSuite(Object instance, Method[] methods) throws IllegalAccessException, InvocationTargetException {
        int countBeforeSuite = 0;
        Method beforeSuite = null;
        for (Method method : methods) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                if (countBeforeSuite == 0) {
                    countBeforeSuite++;
                    beforeSuite = method;
                } else {
                    throw new RuntimeException("Method with annotation BeforeSite must be one");
                }
            }
        }

        if(beforeSuite != null) {
            beforeSuite.invoke(instance);
        }
    }
}
