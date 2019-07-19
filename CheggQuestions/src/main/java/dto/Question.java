package dto;

public class Question {
    private String question;
    private String fileType;

    public Question(String question, String fileTypeName) {
        this.question = question;
        this.fileType = fileTypeName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
