import java.util.Arrays;

class Data {
	public static final int n = 4;
	public static final int P = 4;
	public static final int H = n / P;

	// Ініціалізація даних (MD, MB, MA, C, E, d)
	public static long[][] MB = new long[n][n];
	public static long[][] MO = new long[n][n];
	public static long[][] MC = new long[n][n];
	public static long[][] MM = new long[n][n];
	public static long[] Z = new long[n];

	// Метод для генерації матриці з одиницями по головній діагоналі
	public static long[][] generateMatrix(int size, int values) {
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

	// Метод для генерації вектора з послідовними значеннями
	public static long[] generateVector(int from, int count) {
		int size = count;
		long[] vector = new long[size];

		for (int i = 0; i < size; i++) {
			vector[i] = i + from;
		}

		return vector;
	}

	// Метод для встановлення значень матриці з іншої матриці
	public static void setMatrixValues(long[][] targetMatrix, long[][] sourceMatrix, int from, int to) {
			int rows = targetMatrix.length;

			for (int i = 0; i < rows; i++) {
					for (int j = 0; j < to-from; j++) {
							targetMatrix[i][j+from] = sourceMatrix[i][j];
					}
			}
	}

	// Метод для встановлення значень вектору з іншого вектору
	public static void setValues(long[] obj, long[] values, int from, int to) {
		for (int i = 0; i < to-from; i++) {
			obj[i+from] = values[i];
		}
	}

	// Метод для множення вектора на скаляр
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

	// Метод для множення матриці на скаляр
	public static long[][] multiplyMatrixByScalar(long[][] matrix, long scalar, int from, int to) {
		int rows = matrix.length;
		int cols = matrix[0].length;

		if (to == 0) {
			to = cols;
		}

		long[][] result = new long[rows][to-from];

		for (int i = 0; i < rows; i++) {
				for (int j = 0; j < to-from; j++) {
						result[i][j] = matrix[i][j+from] * scalar;
				}
		}

		return result;
	}

	// Метод для скалярного множення вектора на матрицю
	public static long[] scalarVectorMultiplication(long[] vector, long[][] matrix, int from, int to) {
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

	// Метод для множення матриць
	public static long[][] matrixMultiplication(long[][] matrix1, long[][] matrix2, int from, int to) {
		int rows1 = matrix1.length;
		int cols1 = matrix1[0].length;
		int rows2 = matrix2.length;
		int cols2 = matrix2[0].length;

		if (cols1 != rows2) {
			throw new IllegalArgumentException("Несумісні розміри матриці");
		}

		if (to == 0) {
			to = cols2;
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

	// Метод для додавання матриць
	public static long[][] matrixAddition(long[][] matrixA, long[][] matrixB) {
			int rows = matrixA.length;
			int cols = matrixA[0].length;

			long[][] result = new long[rows][cols];

			for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
							result[i][j] = matrixA[i][j] + matrixB[i][j];
					}
			}

			return result;
	}

	// Метод для додавання векторів
	public static long[] vectorAddition(long[] vector1, long[] vector2) {
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

	// Метод для пошуку максимального значення у векторі
	public static long findMin(long[] vector, int from, int to) {
		long min = vector[from];
		for (int i = 1; i < to-from; i++) {
			if (vector[i+from] < min) {
				min = vector[i+from];
			}
		}
		return min;
	}

	// Метод обчислення Wh
	public static long[][] calcMOh(int p, int H, long a, int d) {
		//Обчислення MC*MM
		System.err.println("Обчислення MC*MM");
		long[][] result1 = matrixMultiplication(MC, MM, H*(p-1), H*p);
		System.err.println(Arrays.deepToString(result1));
		//Обчислення MB*(MC*MM)
		System.err.println("Обчислення MB*(MC*MM)");
		long[][] result2 = matrixMultiplication(MB, result1, 0, 0);
		//Обчислення MB*(MC*MM)*d
		System.err.println("Обчислення MB*(MC*MM)*d");
		long[][] result3 = multiplyMatrixByScalar(result2, d, 0, 0);
		//Обчислення a*MC
		System.err.println("Обчислення a*MC");
		long[][] result4 = multiplyMatrixByScalar(MC, a, H*(p-1), H*p);
		//Обчислення MB*(MC*MM)*d + a*MC
		System.err.println("Обчислення MB*(MC*MM)*d + a*MC");
		return matrixAddition(result3, result4);
	}
}

class MonitorA {
	private long a;

	// Конструктор монітора
	public MonitorA(long initialValue) {
		this.a = initialValue;
	}

	// Метод для отримання значення a
	public synchronized long getA() {
		System.out.println("Ресурс a використовує " + Thread.currentThread().getName());
		return this.a;
	}

	// Метод для зміни значення a та знаходження мінімуму з аргументом
	public synchronized void minA(long value) {
		System.out.println("Обислює мінімальне значення a " + Thread.currentThread().getName());
		this.a = Math.min(a, value);
		System.err.println("Ресурс a " + value);
	}
}

class MonitorD {
	private int d;

	// Метод для отримання значення d
	public synchronized int getD() {
		System.out.println("Ресурс d використовує " + Thread.currentThread().getName());
		return this.d;
	}

	// Метод для задання значення d
	public synchronized void setD(int value) {
		this.d = value;
	}
}

class MonitorSync {
	private int F1;
	private int P;

	// Конструктор монітора
	public MonitorSync() {
		this.F1 = 0;
		this.P = Data.P;
	}

	// Метод для збільшення лічильника сигналів
	public synchronized void signalSync() {
		// Збільшення лічильника
		F1++;
		// Перевірити чи дорівнює кількість сигналів потокам
		if (F1 == P) {
			notifyAll(); // Викликати notifyAll(), щоб розбудити всі потоки
			F1 = 0; // Скидання лічильника сигналів
		}
	}

	// Метод для очікування інших потоків
	public synchronized void waitSync() {
		// Перевірити чи є сигнали
		if (F1 != 0) {
			try {
				wait(); // Чекати, коли буде досягнуто значення числа потоків
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class T1 implements Runnable {
	private int p = 1;
	private MonitorA monA;
	private MonitorD monD;
	private MonitorSync monSync;

	public T1(MonitorA monA, MonitorD monD, MonitorSync monSync) {
    this.monA = monA;
		this.monD = monD;
		this.monSync = monSync;
  }

	@Override
	public void run() {
		System.out.println("T1: Початок роботи");
		// Введення MB, MC
		Data.MB = Data.generateMatrix(Data.n, 3);
		Data.MC = Data.generateMatrix(Data.n, 2);
		// Очікування введення у T2, T3, T4
		monSync.signalSync();
		monSync.waitSync();

		// Критичний розділ - Копіювання та обчислення
		int d1 = monD.getD();
		System.out.println("T1: Копіювання d");
		long a1 = Data.findMin(Data.Z, Data.H * (p - 1), Data.H * p);
		monA.minA(a1);

		// Сигнал про завершення обчислень іншим потокам
		monSync.signalSync();
		monSync.waitSync();

		// Критичний розділ - Копіювання
		a1 = monA.getA();

		// Обчислення MOh та запис до MO
		Data.setMatrixValues(Data.MO, Data.calcMOh(p, Data.H, a1, d1), Data.H * (p - 1), Data.H * p);
		System.out.println(Arrays.deepToString(Data.MO));
		// Сигнал про завершення T2
		monSync.signalSync();

		System.out.println("T1: Кінець роботи");
	}
}

class T2 implements Runnable {
	private int p = 2;
	private MonitorA monA;
	private MonitorD monD;
	private MonitorSync monSync;

	public T2(MonitorA monA, MonitorD monD, MonitorSync monSync) {
    this.monA = monA;
		this.monD = monD;
		this.monSync = monSync;
  }

	@Override
	public void run() {
		System.out.println("T2: Початок роботи");

		Data.Z = Data.generateVector(3, Data.n);
		monD.setD(4);
		Data.MM = Data.generateMatrix(Data.n, 4);
		// Очікування введення у T1, T3, T4
		monSync.signalSync();
		monSync.waitSync();

		// Критичний розділ - Копіювання та обчислення
		int d2 = monD.getD();
		System.out.println("T2: Копіювання d");
		long a2 = Data.findMin(Data.Z, Data.H * (p - 1), Data.H * p);
		monA.minA(a2);

		// Сигнал про завершення обчислень іншим потокам
		monSync.signalSync();
		monSync.waitSync();

		// Критичний розділ - Копіювання
		a2 = monA.getA();

		// Обчислення MOh та запис до MO
		Data.setMatrixValues(Data.MO, Data.calcMOh(p, Data.H, a2, d2), Data.H * (p - 1), Data.H * p);
		System.out.println(Arrays.deepToString(Data.MO));
		// Сигнал про завершення та очікування інших потоків
		monSync.signalSync();
		monSync.waitSync();

		System.out.println("MO = " + Arrays.deepToString(Data.MO));

		System.out.println("T2: Кінець роботи");
	}
}

class T3 implements Runnable {
	private int p = 3;
	private MonitorA monA;
	private MonitorD monD;
	private MonitorSync monSync;

	public T3(MonitorA monA, MonitorD monD, MonitorSync monSync) {
    this.monA = monA;
		this.monD = monD;
		this.monSync = monSync;
  }

	@Override
	public void run() {
		System.out.println("T3: Початок роботи");
		// Очікування введення у T1, T2, T4
		monSync.signalSync();
		monSync.waitSync();

		// Критичний розділ - Копіювання та обчислення
		int d3 = monD.getD();
		System.out.println("T3: Копіювання d");
		long a3 = Data.findMin(Data.Z, Data.H * (p - 1), Data.H * p);
		monA.minA(a3);

		// Сигнал про завершення обчислень іншим потокам
		monSync.signalSync();
		monSync.waitSync();

		// Критичний розділ - Копіювання
		a3 = monA.getA();

		// Обчислення MOh та запис до MO
		Data.setMatrixValues(Data.MO, Data.calcMOh(p, Data.H, a3, d3), Data.H * (p - 1), Data.H * p);
		System.out.println(Arrays.deepToString(Data.MO));
		// Сигнал про завершення T2
		monSync.signalSync();

		System.out.println("T3: Кінець роботи");
	}
}

class T4 implements Runnable {
	private int p = 4;
	private MonitorA monA;
	private MonitorD monD;
	private MonitorSync monSync;

	public T4(MonitorA monA, MonitorD monD, MonitorSync monSync) {
    this.monA = monA;
		this.monD = monD;
		this.monSync = monSync;
  }

	@Override
	public void run() {
		System.out.println("T4: Початок роботи");
		// Введення Z, d, MM
		Data.Z = Data.generateVector(3, Data.n);
		monD.setD(4);
		Data.MM = Data.generateMatrix(Data.n, 4);
		// Очікування введення у T1, T2, T3
		monSync.signalSync();
		monSync.waitSync();

		// Критичний розділ - Копіювання та обчислення
		int d4 = monD.getD();
		System.out.println("T4: Копіювання d");
		long a4 = Data.findMin(Data.Z, Data.H * (p - 1), Data.H * p);
		monA.minA(a4);

		// Сигнал про завершення обчислень іншим потокам
		monSync.signalSync();
		monSync.waitSync();

		// Критичний розділ - Копіювання
		a4 = monA.getA();

		// Обчислення MOh та запис до MO
		Data.setMatrixValues(Data.MO, Data.calcMOh(p, Data.H, a4, d4), Data.H * (p - 1), Data.H * p);
		System.out.println(Arrays.deepToString(Data.MO));
		// Сигнал про завершення T2
		monSync.signalSync();

		System.out.println("T4: Кінець роботи");
	}
}

public class Lab4 {
	public static void main(String[] args) {
		MonitorA monitorA = new MonitorA(Long.MAX_VALUE);
		MonitorD monitorD = new MonitorD();
		MonitorSync monitorSync = new MonitorSync();
		// Створення потоків
		Thread t1 = new Thread(new T1(monitorA, monitorD, monitorSync));
		Thread t2 = new Thread(new T2(monitorA, monitorD, monitorSync));
		Thread t3 = new Thread(new T3(monitorA, monitorD, monitorSync));
		Thread t4 = new Thread(new T4(monitorA, monitorD, monitorSync));

		// Запуск потоків
		long startTime = System.currentTimeMillis();
		t1.start();
		t2.start();
		t3.start();
		t4.start();

		try {
			// Очікування завершення роботи потоків
			t1.join();
			t2.join();
			t3.join();
			t4.join();
		} catch (InterruptedException e) {
			// Обробка випадку переривання: вивід стеку виклику в консоль
			e.printStackTrace();
		} finally {
			long endTime = System.currentTimeMillis();
			long executionTime = endTime - startTime;
			System.out.println("Час виконання: " + executionTime + " мс");
		}
	}
}