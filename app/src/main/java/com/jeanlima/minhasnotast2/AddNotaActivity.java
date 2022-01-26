package com.jeanlima.minhasnotast2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jeanlima.minhasnotast2.Modelo.Nota;
import com.jeanlima.minhasnotast2.dao.NotaDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNotaActivity extends AppCompatActivity {

    final Calendar myCalendar= Calendar.getInstance();

    private EditText etTitulo;
    private EditText etDescricao;
    private EditText etDataCriacao;
    private Nota notaAtual;
    private Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nota);

        etTitulo = findViewById(R.id.etTitulo);
        etDescricao = findViewById(R.id.etDescricao);
        etDataCriacao = findViewById(R.id.etDataCriacao);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        etDataCriacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddNotaActivity.this,
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH)
                        ,myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        Intent it = getIntent();

        try{
            //edita tarefa
            notaAtual = (Nota) it.getExtras().getSerializable("nota");
        }catch(Exception e){
            //criar um nova tarefa
            notaAtual = null;
        }

        if(notaAtual != null){
            etTitulo.setText(notaAtual.getTitulo());
            etDescricao.setText(notaAtual.getDescricao());
            etDataCriacao.setText(obterStringDaDate(notaAtual.getDataCriacao()));
        }

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotaDAO notaDAO = new NotaDAO(getApplicationContext());


                if(notaAtual != null){//editar tarefa
                    String titulo = etTitulo.getText().toString();
                    String descricao = etDescricao.getText().toString();
                    String dataCriacao = etDataCriacao.getText().toString();
                    if(!titulo.isEmpty() && !descricao.isEmpty() && !dataCriacao.isEmpty()){
                        notaAtual.setTitulo(titulo);
                        notaAtual.setDescricao(descricao);
                        notaAtual.setDataCriacao(obterDateDaString(dataCriacao));
                        if(notaDAO.atualizar(notaAtual)){
                            Toast.makeText(getApplicationContext(), "Nota atualizada", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao atualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else { //adicionar tarefa
                    String titulo = etTitulo.getText().toString();
                    String descricao = etDescricao.getText().toString();
                    Date dataCriacao = obterDateDaString(etDataCriacao.getText().toString());
                    Log.d("jmatiasn", "etDataCriacao é " + etDataCriacao.getText().toString());
                    Log.d("jmatiasn", "dataCriacao é " + dataCriacao.toString());
                    if(!descricao.isEmpty()){
                        Nota nota = new Nota();
                        nota.setTitulo(titulo);
                        nota.setDescricao(descricao);
                        nota.setDataCriacao(dataCriacao);
                        if(notaDAO.salvar(nota)){
                            Toast.makeText(getApplicationContext(), "Nota cadastrada", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao cadastrar nota", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }

    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        etDataCriacao.setText(dateFormat.format(myCalendar.getTime()));
    }

    private Date obterDateDaString(String dataRecebida) {
        Date dataFormatada = null;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dataFormatada = formato.parse(dataRecebida);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Erro ao obter data da nota", Toast.LENGTH_SHORT).show();
        }
        return dataFormatada;
    }

    private String obterStringDaDate(Date dataRecebida) {
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        return dateFormat.format(dataRecebida);
    }
}
