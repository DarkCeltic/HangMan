package Runner;

import java.io.FileNotFoundException;

import gui.GuiInit;
import wordController.wordController;

class main {
	
	public static GuiInit guiInit;
	
	public static void main(String[] args) throws FileNotFoundException {
		wordController wordController = new wordController();
		wordController.readWordsFile();
		guiInit = new GuiInit();
		guiInit.buildGui();
//		System.out.println(guiInit.wordPanel.getWidth());
//		System.out.println(guiInit.wordPanel.getHeight());
	}
}