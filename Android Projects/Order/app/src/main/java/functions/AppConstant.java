package functions;


public class AppConstant {

    //used to maintain the data from the database to all activities and back to database
    public static final String COMPANY_INTENT_ID = "magaziID";
    public static final String WAITER_INTENT_ID = "servitorosID";
    public static final String TABLE_INTENT_ID = "table_name";


    //used to handle the search data
    public static final String SNACK_NAME = "snackName";
    public static final String SNACK_IMAGE = "snackImage";
    public static final String SNACK_PRICE = "snackPrice";
    public static final String COFFEE_NAME = "coffeeName";
    public static final String COFFEE_IMAGE = "coffeeImage";
    public static final String COFFEE_PRICE = "coffeePrice";
    public static final String BEVERAGE_NAME = "beverageName";
    public static final String BEVERAGE_IMAGE = "beverageImage";
    public static final String BEVERAGE_PRICE = "beveragePrice";
    public static final String SWEET_NAME = "sweetsName";
    public static final String SWEET_IMAGE = "sweetsImage";
    public static final String SWEET_PRICE = "sweetsPrice";
    public static final String SPIRIT_ITEM = "spirit_item";

    //Paths to the actuall files in the servers
    public static final String REGISTER_URL = "http://api.chatapp.info/order_api/insertData/insert_users_into_db.php";
    public static final String LOGIN_URL = "http://api.chatapp.info/order_api/insertData/user_login.php";

    public static final String WHISKEYS_URL = "http://api.chatapp.info/order_api/files/getwhiskys.php";
    public static final String LIQUERS_URL = "http://api.chatapp.info/order_api/files/getliquers.php";
    public static final String VODKAS_URL = "http://api.chatapp.info/order_api/files/getvodkas.php";
    public static final String TEQUILAS_URL = "http://api.chatapp.info/order_api/files/gettequilas.php";
    public static final String RUMS_URL = "http://api.chatapp.info/order_api/files/getrums.php";
    public static final String GINS_URL = "http://api.chatapp.info/order_api/files/getgins.php";


    public static final String SWEETS_URL = "http://api.chatapp.info/order_api/files/getsweets.php";
    public static final String SPIRITS_URL = "http://api.chatapp.info/order_api/files/getspirits.php";
    public static final String SNACKS_URL = "http://api.chatapp.info/order_api/files/getsnacks.php";
    public static final String COFFEES_URL = "http://api.chatapp.info/order_api/files/getkafedes.php";
    public static final  String BEVERAGES_URL = "http://api.chatapp.info/order_api/files/getbeverages.php";
    public static final String BEERS_URL = "http://api.chatapp.info/order_api/files/getbeers.php";
    public static final String CART_URL = "http://api.chatapp.info/order_api/files/getcartitems.php";

    public static final String COFFEES_ADD_TO_CART_URL = "http://api.chatapp.info/order_api/insertData/insert_coffees_to_cart.php";
    public static final String SNACKS_ADD_TO_CART_URL = "http://api.chatapp.info/order_api/insertData/insert_snacks_to_cart.php";
    public static final String SWEETS_ADD_TO_CART_URL = "http://api.chatapp.info/order_api/insertData/insert_sweets_to_cart.php";
    public static final String BEVERAGES_ADD_TO_CART_URL = "http://api.chatapp.info/order_api/insertData/insert_beverages_to_cart.php";
    public static final String SPIRIT_ADD_TO_CART_URL = "http://api.chatapp.info/order_api/insertData/insert_spirits_to_cart.php";

    public static final String RATINGS_URL = "http://api.chatapp.info/order_api/insertData/insert_ratings.php";


