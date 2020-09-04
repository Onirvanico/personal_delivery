package br.com.projeto.personal_delivery.activities.fragments.navigation_ui.fragment_lista_professores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListaProfessoresViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListaProfessoresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aqui é onde a brincadeira começa");
    }

    public LiveData<String> getText() {
        return mText;
    }
}