package com.br.caminholivre.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.br.caminholivre.R;
import com.br.caminholivre.util.ParseErros;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;

    EditText login;
    EditText password;
    Button logar;
    private ProgressDialog progress;

    private static final int PERMISSION_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verificarPermissao();

        FacebookSdk.getApplicationContext();

        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.edit_login_usuario);
        password = (EditText) findViewById(R.id.edit_login_senha);
        logar = (Button) findViewById(R.id.button_entrar);

        verificarUsuarioLogado();

        ParseUser.logOut();

        //evento logar sem facebook
        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = login.getText().toString();
                String senha = password.getText().toString();

                verificarLogin(usuario, senha);
                progress = new ProgressDialog(LoginActivity.this);
                progress.setMessage("Carregando...");
                progress.show();
            }

        });

        loginButton = (LoginButton) findViewById(R.id.fb_login_bt);

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //startActivity(new Intent(LoginActivity.this, FeedPrincipalActivity.class));
                startActivity(new Intent(LoginActivity.this, FeedSelectionActivity.class));
                System.out.println("On Sucess");
            }

            @Override
            public void onCancel() {
                System.out.println("On Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("On Error");
            }
        });


    }

    private void verificarLogin(String usuario, String senha){
        ParseUser.logInInBackground(usuario, senha, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if ( e == null ){ // sucesso
                    Toast.makeText(LoginActivity.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    abrirAreaPrincipal();
                    progress.dismiss();
                }else { // erro
                    ParseErros parseErros = new ParseErros();
                    String erro = parseErros.getErro(e.getCode());
                    Toast.makeText(LoginActivity.this, erro + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void abrirCadastroUsuario(View view){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    private void verificarUsuarioLogado(){
        if (ParseUser.getCurrentUser().getSessionToken() != null){
            abrirAreaPrincipal();
            System.out.println("USER 1:" + ParseUser.getCurrentUser().getSessionToken());
        }else {
            System.out.println("USER 2:" + ParseUser.getCurrentUser().getSessionToken());
        }
    }

    private void abrirAreaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, FeedSelectionActivity.class);
        startActivity(intent);
        finish();
    }

    public void verificarPermissao(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        }if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    PERMISSION_REQUEST_CODE);

        }if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS},
                    PERMISSION_REQUEST_CODE);
        }if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                    PERMISSION_REQUEST_CODE);
        }else{
            System.out.println("verificarPermissao: Tudo ok");
        }
    }
}
