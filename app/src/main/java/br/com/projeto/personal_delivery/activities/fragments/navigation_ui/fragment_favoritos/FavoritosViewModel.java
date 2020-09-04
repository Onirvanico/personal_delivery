package br.com.projeto.personal_delivery.activities.fragments.navigation_ui.fragment_favoritos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoritosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FavoritosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}