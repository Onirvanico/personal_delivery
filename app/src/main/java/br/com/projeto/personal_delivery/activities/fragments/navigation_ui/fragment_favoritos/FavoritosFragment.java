package br.com.projeto.personal_delivery.activities.fragments.navigation_ui.fragment_favoritos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import br.com.projeto.personal_delivery.R;


public class FavoritosFragment extends Fragment {

    private FavoritosViewModel favoritosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritosViewModel = new FavoritosViewModel();
        View root = inflater.inflate(R.layout.favoritos_navigation_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        favoritosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}