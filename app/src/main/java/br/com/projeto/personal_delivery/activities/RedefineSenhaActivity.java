package br.com.projeto.personal_delivery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.auth.Autenticacao;

public class RedefineSenhaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefine_senha);

        TextView email = findViewById(R.id.input_email_redefine_senha);
        TextView brRedefineSenha = findViewById(R.id.bt_redefine_senha);

        new Autenticacao(this)
    }
}