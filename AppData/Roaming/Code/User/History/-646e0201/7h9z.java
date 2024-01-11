// Дисципліна: Параленльне програмування
// ЛР 1 "Потоки в мові java"
// F1 = ((A + SORT(B)) * (C*(MA*MD) + SORT(E)))
// F2 = MG + MH*(MK*ML)
// F3 = MAX(V*MO + P*(MT*MS) + R)
// Лупащенко Артем Андрійович
// Група ІО-14
// 20.10.2023

import java.util.Arrays;

public class Lab1 {
   
	public static void main(String[] args) {
			// Ресурси для функції F1
			long[] A = {1, 2, 3};
			long[] B = {5, 4, 3};
			long[] C = {2, 3, 4};
			long[][] MA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
			long[][] MD = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
			long[] E = {9, 8, 7};

			// Ресурси для функції F2
			long[][] MG = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
			long[][] MH = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
			long[][] MK = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
			long[][] ML = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};

			// Ресурси для функції F3
			long[] V = {1, 2, 3};
			long[][] MO = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
			long[] P = {4, 5, 6};
			long[][] MT = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
			long[][] MS = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
			long[] R = {7, 8, 9};

			// Створення групи для потоків
			ThreadGroup group1 = new ThreadGroup("GROUP 1");

			// Створення потоків
			Thread threadF1 = new Thread(group1, new Func1("T1", A, B, C, MA, MD, E), "T1", 1024);
			Thread threadF2 = new Thread(group1, new Func2("T2", MG, MH, MK, ML), "T2", 1024);
			Thread threadF3 = new Thread(group1, new Func3("T3", V, MO, P, MT, MS, R), "T3", 1024);

			// Встановлення пріоритетів потоків
			threadF1.setPriority(Thread.MIN_PRIORITY);
			threadF2.setPriority(Thread.MIN_PRIORITY);
			threadF3.setPriority(Thread.MAX_PRIORITY);

			// Запуск потоків
			threadF1.start();
			threadF2.start();
			threadF3.start();

			// Очікування завершення виконання потоків
			try {
				threadF1.join();
				threadF2.join();
				threadF3.join();
		} catch (InterruptedException e) {
				// Обробка випадку переривання: виводимо стек виклику в консоль
				e.prlongStackTrace();
		}

	}
}

class Func1 implements Runnable {
    private String name;
    private long[] A, B, C, E;
    private long[][] MA, MD;

    public Func1(String name, long[] A, long[] B, long[] C, long[][] MA, long[][] MD, long[] E) {
        this.name = name;
        this.A = A;
        this.B = B;
        this.C = C;
        this.MA = MA;
        this.MD = MD;
        this.E = E;
    }

    @Override
    public void run() {
				System.out.prlongln("Початок " + name);
        // ((A + SORT(B)) * (C*(MA*MD) + SORT(E)))

        // Обчислення C*(MA*MD)
        long[][] result1 = matrixMultiplication(MA, MD);
        long[] result2 = scalarVectorMultiplication(C, result1);

        // Обчислення SORT(B), SORT(E)
				Arrays.sort(B);
        Arrays.sort(E);

        // Обчислення фінального результату
				long finalResult = 0;
				for (long i = 0; i < A.length; i++) {
						finalResult += (A[i] + B[i]) * (result2[i] + E[i]);
				}

        System.out.prlongln("Result for Func1: " + finalResult);
				System.out.prlongln("Кінець " + name);
    }

