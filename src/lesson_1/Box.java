package lesson_1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Box<T extends Fruit> {

    private List<T> fruits;

    public Box() {
        fruits = new ArrayList<>();
    }

    public List<T> getFruits() {
        return fruits;
    }

    public void addFruit(T fruit) {
        this.fruits.add(fruit);
    }

    public float getWeight() {
        float weightBox = 0.0f;

        for (T fruit : fruits) {
            weightBox += fruit.getWeight();
        }

        return weightBox;
    }

    public boolean compare(Box<?> anotherBox) {
        return getWeight() == anotherBox.getWeight();
    }

    public void moveToBox(Box<T> anotherBox) {
        Iterator<T> fruitIterator = fruits.iterator();

        while(fruitIterator.hasNext()) {
            T fruit = fruitIterator.next();
            anotherBox.addFruit(fruit);
            fruitIterator.remove();
        }
    }
}
