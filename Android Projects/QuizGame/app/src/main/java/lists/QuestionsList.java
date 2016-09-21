package lists;


import java.util.List;

public class QuestionsList {
    private String name;
    private List<String> answers;
    private List<String> iscorrect;

    public QuestionsList(String name, List<String> answers, List<String> iscorrect) {
        this.name = name;
        this.answers = answers;
        this.iscorrect = iscorrect;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<String> getIscorrect() {
        return iscorrect;
    }

    public void setIscorrect(List<String> iscorrect) {
        this.iscorrect = iscorrect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
