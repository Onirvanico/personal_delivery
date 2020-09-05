package br.com.projeto.personal_delivery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.activities.auth_activities.CriaContaActivity;
import br.com.projeto.personal_delivery.activities.auth_activities.LoginActivity;
import br.com.projeto.personal_delivery.activities.navigation_menu.MenuActivity;

public class EntradaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        configuraBotaoRegistrarMe();

        configuraBotaoEntrarNaConta();


    }

    @Override
    protected void onStart() {
        super.onStart();

        atualizaTela();
    }

    private void atualizaTela() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null) {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }
    }

    private void configuraBotaoEntrarNaConta() {
        Button bt_ir_para_login = findViewById(R.id.bt_entrar_login_activity_entrada);
        bt_ir_para_login.setOnClickListener(this::vaiParaLogin);
    }

    private void configuraBotaoRegistrarMe() {
        Button bt_ir_cria_conta = findViewById(R.id.bt_entrar_cria_conta_activity_entrada);
        bt_ir_cria_conta.setOnClickListener(this::vaiParaCriaConta);
    }

    private void vaiParaCriaConta(View view) {
        startActivity(new Intent(this, CriaContaActivity.class));
        finish();
    }

    private void vaiParaLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}