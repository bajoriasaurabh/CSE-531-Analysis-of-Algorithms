import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class LCS_Saurabh_Bajoria_sbajoria {
	private int matrix[][];
	private String track[][];
	char str1[];
	char str2[];

	LCS_Saurabh_Bajoria_sbajoria(char str1[], char str2[]) {	//Constructor to initialize matrices
		matrix = new int[str1.length + 1][str2.length + 1];		// 2D matrix of size String1.length x String2.length to store the length of LCS at every stage
		track = new String[str1.length + 1][str2.length + 1];	// 2D matrix of size String1.length x String2.length to store directions, used to print the LCS
		this.str1 = str1;
		this.str2 = str2;
	}
	
	//Function to compute the LCS and store the value in the 2D matrix "matrix"
	public int lcs() {
		for (int i = 1; i < matrix.length; i++) {		// iterate over the rows of the 2D matrix
			for (int j = 1; j < matrix[i].length; j++) {	//iterate over the columns of the ith row of the 2D matrix
				if (str1[i - 1] == str2[j - 1]) {			//if character of both the string matches
					matrix[i][j] = matrix[i - 1][j - 1] + 1;	//increment the diagonal element by 1
					track[i][j] = "diagonal";					//update the corresponding element of the track matrix to diagonal 
				} else if (matrix[i][j - 1] >= matrix[i - 1][j]) {	//if no match, then update the (i,j)th element of the matrix with the (maximum of the element (i-1,j)th and (i,j-1)th term) +1
					matrix[i][j] = matrix[i][j - 1];
					track[i][j] = "left";					//update the corresponding element of the track matrix to left
				} else if (matrix[i][j - 1] < matrix[i - 1][j]) {
					matrix[i][j] = matrix[i - 1][j];
					track[i][j] = "up";						//update the corresponding element of the track matrix to up
				}
			}
		}
		return matrix[str1.length][str2.length];			//return the last element of the matrix, giving the length of the LCS
	}

	//Function to print the LCS
	public void printLcs(Writer write) {
		int m = str1.length;
		int n = str2.length;
		char result[] = new char[matrix[str1.length][str2.length]];		//Array to store LCS
		int i = 0;
		while (m > 0 && n > 0) {										//until the first element of the 2D matrix is reached
			if (track[m][n] == "diagonal") {							// if element is equal to Diagonal
				m = m - 1;												//decrement row
				n = n - 1;												//decrement column
				result[i] = str1[m];									//save the character in the result
				i++;
			} else if (track[m][n] == "up") {							//if element is equal to up
				m = m - 1;												//decrement row
			} else if (track[m][n] == "left") {							//if element is equal to left
				n = n - 1;												//decrement column
			}
		}
		for (int j = result.length - 1; j >= 0; j--) {					//Print the LCS
			try {
				write.write(result[j]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));		//read the input
			Writer write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt")));			//write the output
			String str1 = br.readLine();
			String str2 = br.readLine();
			LCS_Saurabh_Bajoria_sbajoria LCS = new LCS_Saurabh_Bajoria_sbajoria(str1.toCharArray(), str2.toCharArray());
			int count = LCS.lcs();
			write.write(count + "\r\n");
			LCS.printLcs(write);
			br.close();
			write.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}