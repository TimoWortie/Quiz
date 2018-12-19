package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.JSONException;

import db.DatabaseHandler;
import db.Score;
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
	private JButton restart, upload;
	private int score;
	private JTable table;

	public Gui() {
		buildGui();

	}

	private void buildGui() {
		score = 0;
		name = "";

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
		this.repaint();
	}

	private void loadGame() {
		start.setEnabled(false);
		start.setVisible(false);
		nameInput.setEnabled(false);
		nameInput.setVisible(false);

		quiz.loadQuestions("questions.txt");

		loadNextQuestion();

	}

	private void loadNextQuestion() {
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
		label = new JLabel("<html><body><br>" + quiz.getCurrentQuestionText() + "</body></html>",
				SwingConstants.CENTER);
		label.setBounds(50, 100, 300, 150);
		label.setFont(new Font("DialogInput", Font.BOLD, 20));
		getContentPane().add(label);

		this.repaint();
	}

	private void showEndScreen() {
		nextQuestion.setVisible(false);
		nextQuestion.setEnabled(false);
		getContentPane().remove(nextQuestion);
		for (int i = 0; i < answers.length; i++) {
			getContentPane().remove(answers[i]);
		}
		label.setText("Name: " + name + ", Score: " + score);
		restart = new JButton("Neues Spiel");
		restart.setBounds(100, 300, 200, 50);
		restart.addActionListener(this);
		upload = new JButton("Upload Score");
		upload.setBounds(100, 350, 200, 50);
		upload.addActionListener(this);
		getContentPane().add(restart);
		getContentPane().add(upload);
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
				showEndScreen();
			}
		} else if (e.getSource().equals(upload)) {
			DatabaseHandler db = new DatabaseHandler();
			try {
				db.postScoreToUrl(new Score(name, score));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ArrayList<Score> scores = null;
			try {
				scores = db.readJsonFromUrl();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			String[] columnNames = { "Name", "Score" };
			String[][] rowData = new String[scores.size()][2];
			for (int i = 0; i < scores.size(); i++) {
				rowData[i][0] = scores.get(i).getName();
				rowData[i][1] = String.valueOf(scores.get(i).getScore());
			}
			table = new JTable(rowData, columnNames);
			table.setEnabled(false);
			table.setBounds(100, 50, 200, 200);
			getContentPane().add(table);
			label.setBounds(50, 250, 300, 50);
			label.setText("Score hochgeladen");
			upload.setEnabled(false);
		} else if (e.getSource().equals(restart)) {
			getContentPane().remove(restart);
			getContentPane().remove(label);
			getContentPane().remove(upload);
			getContentPane().remove(table);
			buildGui();
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
