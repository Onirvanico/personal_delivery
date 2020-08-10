package br.com.projeto.personal_delivery.exceptions;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import static android.widget.Toast.LENGTH_SHORT;

public class FormularioException<T> {

    public void ConflitoEmailException(Task<T> task, Context context){
        try {
            throw task.getException();

        } catch(FirebaseAuthUserCollisionException e) {
            Toast.makeText(context, "Este email já possui cadastro", LENGTH_SHORT).show();
            Log.e("Erro Cadastro", "naFinalizacaoDaCriacaoDaConta: " + e.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
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
}
