import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Data {
   public static final int n = 4;
   public static final int P = 4;
   public static final int H = n / P;
   //ПВВ1
   public static int[][] MM = new int[n][n];
   public static int[] B = new int[n];
   public static int[][] MX = new int[n][n];
   //ПВВ3
   public static int p;
   //ПВВ4
   public static int[] Z = new int[n];
   public static int[][] MT = new int[n][n];
   public static int d;
   public static int[] A = new int[n];

   public static int[][] MK = new int[n][n];
   public static int[] F = new int[n];
   public static int[] X = new int[n];

   public static AtomicInteger e = new AtomicInteger();

   public static final Object Obj1 = new Object();
   public static final Object Obj2 = new Object();
   public static Semaphore Sem1 = new Semaphore(0, true);
   public static Semaphore Sem2 = new Semaphore(0, true);
   public static Semaphore Sem3 = new Semaphore(0, true);
   public static Semaphore Sem4 = new Semaphore(0, true);
   public static Semaphore Sem5 = new Semaphore(0, true);
   public static Semaphore Sem6 = new Semaphore(0, true);
   public static Semaphore Sem7 = new Semaphore(0, true);
   public static Semaphore Sem8 = new Semaphore(0, true);
   public static CyclicBarrier Bar1 = new CyclicBarrier(4);


   // Скалярний добуток векторів
   public static int DotProd(int[] B, int[] Z, int start, int end) {
       int ei = 0;
       for (int i = start; i < end; i++) {
           ei += B[i] * Z[i];
       }
       return ei;
   }


   public static int[] L = new int[n];

   // Добуток вектора на скаляр
   public static void ScalVecProd(int[] X, int x, int start, int end, int[] R) {
       for (int i = start; i < end; i++) {
           R[i] = X[i] * x;
       }
   }

   // Добуток матриць
   public static void MatrixProd(int[][] MX, int[][] MY, int start, int end, int[][] MR) {
       for (int j = start; j < end; j++) {
           for (int i = 0; i < n; i++) {
               MR[i][j] = 0;
               for (int k = 0; k < n; k++) {
                   MR[i][j] += MX[i][k] * MY[k][j];
               }
           }
       }
   }


   //Добуток ветора на матрицю
   public static void VecMatrixProd(int[] X, int[][] MX, int start, int end, int[] R) {
       for (int j = start; j < end; j++) {
           R[j] = 0;
           for (int i = 0; i < n; i++) {
               R[j] += X[i] * MX[i][j];
           }
       }
   }

   //Сума вектора, помноженого на скаляр, та іншого вектора
   public static void VectorSum(int[] X, int[] Y, int x, int start, int end, int[] R) {
       for (int i = start; i < end; i++) {
               R[i] = x*X[i] + Y[i];
       }
   }

   //Злиття відсортованих частин вектора
   public static void BoseNelsonMerge(int j, int r, int m, int[] X) {
       if (j + r < X.length) {          // якщо індекс другого елемента не виходить за межі початкового масиву
           if (m == 1) {                // якщо кількість елементів у групі рівна 1
               if (X[j] > X[j + r]) {  // виконуємо порівняння
                   int t = X[j + r];
                   X[j + r] = X[j];  // та обмін, якщо елемент той, що стоїть ближче до початку масиву більший
                   X[j] = t;
               }
           }else {                       // якщо кількість елементів у групі більша за 1
                   m = Math.floorDiv(m, 2);   // ділимо кількість ел-тів у групі на 2
                   BoseNelsonMerge(j, r, m, X);  // зливаємо початки
                   if (j + r + m < X.length) {  // зливаємо кінці, якщо індекс елемента не виходить за межі початкового масиву
                       BoseNelsonMerge(j + m, r, m, X);
                       BoseNelsonMerge(j + m, r - m, m, X);  // зливаємо середини
                   }
           }
       }
   }

   public static void CalcX(int thread, int d){
       int start = H*(thread-1);
       int end = start + H;
       //    Обчислення Fн = Z*MMн
       Data.VecMatrixProd(Z, MM, start, end, F);
       //    Обчислення Eн =  d*Bн + Fн
       Data.VectorSum(B, F, d, start, end, X);
       //    Обчислення Xн
       Arrays.sort(X, start, end);
   }

   public static void CalcA(int thread, int p, int e){
       int start = H*(thread-1);
       int end = start + H;
       //    Обчислення Lн = e*Zн
       Data.ScalVecProd(Z, e, start, end, L);
       //    Обчислення МKн = MX* MTн
       Data.MatrixProd(MX, MT, start, end, MK);
       int[] R = new int[H*thread];
       Data.VecMatrixProd(X, MK, start, end, R);
       Data.VectorSum(R, L, p, start, end, A);
   }
}
