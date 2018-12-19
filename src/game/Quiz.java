package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import io.FileReader;
import question.Answer;
import question.Question;

public class Quiz {
	ArrayList<Question> questions = new ArrayList();
	List<Question> usedQuestions = new ArrayList();
	String currentQuestionText = "";
	int index = 0;

	public void loadQuestions(String path) {
		FileReader reader = new FileReader();
		List<String> lines = reader.readFileLines(path);
		for (String line : lines) {
			String[] split = line.split("\t");
			Question question = new Question(split[0]);
			for (int i = 1; i < split.length; i++) {
				String[] answer = split[i].split("_");
				question.addAnswer(new Answer(answer[0], Boolean.valueOf(answer[1])));
			}
			questions.add(question);
		}
		usedQuestions = getQuestions(10);
	}

	public Question getNextQuestion() {
		if (index < usedQuestions.size()) {
			currentQuestionText = usedQuestions.get(index).getText();
			Question q = usedQuestions.get(index);
			index++;
			return q;
		} else {
			return null;
		}
	}

	public List<Question> getQuestions(int count) {
		if (questions.size() <= count) {
			return questions;
		} else {
			Collections.shuffle(questions);
			return questions.subList(0, count);
		}
	}

	public String getCurrentQuestionText() {
		return currentQuestionText;
	}

	public QuestionButton[] getButtons() {
		Question q = getNextQuestion();
		QuestionButton[] buttons = new QuestionButton[4];
		ArrayList<Answer> answers = q.getAnswers();
		if (answers.size() != 4) {
			throw new NullPointerException();
		}
		Collections.shuffle(answers);
		for (int i = 0; i < buttons.length; i++) {
			if(answers.get(i).getContent().length()>6){
				buttons[i] = new QuestionButton("<html><body><br>"+answers.get(i).getContent()+"</body></html>");
			}else{
				buttons[i] = new QuestionButton(answers.get(i).getContent());
			}
			buttons[i].setAlignmentY(SwingConstants.CENTER);
			buttons[i].setStatus(answers.get(i).getStatus());
		}
		return buttons;
	}

	public int getIndex() {
		return index;
	}
}
