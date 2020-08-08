package br.com.projeto.personal_delivery.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.auth.Autenticacao;
import br.com.projeto.personal_delivery.model.Usuario;

import static android.widget.Toast.LENGTH_LONG;

public class CriaContaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cria_conta);

        setTitle("Criar conta");

        Button btCriaConta = findViewById(R.id.btCriaConta);
        btCriaConta.setOnClickListener(view -> {
            criaConta();
        });


    }


    private void criaConta() {
        Usuario usuario = preencheUsuario();

        TextView inputConfirmaSenha = findViewById(R.id.inputConfirmaSenha_login);
        String senhaConfirmacao = inputConfirmaSenha.getText().toString();

        if (senhaConfirmacao.equals(usuario.getSenha())) {
            Toast.makeText(this, "Certinho", Toast.LENGTH_SHORT).show();
            Autenticacao autenticacao = new Autenticacao(this);
            autenticacao.criaConta(usuario.getEmail(), usuario.getSenha());

        } else {
            Log.i("Confirma ", "senha " + usuario.getSenha()+ " senhaC " + senhaConfirmacao);
            Toast.makeText(this, "A confirmação de senha não pode ser validada", Toast.LENGTH_SHORT).show();

        }
    }

    private Usuario preencheUsuario() {
        TextView inputSenha = findViewById(R.id.inputSenha_login);
        TextView inputEmail = findViewById(R.id.inputEmail_login);
        String email = inputEmail.getText().toString();
        String senha = inputSenha.getText().toString();

        return new Usuario(email, senha);
    }



}
