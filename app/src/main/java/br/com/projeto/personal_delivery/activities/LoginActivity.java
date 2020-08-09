package br.com.projeto.personal_delivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.auth.Autenticacao;
import br.com.projeto.personal_delivery.model.Usuario;

import static br.com.projeto.personal_delivery.utils.ValidaFormulario.ehValidoFormulario;

public class LoginActivity extends AppCompatActivity {

    public static final String APPBAR_LOGIN = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(APPBAR_LOGIN);

        irParaTelaCriaConta();

        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(this, gso);*/


        Button btLoga = findViewById(R.id.btCriaConta);
        btLoga.setOnClickListener(view -> {
            logaConta();
        });

    }

    private void irParaTelaCriaConta() {
        TextView linkTelaCriaConta = findViewById(R.id.textLinkTelaCriaConta);
        linkTelaCriaConta.setOnClickListener(view -> {
            startActivity(new Intent(this, CriaContaActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null)
            vaiParaMainActivity();*/

    }

    private void logaConta() {
        Usuario usuario = preencheUsuario();
        if (usuario != null)
            autenticaConta(usuario);
    }

    private void autenticaConta(Usuario usuario) {


        try{
            FirebaseUser autenticado = new Autenticacao(this).
                    LogaConta(usuario.getEmail(), usuario.getSenha());

            if(autenticado.isEmailVerified())
                vaiParaMainActivity();

        } catch(NullPointerException e) {
            Log.e("Objeto vazio ", e.getMessage() );
            Toast.makeText(this, "usuário e/ou senha inválido", Toast.LENGTH_SHORT).show();
        }
    }

    private void vaiParaMainActivity() {
        startActivity(new Intent(this, PrincipalActivity.class));
    }

    private Usuario preencheUsuario() {
        TextView email = findViewById(R.id.inputEmail_login);
        Log.i("estado ", String.valueOf(email.isSelected()));
        TextView senha = findViewById(R.id.inputSenha_login);

        if (ehValidoFormulario(email, senha))
            return new Usuario(email.getText().toString(), senha.getText().toString());
        return null;
    }




}
