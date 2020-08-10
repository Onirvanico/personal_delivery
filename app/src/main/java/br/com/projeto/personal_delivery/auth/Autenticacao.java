package br.com.projeto.personal_delivery.auth;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.exceptions.FormularioException;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class Autenticacao {

    public static final String PT_BR = "pt-br";
    private FirebaseAuth auth;
    private Context context;

    public Autenticacao(Context context) {
        auth = FirebaseAuth.getInstance();
        auth.setLanguageCode(PT_BR);
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

    public void redefineSenha(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this::naFinalizacaoDaRedefinicaoDeSenha);
    }


    private void verificaEmail() {
        FirebaseUser user = auth.getCurrentUser();
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


            if (!auth.getCurrentUser().isEmailVerified()) {
                Toast.makeText(context,
                        "Confirme o cadastro enviado ao seu email", LENGTH_SHORT).show();

            }

        } else {
            FormularioException taskFormularioException = new FormularioException<Task<AuthResult>>();
            taskFormularioException.FalhaConexaoException(task, context);
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
            FormularioException formularioException = new FormularioException<Task<AuthResult>>();
            formularioException.ConflitoEmailException(task, context);
            formularioException.FalhaConexaoException(task, context);

            Log.w("Conta ", "Falha ao criar conta: "
                    + task.getException().getMessage());
        }
    }

    private void naFinalizacaoDaRedefinicaoDeSenha(Task<Void> task) {
        if (task.isSuccessful()) {
            Toast.makeText(context, "Email enviado para redefinição de sua senha",
                    LENGTH_SHORT).show();
        } else {
            FormularioException formularioException = new FormularioException<Task<Void>>();
            formularioException.FalhaConexaoException(task, context);
            Toast.makeText(context, "Não foi possível enviar link para redefinir senha com " +
                            "o endereço informado",
                    LENGTH_LONG).show();
        }
    }
}
