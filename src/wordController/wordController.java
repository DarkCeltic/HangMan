package wordController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class wordController {

	private static String genreString;
	private static String subString;
	private static String word;
	private static ArrayList<String> genre = new ArrayList<>();
	private static int totalWords;

	static Random rand = new Random();

	private static Random randomSubGenre;
	private static Random randomItem;
	private static Map<String, Map<String, ArrayList<String>>> wordController = new HashMap<>();

	public void readWordsFile() throws FileNotFoundException {
		File wordFile = new File("./src/HangManWords");
		Scanner wordScanner = new Scanner(wordFile);
		String line = "";
		while (wordScanner.hasNext()) {
			String genreTemp = "";
			Map<String, ArrayList<String>> subGenre = new HashMap<>();
			ArrayList<String> items = new ArrayList<>();
			if (line.isBlank())
				line = wordScanner.nextLine();
			String subGenreTemp = "";
			do {
				if (line.contains("Sub")) {
					subGenreTemp = line.trim().split(":")[1];
					line = wordScanner.nextLine();
				} else if (line.contains("Genre")) {
					genreTemp = line.split(":")[1];
					line = wordScanner.nextLine();
				} else if (line.contains("(")) {
					line = wordScanner.nextLine();
					while (!line.contains(")") && wordScanner.hasNext()) {
						items.add(line.trim().replaceAll(",", ""));
						totalWords++;
						line = wordScanner.nextLine();
					}
					subGenre.put(subGenreTemp, new ArrayList<String>(items));
					items.clear();
					if (wordScanner.hasNext() && !line.contains("Genre"))
						line = wordScanner.nextLine();
				}
			} while (!line.contains("Genre") && wordScanner.hasNext());
			genre.add(genreTemp);
			wordController.put(genreTemp, subGenre);
		}
	}

	public static void setWord() {
		int randGenre = rand.nextInt(genre.size());

		genreString = genre.get(randGenre);
		Map<String, ArrayList<String>> subGenre = wordController.get(genreString);

		ArrayList<String> subGenreTemp = new ArrayList<>(subGenre.keySet());
		int s = rand.nextInt(subGenreTemp.size());
		subString = subGenreTemp.get(s);
		ArrayList<String> items = subGenre.get(subString);

		int w = rand.nextInt(items.size());
		word = items.get(w);
	}

	public static String getWord() {
		return word;
	}
	
	public static String getGenreString() {
		return genreString;
	}

	public static String getSubString() {
		return subString;
	}

	public static int getTotalWords() {
		return totalWords;
	}
	
	public static void removeWord() {
		wordController.get(genreString).get(subString).remove(word);
		if (wordController.get(genreString).get(subString).size() <= 0) {
			wordController.get(genreString).remove(subString);
		}
		if (wordController.get(genreString).size() <= 0) {
			genre.remove(genreString);
			wordController.remove(genreString);
		}
	}

}
