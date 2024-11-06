package com.example.gestioncontact;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;  // Import CheckBox
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText ednom, edmp;
    private Button btnval;
    private Button btnqte;
    private CheckBox checkBoxRememberMe;  // Declare CheckBox
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "PrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ednom = findViewById(R.id.ednom_auth);
        edmp = findViewById(R.id.edpass_auth);
        btnval = findViewById(R.id.btnvalider_auth);
        btnqte = findViewById(R.id.btnquitter_auth);
        checkBoxRememberMe = findViewById(R.id.checkbox_remember_me);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Check if preferences were saved before
        String savedUser = sharedPreferences.getString("user", null);
        if (savedUser != null) {
            Intent i = new Intent(MainActivity.this, Accueil.class);
            i.putExtra("USER", savedUser);
            startActivity(i);
            finish();  // Close the login activity
        }


        btnqte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });


        btnval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = ednom.getText().toString();
                String mp = edmp.getText().toString();
                if (nom.equalsIgnoreCase("safa") && mp.equals("123")) {
                    Intent i = new Intent(MainActivity.this, Accueil.class);
                    i.putExtra("USER", nom);
                    startActivity(i);
                    finish();  // Close the login activity so the user can't go back

                    // Save login state if "Remember Me" is checked
                    if (checkBoxRememberMe.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user", nom);
                        editor.apply();  // Save user
                    }

                } else {
                    Toast.makeText(MainActivity.this, "valeur non valide", Toast.LENGTH_LONG).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}
