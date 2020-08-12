package br.com.projeto.personal_delivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.R;

import static br.com.projeto.personal_delivery.consts.IntentCode.CHAVE_USUARIO;
import static br.com.projeto.personal_delivery.consts.IntentCode.CHAVE_USUARIO_GOOGLE;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buscaUsuario();

    }


    private void buscaUsuario() {
        Intent intent = getIntent();
        if (intent.hasExtra(CHAVE_USUARIO_GOOGLE)) {
            setNomeUsuarioNaTela(intent, CHAVE_USUARIO_GOOGLE, "\\s+");

        } else if (intent.hasExtra(CHAVE_USUARIO)) {
            setNomeUsuarioNaTela(intent, CHAVE_USUARIO, "\\u0020");
        }
    }

    private void setNomeUsuarioNaTela(Intent intent, String chaveUsuario, String s) {
        FirebaseUser usuario = intent.getParcelableExtra(chaveUsuario);
        String usuarioAtual = usuario.getDisplayName();
        String[] nomeUsuario = usuarioAtual.split(s);
        String primeiroNome = nomeUsuario[0];

        TextView texto = findViewById(R.id.textTelaPrincipal);
        String textoExistente = texto.getText().toString();
        texto.setText(textoExistente + " " + primeiroNome);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
