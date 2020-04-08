package lesson_4;

public class Task1 {

    private static final Object object = new Object();
    private volatile char curLetter = 'A';

    public static void main(String[] args) {

        Task1 t = new Task1();
        Thread tA = new Thread(t::printLetterA);
        Thread tB = new Thread(t::printLetterB);
        Thread tC = new Thread(t::printLetterC);
        tA.start();
        tB.start();
        tC.start();
    }

    private void printLetterA() {
        synchronized (object) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (curLetter != 'A') {
                        object.wait();
                    }
                    System.out.println("A");
                    curLetter = 'B';
                    object.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printLetterB() {
        synchronized(object) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (curLetter != 'B') {
                        object.wait();
                    }
                    System.out.println("B");
                    curLetter = 'C';
                    object.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printLetterC() {
        synchronized (object) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (curLetter != 'C') {
                        object.wait();
                    }
                    System.out.println("C");
                    curLetter = 'A';
                    object.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
