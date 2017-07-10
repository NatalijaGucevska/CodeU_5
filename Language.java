import java.util.ArrayList;
import java.util.List;

public class Language {
	ArrayList<String> sortedWords; 
	
	public Language(ArrayList<String> sortedWords) {
		this.sortedWords = sortedWords;
	}
	
	public List<Character> getSortedAlphabet() {
		List<Character> alphabet = new ArrayList<Character>();

		return alphabet; 
	}

	/**
	 * In order to perform topological sort we need to build a graph from the
	 * sorted words of the unknown language
	 */
	private Pair<Map<Character, Set<Character>>, Map<Character, Integer>> buildGraph() {
		Map<Character, Set<Character>> graph = new HashMap<Character, Set<Character>>();
		Map<Character, Integer> degree = new HashMap<Character, Integer>();

		for (int i = 0; i < sortedWords.size() - 1; i++) {
			String currenttWord = sortedWords.get(i);
			String adjacentWord = sortedWords.get(i + 1);

			boolean wait = true;
			int count = 0;
			int limit = Math.min(currenttWord.length(), adjacentWord.length());

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
					degree.put(adjacentWord.charAt(count), deg+1); 
					
					wait = false;
				}
				count++;
				wait = wait && count < limit;
			}
		}
		System.out.println(graph.toString());
		System.out.println(degree.toString());

		return new Pair<Map<Character, Set<Character>>, Map<Character, Integer>>(graph, degree);
	}

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
	 */
	private List<Character> topologicalSort(
			Pair<Map<Character, Set<Character>>, Map<Character, Integer>> graph_degree) {
		List<Character> result = new ArrayList<Character>();

		Map<Character, Set<Character>> graph = graph_degree.getFirst();
		Map<Character, Integer> degree = graph_degree.getSecond();

		Queue<Character> openVertices = new LinkedList<>();
		Set<Character> firstCharacter = findStartVertices(degree);

		// Add all vertices with 0 indegree at the top of the queue
		System.out.println(firstCharacter.toString());
		openVertices.addAll(firstCharacter);

		while (!openVertices.isEmpty()) {
			Character vertex = openVertices.poll();
			result.add(vertex);
			System.out.println(graph.toString());
			for (Character c : graph.getOrDefault(vertex, new HashSet<>())) {
				int vertex_degree = degree.get(c) - 1;
				if (vertex_degree == 0) {
					openVertices.add(c);
				}
				degree.put(c, vertex_degree);
			}
		}

		return result;
	}

}
