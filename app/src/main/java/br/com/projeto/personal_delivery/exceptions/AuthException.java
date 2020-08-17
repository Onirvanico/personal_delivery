package br.com.projeto.personal_delivery.exceptions;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class AuthException<T> {

    public void ConflitoEmailException(Task<T> task, Context context){
        try {
            throw task.getException();

        } catch(FirebaseAuthUserCollisionException e) {
            Toast.makeText(context, "Este email já possui cadastro", LENGTH_SHORT).show();
            Log.e("Erro Cadastro", "naFinalizacaoDaCriacaoDaConta: " + e.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao tentar cadastrar com este endereço de email",
                    LENGTH_SHORT).show();
            Log.e("Erro Cadastro", "naFinalizacaoDaCriacaoDaConta: " + e.getMessage());
        }
    }

    public void FalhaConexaoException(Task<T> task, Context context) {
        try {
            throw task.getException();

        } catch (FirebaseNetworkException e) {
            Toast.makeText(context, "Falha ao tentar se conectar com a internet",
                    LENGTH_SHORT).show();
            Log.e("Falha Internet", "naFinalizacaoDaCriacaoDaConta: " + e.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void UsuarioInvalidoException(Task<T> task, Context context) {
        try {
            throw task.getException();

        } catch (FirebaseAuthInvalidCredentialsException e) {
            Toast.makeText(context, "Email e/ou senha incorreto(s)",
                    LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void usuarioNaoCadastradoException(Task<T> task, Context context) {
        try {
            throw task.getException();

        } catch (FirebaseAuthInvalidUserException e) {
            Toast.makeText(context, "Este email não possui cadastro ", LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void falhaAutenticacaoUsuario(Task<T> task, Context context) {
        try {
            throw task.getException();

        } catch (FirebaseAuthException e) {
            Toast.makeText(context, "Falha ao tentar se autenticar", LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
