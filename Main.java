import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// ==========================================Test from assignment=====================================================
		List<String> sortedWords = new ArrayList<>();
		sortedWords.add("ART");
		sortedWords.add("RAT");
		sortedWords.add("CAT");
		sortedWords.add("CAR");

		Language lang = new Language(sortedWords);
		System.out.println("Expected [A, T, R, C], result: " + lang.getSortedAlphabet().toString());

		// =============================================Test with incorrect order=================================================
		sortedWords = new ArrayList<>();
		sortedWords.add("ART");
		sortedWords.add("RAT");
		sortedWords.add("CAT");
		sortedWords.add("CAR");
		sortedWords.add("AAR");

		System.out.print("Expected -Graph contains cycles-, result: ");
		lang = new Language(sortedWords);
		try {
			lang.getSortedAlphabet();
		} catch (IllegalArgumentException e) {
			System.out.println("-" + e.getMessage() + "-");
		}

		// ==========================================Test with only one word=====================================================
		sortedWords = new ArrayList<>();
		sortedWords.add("ART");

		lang = new Language(sortedWords);
		System.out.println("Expected [A, R, T], result: " + lang.getSortedAlphabet().toString());

		// ==========================================Test with two empty words=====================================================
		sortedWords = new ArrayList<>();
		sortedWords.add("");
		sortedWords.add("");

		lang = new Language(sortedWords);
		System.out.println("Expected [], result: " + lang.getSortedAlphabet().toString());
		// ==========================================Test with two words from which one is empty=====================================================
		sortedWords = new ArrayList<>();
		sortedWords.add("");
		sortedWords.add("ART");

		lang = new Language(sortedWords);
		System.out.println("Expected [A, R, T], result: " + lang.getSortedAlphabet().toString());
		// ==========================================Test with words of different legth=====================================================
		sortedWords = new ArrayList<>();
		sortedWords.add("A");
		sortedWords.add("ART");
		sortedWords.add("CRTa");

		lang = new Language(sortedWords);
		System.out.println("Expected [A, a, R, T, C], result: " + lang.getSortedAlphabet().toString());
	}
}