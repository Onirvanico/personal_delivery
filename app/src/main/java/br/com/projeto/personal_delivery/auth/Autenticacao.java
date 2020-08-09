package br.com.projeto.personal_delivery.auth;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class Autenticacao {

    private FirebaseAuth auth;
    private Context context;

    public Autenticacao(Context context) {
        auth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public void criaConta(String email, String senha) {

        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener((Activity) context, this::naFinalizacaoDaCriacaoDaConta);
    }

    public FirebaseUser LogaConta(String email, String senha) {

        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this::naFinalizacaoDoLogin);
        return auth.getCurrentUser();
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

    private void naFinalizacaoDoLogin(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Log.i("Login ", "Logado com sucesso "
                    + auth.getCurrentUser()
                    .getEmail());


            Toast.makeText(context, "valor " + auth.getCurrentUser().isEmailVerified(), LENGTH_SHORT).show();

            if (!auth.getCurrentUser().isEmailVerified()) {
                Toast.makeText(context,
                        "Confirme o cadastro enviado ao seu email", LENGTH_SHORT).show();

            }

        } else {
            Log.i("LoginError ", "Erro de autenticação " + task.getException()
                    .getMessage());
        }
    }

    private void naFinalizacaoDaCriacaoDaConta(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            verificaEmail();
            Log.i("Conta ", "Conta criada com êxito");
            FirebaseUser currentUser = auth.getCurrentUser();
            Toast.makeText(context, currentUser.getEmail(), LENGTH_LONG).show();

        } else {
            try {
                throw task.getException();

            } catch (FirebaseAuthUserCollisionException e) {
                Toast.makeText(context, "Email já cadastrado", LENGTH_SHORT).show();
                Log.e("Erro Cadastro", "naFinalizacaoDaCriacaoDaConta: " + e.getMessage());

            } catch (Exception e) {
                Toast.makeText(context, "Erro ao tentar cadastrar usuário", LENGTH_LONG).show();
            }

            Log.w("Conta ", "Falha ao criar conta: "
                    + task.getException().getMessage());
        }
    }
}
