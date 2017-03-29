package com.br.caminholivre.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.br.caminholivre.R;
import com.br.caminholivre.Util.ParseErros;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CadastroActivity extends AppCompatActivity {

    EditText user;
    EditText email;
    EditText password;
    Button cadastrar;
    TextView fazer_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        user = (EditText) findViewById(R.id.editText_nome);
        email = (EditText) findViewById(R.id.editText_email);
        password = (EditText) findViewById(R.id.editText_password);
        cadastrar = (Button) findViewById(R.id.bt_cadastrar);
        fazer_login = (TextView) findViewById(R.id.tv_fazer_login);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
        fazer_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLoginUsuario();
            }
        });

    }

    private void cadastrarUsuario() {

        //cria objeto usuario
        ParseUser usuario = new ParseUser();
        usuario.setUsername( user.getText().toString() );
        usuario.setEmail( email.getText().toString() );
        usuario.setPassword( password.getText().toString() );

        //salva dados do usuario
        usuario.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){ // sucesso
                    Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                    abrirLoginUsuario();
                }else{ // erro
                    ParseErros parseErros = new ParseErros();
                    String erro = parseErros.getErro(e.getCode());
                    Toast.makeText(CadastroActivity.this, erro, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
