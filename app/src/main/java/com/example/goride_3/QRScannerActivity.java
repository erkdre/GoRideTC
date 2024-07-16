package com.example.goride_3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult; // Importiere IntentResult

public class QRScannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Starte den QR-Code-Scanner direkt beim Öffnen der Activity
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // Scan abgebrochen
                finish();
            } else {
                // QR-Code erfolgreich gescannt, verarbeite den Inhalt
                handleScannedQRCode(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleScannedQRCode(String qrCodeContent) {
        if (qrCodeContent.equals("AAA")) { // Überprüfe, ob das Codewort korrekt ist
            Intent intent = new Intent(this, SuccessActivity.class); // Erstelle Intent für die neue Activity
            startActivity(intent); // Starte die neue Activity
            finish(); // Schließe die QRScannerActivity
        } else {
            // Falsches Codewort, zeige eine Fehlermeldung oder mache etwas anderes
            // ...
        }
    }
}