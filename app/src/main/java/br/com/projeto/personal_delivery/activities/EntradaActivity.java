package br.com.projeto.personal_delivery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.projeto.personal_delivery.R;

public class EntradaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        configuraBotaoRegistrarMe();

        configuraBotaoEntrarNaConta();


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
    }

    private void vaiParaLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}