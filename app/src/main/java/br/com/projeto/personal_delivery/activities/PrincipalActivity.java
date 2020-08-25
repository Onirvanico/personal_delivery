package br.com.projeto.personal_delivery.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.R;

public class PrincipalActivity extends AppCompatActivity {

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
