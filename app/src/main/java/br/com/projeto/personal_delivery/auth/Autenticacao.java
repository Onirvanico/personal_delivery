package br.com.projeto.personal_delivery.auth;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.auth.callback.CallbackAutentica;
import br.com.projeto.personal_delivery.exceptions.AuthException;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class Autenticacao {

    public static final String PT_BR = "pt-br";
    private FirebaseAuth auth;
    private Context context;
    private CallbackAutentica callback;

    public Autenticacao(Context context, FirebaseAuth auth) {
        this.auth = auth;
        this.auth.setLanguageCode(PT_BR);
        this.context = context;
    }

    public void criaConta(String email, String senha, CallbackAutentica callback) {

        this.callback = callback;

        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener((Activity) context, this::naFinalizacaoDaCriacaoDaConta);
    }

    public void logaConta(String email, String senha, CallbackAutentica callback) {

        this.callback = callback;

        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener((Activity) context, this::naFinalizacaoDoLogin);
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

            FirebaseUser user = auth.getCurrentUser();

            if (!user.isEmailVerified()) {
                Toast.makeText(context,
                        "Confirme o cadastro enviado ao seu email", LENGTH_SHORT).show();

            } else {
                callback.teveSucesso(user);
                Log.i("Login ", "Logado com sucesso "
                        + user
                        .getEmail());

                Toast.makeText(context, "Usuário logado com sucesso", LENGTH_SHORT).show();
               // Log.i("displayName ", user.getDisplayName() );
                Log.i("getPhone", user.getEmail());

            }

        } else {
            AuthException taskAuthException = new AuthException<Task<AuthResult>>();
            taskAuthException.FalhaConexaoException(task, context);
            taskAuthException.UsuarioInvalidoException(task, context);
            taskAuthException.usuarioNaoCadastradoException(task, context);

            callback.teveFalha(task.getException().getMessage());

            Log.i("LoginError ", "Erro de autenticação " + task.getException()
                    .getMessage());
        }
    }

    private void naFinalizacaoDaCriacaoDaConta(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            verificaEmail();
            Log.i("Conta ", "Conta criada com êxito");
            FirebaseUser user = auth.getCurrentUser();

            callback.teveSucesso(user);

            Toast.makeText(context, user.getEmail(), LENGTH_LONG).show();

        } else {
            AuthException authException = new AuthException<Task<AuthResult>>();
            authException.ConflitoEmailException(task, context);
            authException.FalhaConexaoException(task, context);

            callback.teveFalha(task.getException().getMessage());

            Log.w("Conta ", "Falha ao criar conta: "
                    + task.getException().getMessage());
        }
    }

    private void naFinalizacaoDaRedefinicaoDeSenha(Task<Void> task) {
        if (task.isSuccessful()) {
            Toast.makeText(context, "Email enviado para redefinição de sua senha",
                    LENGTH_SHORT).show();
        } else {
            AuthException authException = new AuthException<Task<Void>>();
            authException.FalhaConexaoException(task, context);
            Toast.makeText(context, "Não foi possível enviar link para redefinir senha com " +
                            "o endereço informado",
                    LENGTH_LONG).show();
        }
    }


}
