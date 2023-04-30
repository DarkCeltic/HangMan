package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;

import wordController.wordController;

public class GuiInit implements ActionListener, KeyListener {

	static int WIDTH = 1000;
	static int HEIGHT = 800;

	static JPanel hangManPanel;
	public static JPanel wordPanel;
	static JPanel keyboardPanel;
	private static Map<String, JButton> keyboardButtons;
	private static BorderLayout layout;
	private static GridLayout gridLayout;
	private static JPanel game;
	private static JTable table;
	private static int incorrect;
	private static wordsGuiBuilder wordGuiBuilder;
	private static JButton restart;
	private static JLabel scoreLabel;
	private static JTextArea textArea;
	private static int score;
	private Set<String> keypressed;

	public void buildGui() throws FileNotFoundException {
		// Frame setup
		keypressed = new HashSet<>();
		JFrame frame = new JFrame("Hangman");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		table = new JTable();
		buildLayouts();
		hangman_gui.buildHangman();
		wordController.setWord();

		setHangManPanel();
		setKeyboardPanel();
		setWordPanel();
		setGamePanel();

		frame.getContentPane().add(game);

		frame.setVisible(true);
		wordGuiBuilder.requestFocusInWindow();
	}

	private static void setHangManPanel() {
		hangManPanel = new JPanel();
		hangManPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		for (JLabel temp : hangman_gui.hangManGuy.values()) {
			hangManPanel.add(temp);
		}
		hangman_gui.hangManGuy.get("blank").setVisible(true);
	}

	private void setKeyboardPanel() {
		keyboardPanel = new JPanel();
		keyboardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		keyboardPanel.setPreferredSize(new Dimension(50, 200));
		keyboardPanel.setLayout(new GridLayout(4, 1));
		addLettersToLettersPanel();

	}

	private void setWordPanel() {
		wordPanel = new JPanel();
		wordPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		wordGuiBuilder = new wordsGuiBuilder();
		wordGuiBuilder.addKeyListener(this);
		wordPanel.add(wordGuiBuilder);
	}

	private static void setGamePanel() {
		game = new JPanel();
		game.setLayout(layout);

		game.add(hangManPanel, BorderLayout.WEST);
		game.add(wordPanel, BorderLayout.CENTER);
		game.add(keyboardPanel, BorderLayout.SOUTH);
		game.add(wordPanel);
	}

	private static void buildLayouts() {
		layout = new BorderLayout();
		layout.setHgap(10);
		layout.setVgap(10);

		gridLayout = new GridLayout(0, 7);
		gridLayout.setHgap(10);
		gridLayout.setVgap(10);
	}

	private void addLettersToLettersPanel() {
		String[] row1Keys = { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p" };
		String[] row2Keys = { "a", "s", "d", "f", "g", "h", "j", "k", "l" };
		String[] row3Keys = { "z", "x", "c", "v", "b", "n", "m" };

		keyboardButtons = new HashMap<>();
		for (int i = 0; i < 4; i++) {
			JPanel temp = new JPanel();
			switch (i) {
			case 0:
				for (int row1 = 0; row1 < row1Keys.length; row1++) {
					keyboardButtons.put(row1Keys[row1], new JButton(row1Keys[row1]));
					keyboardButtons.get(row1Keys[row1]).addActionListener(this);
					temp.add(keyboardButtons.get(row1Keys[row1]));
				}
				keyboardPanel.add(temp);
				break;
			case 1:
				for (int row2 = 0; row2 < row2Keys.length; row2++) {
					keyboardButtons.put(row2Keys[row2], new JButton(row2Keys[row2]));
					keyboardButtons.get(row2Keys[row2]).addActionListener(this);
					temp.add(keyboardButtons.get(row2Keys[row2]));
				}
				keyboardPanel.add(temp);
				break;
			case 2:
				for (int row3 = 0; row3 < row3Keys.length; row3++) {
					keyboardButtons.put(row3Keys[row3], new JButton(row3Keys[row3]));
					keyboardButtons.get(row3Keys[row3]).addActionListener(this);
					temp.add(keyboardButtons.get(row3Keys[row3]));
				}
				keyboardPanel.add(temp);
				break;
			case 3:
				restart = new JButton("New Game");
				restart.addActionListener(this);

				scoreLabel = new JLabel("Score");
				textArea = new JTextArea();
				textArea.setEditable(false);
				textArea.setText(score + "/" + wordController.getTotalWords());
				temp.add(restart);
				temp.add(scoreLabel);
				temp.add(textArea);
				keyboardPanel.add(temp);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		action(e.getActionCommand());
	}

	public void setHangImageController() {
		switch (incorrect) {
		case 0:
			setHangImage("blank");
			enableKeyboardButtons();
			break;
		case 1:
			setHangImage("gallows");
			break;
		case 2:
			setHangImage("head");
			break;
		case 3:
			setHangImage("body");
			break;
		case 4:
			setHangImage("rightArm");
			break;
		case 5:
			setHangImage("leftArm");
			break;
		case 6:
			setHangImage("rightLeg");
			break;
		case 7:
			setHangImage("leftLeg");
			disableKeyboardButtons();
			break;
		}
	}

	private void setHangImage(String image) {
		for (String hang : hangman_gui.hangManGuy.keySet()) {
			if (hang.equals(image)) {
				hangman_gui.hangManGuy.get(hang).setVisible(true);
			} else {
				hangman_gui.hangManGuy.get(hang).setVisible(false);
			}
		}
	}

	private void disableKeyboardButtons() {
		for (String keyLetter : keyboardButtons.keySet()) {
			keyboardButtons.get(keyLetter).setEnabled(false);
		}
	}

	private void enableKeyboardButtons() {
		for (String keyLetter : keyboardButtons.keySet()) {
			keyboardButtons.get(keyLetter).setEnabled(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		String key = KeyEvent.getKeyText(e.getKeyCode()).toLowerCase();
		if (!keypressed.contains(key) && Character.isAlphabetic(e.getKeyChar())) {
			keypressed.add(key);
			action(key);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	private void action(String key) {
		if (key.equals("New Game")) {
			incorrect = 0;
			wordGuiBuilder.setFirstTrue();
			wordController.setWord();
			wordGuiBuilder.wordGuessMapBuilder();
			setHangImageController();
			wordGuiBuilder.repaint();
			keypressed.clear();
			wordGuiBuilder.requestFocusInWindow();
		} else {
			keyboardButtons.get(key).setEnabled(false);
			if (wordController.getWord().toLowerCase().contains(key)) {
				wordGuiBuilder.wordGuessMap.put(key.toCharArray()[0], true);
				wordGuiBuilder.repaint();
				if (!wordGuiBuilder.wordGuessMap.containsValue(false)) {
					wordController.removeWord();
					score++;
					textArea.setText(score + "/" + wordController.getTotalWords());
					disableKeyboardButtons();
				}
			} else {
				incorrect++;
				setHangImageController();
			}
		}
	}
}
