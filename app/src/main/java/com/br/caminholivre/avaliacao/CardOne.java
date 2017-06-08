package com.br.caminholivre.avaliacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.br.caminholivre.R;

public class CardOne extends AppCompatActivity {

    private Button avancar;
    private RadioButton acessivel, parcialmente_acessivel, inacessivel;
    private Intent intent;
    private int valor;


    private String id;
    private String nome;
    private String endereco;
    private String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_one);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            id = extra.getString("id");
            nome = extra.getString("nome");
            endereco = extra.getString("endereco");
            status = extra.getString("status");
        }

        acessivel = (RadioButton) findViewById(R.id.rbtnAcessivel);
        parcialmente_acessivel = (RadioButton) findViewById(R.id.rbtnParcialmenteAcessivel);
        inacessivel = (RadioButton) findViewById(R.id.rbtnInacessivel);

        avancar = (Button) findViewById(R.id.btnAvancar);

        avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acessivel.isChecked()){
                    valor = 10;
                }
                if (parcialmente_acessivel.isChecked()){
                    valor = 20;
                }
                if (inacessivel.isChecked()){
                    valor = 30;
                }
                intent = new Intent (CardOne.this, CardTwo.class);
                intent.putExtra("valor", valor);
                intent.putExtra("id", id);
                intent.putExtra("nome", nome);
                intent.putExtra("endereco", endereco);
                intent.putExtra("status", status);
                startActivity(intent);
                finish();

            }
        });
    }
}
