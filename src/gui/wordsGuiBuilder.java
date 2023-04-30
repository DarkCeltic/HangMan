package gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import wordController.wordController;

public class wordsGuiBuilder extends Canvas {

	private LinkedList<String> solved = new LinkedList<>();
	private LinkedList<String> wordGuess = new LinkedList<>();
	Map<Character, Boolean> wordGuessMap = new HashMap<>();

	final int width = 592;
	final int height = 560;
	private Font font;
	private boolean first = true;

	public wordsGuiBuilder() {
		setPreferredSize(new Dimension(592, 560));
		wordGuessMapBuilder();
	}

	public void setFirstTrue() {
		first = true;
	}

	@Override
	public void paint(Graphics g) {
		int y = 100;
		font = new Font("SansSerif", Font.BOLD, 50);
		g.setFont(font);

		if (first) {
			wordGuess.clear();
			solved.clear();
			wordGuessBuilder(g);
		} else
			guessedLetterAdd();
		
		for (String line : wordGuess) {
			int xStart = ((width / 2) - (g.getFontMetrics(font).stringWidth(line) / 2)) + 20;
			g.drawString(line, xStart, y += g.getFontMetrics().getHeight());
		}
	}

	public void wordGuessMapBuilder() {
		wordGuessMap.clear();
		for (String word : wordController.getWord().split(" ")) {
			for (char letter : word.toLowerCase().toCharArray()) {
				wordGuessMap.put(letter, false);
			}
		}
	}

	private void wordGuessBuilder(Graphics g) {
		first = false;
		String wordGuessTemp = "";
		String solvedTemp = "";
		for (String word : wordController.getWord().split(" ")) {
			int currentLength = g.getFontMetrics(font).stringWidth(solvedTemp);
			int nextWordLength = g.getFontMetrics(font).stringWidth(word);
			if ((currentLength + nextWordLength) + 10 > width - 50) {
				wordGuess.add(wordGuessTemp);
				wordGuessTemp = "";
				solved.add(solvedTemp);
				solvedTemp = "";
			}
			for (int i = 0; i < word.length(); i++) {
				wordGuessTemp += "_ ";
				solvedTemp += String.valueOf(word.charAt(i)) + " ";
			}
			wordGuessTemp += "  ";
			solvedTemp += "  ";
		}
		wordGuess.add(wordGuessTemp);
		solved.add(solvedTemp);
	}

	public void guessedLetterAdd() {
		List<Character> alreadyGuessed = new ArrayList<>();
		for (Character ch : wordGuessMap.keySet()) {
			if (wordGuessMap.get(ch)) {
				alreadyGuessed.add(ch);
			}
		}

		for (int i = 0; i < solved.size(); i++) {
			String temp = "";
			for (int j = 0; j < solved.get(i).length(); j++) {
				if (alreadyGuessed.contains(solved.get(i).toLowerCase().charAt(j))) {
					temp += solved.get(i).charAt(j);
				} else {
					temp += String.valueOf(wordGuess.get(i).charAt(j));
				}
			}
			wordGuess.set(i, temp);
		}

	}

	public LinkedList<String> getWordGuess() {
		return wordGuess;
	}

}
