package br.com.projeto.personal_delivery.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.auth.Autenticacao;
import br.com.projeto.personal_delivery.utils.ValidaFormulario;

public class RedefineSenhaActivity extends AppCompatActivity {

    public static final String APPBAR_REDEFINIR_SENHA = "Redefinir Senha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefine_senha);

        setTitle(APPBAR_REDEFINIR_SENHA);


    }

    public void redefinindoSenha(View view) {
        if (view.getId() == R.id.bt_redefine_senha) {
            TextView inputEmail = findViewById(R.id.input_email_redefine_senha);

            if (!ValidaFormulario.ehCampoEmailVazio(inputEmail)) {
                String email = inputEmail.getText().toString();
                new Autenticacao(this).redefineSenha(email);

            }
        }
    }
}