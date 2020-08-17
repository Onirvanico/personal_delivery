package br.com.projeto.personal_delivery.auth.callback;

import com.google.firebase.auth.FirebaseUser;

public interface CallbackAutentica {

    void teveSucesso(FirebaseUser user);
    void teveFalha(String error);
}
