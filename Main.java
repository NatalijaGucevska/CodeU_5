import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> sortedWords = new ArrayList<>(); 
		sortedWords.add("ART"); 
		sortedWords.add("RAT"); 
		sortedWords.add("CAT"); 
		sortedWords.add("CAR"); 

		Language lang = new Language(sortedWords); 
		System.out.println(lang.getSortedAlphabet().toString());
	}

}
