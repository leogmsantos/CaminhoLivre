package com.br.caminholivre.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.br.caminholivre.R;
import com.br.caminholivre.avaliacao.CardOne;
import com.br.caminholivre.model.ParsePlace;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.vision.text.Text;
import com.melnykov.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PlaceFormActivty extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    private TextView place_nome;
    private TextView place_endereco;
    private TextView place_status;
    private TextView place_complemento;
    private TextView place_complemento_dois;
    private TextView place_score;
    private ImageView place_photo;
    private GoogleApiClient mGoogleApiClient;
    private FloatingActionButton button;
    private Intent i;


    private int score_completo;
    private String id;
    private String nome;
    private String endereco;
    private String status;
    private String complemento;
    private String complemento_dois;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_form_activty);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        place_nome = (TextView) findViewById(R.id.tv_place_nome);
        place_endereco = (TextView) findViewById(R.id.tv_place_endereco);
        place_photo = (ImageView) findViewById(R.id.tv_place_photo);
        place_status = (TextView) findViewById(R.id.tv_status_form);
        place_complemento = (TextView) findViewById(R.id.tv_complemento);
        place_complemento_dois = (TextView) findViewById(R.id.tv_complemento_dois);
        place_score = (TextView) findViewById(R.id.tv_score);
        button = (FloatingActionButton) findViewById(R.id.btn_avaliar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(PlaceFormActivty.this, CardOne.class);
                i.putExtra("id", id);
                i.putExtra("nome", nome);
                i.putExtra("endereco", endereco);
                i.putExtra("status", status);
                startActivity(i);
                finish();
            }
        });

        Bundle extra = getIntent().getExtras();

        if (extra != null){

            id = extra.getString("id");
            nome = extra.getString("nome");
            endereco = extra.getString("endereco");
            status = extra.getString("status");
            complemento = extra.getString("complemento");
            complemento_dois = extra.getString("complemento_dois");
            score = extra.getInt("score");

            place_nome.setText(nome);
            place_endereco.setText(endereco);

            ParseQuery<ParseObject> consulta = ParseQuery.getQuery("Place");
            consulta.whereContains("idPlace", id);
            consulta.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                    if (e == null) {
                        if (scoreList.size() != 0){
                            for (int i = 0; i < scoreList.size(); i++){
                                score_completo += (int) scoreList.get(i).getNumber("score");
                            }
                            score_completo = score_completo / scoreList.size();

                            place_score.setText(Integer.toString(score_completo) + "/150");

                            if (score_completo >= 70 && score_completo <= 100){
                                status = "Este local é Acessível";
                                complemento = "O estabelecimento possui vaga própria para pessoa com deficiência.";
                                complemento_dois = "O estabelecimento possui atendimento específico a pessoa com deficiência.";

                            }else if (score_completo > 100 && score_completo <= 130) {
                                status = "Este local é Parcialmente Acessível";
                                complemento = "O estabelecimento possui vaga própria para pessoa com deficiência.";
                                complemento_dois = "O estabelecimento não possui atendimento específico a pessoa com deficiência.";

                            }else if (score_completo > 130 && score_completo <= 150){
                                status = "Este local é Inacessível";
                                complemento = "O estabelecimento não possui vaga própria para pessoa com deficiência.";
                                complemento_dois = "O estabelecimento não possui atendimento específico a pessoa com deficiência.";
                            }
                            place_status.setText(status);
                            place_complemento.setText(complemento);
                            place_complemento_dois.setText(complemento_dois);
                        }else{
                            place_score.setText(Integer.toString(score) + "/150");
                            place_status.setText(status);
                            place_complemento.setText(complemento);
                            place_complemento_dois.setText(complemento_dois);
                        }

                    } else {
                        Log.i("score", "Error: " + e.getMessage());
                    }
                }
            });

            try {
                PendingResult<PlacePhotoMetadataResult> result = Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, id);
                result.setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {
                    @Override
                    public void onResult(@NonNull PlacePhotoMetadataResult placePhotoMetadataResult) {
                        PlacePhotoMetadataBuffer imagem = placePhotoMetadataResult.getPhotoMetadata();
                        if (imagem.get(0) != null){
                            PlacePhotoMetadata photo = imagem.get(0);
                            photo.getPhoto(mGoogleApiClient).setResultCallback(new ResultCallback<PlacePhotoResult>() {
                                @Override
                                public void onResult(@NonNull PlacePhotoResult placePhotoResult) {
                                    Bitmap imagem = placePhotoResult.getBitmap();
                                    place_photo.setImageBitmap(imagem);
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(), "Estabelecimento sem foto", Toast.LENGTH_SHORT).show();
                        }
                        imagem.release();
                    }
                });
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.out.println("PlaceFormActitivty: Falhou");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        System.out.println("PlaceFormActivity: Conectado");
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("PlaceFormActivity: Conexão Suspensa");
    }


    @Override
    public String toString() {
        return score + "/150";
    }

}
