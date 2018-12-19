package game;

import javax.swing.JButton;

public class QuestionButton extends JButton {
	boolean status = false;

	public QuestionButton(String content) {
		super(content);
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
