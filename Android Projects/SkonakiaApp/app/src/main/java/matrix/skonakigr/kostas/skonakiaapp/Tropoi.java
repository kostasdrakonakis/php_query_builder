package matrix.skonakigr.kostas.skonakiaapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adapters.RecyclerAdapter;
import functions.AppConstants;
import listeners.RecyclerItemClickListener;
import lists.CustomList;


public class Tropoi extends AppCompatActivity {

    private List<CustomList> list;
    private Toolbar toolbar;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FloatingActionButton floatingActionButton;
    private boolean visibility;
    private AlertDialog.Builder helpDialog;
    private View helpView;
    private String subjectTXT, messageTXT;
    private EditText subject, message;
    private Intent sendIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tropoi);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.tropoiList);
        layoutManager = new LinearLayoutManager(Tropoi.this);
        recyclerView.setLayoutManager(layoutManager);
        populateList();
        adapter = new RecyclerAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        listCallback();
        fabAction();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            if (getSupportActionBar()!=null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }
    }

    private void fabAction() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helpIt();
            }
        });
    }

    private void helpIt() {
            helpDialog = new AlertDialog.Builder(this);
            helpView = getLayoutInflater().inflate(R.layout.help_dialog, (ViewGroup) findViewById(R.id.helpRootLayout));
            subject = (EditText)helpView.findViewById(R.id.subject);
            message = (EditText)helpView.findViewById(R.id.message);
            helpDialog.setTitle("Ιδέες")
                    .setView(helpView)
                    .setPositiveButton("Αποστολή", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            subjectTXT = subject.getText().toString();
                            messageTXT = message.getText().toString();
                            if (subjectTXT.isEmpty() && messageTXT.isEmpty()) {
                                Toast.makeText(helpDialog.getContext(), "Τα πεδία είναι υποχρεωτικά", Toast.LENGTH_SHORT).show();
                            } else if (subjectTXT.isEmpty()) {
                                Toast.makeText(helpView.getContext(), "Το θέμα είναι υποχρεωτικό", Toast.LENGTH_SHORT).show();
                            } else if (messageTXT.isEmpty()) {
                                Toast.makeText(helpView.getContext(), "Το μήνυμα είναι υποχρεωτικό", Toast.LENGTH_SHORT).show();
                            } else {
                                sendIntent = new Intent(Intent.ACTION_SEND);
                                sendIntent.setData(Uri.parse("mailto:"));
                                sendIntent.setType("text/plain");
                                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{AppConstants.ADMIN_EMAIL});
                                sendIntent.putExtra(Intent.EXTRA_SUBJECT, subjectTXT);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, messageTXT);
                                startActivity(sendIntent);
                            }
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();

            helpDialog.show();
    }

    private void listCallback() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(Tropoi.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                    case 1:
                    case 2:
                    case 4:
                    case 5:
                    case 6:
                    case 11:
                    case 12:
                    case 13:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 21:
                    case 22:
                        visibility = false;
                        Intent intent = new Intent(Tropoi.this, TroposItem.class);
                        intent.putExtra(AppConstants.INTENT_EXTRA_DESCRIPTION, list.get(position).getDescription());
                        intent.putExtra(AppConstants.INTENT_EXTRA_NAME, list.get(position).getItemName());
                        intent.putExtra(AppConstants.INTENT_EXTRA_IMAGE, list.get(position).getMainImage());
                        intent.putExtra(AppConstants.INTENT_EXTRA_VISIBILITY, visibility);
                        intent.putExtra(AppConstants.INTENT_EXTRA_LINK, list.get(position).getLink());
                        startActivity(intent);
                        break;
                    default:
                        visibility = true;
                        Intent intentD = new Intent(Tropoi.this, TroposItem.class);
                        intentD.putExtra(AppConstants.INTENT_EXTRA_DESCRIPTION, list.get(position).getDescription());
                        intentD.putExtra(AppConstants.INTENT_EXTRA_NAME, list.get(position).getItemName());
                        intentD.putExtra(AppConstants.INTENT_EXTRA_IMAGE, list.get(position).getMainImage());
                        intentD.putExtra(AppConstants.INTENT_EXTRA_VISIBILITY, visibility);
                        intentD.putExtra(AppConstants.INTENT_EXTRA_LINK, list.get(position).getLink());
                        startActivity(intentD);
                        break;

                }
            }
        }));
    }

    private void populateList() {
        list = new ArrayList<>();
        list.add(new CustomList(R.mipmap.launch_icon, "Κλασικό Σκονάκι", "Το κλασικό σκονάκι." +
                "\n" +
                "\n" +
                "Συνήθως σε μικρά τετράγωνα χαρτάκια και με γράμματα τόσο μικρά έτσι ώστε να μπορεί να χωρέσει ένα ολόκληρο βιβλίο." +
                "\n\n" +
                "Σχεδόν όλοι καταφεύγουν σε αυτήν την λύση 1 ημέρα η ακόμα και 2-3 ώρες πριν την εξέταση." +
                "\n\nΗ λύση αυτή δεν είναι για αυτούς που φοβούνται μήπως τυχόν περάσει ο καθηγητής απο πάνω τους και τους πιάσει. " +
                "Αυτοί να κάτσουν να διαβάσουν γιατι αλλιώς δεν πρόκειται να περάσουν ποτέ." +
                "\n\nΤο θέμα εδώ είναι η άνεση. Πώς το πετυχαίνουμε αυτο?" +
                "\n" +
                "1)Δεν πανικοβαλλόμαστε οταν οι καθηγητές είναι δίπλα μας και δεν κάνουμε απότομες κινήσεις. Ο επιτηρητής θα μας καταλάβει αμέσως.\n" +
                "2)Πριν αρχίσει η εξέταση καθόμαστε και βάζουμε τα σκονάκια κάτω απο το δεξί ή αριστερό μας μπούτι έστι ώστε να είναι εύκολα διαχειρήσημα.\n", R.drawable.klasiko, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Θρανίο", "Σκονάκι στο θρανίο." +
                "\n" +
                "\n" +
                "Είναι η κλασική λύση για αυτούς που βαριούνται να γράψουν χαρτάκια, να κάνουν διαφάνειες η ακόμα και να περάσουν " +
                "τα \"sos\" στο κινητό και λένε μωρε δεν πάω 10 λεπτά πιο νωρίς να τα γράψω στο θρανίο?" +
                "\n" +
                "\n" +
                "Προσπαθούν λοιπόν να βρουν διαφορετικές σημειώσεις ήδη γραμμένες απο άλλα μαθήματα πάνω στο θρανίο και ανάμεσα σε αυτές " +
                "με μικρά γραμματάκια και μαεστρία να γράψουν τις σημειώσεις του μαθήματος που δίνουν αυτη την ημέρα." +
                "\n" +
                "\n" +
                "Βέβαια αυτός ο τρόπος είναι ριψοκίνδυνος για 2 λόγους:" +
                "\n" +
                "1)Ο επιτηρητής μπορεί να σε βάλει να καθαρίσεις το θρανίο και τότε αρχίζεις να βρίζεις απο μέσα σου περαν του οτι όλος ο κόπος σου πάει χαμένος." +
                "\n" +
                "2)Μπορεί ξαφνικά να σου πουν άλλαξε αίθουσα ή και θρανίο και τότε πάλι όλη η προσπάθεια σου πάει χαμένη." +
                "\n" +
                "\n " +
                "Οπότε κρατήστε και μια πισινή για αυτόν τον τρόπο.", R.drawable.thrania, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Χαρτί μέσα σε στυλό", "Χαρτί μέσα σε στυλό." +
                "\n" +
                "\n" +
                "Συνήθως χρησιμοποιείτε σε μαθήματα που περιέχουν τύπους(Μαθηματικά , Φυσική, Τηλεπικοινωνικά Συστήματα, Ψηφιακές Επικοινωνίες κ.α.)." +
                "\n" +
                "\n" +
                "Συνήθως χρησιμοποιούμε ένα άσπρο χαρτάκι γράφοντας αυτό που μας ενδιαφέρει κατα μήκος και μετά το περνάμε μέσα στο στυλό. " +
                "Προσέχουμε όμως να αφήσουμε την μια επιφάνια του στυλού κενή έτσι ώστε όταν είναι ο επιτηρητής απο πάνω μας να μην εκτεθούμε.", R.drawable.nnik, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Μαύρη Ζελατίνα προστασίας", "Μαύρη ζελατίνα προστασίας ή πιο σωστά Privacy Screen Protector." +
                "\n" +
                "\n" +
                "Συνήθως το χρησιμοποιούμε όταν το σκονάκι μας είναι πολύ μεγάλο μέγεθος κειμένου. Έτσι η μόνη λύση είναι το κινητό. " +
                "\n" +
                "\n" +
                "Τι γίνεται βέβαια στην περίπτωση που το κινητό μας ενω είναι ανοικτό με χαμηλωμένη φωτεινότητα στο τέρμα φαινεται απο μέτρα μακρια?" +
                "\n" +
                "\n" +
                "Εκεί μας χρησιμεύει το Privacy Screen Protector το οποίο ακόμα και απο πάνω σου να είναι ο επιτηρητής " +
                "φαίνεται οτι το τηλέφωνο είναι κλειστό ακόμα και με τέρμα την ένταση της φωτεινότητας.", R.drawable.maurizelatina, "http://www.ebay.com/sch/i.html?_from=R40&_trksid=p2050601.m570.l1313.TR0.TRC0.H0.Xprivacy+screen+protector.TRS0&_nkw=privacy+screen+protector&_sacat=0"));
        list.add(new CustomList(R.mipmap.launch_icon, "Μπουκάλι για σκονάκι", "Μπουκάλι για σκονάκι" +
                "\n" +
                "\n" +
                "Για αυτήν την ενέργεια χρειάζεστε ένα μπουκάλι (coca cola, fanta, sprite, etc)." +
                "\n" +
                "\n" +
                "Αφαιρούμε το περιτύλιγμα έτσι ώστε να μπορούμε να γράψουμε στην άσπρη μερια του περιτυλίγματος οτιδήποτε θέλουμε.", R.drawable.mpoukali, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Διαφάνειες", "Διαφάνειες." +
                "\n" +
                "\n" +
                "Γράφουμε όλη την ύλη που θέλουμε σε αρχείο κειμένου. Πάμε σε ένα βιβλιοπωλείο και ζητάμε να μας εκτυπώσουν τα αρχεία σε χαρτί διαφάνειας και με την κατάλληλη σμίκρυνση." +
                "\n" +
                "\n" +
                "Όταν τοποθετήσουμε το διαφανές χαρτί με το σκονάκι πάνω στην άσπρη κόλλα με τα θέματα θα φαίνονται μόνο τα γράμματα. " +
                "\n" +
                "\n" +
                "Το θέμα εδώ είναι οτι ΠΟΤΕ μα ΠΟΤΕ δεν ανταλλάζουμε ματιές με τον επιτηρητή ουτε κοιτάμε δεξια αριστερά σαν χαζοί έτσι ώστε να περιμένουμε να μην μας βλέπει για να το βγάλουμε. " +
                "Οποιαδήποτε άλλη κίνηση και θα μας υποψιαστεί αμέσως. " +
                "\n" +
                "\n" +
                "Τόσα χρόνια θέλουμε δεν θέλουμε την ξέρουν την δουλειά τους.", R.drawable.glass, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Χαραγμα πάνω σε στυλό", "Το χάραγμα πάνω σε στυλό." +
                "\n" +
                "\n" +
                "Για αυτήν την ενέργεια θα χρειαστούμε ένα διαβήτη και ένα στυλό. Χαράζουμε με την μύτη του διαβήτη επάνω στο στυλό αφήνοντας μια λεπτή γραμμή κενή για κάλυψη " +
                "όταν θα βρισκόμαστε σε δύσκολη θέση ή όταν ο επιτηρητής θα βρίσκεται απο πάνω μας.", R.drawable.xaragmasestilo, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Στυλό για σκονάκι", "Στυλό Σκονάκι." +
                "\n" +
                "\n" +
                "Μπορείτε να το προμηθευτήτε σχεδόν απο όλα τα βιβλιοπωλεία της χώρας διότι είναι ευρέως γνωστό. Το θέμα εδω είναι οτι το χαρτί που διαθέτει ενα τέτοιο στυλό είναι μικρό, " +
                "γιαυτό θα πρέπει να προσέξουμε το μέγεθος των γραμμάτων μας καθώς και το να γράφουμε με μολύβι αν θέλουμε να το ξαναχρησιμοποιήσουμε.", R.drawable.stilo, "http://www.skroutz.gr/c/630/stylo-penes.html?keyphrase=%CF%83%CF%84%CF%85%CE%BB%CE%BF+%CF%83%CE%BA%CE%BF%CE%BD%CE%B1%CE%BA%CE%B9"));
        list.add(new CustomList(R.mipmap.launch_icon, "Bluetooth", "Ακουστικό Bluetooth. " +
                "\n" +
                "\n" +
                "Χρησιμοποιείτε συνήθως απο κοπέλες ή αγόρια με μακριά μαλλιά. Για αυτήν την ενέργεια χρειάζεται και ένας βοηθός έξω απο την αίθουσα." +
                "\n" +
                "\n" +
                "Για να δουλέψει αυτή η διαδικασία θα πρέπει πρώτα να έχουμε πάρει φωτογραφία τα θέματα και να τα στείλουμε στον/στην βοηθό μας ή " +
                "να τον/την πάρουμε τηλέφωνο και προσποιούμενοι απορίας να του λέμε τα θέματα." +
                "(π.χ. Τι εννοείτε εδώ που λέτε \"Υπολογίστε τις ιδιοτιμές και τα (κανονικοποιημένα) ιδιοδυανύσματα του πίνακα α=(1 4 2 -1)\" ). " +
                "\n" +
                "\n" +
                "Προφανώς όμως δεν μπορείτε να έχετε απορία σε κάθε ερώτηση και κάθε τύπο οπότε προσέξτε τι θα ρωτάτε.", R.drawable.bluetooth, "http://www.skroutz.gr/c/435/hands-free-bluetooth.html?keyphrase=%CE%B1%CE%BA%CE%BF%CF%85%CF%83%CF%84%CE%B9%CE%BA%CE%B1+bluetooth"));
        list.add(new CustomList(R.mipmap.launch_icon, "Ψείρα", "Ψείρα. " +
                "\n" +
                "\n" +
                "Μπορεί να χρησιμοποιειθεί απο όλους και είναι τόσο μικρό που ούτε καν φαίνεται. Θα μπορούσαμε να πούμε πως είναι ο καλύτερος τρόπος για " +
                "σκονάκι αλλα παράλληλα ακριβώς διότι τέτοια εξαρτήματα κοστίζουν." +
                "\n" +
                "\n" +
                "Συνήθως συνοδεύετε απο ένα σύστημα ενδοεπικοινωνίας το οποίο μπορεί να περιλαμβάνει μικρόφωνο bluetooth στυλό, μικρόφωνο με καλώδιο και πομποδέκτη , κ.α." +
                "\n" +
                "\n" +
                "Για αυτήν την ενέργεια χρειάζεται και ενας εξωτερικός βοηθός δηλαδή κάποιος" +
                " φίλος μας έξω απο την τάξη για να μας μιλάει και να μας λέει τις απαντήσεις αφού πρώτα έχουμε φροντίσει να του στείλουμε τα θέματα.", R.drawable.psira, "http://www.ebay.com/sch/i.html?_from=R40&_trksid=p2050601.m570.l1313.TR0.TRC0.H0.Xspy+earpiece.TRS0&_nkw=spy+earpiece&_sacat=0"));
        list.add(new CustomList(R.mipmap.launch_icon, "Αριθμομηχανή", "Αριθμομηχανή ή Κομπιουτεράκι." +
                "\n" +
                "\n" +
                "Χρησιμοποιείτε σηνύθως στα μαθήματα που απιτούν πράξεις με μεγάλους αριθμούς, λογάριθμους, κ.α. Συνήθως έχουν πορτάκι το οποίο ανοίγει και κλείνει προφυλάσοντας έτσι το κομπιουτεράκι. " +
                "Σε αυτό το πορτάκι λοιπό μπορούμε να γράψουμε ή και να κολήσουμε χαρτάκια με οτιδήποτε θεωρούμε σημαντικό για να περάσουμε το μάθημα.", R.drawable.kompiouteraki, "http://www.ebay.com/sch/i.html?_odkw=spy+earpiece&_osacat=0&_from=R40&_trksid=p2045573.m570.l1313.TR0.TRC0.H0.Xscientific+calculator.TRS0&_nkw=scientific+calculator&_sacat=0"));
        list.add(new CustomList(R.mipmap.launch_icon, "Ζώνη παντελονιού", "Η ζώνη του παντελονιού." +
                "\n" +
                "\n" +
                "Στην εσωτερική πλευρά της ζώνης του παντελονιού μπορούμε να κολήσουμε χαρτάκια με τα σκονάκια μας ή ακόμα και να γράψουμε επάνω. " +
                "\n" +
                "\n" +
                "Είναι πολύ μεγάλες οι πιθανότητες να πετύχουμε τον στόχο μας αλλά και επίσης πολύ μικρός ο χώρος που έχουμε διαθέσιμο.", R.drawable.zoni, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Τιράντες", "Οι τιράντες." +
                "\n" +
                "\n" +
                "Η εσωτερική μεριά της τιράντας μπορεί να γίνει μια καλή κρυψώνα για το σκονάκι μας. " +
                "\n" +
                "\n" +
                "Αρκει βέβαι να μην τεντονόμαστε όλη την ώρα και να κάνουμε απότομες κινήσεις έτσι ώστε να μπορέσουμε να δούμε τι έχουμε γράψει.", R.drawable.tiranta, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Γάζες", "Γάζα ή Επίδεσμος. " +
                "\n" +
                "\n" +
                "Όλοι έχουμε σκεφτεί να το \"παίξουμε\" υποτιθέμενοι χτυπημένοι. Προσπιούμενοι λοιπόν πρόβλημμα στο χέρι μας μπορούμε να γράψουμε σημειώσεις πάνω στην γάζα ή " +
                "στον επίδεσμο μας. Βέβαια αν τύχει να καθήσετε σε μια τάξη γεμάτη απο άτομα με επιδέσμους και γάζες προτείνω να έχετε και εναλλακτική μέθοδο", R.drawable.gaza, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Κινητό τηλέφωνο", "Το κινητό μας τηλέφωνο." +
                "\n" +
                "\n" +
                "Το κινητό μας τηλέφωνο μπορεί να μας γανεί χρήσιμο με πολλούς τρόπους όπως είναι οι παρακάτω:" +
                "\n" +
                "\n" +
                "1) Το έχουμε επάνω στο δεξί ή αριστερό μας πόδι και βλέπουμε τις σημειώσεις μας με μικρές και οχι απότομες κινήσεις\n\n" +
                "2) Διαμορφώνουμε το κομπιουτεράκι μας όπως φαίνεται στην εικόνα έστι ώστε να βάλουμε το κινητό μας μέσα και ο καθηγητής να υποψιάζεται οτι χρησιμοποιούμε το κομπιουτεράκι.", R.drawable.kinito, "http://www.ebay.com/sch/i.html?_odkw=spy+earpiece&_osacat=0&_from=R40&_trksid=p2045573.m570.l1313.TR0.TRC0.H0.Xscientific+calculator.TRS0&_nkw=scientific+calculator&_sacat=0"));
        list.add(new CustomList(R.mipmap.launch_icon, "Αλλαγη κόλλας", "Αλλαγή της κόλλας." +
                "\n" +
                "\n" +
                "Για να πετύχει η συγκεκριμένη ενέργεια θα πρέπει να έχουμε φροντίσει να έχουμε προμηθευτεί μια απο τις κόλλες των απαντήσεων με την σφραγίδα επάνω. ", R.drawable.kolla, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Χαρτομάντηλα", "Χαρτομάντηλα." +
                "\n" +
                "\n" +
                "Προμηθευόμαστε ένα πακετάκι χαρτομάντηλα. Γράφουμε τις σημειώσεις που επιθυμούμε πάνω σε χαρτομάντηλα απο την μια πλευρά μόνο έτσι ώστε όταν το τυλίξουμε σαν καινούργιο. " +
                "\n" +
                "\n" +
                "Προσπιούμενοι οτι τρέχει η μύτη μας ή οτι φτερνιζόμαστε κοιτάμε τις σημειώσεις μας απο τα χαρτομάντηλα.", R.drawable.xarto, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Σημεία του σώματος", "Τα σημεία του σώματος μας." +
                "\n" +
                "\n" +
                "Πολλές φορές έχουμε σκεφτεί να γράψουμε σημειώσεις πάνω στα χέρια μας ή στα πόδια μας (κυρίως απο κοπέλες που φοράνε φούστα και έχουν εύκολη πρόσβαση). " +
                "Βέβαια ένας τέτοιος τρόπος είναι επίφοβος διότι δεν έχουμε την δυνατότητα να τα σβήσουμε άμεσα σε περίπτωση που μας καταλάβει ο επιτηρητής.", R.drawable.shmeiaswmatos, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Κασετίνα", "Η κασετίνα." +
                "\n" +
                "\n" +
                "Συνήθως χρησιμοποιείτε απο κοπέλες που ανάμεσα στα πολλών τύπων στυλό και μολυβιών που φέρει η κασετίνα τους έχουν και μικρά χαρτάκια με οτι σημειώσεις μπορούν να τους φανούν χρήσιμες. " +
                "\n" +
                "\n" +
                "Αφήνοντας την κασετίνα ανοικτή και το χαρτάκι όρθιο μπορείτε να γράφετε άφοβα χωρίς κίνδυνο.", R.drawable.kasetina, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Στυλός με αόρατο μελάνι", "Στυλό με αόρατο μελάνι. " +
                "\n" +
                "\n" +
                "Είναι ένα ειδικό στυλό το οποίο όταν γράφεις δεν φαίνεται τίποτα παρα μόνο αν ανάψεις το φωτάκι. " +
                "\n" +
                "\n" +
                "Πράγμα το οποίο θα ήταν πολύ χρήσιμο για σημειώσεις πάνω σε θρανία ή και στα σημεία του σώματος.", R.drawable.stilomeaoratomelani, "http://www.ebay.com/sch/i.html?_from=R40&_trksid=p2050601.m570.l1313.TR0.TRC0.H0.Xinvisible+ink+pen.TRS0&_nkw=invisible+ink+pen&_sacat=0"));
        list.add(new CustomList(R.mipmap.launch_icon, "Ρολόϊ για σκονάκι", "Ρολόϊ για σκονάκι." +
                "\n" +
                "\n" +
                "Θεωριτικά ο πιο ασφαλές και εύκολος τρόπος χωρίς να σε αντιληφθούν. " +
                "\n" +
                "\n" +
                "Φτιάχνεις τα αρχεία σου σε έγγραφα κειμένου, τα περνάς στο ρολόϊ και ούτε γάτα ούτε ζημιά. " +
                "\n" +
                "\nΕαν δεν αντιληφθείς οτι έρχετε ο επιτηρητής υπάρχει το κουμπί πανικού το οποίο αλλάζει την λειτουργία του σε κανονικό ρολόϊ δείχνοντας την ώρα και κλειδώνοντας όλα " +
                "τα υπόλοιπα κουμπιά τα οποία ξεκλειδώνουν με ενα συνδυασμό που μόνο εσύ ξέρεις." , R.drawable.roloiskonaki, "http://www.24kupi.com/webshop-en/"));
        list.add(new CustomList(R.mipmap.launch_icon, "Σόλες Παπουτσιών", "Σόλες Παπουτσιών." +
                "\n" +
                "\n" +
                "Οι σόλες των παπθτσιών είναι ένα μέρος στο οποίο μπορούμε να κολήσουμε μικρά χαρτάκια με τις σημειώσεις που εμεις επιθυμούμε. " +
                "Βέβαια ο χώρος είναι πολυ περιορισμένος και μη εύκολα προσεγγίσιμος.", R.drawable.solespapoutsiwn, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Αλλαγή φωτογραφίας πάσου", "Αλλαγή φωτογραφίας πάσου" +
                "\n" +
                "\n" +
                "Μια τέτοια μέθοδος ήταν πιο διαδεδομένη στα παλιά ΠΑΣΟ τα οποία ήταν χάρτινα και έτσι εύκολα θα μπορούσες να αλλάξεις τις φωτογραφίες και να δώσει κάποιος άλλος για εσένα. " +
                "\n" +
                "\n" +
                "Βέβαια ακόμα και τώρα γίνεται με πολύ προσεκτική δουλειά , " +
                "αρκεί βέβαια μην σας κάψει αυτος/αυτή που θα δώσει για εσάς. ", R.drawable.paso, "http://www.google.gr"));
        list.add(new CustomList(R.mipmap.launch_icon, "Σκονάκι Τατουάζ", "Σκονάκι Τατουάζ." +
                "\n" +
                "\n" +
                "Αντί να γράφετε και να λερώνετε τα χέρια/πόδια σας ή οτιδήποτε άλλο σημείο του σωματός σας με στυλο υπάρχει το σκονάκι τατουαζ. " +
                "\n" +
                "\n" +
                "Γράφετε στο χαρτί, το κολλάτε στο επιθυμητό σημείο και το βρέχετε με νερό. Το τατουάζ είναι henna οπότε δεν πρόκειτε να ξεβάψει όπως θα ξέβαφε το στυλό απο το τρίψιμο με τα ρούχα ή άλλες φθορές.", R.drawable.tattou, "http://www.amazon.co.uk/dp/B0015MSY50/?tag=691-21"));
    }
}
