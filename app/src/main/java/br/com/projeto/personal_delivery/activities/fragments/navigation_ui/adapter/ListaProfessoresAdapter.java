package br.com.projeto.personal_delivery.activities.fragments.navigation_ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.projeto.personal_delivery.R;
import br.com.projeto.personal_delivery.model.Professor;

public class ListaProfessoresAdapter extends RecyclerView.Adapter<ListaProfessoresAdapter.MyViewHolder> {

    private final Context context;
    private final List<Professor> professores;

    public ListaProfessoresAdapter(Context context, List<Professor> professores) {
        this.context = context;
        this.professores = professores;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_professor,
                parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int posicao) {

        holder.vincula(posicao);
    }

    @Override
    public int getItemCount() {
        return professores.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView viewNome;
        private TextView viewExperiencia;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNome = itemView.findViewById(R.id.item_lista_nome_professor);
            viewExperiencia = itemView.findViewById(R.id.item_lista_experiencia_professor);
        }

        private void vincula(int posicao) {
            Professor professor = professores.get(posicao);
            viewNome.setText(professor.getNome());
            viewExperiencia.setText(professor.getExperiencia());
        }

    }
}
