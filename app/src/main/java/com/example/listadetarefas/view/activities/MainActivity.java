package com.example.listadetarefas.view.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadetarefas.R;
import com.example.listadetarefas.model.Tarefa;
import com.example.listadetarefas.model.data.TarefaDao;
import com.example.listadetarefas.view.adapter.RecyclerViewAdapter;
import com.example.listadetarefas.view.interfaces.RecyclerOnClick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerOnClick {

    private FloatingActionButton buttonNovaTarefa;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Tarefa> tarefas = new ArrayList<>();
    private TarefaDao tarefaDao;

    public static final String TAREFA_KEY = "tarefakey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initRecycler();

        buttonNovaTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novaTarefa();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarTarefas();
    }

    public void initViews() {
        buttonNovaTarefa = findViewById(R.id.buttonNovaTarefa);
        recyclerView = findViewById(R.id.recyclerViewTarefas);
        tarefaDao = new TarefaDao(getApplicationContext());
    }

    public void initRecycler() {
        adapter = new RecyclerViewAdapter(tarefas, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void carregarTarefas() {
        List<Tarefa> listaAtualizada = tarefaDao.listar();
        adapter.atualizarLista(listaAtualizada);
    }

    public void novaTarefa() {
        startActivity(new Intent(MainActivity.this, AdicionarTarefaActivity.class));
    }

    @Override
    public void onClick(Tarefa tarefa) {
        Intent intent = new Intent(MainActivity.this, EditarTarefaActivity.class);
        intent.putExtra(TAREFA_KEY, tarefa);
        startActivity(intent);
    }

    @Override
    public void onLongClick(final Tarefa tarefa) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

        dialog.setTitle("Confirmar exclusão");
        dialog.setMessage("Deseja excluir a tarefa " + tarefa.getNome() + "?");
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (tarefaDao.deletar(tarefa)){
                    carregarTarefas();
                    Toast.makeText(MainActivity.this, "Tarefa excluída com sucesso!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Erro ao excluir a tarefa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("Não", null);
        dialog.create();
        dialog.show();
    }
}
