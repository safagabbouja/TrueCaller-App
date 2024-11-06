package com.example.gestioncontact;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Ajout extends AppCompatActivity {
    EditText ednom,edpseudo,ednum;
    private Button btnval;
    private Button btnquit;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajout);

        btnval=findViewById(R.id.btnval_ajout);
        btnquit=findViewById(R.id.btnquit_ajout);
        ednom=findViewById(R.id.ednom_ajout);
        edpseudo=findViewById(R.id.edpseudo_ajout);
        ednum=findViewById(R.id.ednum_ajout);

        //evenement
        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ajout.this.finish();
            }
        });

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Event for Save button
        btnval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = ednom.getText().toString().trim();
                String pseudo = edpseudo.getText().toString().trim();
                String numero = ednum.getText().toString().trim();

                if (nom.isEmpty() || pseudo.isEmpty() || numero.isEmpty()) {
                    Toast.makeText(Ajout.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Contact contact = new Contact(nom, pseudo, numero);
                    databaseHelper.addContact(contact); // Save to database

                    Log.d("Ajout", "Contact saved: " + contact.toString());

                    // Clear input fields
                    ednom.setText("");
                    edpseudo.setText("");
                    ednum.setText("");
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