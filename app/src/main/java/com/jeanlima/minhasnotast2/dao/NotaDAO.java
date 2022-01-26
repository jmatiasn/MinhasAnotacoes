package com.jeanlima.minhasnotast2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jeanlima.minhasnotast2.Modelo.Nota;
import com.jeanlima.minhasnotast2.utils.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotaDAO {
    private final SQLiteDatabase escreve;
    private final SQLiteDatabase le;

    public NotaDAO(Context context){
        DBHelper dbHelper = new DBHelper(context);
        escreve = dbHelper.getWritableDatabase();
        le = dbHelper.getReadableDatabase();
        dbHelper.onCreate(escreve);
    }

    public boolean salvar(Nota nota){

        //1. definir o conteudo a ser salvo
        ContentValues cv = new ContentValues();
        cv.put("titulo", nota.getTitulo());
        cv.put("descricao", nota.getDescricao());
        cv.put("dataCriacao", nota.getDataCriacao().toString());

        try{
            escreve.insert(DBHelper.TABELA_NOTAS,null,cv);
            Log.i("INFO","Registro salvo com sucesso!");
        }catch(Exception e){
            Log.i("INFO","Erro ao salvar registro: "+e.getMessage());
            return false;
        }
        return true;
    }

    public List<Nota> listar() {

        List<Nota> notas = new ArrayList<>();

        //1. string sql de consulta
        String sql = "SELECT * FROM "+DBHelper.TABELA_NOTAS+ ";";

        //2. Cursor para acesso aos dados
        Cursor c = le.rawQuery(sql,null);

        //3. percorrer o cursor
        c.moveToFirst();
        while(c.moveToNext()){

            Nota nota = new Nota();

            //Long id = c.getLong( 0 );
            Long id = c.getLong( c.getColumnIndexOrThrow("id") );
            String titulo = c.getString(c.getColumnIndexOrThrow("titulo"));
            String descricao = c.getString(c.getColumnIndexOrThrow("descricao"));
            String dataCriacao = c.getString(c.getColumnIndexOrThrow("dataCriacao"));

            //"Tue Jan 25 21:15:40 GMT-03:00 2022"
            SimpleDateFormat formato = new SimpleDateFormat("E MMM dd HH:mm:ss 'GMT'Z yyyy");
            Date dataCriacaoFormatada = null;

            try {
                dataCriacaoFormatada = formato.parse(dataCriacao);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            nota.setId(id);
            nota.setTitulo(titulo);
            nota.setDescricao(descricao);
            nota.setDataCriacao(dataCriacaoFormatada);

            notas.add(nota);
        }
        c.close();
        return notas;
    }

    public boolean atualizar(Nota nota){

        //1. definir conteudo a ser salvo
        ContentValues cv = new ContentValues();
        cv.put("titulo", nota.getTitulo());
        cv.put("descricao", nota.getDescricao());
        cv.put("dataCriacao", nota.getDataCriacao().toString());

        //2. atualizar valor no banco
        try{
            String[] args = {nota.getId().toString()};
            //2.1 update(nome da tabela, conteudo para atualizar, clausula de atualização (where)
            // o argumento da condição --> ?)
            escreve.update(DBHelper.TABELA_NOTAS,cv,"id=?",args);
            Log.i("INFO","Registro atualizado com sucesso!");
        }catch(Exception e){
            Log.i("INFO","Erro ao atualizar registro!" + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deletar(Nota nota){

        //1. deletar um registro de nota na tabela tarefas

        try{
            //id do registro que será deletado
            String[] args = {nota.getId().toString()};
            escreve.delete(DBHelper.TABELA_NOTAS,"id=?",args);
            Log.i("INFO","Registro apagado com sucesso!");
        }catch(Exception e){
            Log.i("INFO","Erro apagar registro!"+e.getMessage());
            return false;
        }
        return true;

    }
}
