package dto;

import java.util.List;

public class QuestionsFromJsonFile {
    private List<Record> questions;

    public List<Record> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Record> questions) {
        this.questions = questions;
    }
}
