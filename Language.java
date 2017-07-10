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
	private Map<Character, Set<Character>> buildGraph() {
		Map<Character, Set<Character>> graph = new HashMap<Character, Set<Character>>();

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
					wait = false;
				}
				count++;
				wait = count < limit;
			}
		}
		return graph;
	}

}
