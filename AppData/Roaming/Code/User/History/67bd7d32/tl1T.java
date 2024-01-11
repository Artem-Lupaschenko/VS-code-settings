import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Test {
		private static final int n = 3000;
    private static final int P = 4;
		private static final int H = 0;

    private static CyclicBarrier barrier = new CyclicBarrier(P);

		// Initialize data (MD, MB, MA, C, E, d)
		public static long[][] MD = new long[n][n];
		public static long[][] MB = new long[n][n];
		public static long[][] MA = new long[n][n];
		public static long[] C = new long[n];
		public static long[] E = new long[n];
		public static long[] W = new long[n];
		public static int d;
		// 4

    public static void main(String[] args) {

				// System.out.println("H " + H);
				// // Main calculations
				// long[] Oh = scalarVectorMultiplication(C, MD);
				// System.out.println("Oh " + Arrays.toString(Oh));
				// int max = findMax(Oh);
				// System.out.println("max(Oh) " + max);
				// long[][] MBMA = matrixMultiplication(MB, MA);
				// System.out.println("MB*MA " + Arrays.deepToString(MBMA));
				// long[] EMBMA = scalarVectorMultiplication(E, MBMA);
				// System.out.println("E*(MB*MA) " + Arrays.toString(EMBMA));
				// long[] EMBMAd = multiplyVectorByScalar(EMBMA, d);
				// System.out.println("E*(MB*MA)*d " + Arrays.toString(EMBMAd));
				// long[] aC = multiplyVectorByScalar(C, max);
				// System.out.println("a*C " + Arrays.toString(aC));
				// long[] aCEMBMAd = vectorAddition(aC, EMBMAd);
				// System.out.println("a*C + E*(MB*MA)*d " + Arrays.toString(aCEMBMAd));

        // Create threads
        Thread t1 = new Thread(() -> T1());

        // Start threads
				long startTime = System.currentTimeMillis();
        t1.start();
				try {
					t1.join();

			} catch (InterruptedException e) {
					// Обробка випадку переривання: виводимо стек виклику в консоль
					e.printStackTrace();
			} finally {
				long endTime = System.currentTimeMillis();
				long executionTime = endTime - startTime;
				System.out.println("Час виконання: " + executionTime + " мс");
			}
    }

    private static void T1() {
			int p = 1;
        try {
						MB = generateMatrix(n, 2);
						E = generateVector(3, n);
						C = generateVector(2, n);
						MA = generateMatrix(n, 4);
						
            // Wait for input in T2, T3, T4
						System.out.println("T1: Data loaded");

            // Critical section - Copying and calculating
						int d1 = d;;
							
							System.out.println("T1: Coping d");


						long O1[] = scalarVectorMultiplication(C, MD, H*(p-1), H*p);
						// System.out.println("O1 = " + Arrays.toString(O1));
						long a1 = findMax(O1);
						System.out.println("a1 = " + a1);
            a.set(Math.max(a.get(), a1));
						

            // Signal completion of calculations to other threads
            barrier.await();
						System.out.println("a = " + a.get());

            // Critical section - Copying
            a1 = a.get();
						System.out.println("a1 = " + a1);

            // Calculate WH
						setValues(W, calcWh(p, H, a1, d1), H*(p-1), H*p);
						// System.out.println("W1 = " + Arrays.toString(W));

            // Signal completion to T3
						Sem1.release();

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    // private static void T2() {
		// 	int p = 2; 
    //     try {
    //         // Input MB, E
		// 				MB = generateMatrix(n, 2);
		// 				E = generateVector(3, n);
		// 				// System.out.println("E = " + Arrays.toString(E));
		// 				// System.out.println("MB " + Arrays.deepToString(MB));
    //         // Signal T1, T3, T4 about input
		// 				System.out.println("T2: Data loaded");
    //         barrier.await();

    //         // Critical section - Copying and calculating
		// 				int d2;
		// 				synchronized (Lock) {
		// 					d2 = d;
		// 					System.out.println("T2: Coping d");
		// 				}
		// 				long O2[] = scalarVectorMultiplication(C, MD, H*(p-1), H*p);
		// 				// System.out.println("O2 = " + Arrays.toString(O2));
		// 				long a2 = findMax(O2);
    //         a.set(Math.max(a.get(), a2));

    //         // Signal completion of calculations to other threads
    //         barrier.await();
		// 				System.out.println("a = " + a.get());

    //         // Critical section - Copying
    //         a2 = a.get();
		// 				System.out.println("a2 = " + a2);

    //         // Calculate WH
		// 				setValues(W, calcWh(p, H, a2, d2), H*(p-1), H*p);
		// 				// System.out.println("W2 = " + Arrays.toString(W));

    //         // Signal completion to T3
		// 				Sem1.release();

    //     } catch (InterruptedException | BrokenBarrierException e) {
    //         e.printStackTrace();
    //     }
    // }

    // private static void T3() {
		// 	int p = 3; 
    //     try {
    //         // Input C, MA
		// 				C = generateVector(2, n);
		// 				MA = generateMatrix(n, 4);
		// 				// System.out.println("C = " + Arrays.toString(C));
		// 				// System.out.println("MA " + Arrays.deepToString(MA));

    //         // Signal T1, T2, T4 about input
		// 				System.out.println("T3: Data loaded");
    //         barrier.await();

    //         // Critical section - Copying and calculating
    //         int d3;
		// 				synchronized (Lock) {
		// 					d3 = d;
		// 					System.out.println("T3: Coping d");
		// 				}
		// 				long O3[] = scalarVectorMultiplication(C, MD, H*(p-1), H*p);
		// 				// System.out.println("O3 = " + Arrays.toString(O3));
		// 				long a3 = findMax(O3);
		// 				System.out.println("a3 = " + a3);
    //         a.set(Math.max(a.get(), a3));

    //         // // Signal completion of calculations to other threads
    //         barrier.await();
		// 				System.out.println("a = " + a.get());

    //         // // Critical section - Copying
    //         a3 = a.get();
		// 				System.out.println("a3 = " + a3);

    //         // // Calculate WH
		// 				setValues(W, calcWh(p, H, a3, d3), H*(p-1), H*p);
		// 				// System.out.println("W3 = " + Arrays.toString(W));

    //         // // Signal completion to T1, T2, T4
    //         Sem1.acquire(3);
		// 				System.out.println("W = " + Arrays.toString(W));

    //     } catch (InterruptedException | BrokenBarrierException e) {
    //         e.printStackTrace();
    //     }
    // }

    // private static void T4() {
		// 	int p = 4; 
    //     try {
    //         // Input MD, d
		// 				MD = generateMatrix(n, 3);
		// 				d = 4;
		// 				// System.out.println("MD " + Arrays.deepToString(MD));
		// 				// System.out.println("d = " + d);

    //         // Signal T1, T2, T3 about input
		// 				System.out.println("T4: Data loaded");
    //         barrier.await();

    //         // Critical section - Copying and calculating
    //         int d4;
		// 				synchronized (Lock) {
		// 					d4 = d;
		// 					System.out.println("T4: Coping d");
		// 				}
		// 				long O4[] = scalarVectorMultiplication(C, MD, H*(p-1), H*p);
		// 				// System.out.println("O4 = " + Arrays.toString(O4));
		// 				long a4 = findMax(O4);
		// 				System.out.println("a4 = " + a4);
    //         a.set(Math.max(a.get(), a4));

    //         // // Signal completion of calculations to other threads
    //         barrier.await();
		// 				System.out.println("a = " + a.get());
    //         // // Critical section - Copying
    //         a4 = a.get();
		// 				System.out.println("a4 = " + a4);

    //         // // Calculate WH
		// 				setValues(W, calcWh(p, H, a4, d4), H*(p-1), H*p);
		// 				// System.out.println("W4 = " + Arrays.toString(W));

    //         // // Signal completion to T3
    //         Sem1.release();

    //     } catch (InterruptedException | BrokenBarrierException e) {
    //         e.printStackTrace();
    //     }
    // }

		// Метод для генерації матриці з випадковими значеннями
    private static long[][] generateMatrix(int size, int values) {
			long[][] matrix = new long[size][size];

			for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
							if (i==j) {
								matrix[i][j] = 1;
								continue;
							}
							matrix[i][j] = values;
					}
			}

			return matrix;
    }

		private static long[] generateVector(int from, int count) {
			int size = count;
			long[] vector = new long[size];

			for (int i = 0; i < size; i++) {
				vector[i] = i + from;
			}

			return vector;
    }

		private static void setValues(long[] obj, long[] values, int from, int to) {
			for (int i = 0; i < to-from; i++) {
				obj[i+from] = values[i];
			}

    }

    public static long[] multiplyVectorByScalar(long[] vector, long scalar, int from, int to) {
			int length = vector.length;

			if (to == 0) {
				to = length;
			}

			long[] result = new long[to-from];

			for (int i = 0; i < to-from; i++) {
					result[i] = vector[i+from] * scalar;
			}

			return result;
		}

		private static long[] scalarVectorMultiplication(long[] vector, long[][] matrix, int from, int to) {
			int rows = matrix.length;
			int cols = matrix[0].length;

			if (to == 0) {
				to = cols;
			}

			if (vector.length != rows) {
					throw new IllegalArgumentException("Несумісні розміри вектора та матриці");
			}

			long[] result = new long[to-from];

			for (int i = 0; i < to-from; i++) {
					for (int j = 0; j < rows; j++) {
							result[i] += vector[j] * matrix[j][i+from];
					}
			}

			return result;
		}

		private static long[][] matrixMultiplication(long[][] matrix1, long[][] matrix2, int from, int to) {
			int rows1 = matrix1.length;
			int cols1 = matrix1[0].length;
			int rows2 = matrix2.length;

			if (cols1 != rows2) {
					throw new IllegalArgumentException("Несумісні розміри матриці");
			}

			long[][] result = new long[rows1][to-from];

			for (int i = 0; i < rows1; i++) {
					for (int j = 0; j < to-from; j++) {
							for (int k = 0; k < cols1; k++) {
									result[i][j] += matrix1[i][k] * matrix2[k][j+from];
							}
					}
			}

			return result;
		}

    private static long[] vectorAddition(long[] vector1, long[] vector2) {
			int length = vector1.length;

			if (length != vector2.length) {
					throw new IllegalArgumentException("Несумісні векторні розміри");
			}

			long[] result = new long[length];

			for (int i = 0; i < length; i++) {
					result[i] = vector1[i] + vector2[i];
			}

			return result;
		}

		private static long findMax(long[] vector) {
			long max = vector[0];
			for (int i = 1; i < vector.length; i++) {
					if (vector[i] > max) {
							max = vector[i];
					}
			}
			return max;
		}

		private static long[] calcWh(int p, int H, long a, int d) {
			long[][] result1 = matrixMultiplication(MA, MB, H*(p-1), H*p);
			// System.out.println("MA*MB " + Arrays.deepToString(result1));
			long[] result2 = scalarVectorMultiplication(E, result1, 0, 0);
			// System.out.println("E*(MA*MB) " + Arrays.toString(result2));
			long[] result3 = multiplyVectorByScalar(result2, d, 0, 0);
			// System.out.println("E*(MA*MB)*d " + Arrays.toString(result3));
			long[] result4 = multiplyVectorByScalar(C, a, H*(p-1), H*p);
			// System.out.println("a*C " + Arrays.toString(result4));
			return vectorAddition(result4, result3);
		}
}