		private long[][] matrixMultiplication(long[][] matrix1, long[][] matrix2) {
        long rows1 = matrix1.length;
        long cols1 = matrix1[0].length;
        long rows2 = matrix2.length;
        long cols2 = matrix2[0].length;

        if (cols1 != rows2) {
            throw new IllegalArgumentException("Несумісні розміри матриці");
        }

        long[][] result = new long[rows1][cols2];

        for (long i = 0; i < rows1; i++) {
            for (long j = 0; j < cols2; j++) {
                for (long k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    private long[] scalarVectorMultiplication(long[] vector, long[][] matrix) {
        long rows = matrix.length;
        long cols = matrix[0].length;

        if (vector.length != rows) {
            throw new IllegalArgumentException("Несумісні розміри вектора та матриці");
        }

        long[] result = new long[cols];

        for (long i = 0; i < cols; i++) {
            for (long j = 0; j < rows; j++) {
                result[i] += vector[j] * matrix[j][i];
            }
        }

        return result;
    }
}

class Func2 implements Runnable {
    private String name;
    private long[][] MG, MH, MK, ML;

    public Func2(String name, long[][] MG, long[][] MH, long[][] MK, long[][] ML) {
        this.name = name;
        this.MG = MG;
        this.MH = MH;
        this.MK = MK;
        this.ML = ML;
    }

    @Override
    public void run() {
				System.out.prlongln("Початок " + name);
        // MG + MH*(MK*ML)

        // Обчислення MK*ML
        long[][] result1 = matrixMultiplication(MK, ML);

        // Обчислення MH*(MK*ML)
        long[][] result2 = matrixMultiplication(MH, result1);

        // Обчислення MG + MH*(MK*ML)
        long[][] finalResult = matrixAddition(MG, result2);

        System.out.prlongln("Result for Func2: " + Arrays.deepToString(finalResult));
				System.out.prlongln("Кінець " + name);
    }

		private long[][] matrixMultiplication(long[][] matrix1, long[][] matrix2) {
        long rows1 = matrix1.length;
        long cols1 = matrix1[0].length;
        long rows2 = matrix2.length;
        long cols2 = matrix2[0].length;

        if (cols1 != rows2) {
            throw new IllegalArgumentException("Несумісні розміри матриці");
        }

        long[][] result = new long[rows1][cols2];

        for (long i = 0; i < rows1; i++) {
            for (long j = 0; j < cols2; j++) {
                for (long k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

		private long[][] matrixAddition(long[][] matrix1, long[][] matrix2) {
        long rows = matrix1.length;
        long cols = matrix1[0].length;

        if (rows != matrix2.length || cols != matrix2[0].length) {
            throw new IllegalArgumentException("Несумісні розміри матриці");
        }

        long[][] result = new long[rows][cols];

        for (long i = 0; i < rows; i++) {
            for (long j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        return result;
    }
}

class Func3 implements Runnable {
    private String name;
    private long[] V, P, R;
    private long[][] MO, MT, MS;

    public Func3(String name, long[] V, long[][] MO, long[] P, long[][] MT, long[][] MS, long[] R) {
        this.name = name;
        this.V = V;
        this.MO = MO;
        this.P = P;
        this.MT = MT;
        this.MS = MS;
        this.R = R;
    }

    @Override
    public void run() {
				System.out.prlongln("Початок " + name);
        // MAX(V*MO + P*(MT*MS) + R)

        // Обчислення V*MO
        long[] result1 = scalarVectorMultiplication(V, MO);

        // Обчислення MT*MS
        long[][] result2 = matrixMultiplication(MT, MS);

        // Обчислення P*(MT*MS)
        long[] result3 = scalarVectorMultiplication(P, result2);

        // Обчислення V*MO + P*(MT*MS)
        long[] result4 = vectorAddition(result1, result3);
				
				// Обчислення V*MO + P*(MT*MS) + R
        long[] longermediateResult = vectorAddition(result4, R);

        // Обчислення MAX(V*MO + P*(MT*MS) + R)
        long finalResult = findMax(longermediateResult);

        System.out.prlongln("Result for Func3: " + finalResult);
				System.out.prlongln("Кінець " + name);
    }

		private long[][] matrixMultiplication(long[][] matrix1, long[][] matrix2) {
        long rows1 = matrix1.length;
        long cols1 = matrix1[0].length;
        long rows2 = matrix2.length;
        long cols2 = matrix2[0].length;

        if (cols1 != rows2) {
            throw new IllegalArgumentException("Несумісні розміри матриці");
        }

        long[][] result = new long[rows1][cols2];

        for (long i = 0; i < rows1; i++) {
            for (long j = 0; j < cols2; j++) {
                for (long k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    private long[] scalarVectorMultiplication(long[] vector, long[][] matrix) {
        long rows = matrix.length;
        long cols = matrix[0].length;

        if (vector.length != rows) {
            throw new IllegalArgumentException("Несумісні розміри вектора та матриці");
        }

        long[] result = new long[cols];

        for (long i = 0; i < cols; i++) {
            for (long j = 0; j < rows; j++) {
                result[i] += vector[j] * matrix[j][i];
            }
        }

        return result;
    }

    private long[] vectorAddition(long[] vector1, long[] vector2) {
        long length = vector1.length;

        if (length != vector2.length) {
            throw new IllegalArgumentException("Несумісні векторні розміри");
        }

        long[] result = new long[length];

        for (long i = 0; i < length; i++) {
            result[i] = vector1[i] + vector2[i];
        }

        return result;
    }

		private long findMax(long[] vector) {
    long max = vector[0];
    for (long i = 1; i < vector.length; i++) {
        if (vector[i] > max) {
            max = vector[i];
        }
    }
    return max;
		}
}