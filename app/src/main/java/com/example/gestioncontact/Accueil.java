package com.example.gestioncontact;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Accueil extends AppCompatActivity {

    private TextView tvusername;
    private Button btnajout;
    private Button btnaff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accueil);

        tvusername=findViewById(R.id.tv1_accueil);
        btnajout=findViewById(R.id.bntajout_accueil);
        btnaff=findViewById(R.id.bntaff_accueil);

        Intent x = this.getIntent();
        Bundle b = x.getExtras();
        String u = b.getString("USER");
        tvusername.setText("Accueil de Mme. "+u);

        btnajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Accueil.this,Ajout.class);
                Accueil.this.startActivity(i);
            }
        });

        btnaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Accueil.this,Affichage.class);
                Accueil.this.startActivity(i);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}