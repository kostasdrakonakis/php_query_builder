package com.example.kostas.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class NextActivity extends Activity {

    private List<ListaAdapterNext> skonakia = new ArrayList<ListaAdapterNext>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        getActionBar().setTitle(R.string.app_name);
        populateList();
        createAdapter();
        Button btn = (Button) findViewById(R.id.buttontropoi);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NextActivity.this, Tropoi.class);
                startActivity(intent);
            }
        });
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void populateList() {
        skonakia.add((new ListaAdapterNext(R.drawable.ic_skonaki, "Κλασικό Σκονάκι", "Σίγουρα όλοι θα ξέρετε για το πιο κλασικό σκονάκι όλων των εποχών το οποίο ειναι κάποιο απόσπασμα ή τύπος απο βιβλίο ή εγκυκλοπαίδεια.", "Προμηθευτείτε μικρά χαρτάκια άσπρα ή κίτρινα αυτοκόλητα και μη και γράψτε επάνω κάνοντας όσο πιο μικρά γράμματα μπορείτε οτι σας είναι δύσκολο να θυμάστε. \n Δεν έχουν απαραιτητα συγκεκριμένο μέγεθος απλώς να είναι ευανάγνωστα σε εσάς.", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Θρανίο", "Ένας άλλος τρόπος για σκονάκι που σιγουρα όλοι θα έχετε ακούσει και χρησιμοποιήσει ειναι το θρανίο.", "Το θέμα εδώ είναι η προετημασία. Αν έχεις στο μυαλό σου οτι το θρανίο είναι το γούρι σου και πάντα εκεί κάνεις σκονάκια και περνάς τότε θα πρέπει να κανεις κάποια συγκεκριμένα βήματα. \nΘα πρέπει να βρίσκεσαι στο χώρο της εξέτασης τουλάχιστον μισή με 1 ώρα πιο νωρίς και εξοπλισμένος με γόμμες και μπλάνκο έτσι ώστε να προλάβεις θέση και να καθαρίσεις αυτα που πιθανώς έχουν γράψει προηγούμενοι απο εσένα. \n Θα πρέπει να γράφεις με μολύβι(κάποια στυλό δεν γράφουν σε όλες τις επιφάνειες και για να μην τρως τον χρόνο σου χρησιμοποίησε μολύβι) με μικρά και καθαρά γράμματα για να μην πέσουν στην αντίληψη του επιτηρητή όταν περνάει απο πάνω σου.", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_stilos, "Χαρτί μέσα σε στυλό", "", "", R.drawable.stilosskonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Σμίκρυνση", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Μπουκάλι για σκονάκι", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Διαφάνειες", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_xaragma, "Χαραγμα πάνω σε στυλό", "'Ολοι θα έχετε ακούσει για τον περίφημο στυλό πάνω στον οποίο μπορούμε εύκολα να χαράξουμε κάποια πράγματα.", "Χαράζετε επάνω σε ένα στυλό (κατά προτίμηση Bic) με την άκρη ενός διαβήτη οτιδήποτε σας είναι δύσκολο να μάθετε. \n Με τον τρόπο αυτό συμπληρώνετε σωστά την κόλλα σας αντιγράφοντας από το στυλό σας.", R.drawable.xaraksisestilo)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Χαρτί μέσα σε στυλό", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Στυλό για σκονάκι", "", "", R.drawable.stilosskonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Bluetooth", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Ψείρα", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Αριθμομηχανή", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Ζώνη παντελονιού", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Τιράντες", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Γάζες", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Κινητό τηλέφωνο", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Μαυρη Διαφάνεια", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Αλλαγη κόλλας σε τουαλλετα", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Χαρτομάντηλα", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Αντιγραφή απο διπλανό", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Ηχογράφηση", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Φωτογραφία τα θέματα", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Σόλες Παποουτσιών", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Αλλαγή φωτογραφίας πάσου", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Σημεία του σώματος", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Κασετίνα", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Στυλός με αόρατο μελάνι", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Γυαλιά με κάμερα", "", "", R.drawable.skonaki)));
        skonakia.add((new ListaAdapterNext(R.drawable.ic_app, "Ρολόι για σκονάκι", "", "", R.drawable.skonaki)));
    }
    private void createAdapter() {
        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<ListaAdapterNext> adapter = new MySpecialAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NextActivity.this, ListItemActivity.class);
                intent.putExtra("name", skonakia.get(position).getItemNextName().toString());
                intent.putExtra("icon", skonakia.get(position).getNextImage());
                intent.putExtra("usage", skonakia.get(position).getItemUsage());
                intent.putExtra("description", skonakia.get(position).getItemDescription());
                startActivity(intent);
            }
        });
    }

    private class MySpecialAdapter extends ArrayAdapter<ListaAdapterNext> {

        private MySpecialAdapter() {
            super(NextActivity.this, R.layout.list_item, skonakia);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View skonakiView = convertView;

            if (skonakiView == null) {
                skonakiView = getLayoutInflater().inflate(R.layout.list_item, parent, false);
            }

            ListaAdapterNext currentPosition = skonakia.get(position);

            ImageView iv = (ImageView) skonakiView.findViewById(R.id.imageView);
            iv.setImageResource(currentPosition.getImageNextId());


            TextView tv = (TextView) skonakiView.findViewById(R.id.textViewListItem);
            tv.setText(currentPosition.getItemNextName());

            return skonakiView;
        }
    }
}
