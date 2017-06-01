
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Mst_prim {

	static int s = 0;
	static int arr[];
	static int key[];
	static int pos[];

	static int adjMat[][];// to store the weight
	static String input, output = "";

	public static void main(String[] args) throws FileNotFoundException, IOException {
		input = "A:/Semester 1/Algorithms/Project1/Input.txt";
		FileReader fis = new FileReader(input);// reading input file
		Scanner sc = new Scanner(fis);
		// PrintWriter out = new PrintWriter(new FileWriter(output));

		String l = sc.nextLine();
		String[] words = l.split(" ");

		int n = Integer.parseInt(words[0]);// stores the number of vertices in n
											// from input file
		int m = Integer.parseInt(words[1]);// stores the number of edges in m
											// from input file
		adjMat = new int[n + 1][n + 1];// creates adjacency list to store the
										// weights
		while (sc.hasNextLine()) { // loop until the last line of given input
									// file
			String l1 = sc.nextLine();
			String[] words1 = l1.split(" ");// splitting the 3 elements of input
											// file line and storing in array

			adjMat[Integer.parseInt(words1[0])][Integer
					.parseInt(words1[1])] = adjMat[Integer.parseInt(words1[1])][Integer.parseInt(words1[0])] = Integer
							.parseInt(words1[2]);// storing
			// weight
			// in
			// adjacency
			// matrix
		}
		for (int r = 1; r <= n; r++) {
			for (int c = r; c <= n; c++) {
				if (adjMat[r][c] != 0) {
					// System.out.print(r + " " + c + " " + adjMat[r][c] + "
					// ");// printing
					// //
					// adjacency
					// System.out.println();
				} // matrix
			}

		}
		arr = new int[n + 1];
		pos = new int[n + 1];
		key = new int[n + 1];
		min_st(n);// calling minimum spanning tree function
	}

	static void min_st(int n) {
		int d[] = new int[n + 1];
		int edge[] = new int[n + 1];
		HashMap<Integer, Integer> edgeMap = new HashMap<Integer, Integer>();
		int u;
		int c = 0;
		int weight = 0;
		d[1] = 0;// dist of starting node is 0 from starting node

		for (int v = 2; v <= n; v++) {
			d[v] = Integer.MAX_VALUE;// storing largest number for rest of the
										// verticies
		}

		for (int v = 1; v <= n; v++) {
			insert(v, d[v]);
		}
		List<Integer> S = new ArrayList<Integer>();
		S.add(0);

		while (c < n) {
			u = extract_min();// extracting minimum value from heap

			System.out.println(u);
			S.add(u);// storing this u in a seperate set S which is set of
						// visited verticies
			// System.out.println(c);
			// V.remove(V.indexOf(u));// removing the vertex u which has been
			// stored in S as we need to iterate on V-S
			// verticies
			c++;

			for (int v = 1; v <= n; v++) {
				if (adjMat[u][v] != 0 && adjMat[v][u] != 0) // checking
															// if
															// the
															// (u,v)edge
															// exists
															// in
															// Ajdacency
															// Matrix
					if (!S.contains(v)) {
						if (adjMat[u][v] < d[v]) {
							d[v] = adjMat[u][v];
							decrease_key(v, d[v]);
							edge[v] = u;// stores the other end of edge
							edgeMap.put(v, u);
						}
					}

			}
		}
		for (int i = 2; i <= n; i++) {
			System.out.println(edgeMap.get(S.get(i)) + " " + S.get(i) + " " + adjMat[S.get(i)][edgeMap.get(S.get(i))]);
			weight = weight + adjMat[S.get(i)][edgeMap.get(S.get(i))];
		}
		System.out.println("The weight is =" + weight);

	}

	static void insert(int node, int key_value) {
		s = s + 1;
		arr[s] = node;
		pos[node] = s;
		key[node] = key_value;
		heapify_up(s);

	}

	static void heapify_up(int size) {
		int temp, j = 0;
		while (size > 1) {
			j = (int) Math.floor(size / 2);
			if (key[arr[size]] < key[arr[j]]) {
				temp = arr[size];
				arr[size] = arr[j];
				arr[j] = temp;
				pos[arr[size]] = size;
				pos[arr[j]] = j;
				size = j;
			} else {
				break;
			}
		}
	}

	static int extract_min() {
		int minimum = arr[1];
		arr[1] = arr[s];
		pos[arr[1]] = 1;
		s--;

		heapify_down(1);

		return minimum;

	}

	static void decrease_key(int node, int key_value) {
		key[node] = key_value;
		heapify_up(pos[node]);

	}

	static void heapify_down(int i) {
		int j = 0;
		int temp = 0;
		while ((2 * i) <= s) {
			if ((2 * i) == s || key[arr[2 * i]] < key[arr[(2 * i) + 1]]) {
				j = (2 * i);
			} else {
				j = (2 * i) + 1;
			}
			if (key[arr[j]] < key[arr[i]]) {
				temp = arr[j];
				arr[j] = arr[i];
				arr[i] = temp;
				pos[arr[i]] = i;
				pos[arr[j]] = j;
				i = j;
			} else {
				break;
			}
		}

	}

}