package br.com.projeto.personal_delivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.auth.Autenticacao;
import br.com.projeto.personal_delivery.auth.AutenticacaoGoogle;
import br.com.projeto.personal_delivery.model.Usuario;

import static br.com.projeto.personal_delivery.consts.IntentCode.RC_LOGIN;
import static br.com.projeto.personal_delivery.utils.ValidaFormulario.ehValidoFormulario;

public class LoginActivity extends AppCompatActivity {

    public static final String APPBAR_LOGIN = "Login";

    private AutenticacaoGoogle autenticacaoGoogle;
    private Autenticacao autenticacao;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(APPBAR_LOGIN);

        auth = FirebaseAuth.getInstance();
        autenticacaoGoogle = new AutenticacaoGoogle(this);
        autenticacao = new Autenticacao(this, auth);

        irParaTelaCriaConta();
        irParaTelaRedefineSenha();

        configuraBotaLogaComGoogle();

        configuraBotaoLogaComEmailESenha();

    }

    private void configuraBotaoLogaComEmailESenha() {
        Button btLoga = findViewById(R.id.bt_entrar_login);
        btLoga.setOnClickListener(view -> {
           /* auth.signInWithEmailAndPassword("cristianosantosesilva@gmail.com", "33142544" )
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Usuário logado com sucesso",
                                    LENGTH_LONG).show();
                            usuarioVaiParaTelaPrincipal();
                        } else {
                            Toast.makeText(this, "Falha ao tentar logar",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });*/
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
                autenticacaoGoogle.AutenticaGoogle(conta.getIdToken());
                usuarioVaiParaTelaPrincipal();

            } catch (ApiException e) {
                Log.w("Erro ao logar", "onActivityResult: " + e.getMessage());

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void irParaTelaCriaConta() {
        TextView botaoTelaCriaConta = findViewById(R.id.bt_registrar);
        botaoTelaCriaConta.setOnClickListener(view -> {
            startActivity(new Intent(this, CriaContaActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    /*    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null)
            usuarioVaiParaTelaPrincipal();*/

    }

    private void logaConta() {
        Usuario usuario = preencheUsuario();
        if (usuario != null)
            autenticaConta(usuario);
    }

    private void autenticaConta(Usuario usuario) {



                autenticacao.logaConta(usuario.getEmail(), usuario.getSenha(),
                            new Autenticacao.CallbackAutentica() {
                @Override
                public void teveSucesso(FirebaseUser user) {

                    usuarioVaiParaTelaPrincipal();
                }

                @Override
                public void teveFalha(String error)  {
                    Log.e("ExceptionLogin ", error );
                }
            });

    }

    private void usuarioVaiParaTelaPrincipal() {

        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }

    private Usuario preencheUsuario() {
        TextView email = findViewById(R.id.input_email_login);
        TextView senha = findViewById(R.id.input_senha_login);

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
