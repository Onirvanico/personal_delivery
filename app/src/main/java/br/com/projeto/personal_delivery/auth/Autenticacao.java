package br.com.projeto.personal_delivery.auth;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_LONG;

public class Autenticacao {

    private FirebaseAuth  auth;
    private Context context;

    public Autenticacao(Context context) {
        auth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public void criaConta(String email, String senha) {

        auth.createUserWithEmailAndPassword( email, senha)
                .addOnCompleteListener((Activity)context, task -> {
            if (task.isSuccessful()) {
                verificaEmail();
                Log.i("Conta ", "Conta criada com êxito");
                FirebaseUser currentUser = auth.getCurrentUser();
                Toast.makeText(context, currentUser.getEmail(), LENGTH_LONG).show();

            } else {
                Log.w("Conta ", "Deu ruim lek" + task.getException().getMessage());
            }

        });
    }

    private void verificaEmail() {
        FirebaseUser user = auth.getCurrentUser();
        auth.setLanguageCode("pt-br");
        user.sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Email de verificação enviado", LENGTH_LONG).show();
            } else {
                Log.w("Verifica email ", "Erro ao enviar email de verificação " + task.getException().getMessage());
            }
        });
    }
}
