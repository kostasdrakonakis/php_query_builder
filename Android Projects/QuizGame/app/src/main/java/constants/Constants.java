package constants;


public class Constants {

    /**
     * Ο λόγος που έχουμε φτιάξει αυτήν την κλάση είναι απλός.
     * Επειδή καλούμε συνέχεια και απο πολλές κλάσεις τα Strings αυτά
     * και επειδή μελοντικά θα είναι δύσκολο στο maintence τα αρχικοποιούμε όλα εδώ σαν
     * static finals έτσι ώστε αν χρειαστεί να αλλάξουμε κάτι να το αλλάξουμε μόνο εδώ και όχι να
     * ψάχνουμε στα αρχεία.
     */
    public static final String CATEGORIES_URL = "http://my.chatapp.info/quiz/getcategories.php";
    public static final String CATEGORIES_EN_URL = "http://my.chatapp.info/quiz/getcategoriesen.php";
    public static final String QUESTIONS_URL = "http://my.chatapp.info/quiz/getquestions.php";
    public static final String QUESTIONS_EN_URL = "http://my.chatapp.info/quiz/getquestionsen.php";
    public static final String ANSWERS_URL = "http://my.chatapp.info/quiz/getanswers.php";
    public static final String ANSWERS_EN_URL = "http://my.chatapp.info/quiz/getanswersen.php";
    public static final String CATEGORY_ORGANOSI = "http://my.chatapp.info/quiz/getorganwsi.php";
    public static final String CATEGORY_ORGANOSI_EN = "http://my.chatapp.info/quiz/getorganwsien.php";
    public static final String CATEGORY_BASEIS = "http://my.chatapp.info/quiz/getbaseis.php";
    public static final String CATEGORY_BASEIS_EN = "http://my.chatapp.info/quiz/getbaseisen.php";
    public static final String CATEGORY_PERIODIKA = "http://my.chatapp.info/quiz/getperiodika.php";
    public static final String CATEGORY_PERIODIKA_EN = "http://my.chatapp.info/quiz/getperiodikaen.php";
    public static final String CATEGORY_KATALOGOS = "http://my.chatapp.info/quiz/getkatalogos.php";
    public static final String CATEGORY_KATALOGOS_EN = "http://my.chatapp.info/quiz/getkatalogosen.php";

    public static final String ORGANOSI_ARRAY = "organwsi";
    public static final String CATEGORIES_ARRAY = "categories";
    public static final String CATEGORIES_ARRAY_EN = "categories_en";
    public static final String BASEIS_ARRAY = "baseis";
    public static final String PERIODIKA_ARRAY = "periodika";
    public static final String ORGANOSI_ARRAY_EN = "organosien";
    public static final String BASEIS_ARRAY_EN = "baseisen";
    public static final String PERIODIKA_ARRAY_EN = "periodikaen";
    public static final String KATALOGOS_ARRAY = "katalogos";
    public static final String KATALOGOS_ARRAY_EN = "katalogosen";

    public static final String PREFERENCES_FILE = "option-preferences";
    public static final String LANGUAGE_PREFS_FILE = "DisplayLanguage";
    public static final String LIFES_PREFS_FILE = "Lifes";
    public static final String CATEGORIES_INTENT_NAME= "name";
    public static final String CHARACTER_ENCODING = "UTF-8";
}
