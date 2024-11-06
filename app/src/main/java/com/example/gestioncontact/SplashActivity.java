package com.example.gestioncontact;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Assurez-vous que le bon layout est utilisé

        // Délai pour afficher le splash screen
        new Handler().postDelayed(() -> {
            // Lancer l'activité principale après un délai
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Terminer l'activité splash
        }, 3000); // 3000 ms = 3 secondes
    }
}
