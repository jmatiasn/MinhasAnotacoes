package com.jeanlima.minhasnotast2.Modelo;

import java.io.Serializable;
import java.util.Date;

public class Nota implements Serializable {

    //A entidade nota deve ter os seguintes campos: id, título, descrição e data de criação.
    private Long id;
    private String titulo;
    private String descricao;
    private Date dataCriacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return descricao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
