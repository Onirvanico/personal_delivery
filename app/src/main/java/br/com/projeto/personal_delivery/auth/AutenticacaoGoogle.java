package br.com.projeto.personal_delivery.auth;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.auth.callback.CallbackAutentica;
import br.com.projeto.personal_delivery.exceptions.AuthException;

import static br.com.projeto.personal_delivery.consts.IntentCode.RC_LOGIN;

public class AutenticacaoGoogle  {

    private CallbackAutentica callbackAutentica;
    private Context context;
    private FirebaseAuth auth;
    private GoogleSignInClient client;

    public AutenticacaoGoogle(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
        configuraLoginGoogle(context);
    }

    private void configuraLoginGoogle(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(context, gso);
    }


    public void LogaContaGoogle(Logar logar ) {
        Intent logaIntent = client.getSignInIntent();
        logar.tentaLogar(logaIntent, RC_LOGIN);
    }

    public void AutenticaGoogle(String tokenId, CallbackAutentica callback) {
        this.callbackAutentica = callback;

        AuthCredential credencial = GoogleAuthProvider.getCredential(tokenId, null);
        auth.signInWithCredential(credencial).addOnCompleteListener(this::naFinalizacaoDoLogin);
    }

    private void naFinalizacaoDoLogin(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            FirebaseUser user = auth.getCurrentUser();
            Toast.makeText(context, "Usuário com credencial aceita", Toast.LENGTH_SHORT).show();
            callbackAutentica.teveSucesso(user);

        } else {
            AuthException taskAuthException = new AuthException<Task<AuthResult>>();
            taskAuthException.FalhaConexaoException(task, context);
            taskAuthException.falhaAutenticacaoUsuario(task, context);

            callbackAutentica.teveFalha(task.getException().getMessage());

        }
    }


    public interface Logar {
        void tentaLogar(Intent client, int requestLogin);
    }

}
