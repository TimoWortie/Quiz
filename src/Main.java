import javax.swing.JFrame;

import game.Quiz;
import ui.Gui;

public class Main {

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.setBounds(0, 0, 1600, 900);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		gui.setAlwaysOnTop(true);

		Quiz q = new Quiz();
		q.loadQuestions("questions.txt");

	}

}
