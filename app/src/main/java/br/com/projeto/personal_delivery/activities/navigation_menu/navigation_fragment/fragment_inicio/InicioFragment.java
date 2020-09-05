package br.com.projeto.personal_delivery.activities.navigation_menu.navigation_fragment.fragment_inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import br.com.projeto.personal_delivery.R;


public class InicioFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.principal_navigation_fragment, container, false);

        return root;
    }
}