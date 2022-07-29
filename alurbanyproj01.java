
import java.util.Random;
public class alurbanyproj01 
{
	public static void main(String[] args) 
    {
		//How many times the program will run
		final int RUNS = 50;
		//decides how big the array will be
		int n;
		//Timer start
		long start;
		//Timer end
		long end;
		//Timer for Classic
		long timerClassic = 0;
		//Timer for Divide and Conquer
		long timerDnC = 0;
		//Timer for Strassen
		long timerStrassen = 0;
		//Final time count for Classic
		long finalTimeClassic = 0;
		//Final time count for Divide and Conquer
		long finalTimeDnC = 0;
		//Final time count for Strassen
		long finalTimeStrassen = 0;
		int[][] A, B;


		for (int i = 1; i > 0; i++) 
		{
			n = (int) Math.pow(2, i);

			A = matrixMaker(n);
			B = matrixMaker(n);

			for (int ii = 0; ii < RUNS; ii++) 
			{
				start = System.nanoTime();
				classicMatrix(A, B, A.length);
				end = System.nanoTime();
				timerClassic += end - start;
				System.out.println();

				start = System.nanoTime();
				dncMatrix(A, B, A.length);
				end = System.nanoTime();
				timerDnC += end - start;

				start = System.nanoTime();
				strassenMatrix(A, B, A.length);
				end = System.nanoTime();
				timerStrassen += end - start;
			}

			finalTimeClassic = timerClassic / RUNS;
			finalTimeDnC = timerDnC / RUNS;
			finalTimeStrassen = timerStrassen / RUNS;

			System.out.println("For n = " + n + ":\n"
							+ "Classic took: " + finalTimeClassic + " nanoseconds over " + RUNS + " runs.\n"
							+ "Divide and Conquer took: " + finalTimeDnC + "  nanoseconds over " + RUNS + " runs.\n"
							+ "Strassen took: " + finalTimeStrassen + " nanoseconds over " + RUNS + " runs.\n");
		}
	}
	public static int[][] matrixMaker(int n) 
	{
		Random r = new Random();
		int[][] matrix = new int[n][n];

		for (int i = 0; i < n; i++) 
		{
			for (int ii = 0; ii < n; ii++) 
			{
				matrix[i][ii] = r.nextInt(100);
			}
		}
		return matrix;
	}

private static void constructMatrix(int[][] initialMatrix, int[][] newMatrix, int a, int b) 
{

	int y = b;

	for (int i = 0; i < initialMatrix.length; i++) 
	{
		for (int ii = 0; ii < initialMatrix.length; ii++) 
		{
			newMatrix[a][y++] = initialMatrix[i][ii];
		}
		y = b;
		a++;
	}
}

private static int[][] addition(int[][] A, int[][] B, int n) 
{

int[][] C = new int[n][n];

for (int i = 0; i < n; i++) {
	for (int ii = 0; ii < n; ii++) {
		C[i][ii] = A[i][ii] + B[i][ii];
	}
}
return C;
}


private static int[][] subtraction(int[][] A, int[][] B, int n) 
{

int[][] C = new int[n][n];

for (int i = 0; i < n; i++) {
	for (int ii = 0; ii < n; ii++) {
		C[i][ii] = A[i][ii] - B[i][ii];
	}
}
return C;
}


private static void deconstructMatrix(int[][] initialMatrix, int[][] newMatrix, int a, int b) 
{
	int y = b;
	for (int i = 0; i < newMatrix.length; i++) 
	{
		for (int ii = 0; ii < newMatrix.length; ii++) 
		{
			newMatrix[i][ii] = initialMatrix[a][y++];
		}
		y = b;
		a++;
	}
}


