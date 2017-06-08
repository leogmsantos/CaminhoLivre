package com.br.caminholivre.avaliacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.br.caminholivre.R;

public class CardTwo extends AppCompatActivity {

    private Button avancar;
    private int valor, valor1;
    private RadioButton temVaga, naoTemVaga;
    private RadioButton temAcessoPersonalizado, naoTemAcessoPersonalizado;
    private RadioButton possuiBalcao, naoPossuiBalcao;
    private Intent intent;

    private String id;
    private String nome;
    private String endereco;
    private String status;
    private String complemento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_two);

        temVaga = (RadioButton) findViewById(R.id.rbtnTemVaga);
        naoTemVaga = (RadioButton) findViewById(R.id.rbtnNaoTemVaga);

        temAcessoPersonalizado = (RadioButton) findViewById(R.id.rbtnTemAcessoPersonalizado);
        naoTemAcessoPersonalizado = (RadioButton) findViewById(R.id.rbtnNaoTemAcessoPersonalizado);

        possuiBalcao = (RadioButton) findViewById(R.id.rbtnTemBalcao);
        naoPossuiBalcao = (RadioButton) findViewById(R.id.rbtnnaoTemBalcao);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            this.valor = extra.getInt("valor");
            id = extra.getString("id");
            nome = extra.getString("nome");
            endereco = extra.getString("endereco");
            status = extra.getString("status");

        }

        avancar = (Button) findViewById(R.id.btn_ir_proxima);
        avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (temVaga.isChecked()){
                    valor = valor + 10;
                    complemento = "O estabelecimento possui vaga própria para pessoa com deficiência.";
                }
                if (naoTemVaga.isChecked()){
                    valor = valor + 20;
                    complemento = "O estabelecimento não possui vaga própria para pessoa com deficiência.";
                }

                if (temAcessoPersonalizado.isChecked()){
                    valor = valor + 10;
                }
                if (naoTemAcessoPersonalizado.isChecked()){
                    valor = valor + 20;
                }

                if (possuiBalcao.isChecked()){
                    valor = valor + 10;
                }
                if (naoPossuiBalcao.isChecked()){
                    valor = valor + 20;
                }
                intent = new Intent(CardTwo.this, CardThree.class);
                intent.putExtra("valor", valor);
                intent.putExtra("id", id);
                intent.putExtra("nome", nome);
                intent.putExtra("endereco", endereco);
                intent.putExtra("status", status);
                intent.putExtra("complemento", complemento);
                startActivity(intent);
                finish();
            }
        });
    }
}
