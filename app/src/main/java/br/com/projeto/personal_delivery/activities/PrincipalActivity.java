package br.com.projeto.personal_delivery.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.R;

public class PrincipalActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        buscaUsuario(usuarioAtual);

        View sair = findViewById(R.id.btSair);
        sair.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            finish();
        });


    }


    private void buscaUsuario(FirebaseUser usuarioAtual) {

        if(usuarioAtual != null) {
            Log.i("principal_usuario ", usuarioAtual.getEmail());

            TextView texto = findViewById(R.id.textTelaPrincipal);
            String textoExistente = texto.getText().toString();
            texto.setText(textoExistente + " " + usuarioAtual.getEmail());

        }
    }

}
