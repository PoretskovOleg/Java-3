package lesson_1;

public class TestBox {

    public static void main(String[] args) {
        // Создаем коробки для фруктов
        Box<Apple> appleBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        // Создаем яблоки
        Apple apple1 = new Apple(1.0f);
        Apple apple2 = new Apple(1.5f);
        Apple apple3 = new Apple(0.5f);

        // Создам апельсины
        Orange orange1 = new Orange(1.0f);
        Orange orange2 = new Orange(1.2f);
        Orange orange3 = new Orange(0.5f);

        // Добавляем яблоки в коробку
        appleBox.addFruit(apple1);
        appleBox.addFruit(apple2);
        appleBox.addFruit(apple3);

        // Убедимся, что все добавилось
        System.out.println(appleBox.getFruits());

        // Добавляем апельсины в коробку
        orangeBox.addFruit(orange1);
        orangeBox.addFruit(orange2);
        orangeBox.addFruit(orange3);

        // Убедимся, что все добавилось
        System.out.println(orangeBox.getFruits());

        // Посчитаем вес коробок
        System.out.println(appleBox.getWeight());
        System.out.println(orangeBox.getWeight());

        // Сравним вес коробок
        System.out.println(appleBox.compare(orangeBox));

        // Пересыпем яблоки в другую коробку
        Box<Apple> newAppleBox = new Box<>();
        appleBox.moveToBox(newAppleBox);

        // Убедимся, что все пересыпали
        System.out.println(appleBox.getFruits());
        System.out.println(appleBox.getWeight());
        System.out.println(newAppleBox.getFruits());
        System.out.println(newAppleBox.getWeight());
    }
}
