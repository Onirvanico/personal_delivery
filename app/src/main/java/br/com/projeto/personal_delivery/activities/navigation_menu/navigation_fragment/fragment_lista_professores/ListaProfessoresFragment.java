package br.com.projeto.personal_delivery.activities.navigation_menu.navigation_fragment.fragment_lista_professores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.activities.navigation_menu.navigation_fragment.adapter.ListaProfessoresAdapter;
import br.com.projeto.personal_delivery.model.Professor;


public class ListaProfessoresFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.lista_professores_navigation_fragment, container, false);

        RecyclerView adapaterListaProfessores = root.findViewById(R.id.recycler_lista_professores);
        List<Professor> professores = new ArrayList<Professor>(Arrays.asList(
                new Professor("Pudim", "50"),
                new Professor("Padim", "45"),
                new Professor("A história de um menino", "62")
        ));

        ListaProfessoresAdapter adapter = new ListaProfessoresAdapter(this.getContext(), professores);
        adapaterListaProfessores.setAdapter(adapter);

        return root;
    }
}