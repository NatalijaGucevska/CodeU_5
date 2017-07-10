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
			}
		}
		return graph;
	}

}
