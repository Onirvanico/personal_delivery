package br.com.projeto.personal_delivery.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import br.com.projeto.personal_delivery.R;

public class AutenticacaoGoogle {

    private Context context;
    private static final int RC_LOGIN = 1;
    private Logar logar;
    private GoogleSignInClient client;

    public AutenticacaoGoogle(Context context) {
        this.context = context;
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

    public void AutenticaGoogle(String tokenId) {

        AuthCredential credencial = GoogleAuthProvider.getCredential(tokenId, null);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credencial).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Deu boa", Toast.LENGTH_SHORT).show();
                FirebaseUser currentUser = auth.getCurrentUser();
                Toast.makeText(context, "user " + currentUser.getDisplayName(),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Deu ruim" + task.getException().getMessage()
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface Logar {
        void tentaLogar(Intent client, int requestLogin);
    }

}
