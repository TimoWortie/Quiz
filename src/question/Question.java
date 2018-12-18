package question;

import java.util.ArrayList;

public class Question {
	private String content;
	private ArrayList<Answer> answers = new ArrayList();

	public Question(String content) {
		this.content = content;
	}

	public void addAnswer(Answer answer) {
		answers.add(answer);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
