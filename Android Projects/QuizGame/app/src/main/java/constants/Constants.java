package constants;


public class Constants {

    /**
     * Ο λόγος που έχουμε φτιάξει αυτήν την κλάση είναι απλός.
     * Επειδή καλούμε συνέχεια και απο πολλές κλάσεις τα Strings αυτά
     * και επειδή μελοντικά θα είναι δύσκολο στο maintenance τα αρχικοποιούμε όλα εδώ σαν
     * static finals έτσι ώστε αν χρειαστεί να αλλάξουμε κάτι να το αλλάξουμε μόνο εδώ και όχι να
     * ψάχνουμε στα αρχεία.
     */
    public static final String CATEGORIES_URL = "http://my.chatapp.info/quiz/getcategories.php";
    public static final String CATEGORIES_EN_URL = "http://my.chatapp.info/quiz/getcategoriesen.php";
    public static final String QUESTIONS_BY_CATEGORY_URL = "http://my.chatapp.info/quiz/getquestionsforeachcategory.php";
    public static final String QUESTIONS_BY_CATEGORY_EN_URL = "http://my.chatapp.info/quiz/getquestionsforeachcategoryen.php";

    public static final String CATEGORIES_ARRAY = "categories";
    public static final String CATEGORIES_ARRAY_EN = "categories_en";
    public static final String QUESTIONS_BY_CATEGORY_ARRAY = "quiz";
    public static final String QUESTIONS_BY_CATEGORY_EN_ARRAY = "quizen";
    public static final String QUESTIONS_ANSWERS_ARRAY = "question_answers";
    public static final String QUESTION_NAME_JSON_NAME = "question_name";
    public static final String ISCORRECT_NAME_JSON_NAME = "iscorrect";
    public static final String ANSWER_NAME_JSON_NAME = "answer";
    public static final String CATEGORY_JSON_NAME = "cat_name";
    public static final String CATEGORY_ID_JSON_NAME = "cat_id";
    public static final String CATEGORY_ID_POST_NAME = "category_id";
    public static final String CORRECTANSWER = "001";

    public static final String PREFERENCES_FILE = "option-preferences";
    public static final String LANGUAGE_PREFS_FILE = "DisplayLanguage";
    public static final String LIFES_PREFS_FILE = "Lifes";
    public static final String CATEGORIES_INTENT_NAME= "name";
    public static final String CATEGORIES_INTENT_ID= "id";
    public static final String CHARACTER_ENCODING = "UTF-8";
    public static final String EN = "en";
    public static final String GR = "el";
    public static final String GR_FULL = "Ελληνικά";
    public static final String GR_EN_FULL = "Greek";
    public static final String EN_FULL = "Αγγλικά";
    public static final String EN_EN_FULL = "English";
}
