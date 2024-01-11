import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.CountDownLatch;

class Thread_1 extends Thread {
   public int P = 4;
   public int H1 = Main.N/P;
   public int p1;
   public int d1;
   public static int a1 = 0;
   public static int [] E = new int[Main.N];
   public static int[][] MZ = new int[Main.N][Main.N];
   public static int[][] MZMR= new int[Main.N][Main.N];
   static CyclicBarrier barrier;
   static CountDownLatch L;
   static Semaphore sem1;
   static Semaphore sem2;
   public void run() {
       System.out.println("Thread 1 is started!"); // Початок першого потоку

       for(int i = 0; i < Main.N; i++){
           for(int j = 0; j < Main.N; j++){
               MZ[i][j] = 1;
           }
       }
       for(int i = 0; i < Main.N;i++){
           E[i] = 1;
       }

       try {
           barrier.await(); // Чекаємо інші потоки
       } catch (Exception e) {
           e.printStackTrace();
       }

       //Копіювання скалярів

       try {
           sem2.acquire(); //Використання семафора для копіювання скаляру
           //Копіювання p у p1
           p1 = Thread_2.p; кд
       } catch (Exception e) {
           e.printStackTrace();
       }
       sem2.release();
       synchronized (Main.lock) {
           // Копіювання значення змінної d у змінну d1
           d1 = Thread_4.d;  КД2

       }

       //Обчислення скалярного добутку
       for(int i = 0; i < H1; i++){
           a1 += Thread_3.B[i]*Thread_4.X[i];
       }
       Main.a.addAndGet(a1);

       L.countDown();
       try {
           L.await();
       } catch (Exception e) {
           e.printStackTrace();
       }

       synchronized (Main.lock1) {
           // Копіювання значення змінної a у змінну a1
           a1 = Main.a.get();

       }
       System.out.println(a1);
       for (var i = 0; i < H1; i++) {
           for (var j = 0; j < Main.N; j++) {
               MZMR[i][j] = 0;
               for (var k = 0; k < Main.N; k++) {
                   MZMR[i][j] += MZ[i][k] * Thread_3.MR[k][j];
               }
           }
       }
       int [] DEH = new int[Main.N];
       // Обчислення d*Eh
       for(int i = 0;i<H1;i++){
           DEH[i] = E[i]*Thread_4.d;
       }
       int [] res1 = new int[H1];
       for (int i = 0; i < H1; i++) {
           int sum = 0;
           for (int j = 0; j < Main.N; j++) {
               sum += MZMR[i][j] * Main.R[j];
           }
           res1[i] = sum;
       }
       int [] res2 = new int[H1];
       // Обчислення результату
       for(int i = 0;i<H1;i++){
           res2[i] = res1[i] + DEH[i];
       }
       for(int i = 0;i<H1;i++){
           Thread_2.Z[i] = res2[i]*a1*d1;
       }


       System.out.println("Thread 1 is finished!"); //Кінець першого потоку
       sem1.release();

   }
}

class Thread_2 extends Thread {
   static CyclicBarrier barrier;
   public static int [] Z = new int[Main.N];
   static CountDownLatch L;
   static Semaphore sem1;
   static Semaphore sem2;
   public static int [] R = new int[Main.N];
   public static int a2;
   public static int p;
   public static int d2;
   public int p2;
   public int P = 4;
   public int H2 = Main.N/P;

