package com.jeanlima.minhasnotast2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jeanlima.minhasnotast2.Modelo.Nota;
import com.jeanlima.minhasnotast2.adapters.AdapterMod;
import com.jeanlima.minhasnotast2.dao.NotaDAO;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabAdd;
    RecyclerView rvNotas;

    AdapterMod adapterMod;

    List<Nota> mNotas = new ArrayList<Nota>();

    Nota notaSelecionada = new Nota();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAdd = findViewById(R.id.fabAdd);
        rvNotas = findViewById(R.id.rvNotas);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),AddNotaActivity.class);
                startActivity(it);
            }
        });
    }

    public void carregarTarefas(){

        //1. preenchendo mTarefas com as tarefas cadastradas no banco
        NotaDAO notaDAO = new NotaDAO(getApplicationContext());
        mNotas = notaDAO.listar();

        //2. Exibir a lista de tarefas

        //2.1 Configurar o adapter

        adapterMod = new AdapterMod(mNotas);

        adapterMod.implementaAoClicarNoItem(new AdapterMod.AoClicarNoItem() {
            @Override
            public void clicouNaNota(int pos) {
                //Editar nota

                Nota nota = new Nota();
                nota = mNotas.get(pos);

                Intent it = new Intent(getApplicationContext(), AddNotaActivity.class);
                it.putExtra("nota",nota);
                startActivity(it);
            }

            @Override
            public void pressionouNota(int pos) {

                notaSelecionada = mNotas.get(pos);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                dialog.setTitle("Confirmar Exclusão");

                dialog.setMessage("Deseja excluir a nota: "+ notaSelecionada.getTitulo()+ " ?");

                dialog.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotaDAO tarefaDAO = new NotaDAO(getApplicationContext());
                        if(tarefaDAO.deletar(notaSelecionada)){
                            Toast.makeText(getApplicationContext(), "Nota foi removida", Toast.LENGTH_SHORT).show();
                            carregarTarefas();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao deletar nota", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Não",null);

                dialog.create();
                dialog.show();

            }
        });

        //2. configuração recycler view

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvNotas.setLayoutManager(layoutManager);
        rvNotas.setHasFixedSize(true);
        rvNotas.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        rvNotas.setAdapter(adapterMod);

    }

    @Override
    protected void onStart() {
        this.carregarTarefas();
        super.onStart();
    }
}