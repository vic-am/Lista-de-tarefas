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

import static com.example.listadetarefas.view.activities.MainActivity.TAREFA_KEY;

public class EditarTarefaActivity extends AppCompatActivity {

    private TextInputLayout layoutTarefa;
    private TextInputEditText editTarefa;
    private TarefaDao tarefaDao;
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarefa);
        initViews();

        tarefaSelecionada = (Tarefa) getIntent().getSerializableExtra(TAREFA_KEY);
        editTarefa.setText(tarefaSelecionada.getNome());

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
                tarefaSelecionada.setNome(editTarefa.getText().toString());
                tarefaDao.atualizar(tarefaSelecionada);
                Toast.makeText(this, "Tarefa atualizada!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                layoutTarefa.setError("Por favor, descreva sua tarefa");
            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initViews() {
        layoutTarefa = findViewById(R.id.textinputEditarTarefa);
        editTarefa = findViewById(R.id.editTextEditarTarefa);
        tarefaDao = new TarefaDao(getApplicationContext());
    }
}
