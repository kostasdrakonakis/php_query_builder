package lists;


public class QuestionsList {
    private String name;
    private String[] answers;
    private String[] correct;

    public QuestionsList(String name, String[] answers, String[] correct) {
        this.name = name;
        this.answers = answers;
        this.correct = correct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String[] getCorrect() {
        return correct;
    }

    public void setCorrect(String[] correct) {
        this.correct = correct;
    }
}
