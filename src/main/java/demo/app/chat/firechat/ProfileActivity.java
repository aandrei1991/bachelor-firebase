package demo.app.chat.firechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {
    long totalUserCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initialize elements
        final Spinner spokenLangsSpinner = (Spinner) findViewById(R.id.nativeLangSpinner);
        final Spinner requestedLangsSpinner = (Spinner) findViewById(R.id.requestedLanguageSpinner);
        final Spinner locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        Button btnContinue = (Button) findViewById(R.id.btn_continue);

        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference fireReference = FirebaseDatabase.getInstance().getReference();
        totalUserCount = Long.parseLong(fireReference.child("users").orderByChild("totalUserCount").toString());

        //Populate spinners
        List<String> spokenSpinnerArray = new ArrayList<>();
        for (int i = 0; i < Locale.getAvailableLocales().length; i++) {
            spokenSpinnerArray.add(Locale.getAvailableLocales()[i].getDisplayName() + " - " + Locale.getAvailableLocales()[i].getDisplayLanguage());
            Log.i("INFO: ", spokenSpinnerArray.get(i));
        }
        ArrayAdapter<String> spokenAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spokenSpinnerArray);
        spokenLangsSpinner.setAdapter(spokenAdapter);

        List<String> requestedSpinnerAdapter = new ArrayList<>(Arrays.asList(Locale.getISOLanguages()));
        ArrayAdapter<String> requestedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, requestedSpinnerAdapter);
        requestedLangsSpinner.setAdapter(requestedAdapter);

        List<String> locationSpinnerArray = new ArrayList<>(Arrays.asList(Locale.getISOCountries()));
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationSpinnerArray);
        locationSpinner.setAdapter(locationAdapter);

        //On click listener for the continue button
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spokenLangsSpinner.getSelectedItem().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_native_lang), Toast.LENGTH_LONG).show();
                } else if (requestedLangsSpinner.getSelectedItem().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_requested_lang), Toast.LENGTH_LONG).show();
                } else if (locationSpinner.getSelectedItem().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_location), Toast.LENGTH_LONG).show();
                } else {
                    ArrayList<String> currentSpokenLangs = new ArrayList<>();
                    currentSpokenLangs.add(spokenLangsSpinner.getSelectedItem().toString());

                    ArrayList<String> currentReqLangs = new ArrayList<>();
                    currentReqLangs.add(requestedLangsSpinner.getSelectedItem().toString());
                    if (TextUtils.isEmpty(currentFirebaseUser.getDisplayName())) {
                        User currentUser = new User("Anonymous" + (++totalUserCount), currentFirebaseUser.getUid(), currentFirebaseUser.getEmail(), locationSpinner.getSelectedItem().toString(), currentReqLangs, currentSpokenLangs);
                        fireReference.child("users").child(currentFirebaseUser.getUid()).setValue(currentUser);
                    } else {
                        User currentUser = new User(currentFirebaseUser.getDisplayName(), currentFirebaseUser.getUid(), currentFirebaseUser.getEmail(), locationSpinner.getSelectedItem().toString(), currentReqLangs, currentSpokenLangs);
                        fireReference.child("users").child(currentFirebaseUser.getUid()).setValue(currentUser);
                    }

                    Intent startSearch = new Intent(getBaseContext(), SearchActivity.class);
                    startActivity(startSearch);
                }
            }
        });
    }
}
