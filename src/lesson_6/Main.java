package lesson_6;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[] arr = new int[]{5, 2, 2, 3, 2, 3, 6, 8, 7};
        Main main = new Main();
        int[] newArr = main.newArray(arr);
        System.out.println(Arrays.toString(newArr));
        System.out.println(main.isArrayContain4Or1(arr));
    }

    public int[] newArray(int[] array) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) {
                index = i;
            }
        }

        if (index >= 0) {
            int sizeNewArray = array.length - index - 1;
            int[] newArray = new int[sizeNewArray];
            for (int i = 0; i < sizeNewArray; i++) {
                newArray[i] = array[index + 1 + i];
            }
            return newArray;
        } else {
            throw new RuntimeException("Нет ни одного элемента 4");
        }
    }

    public boolean isArrayContain4Or1 (int[] array) {

        boolean isContain = false;
        for (int item : array) {
            if (item == 1 || item == 4) {
                isContain = true;
                break;
            }
        }
        return isContain;
    }
}
