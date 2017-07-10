import java.util.ArrayList;
import java.util.List;
import java.util.*;

/**
 * Class representing an invented language with sorted
 * list of words
 * @author Natalija
 */
public class Language {
	List<String> sortedWords;

	public Language(List<String> sortedWords) {
		if(sortedWords==null || sortedWords.size()==0) {
			throw new IllegalArgumentException();
		}
		this.sortedWords = sortedWords;
	}

	/**
	 * Creates an alphabet for the invented language
	 * from an ordered list of words. 
	 * return - Sorted list representing the alphabet 
	 */
	public List<Character> getSortedAlphabet() {
		
		//In order to perform topological sort we need to build a graph from the
		// sorted words of the unknown language
		Pair<Map<Character, Set<Character>>, Map<Character, Integer>> graph_degree = buildGraph();
		Map<Character, Set<Character>> graph = graph_degree.getFirst();
		Map<Character, Integer> degree = graph_degree.getSecond();
		
		return topologicalSort(graph, degree);
	}


	/**
	 * Calculates the graph and the indegrees of it's vertices. 
	 * 
	 * For the sorted words [ART, RAT, CAT, CAR] the graph would be: 
	 * 		A->R 
	 * 		R->C
	 * 		T->R
	 * The indegrees of the vertices would be: 
	 * 		A: 0
	 * 		R: 2
	 * 		C: 1
	 * 		T: 0
	 * 
	 * @return - A pair of two Maps: The first one representing the graph created 
	 * from the sorted words and the second one representing the indegrees of 
	 * each vertex of the created graph. 
	 */
	private Pair<Map<Character, Set<Character>>, Map<Character, Integer>> buildGraph() {
		Map<Character, Set<Character>> graph = new HashMap<Character, Set<Character>>();
		Map<Character, Integer> degree = new HashMap<Character, Integer>();
		
		if(sortedWords.size()==1){
			String currentWord = sortedWords.get(0);
			for(char c: currentWord.toCharArray()) {
				graph.putIfAbsent(c, new HashSet<>());
				degree.putIfAbsent(c, 0);
			}
		} else {
			for (int i = 0; i < sortedWords.size() - 1; i++) {
				String currenttWord = sortedWords.get(i);
				String adjacentWord = sortedWords.get(i + 1);
	
				boolean wait = true;
				int count = 0;
				int limit = Math.min(currenttWord.length(), adjacentWord.length());
				//Don't enter if word is empty
				wait = wait && count < limit;
				
				while (wait) {
					if (currenttWord.charAt(count) != adjacentWord.charAt((count))) {
						Set<Character> set = graph.get(currenttWord.charAt(count));
						if (set == null) {
							set = new HashSet<>();
							graph.put(currenttWord.charAt(count), set);
						}
						set.add(adjacentWord.charAt(count));
	
						// Make sure that vertices with zero indegree will be in the
						// degree map
						degree.putIfAbsent(currenttWord.charAt(count), 0);
	
						// Keep track of the indegree of every vertex while
						// constructing the graph
						int deg = degree.getOrDefault(adjacentWord.charAt(count), 0);
						degree.put(adjacentWord.charAt(count), deg + 1);
	
						wait = false;
					}
					count++;
					wait = wait && count < limit;
				}
			}
		}

		return new Pair<Map<Character, Set<Character>>, Map<Character, Integer>>(graph, degree);
	}

	/**
	 * @param degree - Indegrees of the vertices from a given graph
	 * @return A set of vertices with zero indegree
	 */
	private Set<Character> findStartVertices(Map<Character, Integer> degree) {
		Set<Character> vertices = new HashSet<>();

		for (Character character : degree.keySet()) {
			if (degree.get(character) == 0) {
				vertices.add(character);
			}
		}
		return vertices;
	}

	/**
	 * Topological sort of the vertices of an acyclic graph
	 * 
	 * For the language with sorted words [ART, RAT, CAT, CAR] 
	 * valid sorts are: 
	 * [A, T, R, C] and [T, A, R, C]
	 * 
	 * return - A list of sorted vertices.  
	 * 
	 */
	private List<Character> topologicalSort(Map<Character, Set<Character>> graph, Map<Character, Integer> degree) {
		List<Character> result = new ArrayList<Character>(); 

		Queue<Character> openVertices = new LinkedList<>();
		Set<Character> firstCharacter = findStartVertices(degree);

		// Add all vertices with 0 indegree at the top of the queue
		openVertices.addAll(firstCharacter);
		
		while (!openVertices.isEmpty()) {
			Character vertex = openVertices.poll();
			result.add(vertex);
			for (Character c : graph.getOrDefault(vertex, new HashSet<>())) {
				int vertex_degree = degree.get(c) - 1;
				if (vertex_degree == 0) {
					openVertices.add(c);
				}
				degree.put(c, vertex_degree);
			}
		}
		
		//Make sure that all vertices are in result
		if(degree.size() != result.size()) {
			throw new IllegalArgumentException("Graph contains cycles"); 
		}
		return result;
	}

}
