package lesson_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task2<T> {

    private T[] array;

    public Task2(T[] array) {
        this.array = array.clone();
    }

    public List<T> toArrayList(){
        return new ArrayList<>(Arrays.asList(array));
    }
}

class TestTask2 {

    public static void main(String[] args) {

        String[] arrString = new String[]{"A","B","C","D"};
        Task2<String> taskString = new Task2<>(arrString);

        System.out.println(taskString.toArrayList());
    }
}
