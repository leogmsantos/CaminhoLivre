package com.br.caminholivre.Model;

/**
 * Created by Leonardo on 25/03/2017.
 */

public class OnePlace {

    private String id;
    private String nome;
    private String endereco;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
