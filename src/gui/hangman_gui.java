package gui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class hangman_gui {

	static Map<String, JLabel> hangManGuy = new HashMap<>();

	public static void buildHangman() {
		initMap();
		File img = new File("./images/hangman");
		for (File step : img.listFiles()) {
			ImageIcon icon = new ImageIcon(step.toString());
			String part = step.toString().substring(step.toString().lastIndexOf("/") + 1,
					step.toString().lastIndexOf("."));
			hangManGuy.put(part, new JLabel(icon));
			hangManGuy.get(part).setVisible(false);
		}
	}

	public static void initMap() {
		hangManGuy.put("blank", null);
		hangManGuy.put("gallows", null);
		hangManGuy.put("head", null);
		hangManGuy.put("leftArm", null);
		hangManGuy.put("rightArm", null);
		hangManGuy.put("body", null);
		hangManGuy.put("leftLeg", null);
		hangManGuy.put("rightLeg", null);

	}

}
