package br.com.projeto.personal_delivery.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.auth.Autenticacao;
import br.com.projeto.personal_delivery.model.Usuario;
import br.com.projeto.personal_delivery.utils.ValidaFormulario;

public class CriaContaActivity extends AppCompatActivity {


    public static final String APPBAR_CRIACONTA = "Criar conta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cria_conta);

        setTitle(APPBAR_CRIACONTA);

        Button btCriaConta = findViewById(R.id.btCriaConta);
        btCriaConta.setOnClickListener(view -> {
            criaConta();
        });


    }


    private void criaConta() {


        try {
            Usuario usuario = preencheUsuario();
            Autenticacao autenticacao = new Autenticacao(this);
            autenticacao.criaConta(usuario.getEmail(), usuario.getSenha());
        } catch (NullPointerException e) {
            Log.e("Erro criar usuario ", e.getMessage() );
        }
    }

    private Usuario preencheUsuario() {
        TextView inputConfirmaSenha = findViewById(R.id.inputConfirmaSenha_login);

        TextView inputSenha = findViewById(R.id.inputSenha_login);
        TextView inputEmail = findViewById(R.id.inputEmail_login);
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
