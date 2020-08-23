package br.com.projeto.personal_delivery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.projeto.personal_delivery.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(this::mostraInicio, 3000);
    }

    private void mostraInicio() {
        startActivity(new Intent(this, EntradaActivity.class));
        finish();

    }
}