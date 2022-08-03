
import java.util.Random;
import java.util.Arrays;
import java.lang.Math;
public class alurbanyproj01 
{
	public static void main(String[] args) 
    {
		//How many times the program will run
		final int RUNS = 1000;
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
			//Size of Matrix grid
			n = (int) Math.pow(2, i);
			//Call to make the random matrixes
			A = matrixMaker(n);
			B = matrixMaker(n);
			
				for (int iii = 0; iii < RUNS; iii++) 
				{
					start = System.nanoTime();
					classicMatrix(A, B, A.length);
					end = System.nanoTime();
					timerClassic += end - start;

					start = System.nanoTime();
					dncMatrix(A, B, A.length);
					end = System.nanoTime();
					timerDnC += end - start;

					start = System.nanoTime();
					strassencaller(A, B, A.length);
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

private static int[][] addition(int[][] A, int[][] B, int n) 
{
	int[][] C = new int[n][n];

	for (int i = 0; i < n; i++) 
	{
		for (int ii = 0; ii < n; ii++) 
		{
			C[i][ii] = A[i][ii] + B[i][ii];
		}
	}
	return C;
}

private static int[][] subtraction(int[][] A, int[][] B, int n) 
{
	int[][] C = new int[n][n];

	for (int i = 0; i < n; i++) 
	{
		for (int ii = 0; ii < n; ii++) 
		{
			C[i][ii] = A[i][ii] - B[i][ii];
		}
	}
	return C;
}

public static int[][] matrixMaker(int n) 
{
	Random random = new Random();
	int[][] matrix = new int[n][n];

	for (int i = 0; i < n; i++) 
	{
		for (int ii = 0; ii < n; ii++) 
		{
			matrix[i][ii] = random.nextInt(100);
		}
	}
	return matrix;
}

public static void matrixTester(int[][] C, int n) 
{
	for (int i = 0; i < n; i++) 
	{
		for (int ii = 0; ii < n; ii++) 
		{
			System.out.printf("%10d", C[i][ii]);
		}
		System.out.println();
	}
}

public static int[][] classicMatrix(int[][] A, int[][] B, int n) 
{
	int[][] C = new int[n][n];

	for (int i = 0; i < n; i++) 
	{
		for (int ii = 0; ii < n; ii++) 
		{
			C[i][ii] = 0;
		}
	}
	for (int i = 0; i < n; i++) 
	{
		for (int ii = 0; ii < n; ii++) 
		{
			for (int iii = 0; iii < n; iii++) 
			{
				C[i][ii] += A[i][iii] * B[iii][ii];
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
		int[][] A11 = new int[n/2][n/2];
		int[][] A12 = new int[n/2][n/2];
		int[][] A21 = new int[n/2][n/2];
		int[][] A22 = new int[n/2][n/2];

		int[][] B11 = new int[n/2][n/2];
		int[][] B12 = new int[n/2][n/2];
		int[][] B21 = new int[n/2][n/2];
		int[][] B22 = new int[n/2][n/2];

		int [][] C11 = new int[n/2][n/2];
        int [][] C12 = new int[n/2][n/2];
        int [][] C21 = new int[n/2][n/2];
        int [][] C22 = new int[n/2][n/2];
			
		for(int i = 0; i < n/2; i++)
		{
			for(int ii = 0; ii < n/2; ii++)
			{
					//A quadrants
					//[A11 A12]
					//[A21 A22]
				A11[i][ii] = A[i][ii];
                A12[i][ii] = A[i][ii + n/2];
                A21[i][ii] = A[i + n/2][ii];
                A22[i][ii] = A[i+ n/2][ii + n/2];
					//B quadrants
					//[B11 B12]
					//[B21 B22]
                B11[i][ii] = B[i][ii];
                B12[i][ii] = B[i][ii + n/2];
                B21[i][ii] = B[i + n/2][ii];
                B22[i][ii] = B[i+ n/2][ii + n/2];
			}
		}
			//Quadrant Solver
			C11 = addition(dncMatrix(A11, B11, n/2), dncMatrix(A12, B21, n/2), n/2);
			C12 = addition(dncMatrix(A11, B12, n/2), dncMatrix(A12, B22, n/2), n/2);
			C21 = addition(dncMatrix(A21, B11, n/2), dncMatrix(A22, B21, n/2), n/2);
			C22 = addition(dncMatrix(A21, B12, n/2), dncMatrix(A22, B22, n/2), n/2);

			//Taking the results and combining
			int[][] C11C21 = new int [n][n/2];
            int[][] C12C22 = new int [n][n/2];

			System.arraycopy(C11, 0, C11C21, 0, n/2);
            System.arraycopy(C21, 0, C11C21, n/2, n/2);
            System.arraycopy(C12, 0, C12C22, 0, n/2);
            System.arraycopy(C22, 0, C12C22, n/2, n/2);

			//L & R stack copiers
			for (int i = 0; i < n; i ++)
            {
                for (int ii = 0; ii < n/2; ii++)
                {
                    C[i][ii] = C11C21[i][ii];
                }
            }
            for (int i = 0; i < n; i ++)
            {
                for (int ii = 0; ii < n/2; ii++)
                {
                    C[i][ii+n/2] = C12C22[i][ii];
                }
            }
		}

		return C;
	}

	public static int[][] strassencaller(int[][] A, int[][] B, int n) 
	{
		int[][] C = new int[n][n];
		strassenMatrix(n, A, B, C);
		return C;
	}
	public static void strassenMatrix( int n, int[][] A, int[][] B, int[][] C) 
	{
		if (n == 2) 
		{
			classicMatrix(A, B, n);
		} 
		else 
		{
			int[][] A11 = new int[n/2][n/2];
			int[][] A12 = new int[n/2][n/2];
			int[][] A21 = new int[n/2][n/2];
			int[][] A22 = new int[n/2][n/2];

			int[][] B11 = new int[n/2][n/2];
			int[][] B12 = new int[n/2][n/2];
			int[][] B21 = new int[n/2][n/2];
			int[][] B22 = new int[n/2][n/2];

			int[][] P = new int[n/2][n/2];
			int[][] Q = new int[n/2][n/2];
			int[][] R = new int[n/2][n/2];
			int[][] S = new int[n/2][n/2];
			int[][] T = new int[n/2][n/2];
			int[][] U = new int[n/2][n/2];
			int[][] V = new int[n/2][n/2];

			for(int i = 0; i < n/2; i++)
			{
				for(int ii = 0; ii < n/2; ii++)
				{
					//A quadrants
					//[A11 A12]
					//[A21 A22]
					A11[i][ii] = A[i][ii];
                    A12[i][ii] = A[i][ii + n/2];
                    A21[i][ii] = A[i + n/2][ii];
                    A22[i][ii] = A[i+ n/2][ii + n/2];
					//B quadrants
					//[B11 B12]
					//[B21 B22]
                    B11[i][ii] = B[i][ii];
                    B12[i][ii] = B[i][ii + n/2];
                    B21[i][ii] = B[i + n/2][ii];
                    B22[i][ii] = B[i+ n/2][ii + n/2];
				}
			}

			//It's Strassen Time
			strassenMatrix(n/2, addition(A11, A22, n / 2), addition(B11, B22, n / 2), P);
			strassenMatrix(n/2, addition(A21, A22, n / 2), B11, Q);
			strassenMatrix(n/2, A11, subtraction(B12, B22, n/2), R);
			strassenMatrix(n/2, A22, subtraction(B21, B11, n/2), S);
			strassenMatrix(n/2, addition(A11, A12, n / 2), B22, T);
			strassenMatrix(n/2, subtraction(A21, A11, n/2), addition(B11, B12, n/2), U);
			strassenMatrix(n/2, subtraction(A12, A22, n/2), addition(B21, B22, n/2), V);

			int[][] C11 = addition(subtraction(addition(P, S, P.length), T, T.length), V, V.length);
			int[][] C12 = addition(R, T, R.length);
			int[][] C21 = addition(Q, S, Q.length);
			int[][] C22 = addition(subtraction(addition(P, R, P.length), Q, Q.length), U, U.length);

			int[][] C11C21 = new int [n][n/2];
            int[][] C12C22 = new int [n][n/2];

			System.arraycopy(C11, 0, C11C21, 0, n/2);
            System.arraycopy(C21, 0, C11C21, n/2, n/2);
            System.arraycopy(C12, 0, C12C22, 0, n/2);
            System.arraycopy(C22, 0, C12C22, n/2, n/2);
			
			//L & R stack copiers
			for (int i = 0; i < n; i ++)
			{
				for (int ii = 0; ii < n/2; ii++)
				{
								C[i][ii] = C11C21[i][ii];
				}
			}
			
			for (int i = 0; i < n; i ++)
			{
				for (int ii = 0; ii < n/2; ii++)
				{
					C[i][ii+n/2] = C12C22[i][ii];
				}
			}
		}
	}

}