package lesson_1;

import java.util.Arrays;

public class Task1<T> {

    private T[] array;

    public Task1(T[] array) {
        this.array = array.clone();
    }

    public T[] getArray() {
        return array;
    }

    public void changeArrayItems(int i, int j) {
        int lengthArray = array.length;

        if (i >= lengthArray || j >= lengthArray) {
            System.out.println("Неверно переданы индексы");
        } else {
            T tempElement = array[i];
            array[i] = array[j];
            array[j] = tempElement;
        }
    }
}

class TestTask1 {
    public static void main(String[] args) {

        String[] arrString = new String[]{"1", "2", "3", "4", "5"};
        Task1<String> taskString = new Task1<>(arrString);

        Integer[] arrInt = new Integer[]{1, 2, 3, 4, 5};
        Task1<Integer> taskInt = new Task1<>(arrInt);

        taskString.changeArrayItems(0, 4);
        taskInt.changeArrayItems(1, 3);

        System.out.println(Arrays.toString(taskString.getArray()));
        System.out.println(Arrays.toString(taskInt.getArray()));
    }
}
