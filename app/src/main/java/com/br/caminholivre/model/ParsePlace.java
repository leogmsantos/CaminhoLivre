package com.br.caminholivre.model;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Thiago on 29/05/2017.
 */
@ParseClassName("Place")
public class ParsePlace extends ParseObject{

    public String getNome(){
        return getString("nome");
    }

    public void setNome(String nome){
        put("nome", nome);
    }

    public String getScore(){
        return getString ("score");
    }

    public void setScore(String score){
        put("score", score);
    }


    @Override
    public String toString() {
        return getString("nome") + "\n" + getString("score");
    }
}
