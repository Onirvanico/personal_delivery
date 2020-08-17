package br.com.projeto.personal_delivery.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.auth.Autenticacao;
import br.com.projeto.personal_delivery.utils.ValidaCampo;

public class RedefineSenhaActivity extends AppCompatActivity {

    public static final String APPBAR_REDEFINIR_SENHA = "Redefinir Senha";
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefine_senha);

        setTitle(APPBAR_REDEFINIR_SENHA);
        auth = FirebaseAuth.getInstance();


    }

    public void redefinindoSenha(View view) {
        if (view.getId() == R.id.bt_redefine_senha) {
            TextView inputEmail = findViewById(R.id.input_email_redefine_senha);

            if (!ValidaCampo.ehCampoEmailVazio(inputEmail)) {
                String email = inputEmail.getText().toString();
                new Autenticacao(this, auth).redefineSenha(email);

            }
        }
    }
}