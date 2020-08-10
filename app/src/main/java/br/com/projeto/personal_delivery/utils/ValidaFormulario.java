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

        return false;

    }

    private static boolean ehCampoSenhaValido(TextView senha) {
        boolean ehVazio = senha.getText().toString().isEmpty();

        if(ehVazio)
            senha.setError("Preencha o campo de senha ");

        return  !ehVazio &&
                !temTamanhoSenha(senha);
    }

    public static boolean ehCampoEmailVazio(TextView email) {
        boolean ehVazio = email.getText().toString().isEmpty();

        if (ehVazio)
            email.setError("Preencha o campo de email");

        return ehVazio;
    }

    private static boolean temTamanhoSenha(TextView senha) {
        int tamanho = senha.getText().toString().length();

        if (tamanho < 6)
            senha.setError("A senha deve conter no mínimo seis caracteres");

        return senha.getText().toString().length() > 5;
    }

    private static boolean ehMesmaSenha(TextView campoSenha, TextView campoSenhaConfirmacao) {
        String senha = campoSenha.getText().toString();
        String senhaConfirmacao = campoSenhaConfirmacao.getText().toString();

        if(!senha.equals(senhaConfirmacao)) campoSenha.setError("Senha não confirmada");

        return senha.equals(senhaConfirmacao);
    }
}
