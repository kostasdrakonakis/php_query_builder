package constants;


public class Constants {

    /**
     * Ο λόγος που έχουμε φτιάξει αυτήν την κλάση είναι απλός.
     * Επειδή καλούμε συνέχεια και απο πολλές κλάσεις τα Strings αυτά
     * και επειδή μελοντικά θα είναι δύσκολο στο maintenance τα αρχικοποιούμε όλα εδώ σαν
     * static finals έτσι ώστε αν χρειαστεί να αλλάξουμε κάτι να το αλλάξουμε μόνο εδώ και όχι να
     * ψάχνουμε στα αρχεία.
     */
    public static final String HOST = "http://my.chatapp.info";
    public static final String PATH = "/quiz/";
    public static final String CATEGORIES_URL = HOST + PATH + "getcategories.php";
    public static final String CATEGORIES_EN_URL = HOST + PATH + "getcategoriesen.php";
    public static final String QUESTIONS_BY_CATEGORY_URL = HOST + PATH + "getquestionsforeachcategory.php";
    public static final String QUESTIONS_BY_CATEGORY_EN_URL = HOST + PATH + "getquestionsforeachcategoryen.php";
    public static final String QUESTIONS_EN_URL = HOST + PATH + "getallquestionsen.php";
    public static final String QUESTIONS_URL = HOST + PATH + "getallquestions.php";

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
    public static final String ADMIN_EMAIL = "libreq@staff.teicrete.gr";
    public static final String ENABLED_BUTTON_COLOR = "#FF4081";
    public static final String TEL = "+30 - 2810 - 379330";
    public static final int MY_PERMISSION_CODE = 1;
    public static final int TOTAL_PAGES = 2;
    public static final String PLAYER_SCORE = "player_score";
    public static final String LIST_SIZE = "list_size";
    public static final String USER_SAW_WELCOME_ACTIVITY = "user_saw";
    public static final String USER_SAW = "userSaw";
    public static final String USER_LIFES = "userlifenumber";
}
