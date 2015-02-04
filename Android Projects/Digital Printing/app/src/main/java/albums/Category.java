package albums;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.providers.restaurant.ordertakingsystem.AndroidGridLayoutActivity;
import com.providers.restaurant.ordertakingsystem.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dialogs.DialogMessageDisplay;


public class Category extends ActionBarActivity {

    private String[] myItems;
    private ListView lv;
    private EditText et;
    private Toolbar tb;
    private static final String Αστακος = "Αστακος";
    private static final String Γεμιστα = "Γεμιστα";
    private static final String Γιαουρτι_Μελι_Καρυδια = "Γιαουρτι Μελι-Καρυδια/Γιαουρτι Φρουτα";
    private static final String Γιουβετσι = "Γιουβετσι";
    private static final String Γλωσσα = "Γλωσσα";
    private static final String Γρανιτες_Μιλκ_Σεικ = "Γρανιτες - Μιλκ Σεικ";
    private static final String Γυρος_Ψωμι_Πιτες = "Γυρος/Ψωμι/Πιτες";
    private static final String Διαφορα = "Διαφορα";
    private static final String Ελληνικες_Σπεσιαλιτε = "Ελληνικες Σπεσιαλιτε";
    private static final String Ελληνικο_Πιατο = "Ελληνικο Πιατο";
    private static final String Επιδορπια_Ταρτες_Παστες_Τουρτες = "Επιδορπια/Ταρτες/Παστες/Τουρτες";
    private static final String Θαλασσινα_Ορεκτικα = "Θαλασσινα Ορεκτικα";
    private static final String Κανελονια_Κρεπες = "Κανελονια - Κρεπες";
    private static final String Καφεδες_Ροφηματα = "Καφεδες - Ροφηματα";
    private static final String Κινεζικο_Ινδικο = "Κινεζικο - Ινδικο";
    private static final String Κλεφτικο = "Κλεφτικο";
    private static final String Κοκκινιστο_Στιφαδο = "Κοκκινιστο - Στιφαδο";
    private static final String Κοκτεϊλ = "Κοκτεϊλ";
    private static final String Κοτοπουλα = "Κοτοπουλα";
    private static final String Κρεπες_Βαφλες = "Κρεπες - Βαφλες";
    private static final String Κρεπες_Αλμυρες = "Κρεπες Αλμυρες";
    private static final String Κρουασαν = "Κρουασαν";
    private static final String Κυπριακη_Πιτα = "Κυπριακη Πιτα";
    private static final String Λαζανια = "Λαζανια";
    private static final String Λαχανικα_Σαλατες = "Λαχανικα - Σαλατες";
    private static final String Λουκανικα = "Λουκανικα";
    private static final String Λουκουμαδες_Ντονατς = "Λουκουμαδες - Ντονατς";
    private static final String Μακαρονια = "Μακαρονια";
    private static final String Μηλοπιτα_Κανταϊφι = "Μηλοπιτα - Κανταϊφι";
    private static final String Μιξ_Γκριλ = "Μιξ Γκριλ";
    private static final String Μιξ_Φις = "Μιξ Φις";
    private static final String Μπακλαβας_Γαλακτομπουρεκο = "Μπακλαβας - Γαλακτομπουρεκο";
    private static final String Μπανανα_Σπλιτ = "Μπανανα Σπλιτ";
    private static final String Μπεκρη_Μεζε = "Μπεκρη Μεζε";
    private static final String Μπιφτεκια_Κεμπαπ_Σεφταλιες = "Μπιφτεκια - Κεμπαπ/Σεφταλιες";
    private static final String Μπυρες_Κρασια = "Μπυρες - Κρασια";
    private static final String Ναγκετ_Ψαροκροκετες_Ολλανδικα_Σνιτσελ = "Ναγκετ - Ψαροκροκετες/Ολλανδικα Σνιτσελ";
    private static final String Ξιφιας = "Ξιφιας";
    private static final String Ομελετες = "Ομελετες";
    private static final String Ορεκτικα = "Ορεκτικα";
    private static final String Παγωτο_Ακτινιδιο = "Παγωτο Ακτινιδιο";
    private static final String Παγωτο_Ανανα = "Παγωτο Ανανα";
    private static final String Παγωτο_Ξηροι_Καρποι = "Παγωτο Ξηροι Καρποι";
    private static final String Παγωτο_Πεπονι = "Παγωτο Πεπονι";
    private static final String Παγωτο_Πορτοκαλι_Ροδακινο = "Παγωτο Πορτοκαλι - Ροδακινο";
    private static final String Παγωτο_Σοκολατα_Σικαγο = "Παγωτο Σοκολατα/Σικαγο";
    private static final String Παγωτο_Φραουλες_Κερασι = "Παγωτο Φραουλες - Κερασι";
    private static final String Παγωτο_Φρουτα = "Παγωτο Φρουτα";
    private static final String Παιδικα_Παγωτα = "Παιδικα Παγωτα";
    private static final String Παιδικο_Μενου = "Παιδικο Μενου";
    private static final String Πανσετες_Μπριζολες = "Πανσετες - Μπριζολες";
    private static final String Παπουτσακι_Ιμαμ = "Παπουτσακι-Ιμαμ";
    private static final String Παστιτσιο_Μουσακας = "Παστιτσιο-Μουσακας";
    private static final String Παϊδακια_Σπεαρ_Ριμπς = "Παϊδακια/Σπεαρ Ριμπς";
    private static final String Πενες = "Πενες";
    private static final String Πιτσες = "Πιτσες";
    private static final String Πρωινα = "Πρωινα";
    private static final String Ριζοτο = "Ριζοτο";
    private static final String Σαντουιτς_Μπαγκετες = "Σαντουιτς/Μπαγκετες";
    private static final String Σατωμπριαν = "Σατωμπριαν";
    private static final String Σνιτσελ = "Σνιτσελ";
    private static final String Σουβλακια = "Σουβλακια";
    private static final String Σουπες = "Σουπες";
    private static final String Σουτζουκακια_Σμυρνεικα_Κεφτεδες = "Σουτζουκακια Σμυρνεικα - Κεφτεδες";
    private static final String Ταλιατελες = "Ταλιατελες";
    private static final String Τι_Μπον = "Τι-Μπον";
    private static final String Τορτελινια = "Τορτελινια";
    private static final String Τοστ_Κλαμπ_Σαντουιτς = "Τοστ/Κλαμπ Σαντουιτς";
    private static final String Τυροπιτες = "Τυροπιτες";
    private static final String Φιλετα = "Φιλετα";
    private static final String Φιλετα_Ψαριου = "Φιλετα Ψαριου";
    private static final String Φιλετο_Ανανα = "Φιλετο Ανανα";
    private static final String Φιλετο_Κοτοπουλο = "Φιλετο Κοτοπουλο";
    private static final String Φιλετο_Πιπερατο = "Φιλετο Πιπερατο";
    private static final String Φιλετο_Σχαρας_Ψαρονεφρι_Γκαμον = "Φιλετο Σχαρας/Ψαρονεφρι/Γκαμον";
    private static final String Φρεσκα_Ψαρια = "Φρεσκα Ψαρια";
    private static final String Φρουτοσαλατες = "Φρουτοσαλατες";
    private static final String Χαμπουργκερ = "Χαμπουργκερ";
    private static final String Χορτοπιτες = "Χορτοπιτες";
    private static final String Χοτ_Ντογκ_Σαντουιτς_Κρεατικων = "Χοτ-Ντογκ/Σαντουιτς Κρεατικων";
    private static final String Χυμοι_Αναψυκτικα = "Χυμοι - Αναψυκτικα";
    private static final String Χωνακια_Συνθεσεις = "Χωνακια - Συνθεσεις";
    private static final String Ψαρια = "Ψαρια";
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album1);
        tb = (Toolbar)findViewById(R.id.top_bar);
        setSupportActionBar(tb);
        lv = (ListView)findViewById(R.id.listViewAlbum1);
        myItems = getResources().getStringArray(R.array.album1);
        adapter = new ArrayAdapter<>(Category.this, R.layout.list_layout, myItems);
        sortArrays();
        lv.setAdapter(adapter);
        et = (EditText)findViewById(R.id.album1);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Category.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                switch (adapter.getItem(position)){
                    case Αστακος:{
                        int[] mThumbIds = {
                                R.drawable.pic_1, R.drawable.pic_2,
                                R.drawable.pic_3, R.drawable.pic_4,
                                R.drawable.pic_5, R.drawable.pic_6,
                                R.drawable.pic_7, R.drawable.pic_8,
                                R.drawable.pic_9, R.drawable.pic_10,
                                R.drawable.pic_11, R.drawable.pic_12,
                                R.drawable.pic_13, R.drawable.pic_14,
                                R.drawable.pic_15
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Γεμιστα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1, R.drawable.pic_2,
                                R.drawable.pic_3, R.drawable.pic_4,
                                R.drawable.pic_5
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Γιαουρτι_Μελι_Καρυδια:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Γιουβετσι:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Γλωσσα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Γρανιτες_Μιλκ_Σεικ:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Γυρος_Ψωμι_Πιτες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Διαφορα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Ελληνικες_Σπεσιαλιτε:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Ελληνικο_Πιατο:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Επιδορπια_Ταρτες_Παστες_Τουρτες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Θαλασσινα_Ορεκτικα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Κανελονια_Κρεπες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Καφεδες_Ροφηματα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Κινεζικο_Ινδικο:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Κλεφτικο:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Κοκκινιστο_Στιφαδο:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Κοκτεϊλ:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Κοτοπουλα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Κρεπες_Αλμυρες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Κρεπες_Βαφλες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Κρουασαν:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Κυπριακη_Πιτα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Λαζανια:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Λαχανικα_Σαλατες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Λουκανικα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Λουκουμαδες_Ντονατς:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Μακαρονια:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Μηλοπιτα_Κανταϊφι:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Μιξ_Γκριλ:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Μιξ_Φις:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Μπακλαβας_Γαλακτομπουρεκο:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Μπανανα_Σπλιτ:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Μπεκρη_Μεζε:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Μπιφτεκια_Κεμπαπ_Σεφταλιες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Μπυρες_Κρασια:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Ναγκετ_Ψαροκροκετες_Ολλανδικα_Σνιτσελ:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Ξιφιας:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Ομελετες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Ορεκτικα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παγωτο_Ακτινιδιο:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παγωτο_Ανανα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παγωτο_Ξηροι_Καρποι:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παγωτο_Πεπονι:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παγωτο_Πορτοκαλι_Ροδακινο:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παγωτο_Σοκολατα_Σικαγο:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παγωτο_Φραουλες_Κερασι:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παγωτο_Φρουτα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παιδικα_Παγωτα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παιδικο_Μενου:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Πανσετες_Μπριζολες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παπουτσακι_Ιμαμ:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παστιτσιο_Μουσακας:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Παϊδακια_Σπεαρ_Ριμπς:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Πενες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Πιτσες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Πρωινα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Ριζοτο:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Σαντουιτς_Μπαγκετες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Σατωμπριαν:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Σνιτσελ:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Σουβλακια:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Σουπες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Σουτζουκακια_Σμυρνεικα_Κεφτεδες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Ταλιατελες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Τι_Μπον:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Τορτελινια:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Τοστ_Κλαμπ_Σαντουιτς:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Τυροπιτες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Φιλετα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Φιλετα_Ψαριου:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Φιλετο_Ανανα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Φιλετο_Κοτοπουλο:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Φιλετο_Πιπερατο:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Φιλετο_Σχαρας_Ψαρονεφρι_Γκαμον:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Φρεσκα_Ψαρια:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Φρουτοσαλατες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Χαμπουργκερ:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Χορτοπιτες:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Χοτ_Ντογκ_Σαντουιτς_Κρεατικων:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Χυμοι_Αναψυκτικα:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Χωνακια_Συνθεσεις:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    case Ψαρια:{
                        int[] mThumbIds = {
                                R.drawable.pic_1
                        };
                        Intent intent = new Intent(Category.this, AndroidGridLayoutActivity.class);
                        intent.putExtra("pic_images", mThumbIds);
                        startActivity(intent);
                        break;
                    }
                    default:{
                        DialogMessageDisplay.displayInfoMessage(Category.this, "Σφάλμα", "Κάτι πήγε στραβά. \n Προσπαθήστε ξανά.", AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                    }
                }


            }
        });
    }

    private void sortArrays() {
        List<String> sortedList = Arrays.asList(myItems);
        Collections.sort(sortedList);
        myItems = (String[]) sortedList.toArray();
    }


}
