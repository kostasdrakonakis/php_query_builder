package lists;


public class CategoryList {
    private String name;
    private String[] questions;
    private String[] answers;
    private String[] correct;

    public CategoryList(String name, String[] questions, String[] answers, String[] correct) {
        this.name = name;
        this.questions = questions;
        this.answers = answers;
        this.correct = correct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getQuestions() {
        return questions;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
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
