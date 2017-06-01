import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

	// This is a class to plot the graph using an adjacency matrix
class Graph {
	
	// This is class to hold the values of the neighbors and the corresponding edge weights
	class Edge{
		int neighbor;
		int weight;
	}
	
	private int vertices;
	public static ArrayList<LinkedList<Edge>> adjacencyList = new ArrayList<LinkedList<Edge>>();			//adjacency List to store the neighbors and corresponding weights
	
	// Constructor to initialize adjacency list and check for the values of edge
	public Graph(int vertices) {
		for(int i=1; i<=vertices+1;i++)
		{
			adjacencyList.add(new LinkedList<Edge>());
		}
	}

	// Add an edge and its weight to the adjacency List
	public void addEdge(int u, int v, int w) {
			Edge e1 = new Edge();
			Edge e2 = new Edge();
			e1.neighbor=u;
			e1.weight=w;
			e2.neighbor=v;
			e2.weight=w;
			adjacencyList.get(v).add(e1);			// add u to the adjacency list of v
			adjacencyList.get(u).add(e2);			// add v to the adjacency list of u	
	}

	 //Print graph
	 public void printGraph() {
	 for (int i = 1; i <=vertices ; i++) {
		System.out.print(i+"--> "); 
		 for(Edge e: adjacencyList.get(i))
			 System.out.print("{"+ e.neighbor+", "+ e.weight+"}, " );
	 System.out.println();
	 }
	 }
}

	// This is a class to create a minimum Heap for the Prim's algorithm
class Heap {
	HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
	private int heapSize; 				// maintain the heap size
	private node[] heap; 				// array of type Class node to hold the value at every node of the heap

	// This is a class to hold the values of the vertex and the weight at every node of the heap
	class node {
		int vertex;
		int weight;
	}

	// Constructor to initialize the size of the heap
	public Heap(int size) {
		heapSize = 0;
		heap = new node[size + 1]; 		// initialize the array with the size of the heap required
	}

	// Check if the heap is empty
	public boolean isEmpty() {
		if (heapSize == 0) 				// check if the heapSize is 0
			return true; 				// if yes return true
		else
			return false; 				// else return false
	}

	// Check if the heap is full
	public boolean isFull() {
		if (heapSize == heap.length - 1) // check if heap is full
			return true; 				// if yes return true
		else
			return false; 				// else return false
	}

	// Get the parent of any node
	public int parent(int i) {
		return (int) Math.floor(i / 2); // return the parent of the element (using heap properties)
	}

	// Get the children for any node
	public int child(int i, int n) {
		return 2 * i + (n - 1);				// return child of the element (using heap properties)
	}

	// Extract the minimum from the heap and heapify down
	public node extractMin() {
		node minimum = heap[1];			// extract the root of the heap
		heap[1] = heap[heapSize];		// copy the last element of the heap to the root of the heap
		heapSize--;						// delete the last element and decrease the size of the heap
		heapifyDown(1);					// apply heapify down to the root of the heap
		return minimum;					// return the minimum
	}

	// Insert any new element to the heap
	public void insert(int vertex, int weight) {
		if (!isFull()) {												// check if heap is already full
			node n = new node();
			heapSize++;												// increment heap size
			n.weight = weight;										// assign weight to the node
			n.vertex = vertex;										// assign vertex to the node
			heap[heapSize] = n;										// insert heap in the array
			indexMap.put(n.vertex, heapSize);						// map the vertex with the index in the array
			heapifyUp(heapSize); 									// apply heapify up
		}
	}

	// Heapify Up
	public void heapifyUp(int i) {
		int childValue = heap[i].weight;				// initialize a variable with the weight of the element to be heapified up
		node nodeTemp = heap[i];						// create a temporary variable to store the element to be heapified up
		while (i > 1) {									// while the root of the heap is reached
			if (childValue < heap[parent(i)].weight) {	// check if weight of the child is less than the weight of the parent node
				heap[i] = heap[parent(i)];				// if yes swap the child and parent node using the temporary variable
				heap[parent(i)] = nodeTemp;
				indexMap.put(heap[i].vertex, i);
				i = parent(i);							// point to the parent element
				indexMap.put(heap[i].vertex, i);		// change the mapping of the vertex with the index in the array
			} else {
				break;
			}
		}
	}

	// Heapify down
	public void heapifyDown(int i) {
		int child;
		node nodeTemp = heap[i];						// create a temporary variable to store the element to be heapified down
		int parentValue = heap[i].weight;				// initialize a variable with the weight of the element to be heapified down
		while (child(i, 1) <= heapSize) {				// while the element has any more children
			if (child(i, 1) == heapSize || heap[child(i, 1)].weight <= heap[child(i, 2)].weight)	// check if there is only one child or 
																									// if the weight of the first child is less than
																									// or equal to the second child
				child = child(i, 1);																// if yes then child is the first child
			else
				child = child(i, 2);																// else it is the second child
			if (heap[child].weight < parentValue) {													// check if the weight of the child is less than the weight of the parent
				heap[i] = heap[child];																// if yes swap the child and parent node using the temporary variable
				heap[child] = nodeTemp;				
				indexMap.put(heap[i].vertex, i);
				i = child;																			// point to the child element
				indexMap.put(heap[i].vertex, i);
			} else
				break;
		}
	}

