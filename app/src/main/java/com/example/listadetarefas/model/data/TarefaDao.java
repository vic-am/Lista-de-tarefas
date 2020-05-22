package com.example.listadetarefas.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDao implements ITarefaDao {

    private SQLiteDatabase databaseWriter;
    private SQLiteDatabase databaseReader;

    public TarefaDao(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseWriter = databaseHelper.getWritableDatabase();
        databaseReader = databaseHelper.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", tarefa.getNome());
        try {
            databaseWriter.insert(DatabaseHelper.TABELA_TAREFAS, null, contentValues);
            Log.i("INFO", "Tarefa salva com sucesso");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", tarefa.getNome());
        try {
            String[] args = {tarefa.getId().toString()};
            databaseWriter.update(DatabaseHelper.TABELA_TAREFAS, contentValues,
                    "id=?", args);
            Log.i("INFO", "Tarefa atualizada com sucesso ");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao atualizar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = {tarefa.getId().toString()};
            databaseWriter.delete(DatabaseHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("INFO", "Tarefa removida com sucesso ");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao remover tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DatabaseHelper.TABELA_TAREFAS + " ;";
        Cursor cursor = databaseReader.rawQuery(sql, null);

        while (cursor.moveToNext()) {

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));

            Tarefa tarefa = new Tarefa(nome);
            tarefa.setId(id);

            tarefas.add(tarefa);

        }
        return tarefas;
    }
}
