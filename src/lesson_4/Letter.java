package lesson_4;

public class Letter {

    private static final int COUNT_LETTER = 5;
    private static final Object lock = new Object();
    private volatile static char currentLetter = 'A';

    private final char letter;
    private final char nextLetter;

    public Letter(char letter, char nextLetter) {
        this.letter = letter;
        this.nextLetter = nextLetter;
    }

    public void print() {
        for (int i = 0; i < COUNT_LETTER; i++) {
            synchronized (lock) {
                while (currentLetter != letter) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(letter);
                currentLetter = nextLetter;
                lock.notifyAll();
            }
        }
    }
}
