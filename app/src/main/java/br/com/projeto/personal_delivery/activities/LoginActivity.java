package br.com.projeto.personal_delivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.auth.Autenticacao;
import br.com.projeto.personal_delivery.model.Usuario;

import static br.com.projeto.personal_delivery.utils.ValidaFormulario.ehValidoFormulario;

public class LoginActivity extends AppCompatActivity {

    public static final String APPBAR_LOGIN = "Login";
    public static final int RC_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(APPBAR_LOGIN);

        irParaTelaCriaConta();
        irParaTelaRedefineSenha();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(this, gso);
        Intent logaIntent = client.getSignInIntent();

        startActivityForResult(logaIntent, RC_LOGIN);




        Button btLoga = findViewById(R.id.btLogaConta);
        btLoga.setOnClickListener(view -> {
            logaConta();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_LOGIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount conta = task.getResult(ApiException.class);
                logaContaGoogle(conta.getIdToken());
            } catch (ApiException e) {
                Log.w("Erro ao logar", "onActivityResult: " + e.getMessage() );
            }
        }
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


        try {
            FirebaseUser usuarioAtenticado = new Autenticacao(this).
                    LogaConta(usuario.getEmail(), usuario.getSenha());

            if (usuarioAtenticado.isEmailVerified())
                vaiParaMainActivity();

        } catch (NullPointerException e) {
            Log.w("Email nao verificado", e.getMessage());
            Toast.makeText(this, "usuário e/ou senha inválido",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void vaiParaMainActivity() {

        startActivity(new Intent(this, PrincipalActivity.class));
    }

    private Usuario preencheUsuario() {
        TextView email = findViewById(R.id.inputEmail_login);
        TextView senha = findViewById(R.id.inputSenha_login);

        if (ehValidoFormulario(email, senha))
            return new Usuario(email.getText().toString(), senha.getText().toString());
        return null;
    }


    private void irParaTelaRedefineSenha() {
        TextView linkTelaRedefineSenha = findViewById(R.id.textLinkTelaAlteraSenha);
        linkTelaRedefineSenha.setOnClickListener(view -> startActivity(
                new Intent(this, RedefineSenhaActivity.class)));

    }

    private void logaContaGoogle(String tokenId) {

        AuthCredential credencial = GoogleAuthProvider.getCredential(tokenId, null);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credencial).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(this, "Deu boa", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Deu ruim" + task.getException().getMessage()
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