	// Decrease the value (weight) of any node
	public void decreaseKey(int vertex, int weight) {
		int i = indexMap.get(vertex);						// get the index of the vertex in the array
		if (heap[i].weight > weight) {						// check if the current weight is greater than the new weight
			heap[i].weight = weight;						// if yes decrease the weight 
			heapifyUp(i);									// apply heapify up
		}
	}

	// Print Heap
	 public void printHeap() {
	 System.out.print("\nHeap = ");
	 for (int i = 1; i <= heapSize; i++) {
	 System.out.print(heap[i].vertex + " ");
	 System.out.println(heap[i].weight);
	 }
	 }

	// Get the weight associated with a vertex
	public int getWeight(int vertex) {
		return heap[indexMap.get(vertex)].weight;		// return the weight of any vertex
	}
}

// This is the class to implement Prim's Algorithm
public class MST_Saurabh_Bajoria_50208005 {
	private int totalWeight = 0;				// variable to get the total weight of the minimum spanning tree
	private HashMap<Integer, Integer> edgeMap = new HashMap<Integer, Integer>();	// map the element to its parent element of the minimum spanning tree
	private ArrayList<Integer> visited = new ArrayList<Integer>();			// list to maintain of all the visited vertices (minimum spanning tree vertices)

	// Perform Prim's algorithm using the heap, graph to get the minimum spanning tree
	void primMST(Heap h, int v) {
		h.decreaseKey(1, 0);				// set the weight of an arbitrary vertex (1) to 0
		while (!h.isEmpty()) {				// until there are anymore elements in the heap
			Heap.node n = h.new node();		
			n = h.extractMin();				// extract the root of the heap
			int currentMinimum = n.vertex;	// temporary variable to store the weight of the root element
			totalWeight = totalWeight + n.weight;	// add the weight of the minimum vertex to the total weight
			visited.add(currentMinimum);			// add the minimum vertex to the visited list
			LinkedList<Graph.Edge>currentMinimumList= Graph.adjacencyList.get(currentMinimum);	// get the adjacency list of the current minimum vertex
			for(Graph.Edge s: currentMinimumList)							// for all the neighbors of current minimum
			{
					if (!visited.contains(s.neighbor)) {				// check if that neighbor is already in the minimum spanning tree
						if (h.getWeight(s.neighbor) > s.weight) {	// if not, check if the weight associated with that vertex is greater than the weight
																				// of the edge between itself and the current minimum
							h.decreaseKey(s.neighbor,s.weight);		// if yes update it with the weight of the edge between itself and the current minimum
							edgeMap.put(s.neighbor, currentMinimum);						// map the vertex with its parent
						}
					}	
			}
		}
	}

	// Print the Minimum Spanning tree to the output file
	void printMST(Writer write) {
		try {
			write.write(totalWeight + "\r\n");		// print the total weight of the minimum spanning tree
			for (int i : visited) {					// for all the vertices in the minimum spanning tree
				if (i != 1)							// except first vertex
					write.write(edgeMap.get(i) + " " + i  +"\r\n");		// print the vertex and its parent (edge)	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Main method
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream("input.txt")));			// read the Input file
			Writer write = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("output.txt")));		// write to the Output file
			String line;
			MST_Saurabh_Bajoria_50208005 mst = new MST_Saurabh_Bajoria_50208005();					// Instantiate the class of the Prim's algorithm
			int v, e;
			line = br.readLine();
			String inputs[] = line.split("\\s+");													// Read the numbers from the first line					
			v = Integer.parseInt(inputs[0]);														// initialize the number of vertices
			e = Integer.parseInt(inputs[1]);														// initialize the number of edges
			Graph g = new Graph(v);																// Instantiate the class for graph using vertices and edges
			Heap h = new Heap(v);																	// Instantiate the class for Heap using vertices
			while ((line = br.readLine()) != null) {												// until no more line exists in the input file
				if(e!= 0)
				{
				String edges[] = line.split("\\s+");												// split the line in an array of strings
				g.addEdge(Integer.parseInt(edges[0]), Integer.parseInt(edges[1]), Integer.parseInt(edges[2]));	// add an edge between the two vertices
				}
				else
				{
					break;
				}
				e--;
			}
			for (int i = 1; i <= v; i++) {															// for all the vertices
				h.insert(i, Integer.MAX_VALUE);														// insert in the heap with weight = Integer.MAX_VALUE
			}
			// bh.printHeap();
			// g.printGraph();
			mst.primMST(h, v);																		// call the Prim's algorithm method
			mst.printMST(write);																	// print the Minimum spanning tree
			br.close();	
			write.close();
		} catch (Exception E) {
			E.printStackTrace();
		}
		
		
	}
}
