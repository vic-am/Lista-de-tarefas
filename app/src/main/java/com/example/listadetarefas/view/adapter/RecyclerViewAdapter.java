package com.example.listadetarefas.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadetarefas.R;
import com.example.listadetarefas.model.Tarefa;
import com.example.listadetarefas.view.interfaces.RecyclerOnClick;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Tarefa> tarefas;
    private RecyclerOnClick onClick;

    public RecyclerViewAdapter(List<Tarefa> tarefas, RecyclerOnClick onClick) {
        this.tarefas = tarefas;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Tarefa tarefa = tarefas.get(position);
        holder.bind(tarefa);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(tarefa);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onClick.onLongClick(tarefa);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public void atualizarLista(List<Tarefa> tarefaList){
        this.tarefas.clear();
        this.tarefas = tarefaList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTarefa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTarefa = itemView.findViewById(R.id.textViewTarefa);
        }

        public void bind(Tarefa tarefa) {
            textTarefa.setText(tarefa.getNome());
        }
    }
}
