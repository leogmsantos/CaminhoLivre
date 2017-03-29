package com.br.caminholivre.Util;

import java.util.HashMap;

/**
 * Created by Leonardo on 10/03/2017.
 */

public class ParseErros {

    private HashMap<Integer, String> erros;

    public ParseErros() {
        this.erros = new HashMap<>();
        this.erros.put(201, "Password não preenchido");
        this.erros.put(202, "Usuário já cadastrado, escolha um outro nome de usuário");
    }

    public String getErro(int codErro){
        return this.erros.get( codErro );
    }
}
