package br.com.projeto.personal_delivery.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.auth.Autenticacao;
import br.com.projeto.personal_delivery.model.Usuario;
import br.com.projeto.personal_delivery.utils.ValidaFormulario;

public class CriaContaActivity extends AppCompatActivity {


    public static final String APPBAR_CRIACONTA = "Criar conta";
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
            autenticacao.criaConta(usuario.getEmail(), usuario.getSenha());
        } catch (NullPointerException e) {
            Log.e("Erro criar usuario ", e.getMessage() );
        }
    }

    private Usuario preencheUsuario() {
        TextView inputConfirmaSenha = findViewById(R.id.input_confirma_senha_cria_conta);

        TextView inputSenha = findViewById(R.id.input_senha_cria_conta);
        TextView inputEmail = findViewById(R.id.input_email_cria_conta);
        String email = inputEmail.getText().toString();
        String senha = inputSenha.getText().toString();
        if(ValidaFormulario.ehValidoFormularioESenhaDeConfirmacao(inputEmail,
                inputSenha,
                inputConfirmaSenha)) {

            return new Usuario(email, senha);
        }

        return null;
    }



}
