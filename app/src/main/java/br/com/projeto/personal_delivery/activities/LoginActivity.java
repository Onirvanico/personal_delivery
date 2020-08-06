package br.com.projeto.personal_delivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        TextView linkTelaCriaConta = findViewById(R.id.textLinkTelaCriaConta);
        linkTelaCriaConta.setOnClickListener(view -> {
            startActivity(new Intent(this, CriaContaActivity.class));
        });
        Button btLoga = findViewById(R.id.btCriaConta);
        btLoga.setOnClickListener(view -> {
            logaConta();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
      /*  if(currentUser != null)
            vaiParaMainActivity();*/

    }

    private void logaConta() {
        Usuario usuario = preencheUsuario();
        if (usuario != null)
            autenticaConta(usuario);
    }

    private void autenticaConta(Usuario usuario) {
        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i("Login ", "Logado com sucesso "
                                + auth.getCurrentUser()
                                .getEmail());

                       /* if (auth.getCurrentUser() != null) {
                            vaiParaMainActivity();
                        }*/
                    } else {
                        Log.i("LoginError ", "Aí deu ruim lek" + task.getException()
                                .getMessage());

                        Toast.makeText(this,
                                "Falha na autenticação" + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void vaiParaMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private Usuario preencheUsuario() {
        TextView email = findViewById(R.id.inputEmail_login);
        Log.i("estado ", String.valueOf(email.isSelected()));
        TextView senha = findViewById(R.id.inputSenha_login);

        if (ehValidoFormulario(email, senha))
            return new Usuario(email.getText().toString(), senha.getText().toString());
        return null;
    }


    private boolean ehValidoFormulario(TextView email, TextView senha) {
        if (!email.getText().toString().isEmpty() && !senha.getText().toString().isEmpty()) return true;
           senha.getText().toString().length();
        if (email.getText().toString().isEmpty()) email.setError("Preencha o campo de email");

        if (senha.getText().toString().isEmpty()) senha.setError("Preencha o campo de senha ");

        return false;
    }
}
