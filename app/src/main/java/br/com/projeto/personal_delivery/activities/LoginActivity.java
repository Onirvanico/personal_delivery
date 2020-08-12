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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.auth.Autenticacao;
import br.com.projeto.personal_delivery.auth.AutenticacaoGoogle;
import br.com.projeto.personal_delivery.model.Usuario;

import static br.com.projeto.personal_delivery.consts.IntentCode.CHAVE_USUARIO;
import static br.com.projeto.personal_delivery.consts.IntentCode.CHAVE_USUARIO_GOOGLE;
import static br.com.projeto.personal_delivery.consts.IntentCode.RC_LOGIN;
import static br.com.projeto.personal_delivery.utils.ValidaFormulario.ehValidoFormulario;

public class LoginActivity extends AppCompatActivity {

    public static final String APPBAR_LOGIN = "Login";

    private AutenticacaoGoogle autenticacaoGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(APPBAR_LOGIN);

        autenticacaoGoogle = new AutenticacaoGoogle(this);

        irParaTelaCriaConta();
        irParaTelaRedefineSenha();

        configuraBotaLogaComGoogle();

        configuraBotaoLogaComEmailESenha();

    }

    private void configuraBotaoLogaComEmailESenha() {
        Button btLoga = findViewById(R.id.btLogaConta);
        btLoga.setOnClickListener(view -> {
            logaConta();
        });
    }

    private void configuraBotaLogaComGoogle() {
        Button btLogaContaGoogle = findViewById(R.id.btLogaContaGoogle);
        btLogaContaGoogle.setOnClickListener(view -> {
            autenticacaoGoogle.LogaContaGoogle((client, requestLogin) -> {
                startActivityForResult(client, requestLogin);
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_LOGIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount conta = task.getResult(ApiException.class);
                FirebaseUser usuarioAtual = autenticacaoGoogle.AutenticaGoogle(conta.getIdToken());
                enviaUsuarioGoogleParaTelaPrincipal(usuarioAtual);
                //vaiParaMainActivity();
            } catch (ApiException e) {
                Log.w("Erro ao logar", "onActivityResult: " + e.getMessage());

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void enviaUsuarioGoogleParaTelaPrincipal(FirebaseUser usuarioAtual) {
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra(CHAVE_USUARIO_GOOGLE, usuarioAtual);
        startActivity(intent);
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
            FirebaseUser usuarioAtual = new Autenticacao(this).
                    LogaConta(usuario.getEmail(), usuario.getSenha());

            if (usuarioAtual.isEmailVerified()) {

                usuarioVaiParaTelaPrincipal(usuarioAtual);
            }

        } catch (NullPointerException e) {
            Log.w("Email nao verificado", e.getMessage());
            Toast.makeText(this, "usuário e/ou senha inválido",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void usuarioVaiParaTelaPrincipal(FirebaseUser usuarioAtual) {

        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra(CHAVE_USUARIO, usuarioAtual);
        startActivity(intent);
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

}
