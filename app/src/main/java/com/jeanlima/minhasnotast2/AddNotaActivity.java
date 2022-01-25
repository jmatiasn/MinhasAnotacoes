package com.jeanlima.minhasnotast2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jeanlima.minhasnotast2.Modelo.Nota;
import com.jeanlima.minhasnotast2.dao.NotaDAO;

public class AddNotaActivity extends AppCompatActivity {

    private EditText etAddNota;
    private Nota notaAtual;

    private Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nota);

        etAddNota = findViewById(R.id.etAddNota);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        Intent it = getIntent();

        try{
            //edita tarefa
            notaAtual = (Nota) it.getExtras().getSerializable("tarefa");
        }catch(Exception e){
            //criar um nova tarefa
            notaAtual = null;
        }

        if(notaAtual != null){
            etAddNota.setText(notaAtual.getDescricao());
        }

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotaDAO notaDAO = new NotaDAO(getApplicationContext());


                if(notaAtual != null){//editar tarefa

                    String descricao = etAddNota.getText().toString();
                    if(!descricao.isEmpty()){
                        notaAtual.setDescricao(descricao);
                        if(notaDAO.atualizar(notaAtual)){
                            Toast.makeText(getApplicationContext(), "Tarefa atualizada", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao atualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else { //adicionar tarefa
                    String descricao = etAddNota.getText().toString();
                    if(!descricao.isEmpty()){
                        Nota tarefa = new Nota();
                        tarefa.setDescricao(descricao);
                        if(notaDAO.salvar(tarefa)){
                            Toast.makeText(getApplicationContext(), "Tarefa cadastrada", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao ao cadastrar tarefa", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }
}