   public void run() {
       System.out.println("Thread 2 is started!"); //Початок першого потоку
       for(int i = 0; i < Main.N; i++){
           Main.R[i] = 1;
       }
       p = 5;
       try {
           barrier.await(); // Чекаємо інші потоки
       } catch (Exception e) {
           e.printStackTrace();
       }

       //Копіювання скалярів

       try {
           sem2.acquire();
           //Копіювання p у p2
           p2 = Thread_2.p;
       } catch (Exception e) {
           e.printStackTrace();
       }
       sem2.release();
       synchronized (Main.lock) {
           // Копіювання значення змінної d у змінну d2
           d2 = Thread_4.d;

       }
       // Обчислення скалярного добутку
       for(int i = H2; i < 2*H2; i++){
           a2 += Thread_3.B[i]*Thread_4.X[i];
       }
       Main.a.addAndGet(a2);

       L.countDown();
       try {
           L.await();
       } catch (Exception e) {
           e.printStackTrace();
       }

       synchronized (Main.lock1) {
           // Копіювання значення змінної a у змінну a2
           a2 = Main.a.get();

       }
       //Множення матриць
       for (int i = H2; i < 2*H2; i++) {
           for (int j = 0; j < Main.N; j++) {
               Thread_1.MZMR[i][j] = 0;
               for (var k = 0; k < Main.N; k++) {
                   Thread_1.MZMR[i][j] += Thread_1.MZ[i][k] * Thread_3.MR[k][j];
               }
           }
       }
       int [] DEH = new int[Main.N];
       // Обчислення d*Eh
       for(int i = H2;i<2*H2;i++){
           DEH[i] = Thread_1.E[i]*Thread_4.d;
       }
       int [] res1 = new int[Main.N];
       for (int i = H2; i < 2*H2; i++) {
           int sum = 0;
           for (int j = 0; j < Main.N; j++) {
               sum += Thread_1.MZMR[i][j] * Main.R[j];
           }
           res1[i] = sum;
       }

       int [] res2 = new int[Main.N];

       for(int i = H2;i<2*H2;i++){
           res2[i] = res1[i] + DEH[i];
       }
       for(int i = H2;i<2*H2;i++){
           Thread_2.Z[i] = res2[i]*a2*d2;
       }
       try {
           sem1.acquire(3); // Чекаємо інші потоки
       } catch (Exception e) {
           e.printStackTrace();
       }
       for(int i = 0;i < Main.N;i++){
           System.out.println("Z"+ i +" ="+ Z[i]);
       }
       System.out.println("Thread 2 is finished!"); // Кінець другого потоку

   }
}

// ПОТІК 3!
class Thread_3 extends Thread {
   static CyclicBarrier barrier;
   static CountDownLatch L;
   static Semaphore sem1;
   static Semaphore sem2;
   public static int d3;
   public int p3;
   public static int a3;
   public static int [] B = new int[Main.N];
   public static int[][] MR = new int[Main.N][Main.N];

   public int P = 4;
   public int H2 = Main.N/P;
   public void run() {
       System.out.println("Thread 3 is started!"); //Початок третього потоку
       for(int i = 0; i < Main.N; i++){
           for(int j = 0; j < Main.N; j++){
               MR[i][j] = 2;
           }
       }
       for(int i = 0; i < Main.N;i++){
           B[i] = 1;
       }
       try {
           barrier.await(); // Чекаємо інші потоки
       } catch (Exception e) {
           e.printStackTrace();
       }
       // Копіювання скалярів
       try {
           sem2.acquire();
           //Копіювання p у p3
           p3 = Thread_2.p;
       } catch (Exception e) {
           e.printStackTrace();
       }
       sem2.release();
       synchronized (Main.lock) {
           // Копіювання значення змінної d у змінну d3
           d3 = Thread_4.d;

       }
       //Обчислення скалярного добутку
       for(int i = 2*H2; i < 3*H2; i++){
           a3 += Thread_3.B[i]*Thread_4.X[i];
       }
       Main.a.addAndGet(a3);
       L.countDown();
       try {
           L.await();
       } catch (Exception e) {
           e.printStackTrace();
       }
       synchronized (Main.lock1) {
           // Копіювання значення змінної a у змінну a3
           a3 = Main.a.get();

       }
       //Множення матриць
       for (int i = 2*H2; i < 3*H2; i++) {
           for (int j = 0; j < Main.N; j++) {
               Thread_1.MZMR[i][j] = 0;
               for (var k = 0; k < Main.N; k++) {
                   Thread_1.MZMR[i][j] += Thread_1.MZ[i][k] * Thread_3.MR[k][j];
               }
           }
       }
       int [] DEH = new int[Main.N];
       int [] res1 = new int[Main.N];
       // Обчислення d*Eh
       for(int i = 0;i<H2;i++){
           DEH[i] = Thread_1.E[i]*Thread_4.d;
       }
       for (int i = 2*H2; i < 3*H2; i++) {
           int sum = 0;
           for (int j = 0; j < Main.N; j++) {
               sum += Thread_1.MZMR[i][j] * Main.R[j];
           }
           res1[i] = sum;
       }
       int [] res2 = new int[Main.N];

       for(int i = 2*H2;i<3*H2;i++){
           res2[i] = res1[i] + DEH[i];
       }
       for(int i = 2*H2;i<3*H2;i++){
           Thread_2.Z[i] = res2[i]*a3*d3;
       }


       System.out.println("Thread 3 is finished!"); //Кінець третього потоку
       sem1.release();
   }
}

