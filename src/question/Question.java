package question;

import java.util.ArrayList;

public class Question {
	private String text;
	private ArrayList<Answer> answers = new ArrayList();

	public Question(String content) {
		this.text = content;
	}

	public void addAnswer(Answer answer) {
		answers.add(answer);
	}

	public String getText() {
		return text;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setText(String content) {
		this.text = content;
	}

}
