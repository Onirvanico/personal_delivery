package br.com.projeto.personal_delivery.utils;

import android.widget.TextView;

public class ValidaFormulario {

    public static boolean ehValidoFormularioESenhaDeConfirmacao(TextView email,
                                                                TextView senha,
                                                                TextView senhaConfirmacao) {
         return
                 ehValidoFormulario(email, senha) &&
                 ehMesmaSenha(senha, senhaConfirmacao);
    }


    public static boolean ehValidoFormulario(TextView email, TextView senha) {
        if (!ehCampoEmailVazio(email) && ehCampoSenhaValido(senha))
            return true;
        if(!temTamanhoSenha(senha)) senha.setError("A senha deve conter no mínimo seis caracteres");

        if (email.getText().toString().isEmpty()) email.setError("Preencha o campo de email");

        if (senha.getText().toString().isEmpty()) senha.setError("Preencha o campo de senha ");

        return false;

    }

    private static boolean ehCampoSenhaValido(TextView senha) {
        return  temTamanhoSenha(senha) &&
                !senha.getText().toString().isEmpty();
    }

    private static boolean ehCampoEmailVazio(TextView email) {
        return email.getText().toString().isEmpty();
    }

    private static boolean temTamanhoSenha(TextView senha) {
        return senha.getText().toString().length() > 5;
    }

    private static boolean ehMesmaSenha(TextView campoSenha, TextView campoSenhaConfirmacao) {
        String senha = campoSenha.getText().toString();
        String senhaConfirmacao = campoSenhaConfirmacao.getText().toString();

        if(!senha.equals(senhaConfirmacao)) campoSenha.setError("Senha não confirmada");

        return senha.equals(senhaConfirmacao);
    }
}
