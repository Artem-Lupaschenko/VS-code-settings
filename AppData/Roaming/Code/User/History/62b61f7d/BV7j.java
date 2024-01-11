public class Data {
	// Підпрограми для функцій F1, F2, F3
class Func1 implements Runnable {
	private String name;
	private int[] A, B, C, E;
	private int[][] MA, MD;

	public Func1(String name, int[] A, int[] B, int[] C, int[][] MA, int[][] MD, int[] E) {
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
			// Реалізація функції F1
			// ((A + SORT(B)) * (C*(MA*MD) + SORT(E)))
			// Ваш код тут
	}
}

class Func2 implements Runnable {
	private String name;
	private int[][] MG, MH, MK, ML;

	public Func2(String name, int[][] MG, int[][] MH, int[][] MK, int[][] ML) {
			this.name = name;
			this.MG = MG;
			this.MH = MH;
			this.MK = MK;
			this.ML = ML;
	}

	@Override
	public void run() {
			// Реалізація функції F2
			// MG + MH*(MK*ML)
			// Ваш код тут
	}
}

class Func3 implements Runnable {
	private String name;
	private int[] V, P, R;
	private int[][] MO, MT, MS;

	public Func3(String name, int[] V, int[][] MO, int[] P, int[][] MT, int[][] MS, int[] R) {
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
			// Реалізація функції F3
			// MAX(V*MO + P*(MT*MS) + R)
			// Ваш код тут
	}
}
}
