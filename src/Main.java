import javax.swing.JFrame;

import game.Quiz;
import ui.Gui;

public class Main {

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.setBounds(200, 150, 400, 600);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		gui.setAlwaysOnTop(true);
		gui.setResizable(false);

	}

}