	public static int[][] classicMatrix(int[][] A, int[][] B, int n) 
	{
		int[][] C = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int ii = 0; ii < n; ii++) 
			{
				C[i][ii] = 0;
			}
		}

		for (int i = 0; i < n; i++) {
			for (int ii = 0; ii < n; ii++) 
			{
				for (int k = 0; k < n; k++) 
				{
					C[i][ii] += A[i][k] * B[k][ii];
				}
			}
		}
		return C;
	}

	public static int[][] dncMatrix(int[][] A, int[][] B, int n) 
	{
		int[][] C = new int[n][n];

		if (n == 1) 
		{
			C[0][0] = A[0][0] * B[0][0];
			return C;
		} 

		else 
		{
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];

			deconstructMatrix(A, A11, 0, 0);
			deconstructMatrix(A, A12, 0, n / 2);
			deconstructMatrix(A, A21, n / 2, 0);
			deconstructMatrix(A, A22, n / 2, n / 2);
			deconstructMatrix(B, B11, 0, 0);
			deconstructMatrix(B, B12, 0, n / 2);
			deconstructMatrix(B, B21, n / 2, 0);
			deconstructMatrix(B, B22, n / 2, n / 2);

			int[][] C11 = addition(dncMatrix(A11, B11, n / 2),
					dncMatrix(A12, B21, n / 2), n / 2);
			int[][] C12 = addition(dncMatrix(A11, B12, n / 2),
					dncMatrix(A12, B22, n / 2), n / 2);
			int[][] C21 = addition(dncMatrix(A21, B11, n / 2),
					dncMatrix(A22, B21, n / 2), n / 2);
			int[][] C22 = addition(dncMatrix(A21, B12, n / 2),
					dncMatrix(A22, B22, n / 2), n / 2);

			constructMatrix(C11, C, 0, 0);
			constructMatrix(C12, C, 0, n / 2);
			constructMatrix(C21, C, n / 2, 0);
			constructMatrix(C22, C, n / 2, n / 2);
		}

		return C;
	}


	public static int[][] strassenMatrix(int[][] A, int[][] B, int n) 
	{
		int[][] C = new int[n][n];
		strassenMatrixHelper(A, B, C, n);
		return C;
	}
	public static void strassenMatrixHelper(int[][] A, int[][] B, int[][] C, int n) 
	{
		if (n == 2) 
		{
			C[0][0] = (A[0][0] * B[0][0]) + (A[0][1] * B[1][0]);
			C[0][1] = (A[0][0] * B[0][1]) + (A[0][1] * B[1][1]);
			C[1][0] = (A[1][0] * B[0][0]) + (A[1][1] * B[1][0]);
			C[1][1] = (A[1][0] * B[0][1]) + (A[1][1] * B[1][1]);
		} else 
		{
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];

			int[][] P = new int[n / 2][n / 2];
			int[][] Q = new int[n / 2][n / 2];
			int[][] R = new int[n / 2][n / 2];
			int[][] S = new int[n / 2][n / 2];
			int[][] T = new int[n / 2][n / 2];
			int[][] U = new int[n / 2][n / 2];
			int[][] V = new int[n / 2][n / 2];

			deconstructMatrix(A, A11, 0, 0);
			deconstructMatrix(A, A12, 0, n / 2);
			deconstructMatrix(A, A21, n / 2, 0);
			deconstructMatrix(A, A22, n / 2, n / 2);
			deconstructMatrix(B, B11, 0, 0);
			deconstructMatrix(B, B12, 0, n / 2);
			deconstructMatrix(B, B21, n / 2, 0);
			deconstructMatrix(B, B22, n / 2, n / 2);

			strassenMatrixHelper(addition(A11, A22, n / 2),
					addition(B11, B22, n / 2), P, n / 2);
			strassenMatrixHelper(addition(A21, A22, n / 2), B11, Q, n / 2);
			strassenMatrixHelper(A11, subtraction(B12, B22, n / 2), R, n / 2);
			strassenMatrixHelper(A22, subtraction(B21, B11, n / 2), S, n / 2);
			strassenMatrixHelper(addition(A11, A12, n / 2), B22, T, n / 2);
			strassenMatrixHelper(subtraction(A21, A11, n / 2),
					addition(B11, B12, n / 2), U, n / 2);
			strassenMatrixHelper(subtraction(A12, A22, n / 2),
					addition(B21, B22, n / 2), V, n / 2);

			int[][] C11 = addition(
					subtraction(addition(P, S, P.length), T, T.length), V,
					V.length);
			int[][] C12 = addition(R, T, R.length);
			int[][] C21 = addition(Q, S, Q.length);
			int[][] C22 = addition(
					subtraction(addition(P, R, P.length), Q, Q.length), U,
					U.length);

			constructMatrix(C11, C, 0, 0);
			constructMatrix(C12, C, 0, n / 2);
			constructMatrix(C21, C, n / 2, 0);
			constructMatrix(C22, C, n / 2, n / 2);
		}
	}

}