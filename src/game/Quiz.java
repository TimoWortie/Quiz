package game;

import java.util.ArrayList;
import java.util.List;

import io.FileReader;
import question.Answer;
import question.Question;

public class Quiz {
	ArrayList<Question> questions=new ArrayList();
	public void loadQuestions(String path){
		FileReader reader=new FileReader();
		List<String> lines=reader.readFileLines(path);
		for(String line:lines){
			String[] split=line.split(";");
			Question question=new Question(split [0]);
			for(int i=1;i<split.length;i++){
				String[] answer=split[i].split("_");
				question.addAnswer(new Answer(answer[0], Boolean.valueOf(answer[1])));
			}
			questions.add(question);
		}
		
	}
}
