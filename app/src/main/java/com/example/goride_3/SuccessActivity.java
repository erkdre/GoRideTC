package com.example.goride_3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_success);

    // Handler erstellen, um nach 2 Sekunden zur Karte zurückzukehren
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
        @Override
        public void run() {
            // Intent erstellen, um zur Karten-Activity zurückzukehren
            Intent intent = new Intent(SuccessActivity.this, MapsActivity.class); // Ersetze MapsActivity mit dem Namen deiner Karten-Activity
            startActivity(intent);finish(); // Beende die SuccessActivity
        }
    }, 2000); // 2000 Millisekunden = 2 Sekunden
}
}