    //JSON Arrays
    public static final String COFFEE_JSON_ARRAY = "kafedes";
    public static final String SNACKS_JSON_ARRAY = "snacks";
    public static final String SWEETS_JSON_ARRAY = "sweets";
    public static final String BEVERAGES_JSON_ARRAY = "beverages";
    public static final String SPIRITS_JSON_ARRAY = "spirits";
    public static final String BEERS_JSON_ARRAY = "beers";
    public static final String GINS_JSON_ARRAY = "gins";
    public static final String LIQUERS_JSON_ARRAY = "liquers";
    public static final String RUMS_JSON_ARRAY = "rums";
    public static final String TEQUILAS_JSON_ARRAY = "tequilas";
    public static final String VODKAS_JSON_ARRAY = "vodkas";
    public static final String WHISKEYS_JSON_ARRAY = "whiskys";


    public static final String PRODUCT_NAME_VALUE_PAIR = "productName";
    public static final String PRODUCT_IMAGE_VALUE_PAIR = "productImage";
    public static final String PRODUCT_PRICE_VALUE_PAIR = "productPrice";
    public static final String PRODUCT_QUANTITY_VALUE_PAIR = "quantity";
    public static final String PRODUCT_COMPANY_ID_VALUE_PAIR = "magazi_id";
    public static final String PRODUCT_WAITER_ID_VALUE_PAIR = "servitoros_id";
    public static final String PRODUCT_TABLE_ID_VALUE_PAIR = "trapezi";
    public static final String PRODUCT_COMMENT_VALUE_PAIR = "comment";
    public static final String PRODUCT_COMPONENT_VALUE_PAIR = "component";


    //help and complaints email
    public static final String ADMIN_EMAIL = "admin@chatapp.info";

    //used for the confirmation of the photo
    public static final int REQ_CODE = 1152;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    //used for the receiver
    public static final String TEMP_WAKELOCK = "TempWakeLock";
    public static final String ORDERING_SYSTEM_DEFAULT = "Order Taking System";

    //used to saveInstance and restoreInstance of the sharedPreferences files
    public static final String PREF_NAME = "OrderTakingSystem";
    public static final String RATING_NAME_FILE_PREFS = "RatingComplete";
    public static final String IMAGE_NAME_FILE_PREFS = "ImageTaken";
    public static final String ORIENTATION_NAME_FILE_PREFS = "ImageTakenorientation";
    public static final String PROFILE_IMAGE_NAME = "waiterProfile.jpg";

    //used to maintain the session
    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_WAITER_ID = "servitoros_id";
    public static final String KEY_SHOP_ID = "magazi_id";
    public static final int PRIVATE_MODE = 0;

    //create database adapter
    public static final String DATABASE_NAME = "login.db";
    public static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    public static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text); ";


    public static final String TEL = "+30-6949290612";

    //change the button color depending on its state
    public static final String ENABLED_BUTTON_COLOR = "#26ae90";
    public static final String DISABLED_BUTTON_COLOR = "#d8d8d8";

    //character encoding used for sending requests and receiving responces to and from the server
    public static final String CHARACTER_ENCODING = "UTF-8";

    //preference keys

    public static final String COFFEE_KEY = "coffee_file";
    public static final String SWEET_KEY = "sweet_file";
    public static final String SNACK_KEY = "snack_file";
    public static final String BEVERAGE_KEY = "beverage_file";
    public static final String SPIRIT_KEY = "spirit_file";
    public static final String BEER_KEY = "beer_file";
    public static final String GIN_KEY = "gin_file";
    public static final String VODKA_KEY = "vodka_file";
    public static final String TEQUILA_KEY = "tequila_file";
    public static final String RUM_KEY = "rum_file";
    public static final String WHISKEY_KEY = "whiskey_file";
    public static final String LIQUER_KEY = "liquer_file";
    public static final String BADGE_COUNT = "CountValue";
    public static final String BADGE_COUNT_VALUE = "counter_value";
    public static final String DELETE_URL = "http://api.chatapp.info/order_api/deleteData/deletecartitems.php";
    public static final int MY_PERMISSION_CODE = 1;


    public static String CART_JSON_ARRAY = "cart";
}
