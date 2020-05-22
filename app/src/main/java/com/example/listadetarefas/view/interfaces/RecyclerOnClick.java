package com.example.listadetarefas.view.interfaces;

import com.example.listadetarefas.model.Tarefa;

public interface RecyclerOnClick {

    void onClick(Tarefa tarefa);
    void onLongClick(Tarefa tarefa);

}
