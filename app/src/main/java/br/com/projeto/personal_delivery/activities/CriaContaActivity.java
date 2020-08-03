package br.com.projeto.personal_delivery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.model.Usuario;

import static android.widget.Toast.LENGTH_LONG;

public class CriaContaActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);

        auth = FirebaseAuth.getInstance();

        Button btCriaConta = findViewById(R.id.btLogarConta);
        btCriaConta.setOnClickListener(view -> {
            criaConta();
        });


    }



    private void criaConta() {
        Usuario usuario = preencheUsuario();

        auth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful())  {
                        verificaEmail();
                        Log.i("Conta ", "Conta criada com êxito");
                        FirebaseUser currentUser = auth.getCurrentUser();
                        Toast.makeText(this, currentUser.getEmail(), LENGTH_LONG).show();

                    } else {
                        Log.w("Conta ", "Deu ruim lek" + task.getException().getMessage() );
                    }

                });
    }

    private Usuario preencheUsuario() {
        TextView inputSenha = findViewById(R.id.inputSenha_login);
        TextView inputEmail = findViewById(R.id.inputEmail_login);
        String email = inputEmail.getText().toString();
        String senha = inputSenha.getText().toString();

        return new Usuario(email, senha);
    }

    private void verificaEmail() {
        FirebaseUser user = auth.getCurrentUser();
        auth.setLanguageCode("pt-br");
        user.sendEmailVerification().addOnCompleteListener(task -> {
           if(task.isSuccessful()) {
               Toast.makeText(this, "Email de verificação enviado", LENGTH_LONG).show();
           } else {
               Log.w("Verifica email ", "Erro ao enviar email de verificação " + task.getException().getMessage() );
           }
        });
    }

}
