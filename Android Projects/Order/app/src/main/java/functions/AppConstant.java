package functions;


public class AppConstant {
    public static final String COMPANY_INTENT_ID = "magaziID";
    public static final String WAITER_INTENT_ID = "servitorosID";
    public static final String TABLE_INTENT_ID = "table_name";
    public static final String SNACK_NAME = "snackName";
    public static final String SNACK_IMAGE = "snackImage";
    public static final String SNACK_PRICE = "snackPrice";
    public static final String COFFEE_NAME = "coffeeName";
    public static final String COFFEE_IMAGE = "coffeeImage";
    public static final String COFFEE_PRICE = "coffeePrice";
    public static final String SWEET_NAME = "sweetsName";
    public static final String SWEET_IMAGE = "sweetsImage";
    public static final String SWEET_PRICE = "sweetsPrice";
    public static final String SPIRIT_ITEM = "spirit_item";

    public static final String REGISTER_URL = "http://my.chatapp.info/order_api/insertData/insert_users_into_db.php";
    public static final String LOGIN_URL = "http://my.chatapp.info/order_api/insertData/user_login.php";

    public static final String WHISKEYS_URL = "http://my.chatapp.info/order_api/files/getwhiskys.php";
    public static final String LIQUERS_URL = "http://my.chatapp.info/order_api/files/getliquers.php";
    public static final String VODKAS_URL = "http://my.chatapp.info/order_api/files/getvodkas.php";
    public static final String TEQUILAS_URL = "http://my.chatapp.info/order_api/files/gettequilas.php";
    public static final String RUMS_URL = "http://my.chatapp.info/order_api/files/getrums.php";
    public static final String GINS_URL = "http://my.chatapp.info/order_api/files/getgins.php";
    public static final String SWEETS_URL = "http://my.chatapp.info/order_api/files/getsweets.php";
    public static final String SPIRITS_URL = "http://my.chatapp.info/order_api/files/getspirits.php";
    public static final String SNACKS_URL = "http://my.chatapp.info/order_api/files/getsnacks.php";
    public static final String COFFEES_URL = "http://my.chatapp.info/order_api/files/getkafedes.php";
    public static final  String BEVERAGES_URL = "http://my.chatapp.info/order_api/files/getbeverages.php";
    public static final String BEERS_URL = "http://my.chatapp.info/order_api/files/getbeers.php";

    public static final String COFFEES_ADD_TO_CART_URL = "http://my.chatapp.info/order_api/insertData/insert_coffees_to_cart.php";
    public static final String SNACKS_ADD_TO_CART_URL = "http://my.chatapp.info/order_api/insertData/insert_snacks_to_cart.php";
    public static final String SWEETS_ADD_TO_CART_URL = "http://my.chatapp.info/order_api/insertData/insert_sweets_to_cart.php";

    public static final String RATINGS_URL = "http://my.chatapp.info/order_api/insertData/insert_ratings.php";

    public static final String ADMIN_EMAIL = "admin@chatapp.info";

    public static final int REQ_CODE = 1152;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static final String TEMP_WAKELOCK = "TempWakeLock";
    public static final String ORDERING_SYSTEM_DEFAULT = "Order Taking System";

    public static final String PREF_NAME = "OrderTakingSystem";
    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_WAITER_ID = "servitoros_id";
    public static final String KEY_SHOP_ID = "magazi_id";
    public static final int PRIVATE_MODE = 0;

    public static final String DATABASE_NAME = "login.db";
    public static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    public static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text); ";


    public static final String TEL = "+30-6949290612";

    public static final String RATING_NAME_FILE_PREFS = "RatingComplete";
    public static final String IMAGE_NAME_FILE_PREFS = "ImageTaken";
    public static final String ORIENTATION_NAME_FILE_PREFS = "ImageTakenorientation";
    public static final String PROFILE_IMAGE_NAME = "waiterProfile.jpg";


    public static final String ENABLED_BUTTON_COLOR = "#26ae90";
    public static final String DISABLED_BUTTON_COLOR = "#d8d8d8";

    public static final String CHARACTER_ENCODING = "UTF-8";


}
