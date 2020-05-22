package com.example.listadetarefas.view.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listadetarefas.R;
import com.example.listadetarefas.model.Tarefa;
import com.example.listadetarefas.model.data.TarefaDao;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputLayout layoutTarefa;
    private TextInputEditText editTarefa;
    private TarefaDao tarefaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);
        initViews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_adicionar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.itemSalvar) {

            if (!editTarefa.getText().toString().isEmpty()) {
                String tarefaNome = editTarefa.getText().toString();
                Tarefa tarefa = new Tarefa(tarefaNome);
                tarefaDao.salvar(tarefa);
                Toast.makeText(this, "Tarefa salva!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                layoutTarefa.setError("Por favor, descreva sua tarefa");
            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initViews() {
        layoutTarefa = findViewById(R.id.textinputAddTarefa);
        editTarefa = findViewById(R.id.editTextAddTarefa);
        tarefaDao = new TarefaDao(getApplicationContext());
    }
}
