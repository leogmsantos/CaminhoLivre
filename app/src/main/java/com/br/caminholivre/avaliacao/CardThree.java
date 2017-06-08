package com.br.caminholivre.avaliacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.br.caminholivre.R;
import com.br.caminholivre.activity.PlaceFormActivty;
import com.parse.ParseObject;

public class CardThree extends AppCompatActivity {

    private int valor, valor1;
    private RadioButton temCirculacao, naoTemCirculacao;
    private RadioButton temBanheiro, naoTemBanheiro;
    private RadioButton temAtendimentoEspecifico, naotemAtendimentoEspecifico;
    private Button finalizar;
    private Intent intent;

    private String id;
    private String nome;
    private String endereco;
    private String status;
    private String complemento;
    private String complemento_dois;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_three);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            this.valor = extra.getInt("valor");
            id = extra.getString("id");
            nome = extra.getString("nome");
            endereco = extra.getString("endereco");
            status = extra.getString("status");
            complemento = extra.getString("complemento");
        }

        temCirculacao = (RadioButton) findViewById(R.id.rbtnTemFacilidadeCirculacao);
        naoTemCirculacao = (RadioButton) findViewById(R.id.rbtnNaoTemFacilidade);

        temBanheiro = (RadioButton) findViewById(R.id.rbtnTemBanheiro);
        naoTemBanheiro = (RadioButton) findViewById(R.id.rbtnNaoTemBanheiro);

        temAtendimentoEspecifico = (RadioButton) findViewById(R.id.rbtnTemAtendimentoEspecifico);
        naotemAtendimentoEspecifico = (RadioButton) findViewById(R.id.rbtnNaoTemAtendimentoEspecifico);

        finalizar = (Button) findViewById(R.id.btn_finalizar);
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temCirculacao.isChecked()){
                    valor = valor + 10;
                }
                if (naoTemCirculacao.isChecked()){
                    valor = valor + 20;
                }

                if (temBanheiro.isChecked()){
                    valor = valor + 10;
                }
                if (naoTemBanheiro.isChecked()){
                    valor = valor + 20;
                }

                if (temAtendimentoEspecifico.isChecked()){
                    valor = valor + 10;
                    complemento_dois = "O estabelecimento possui atendimento específico a pessoa com deficiência.";
                }
                if (naotemAtendimentoEspecifico.isChecked()){
                    valor += 20;
                    complemento_dois = "O estabelecimento não possui atendimento específico a pessoa com deficiência.";
                }
                if (valor >= 70 && valor <= 100){
                    status = "Este local é Acessível";
                }else if (valor > 100 && valor <= 130) {
                    status = "Este local é Parcialmente Acessível";
                }else if (valor > 130 && valor <= 150){
                    status = "Este local é Inacessível";
                }

                intent = new Intent(CardThree.this, PlaceFormActivty.class);
                intent.putExtra("id", id);
                intent.putExtra("nome", nome);
                intent.putExtra("endereco", endereco);
                intent.putExtra("status", status);
                intent.putExtra("complemento", complemento);
                intent.putExtra("complemento_dois", complemento_dois);
                intent.putExtra("score", valor);

                ParseObject place = new ParseObject("Place");
                place.put("idPlace", id);
                place.put("nome", nome);
                place.put("endereco", endereco);
                place.put("status", status);
                place.put("score", valor);
                place.put("complemento1", complemento);
                place.put("complemento2", complemento_dois);
                place.saveInBackground();

                Toast.makeText(getApplicationContext(), "Obrigado por contribuir!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });

    }
}
