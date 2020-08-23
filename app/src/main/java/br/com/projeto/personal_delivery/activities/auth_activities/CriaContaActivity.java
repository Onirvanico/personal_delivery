package br.com.projeto.personal_delivery.activities.auth_activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.auth.Autenticacao;
import br.com.projeto.personal_delivery.auth.callback.CallbackAutentica;
import br.com.projeto.personal_delivery.model.Usuario;
import br.com.projeto.personal_delivery.utils.ValidaCampo;

public class CriaContaActivity extends AppCompatActivity {


    public static final String APPBAR_CRIACONTA = "Criar conta";
    public static final String INFO_VERIFICA_EMAIL = "Foi enviado um email para o endereço cadastrado." +
        "Verifique sua caixa de mensagens e o valide, depois aperte ok e vá para tela de login";

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cria_conta);


        setTitle(APPBAR_CRIACONTA);

        auth = FirebaseAuth.getInstance();

        Button btCriaConta = findViewById(R.id.bt_cria_conta);
        btCriaConta.setOnClickListener(view -> {
            criaConta();
        });


    }


    private void criaConta() {

        try {
            Usuario usuario = preencheUsuario();
            Autenticacao autenticacao = new Autenticacao(this, auth);
            autenticacao.criaConta(usuario.getEmail(), usuario.getSenha(), new CallbackAutentica() {
                @Override
                public void teveSucesso(FirebaseUser user) {
                    enviaMensagemConfirmacaoSenha();
                }

                @Override
                public void teveFalha(String error) {

                }
            });
        } catch (NullPointerException e) {
            Log.e("Erro criar usuario ", e.getMessage());
        }
    }

    private void enviaMensagemConfirmacaoSenha() {
        new AlertDialog.Builder(CriaContaActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Validar cadastro")
                .setMessage(INFO_VERIFICA_EMAIL)
                .setPositiveButton("ok", this::vaiParaTelaLogin)
                .setNegativeButton("sair", (dialog, which) -> Snackbar.make(findViewById(R.id.meu_coordinator),
                        R.string.ratificar_verificacao_email,
                        Snackbar.LENGTH_LONG)
                        .setDuration(5000)
                        .show())
                .create()
                .show();
    }

    private Usuario preencheUsuario() {
        TextView inputConfirmaSenha = findViewById(R.id.input_confirma_senha_cria_conta);

        TextView inputSenha = findViewById(R.id.input_senha_cria_conta);
        TextView inputEmail = findViewById(R.id.input_email_cria_conta);
        String email = inputEmail.getText().toString();
        String senha = inputSenha.getText().toString();
        if (ValidaCampo.ehValidoFormularioESenhaDeConfirmacao(inputEmail,
                inputSenha,
                inputConfirmaSenha)) {

            return new Usuario(email, senha);
        }

        return null;
    }


    private void vaiParaTelaLogin(DialogInterface dialog, int which) {
        startActivity(new Intent(CriaContaActivity.this,
                LoginActivity.class));
        finish();
    }
}
