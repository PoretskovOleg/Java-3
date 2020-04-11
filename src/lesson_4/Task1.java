package lesson_4;

public class Task1 {

    public static void main(String[] args) {

        Thread tA = new Thread(() -> {
            Letter letterA = new Letter('A', 'B');
            letterA.print();
        });

        Thread tB = new Thread(() -> {
            Letter letterB = new Letter('B', 'C');
            letterB.print();
        });

        Thread tC = new Thread(() -> {
            Letter letterC = new Letter('C', 'A');
            letterC.print();
        });

        tA.start();
        tB.start();
        tC.start();
    }
}
