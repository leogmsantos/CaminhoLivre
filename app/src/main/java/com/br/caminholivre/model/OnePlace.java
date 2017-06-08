package com.br.caminholivre.model;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.List;

/**
 * Created by Leonardo on 25/03/2017.
 */

public class OnePlace {

    private String id;
    private String nome;
    private String endereco;
    private int status;
    private String status_name;
    private Bitmap photo;


    public String getStatus_name() {
        switch (status){
            case 0:
                status_name = "Não avaliado";
                break;
            case 1:
                status_name = "Acessivel";
                break;
            case 2:
                status_name = "Inacessível";
                break;
            case 3:
                status_name = "Parcialmente acessível";
                break;
        }
        return status_name;
    }

    public List<Integer> getTipo() {
        return tipo;
    }

    public void setTipo(List<Integer> tipo) {
        this.tipo = tipo;
    }

    private List<Integer> tipo;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return getNome() + " - " + " Status:" + " " +getStatus_name();
    }

}
