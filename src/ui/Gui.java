package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import game.QuestionButton;
import game.Quiz;

public class Gui extends JFrame implements ActionListener {
	private Quiz quiz;
	private JButton start;
	private JButton nextQuestion;
	private QuestionButton[] answers = new QuestionButton[4];
	private JLabel label;
	private JTextField nameInput;
	private String name;
	private int score = 0;

	public Gui() {
		label = new JLabel("Enter a name and press Start to start", SwingConstants.CENTER);
		label.setBounds(50, 300, 300, 50);

		quiz = new Quiz();

		nameInput = new JTextField("Name");
		nameInput.setBounds(100, 150, 200, 50);

		start = new JButton("Start");
		setLayout(null);
		start.setBounds(100, 200, 200, 50);
		start.addActionListener(this);

		getContentPane().add(start);
		getContentPane().add(label);
		getContentPane().add(nameInput);

	}

	public void loadGame() {
		start.setEnabled(false);
		start.setVisible(false);
		nameInput.setEnabled(false);
		nameInput.setVisible(false);

		quiz.loadQuestions("questions.txt");

		loadNextQuestion();

	}

	public void loadNextQuestion() {
		answers = new QuestionButton[4];
		answers = quiz.getButtons();
		answers[0].setBounds(50, 300, 150, 50);
		answers[1].setBounds(200, 300, 150, 50);
		answers[2].setBounds(50, 350, 150, 50);
		answers[3].setBounds(200, 350, 150, 50);
		for (int i = 0; i < answers.length; i++) {
			answers[i].addActionListener(this);
			getContentPane().add(answers[i]);
		}

		getContentPane().remove(label);
		label = new JLabel(quiz.getCurrentQuestionText(), SwingConstants.CENTER);
		label.setBounds(50, 150, 300, 80);
		label.setFont(new Font("DialogInput", Font.BOLD, 20));
		getContentPane().add(label);

		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(start)) {
			if (nameInput.getText().equals("")) {
				label.setText("Ungültiger Name");
			} else {
				name = nameInput.getText();
				loadGame();
			}
		} else if (e.getSource().equals(nextQuestion)) {
			if (quiz.getIndex() < 10) {
				for (int j = 0; j < answers.length; j++) {
					getContentPane().remove(answers[j]);
				}
				loadNextQuestion();
				nextQuestion.setVisible(false);
				nextQuestion.setEnabled(false);
				getContentPane().remove(nextQuestion);
			} else {
				System.out.println(score);
			}
		} else {
			for (int i = 0; i < answers.length; i++) {
				if (e.getSource().equals(answers[i])) {
					if (answers[i].getStatus()) {
						answers[i].setBackground(Color.green);
						score++;

					} else {
						for (int j = 0; j < answers.length; j++) {
							if (answers[j].getStatus()) {
								answers[j].setBackground(Color.green);
							}
						}
						answers[i].setBackground(Color.red);
					}
					nextQuestion = new JButton("Weiter");
					nextQuestion.setBounds(100, 430, 200, 50);
					nextQuestion.addActionListener(this);
					getContentPane().add(nextQuestion);
					for (int j = 0; j < answers.length; j++) {
						answers[j].setEnabled(false);
					}
					i = answers.length;
				}

			}
		}

	}

}
