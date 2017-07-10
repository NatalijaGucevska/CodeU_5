import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		//==========================================Test from assignment=====================================================
		List<String> sortedWords = new ArrayList<>(); 
		sortedWords.add("ART"); 
		sortedWords.add("RAT"); 
		sortedWords.add("CAT"); 
		sortedWords.add("CAR"); 

		Language lang = new Language(sortedWords); 
		System.out.println("Expected [A, T, R, C], result: " + lang.getSortedAlphabet().toString());

		
		//=============================================Test with incorrect order=================================================
		sortedWords = new ArrayList<>(); 
		sortedWords.add("ART"); 
		sortedWords.add("RAT"); 
		sortedWords.add("CAT"); 
		sortedWords.add("CAR"); 
		sortedWords.add("AAR"); 
	
	
		lang = new Language(sortedWords); 
		System.out.println("Expected [], result: " + lang.getSortedAlphabet().toString());
	}
}