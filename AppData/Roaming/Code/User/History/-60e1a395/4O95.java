import java.util.concurrent.Semaphore;

public class ParallelProgram {
    // Семафори для синхронізації потоків
    static Semaphore wSem = new Semaphore(0);
    static Semaphore sSem = new Semaphore(0);

    // Змінні для обчислень
    static int d;
    static int a;

    public static void main(String[] args) {
        // Поток T1
        new Thread(() -> {
            waitForInput("T2,T3,T4 - W(234,1)");
            criticalSection(() -> d = d - 1);
            int[] MDH = calculateMDH();
            criticalSection(() -> a = Math.max(a, findMax(MDH)));
            signal("234,1");
            waitForSignal("234,2");
            criticalSection(() -> a = a - 1);
            int WH = calculateWH();
            System.out.println("W: " + WH);
        }).start();

        // Поток T2
        new Thread(() -> {
            int[] MB = {1, 2, 3}; // Приклад значень MB
            int E = 5; // Приклад значення E
            signal("134,1");
            waitForInput("T1,T3,T4 - W(134,1)");
            criticalSection(() -> d = d - 1);
            int[] MDH = calculateMDH();
            criticalSection(() -> a = Math.max(a, findMax(MDH)));
            signal("134,2");
            waitForSignal("134,2");
            criticalSection(() -> a = a - 1);
            int WH = calculateWH();
            System.out.println("W: " + WH);
        }).start();

        // Поток T3
        new Thread(() -> {
            int C = 2; // Приклад значення C
            int[] MA = {4, 5, 6}; // Приклад значень MA
            signal("124,1");
            waitForInput("T1,T2,T4 - W(124,1)");
            criticalSection(() -> d = d - 1);
            int[] MDH = calculateMDH();
            criticalSection(() -> a = Math.max(a, findMax(MDH)));
            signal("124,2");
            waitForSignal("124,2");
            criticalSection(() -> a = a - 1);
            int WH = calculateWH();
            waitForSignal("124,3");
            System.out.println("W: " + WH);
        }).start();

        // Поток T4
        new Thread(() -> {
            int[] MD = {7, 8, 9}; // Приклад значень MD
            signal("123,1");
            waitForInput("T1,T2,T3 - W(123,1)");
            criticalSection(() -> d = d - 1);
            int[] MDH = calculateMDH();
            criticalSection(() -> a = Math.max(a, findMax(MDH)));
            signal("123,2");
            waitForSignal("123,2");
            criticalSection(() -> a = a - 1);
            int WH = calculateWH();
            signal("3,3");
        }).start();
    }

    // Метод для очікування на введення даних в потоках
    static void waitForInput(String message) {
        System.out.println("Waiting for input in threads " + message);
        // Чекаємо на введення
    }

    // Метод для критичної ділянки
    static void criticalSection(Runnable code) {
        synchronized (ParallelProgram.class) {
            code.run();
        }
    }

    // Метод для обчислення MDH
    static int[] calculateMDH() {
        // Реалізація обчислень MDH
        return new int[]{1, 2, 3}; // Приклад значень MDH
    }

    // Метод для пошуку максимального значення в масиві
    static int findMax(int[] array) {
        int max = array[0];
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    // Метод для сигналізації потокам
    static void signal(String message) {
        System.out.println("Signal to threads " + message);
        wSem.release();
    }

    // Метод для очікування сигналу від потоків
    static void waitForSignal(String message) {
        System.out.println("Waiting for signal from threads " + message);
        try {
            wSem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Метод для обчислення WH
    static int calculateWH() {
        // Реалізація обчислень WH
        return 123; // Приклад значення WH
    }
}