// Потік 4!

class Thread_4 extends Thread {
   static CyclicBarrier barrier;
   static CountDownLatch L;
   static Semaphore sem1;
   static Semaphore sem2;
   public int p4;

   public int P = 4;
   public static int a4;
   public static int [] X = new int[Main.N];
   public static int d;
   public static int d4;
   public int H2 = Main.N/P;
   public void run() {
       System.out.println("Thread 4 is started!"); //Початок четвертого потоку
       for(int i = 0; i < Main.N;i++){
           X[i] = 1;
       }
       d = 2;
       try {
           barrier.await(); // Чекаємо інші потоки
       } catch (Exception e) {
           e.printStackTrace();
       }
       //Копіювання скалярів
       try {
           sem2.acquire();
           //Копіювання p у p4
           p4 = Thread_2.p;
       } catch (Exception e) {
           e.printStackTrace();
       }
       sem2.release();
       synchronized (Main.lock) {
           // Копіювання значення змінної d у змінну d2
           d4 = d;

       }
       //Обчислення скалярного добутку
       for(int i = 3*H2; i < Main.N; i++){
           a4 += Thread_3.B[i]*Thread_4.X[i];
       }
       Main.a.addAndGet(a4);
       L.countDown();
       try {
           L.await();
       } catch (Exception e) {
           e.printStackTrace();
       }
       synchronized (Main.lock1) {
           // Копіювання значення змінної a у змінну d4
           a4 = Main.a.get();

       }
       for (int i = 3*H2; i < Main.N; i++) {
           for (int j = 0; j < Main.N; j++) {
               Thread_1.MZMR[i][j] = 0;
               for (var k = 0; k < Main.N; k++) {
                   Thread_1.MZMR[i][j] += Thread_1.MZ[i][k] * Thread_3.MR[k][j];
               }
           }
       }
       int [] DEH = new int[Main.N];
       int [] res1 = new int[Main.N];
       // Обчислення d*Eh
       for(int i = 3*H2;i<Main.N;i++){
           DEH[i] = Thread_1.E[i]*Thread_4.d;
       }
       for (int i = 3*H2; i < Main.N; i++) {
           int sum = 0;
           for (int j = 0; j < Main.N; j++) {
               sum += Thread_1.MZMR[i][j] * Main.R[j];
           }
           res1[i] = sum;
       }
       int [] res2 = new int[Main.N];

       for(int i = 3*H2;i<Main.N;i++){
           res2[i] = res1[i] + DEH[i];
       }
       for(int i = 3*H2;i<Main.N;i++){
           Thread_2.Z[i] = res2[i]*a4*d4;
       }

       System.out.println("Thread 4 is finished!"); //Кінець четвертого потоку
       sem1.release();
   }
}
public class Main {
   public static final Object lock = new Object();
   public static final Object lock1 = new Object();
   public static int N = 3000;
   public static AtomicInteger a = new AtomicInteger(0);
   public static int [] R = new int[N];
   public static void main(String[] args) {
       Semaphore S1 = new Semaphore(0);
       Semaphore S2 = new Semaphore(1);
       CyclicBarrier B = new CyclicBarrier(4);
       CountDownLatch L = new CountDownLatch(4);

       Thread_1 T1 = new Thread_1();
       Thread_2 T2 = new Thread_2();
       Thread_3 T3 = new Thread_3();
       Thread_4 T4 = new Thread_4();
       Thread_1.barrier = B;
       Thread_2.barrier = B;
       Thread_3.barrier = B;
       Thread_4.barrier = B;
       Thread_1.sem1 = S1;
       Thread_2.sem1 = S1;
       Thread_3.sem1 = S1;
       Thread_4.sem1 = S1;
       Thread_1.sem2 = S2;
       Thread_2.sem2 = S2;
       Thread_3.sem2 = S2;
       Thread_4.sem2 = S2;
       Thread_1.L = L;
       Thread_2.L = L;
       Thread_3.L = L;
       Thread_4.L = L;



       T1.start();
       T2.start();
       T3.start();
       T4.start();


   }
}
