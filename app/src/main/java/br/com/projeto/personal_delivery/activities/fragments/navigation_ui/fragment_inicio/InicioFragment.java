package br.com.projeto.personal_delivery.activities.fragments.navigation_ui.fragment_inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import br.com.projeto.personal_delivery.R;


public class InicioFragment extends Fragment {

    private InicioViewModel inicioViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inicioViewModel = new InicioViewModel();
        View root = inflater.inflate(R.layout.principal_navigation_fragment, container, false);


       // adapaterListaProfessores.setAdapter(ListaProfessoresAdapter)
        inicioViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}