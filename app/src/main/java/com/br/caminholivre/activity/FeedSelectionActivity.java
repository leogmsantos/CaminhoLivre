package com.br.caminholivre.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.caminholivre.R;
import com.br.caminholivre.model.OnePlace;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.parse.ParseUser;

public class FeedSelectionActivity extends AppCompatActivity {

    private ImageView restaurates, hospedagem, saude, diversao;
    private Toolbar toolbarPrincipal;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private TextView procurar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_selection);

        restaurates = (ImageView) findViewById(R.id.img_restaurantes);
        restaurates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedSelectionActivity.this, FeedPrincipalActivity.class);
                intent.putExtra("codigo", Place.TYPE_RESTAURANT);
                startActivity(intent);
            }
        });

        hospedagem = (ImageView) findViewById(R.id.img_hospedagem);
        hospedagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedSelectionActivity.this, FeedPrincipalActivity.class);
                intent.putExtra("codigo", Place.TYPE_SHOPPING_MALL);
                startActivity(intent);
            }
        });
        saude = (ImageView) findViewById(R.id.img_saude);
        saude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedSelectionActivity.this, FeedPrincipalActivity.class);
                intent.putExtra("codigo", Place.TYPE_HOSPITAL);
                startActivity(intent);
            }
        });
        diversao = (ImageView) findViewById(R.id.img_diversao);
        diversao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedSelectionActivity.this, FeedPrincipalActivity.class);
                intent.putExtra("codigo", Place.TYPE_NIGHT_CLUB);
                startActivity(intent);
            }
        });


        procurar = (TextView) findViewById(R.id.btn_procurar_place);
        procurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(FeedSelectionActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Solucionar o erro.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Solucionar o erro.
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                System.out.println(place.getId());
                Intent i = new Intent(this, PlaceFormActivty.class);
                OnePlace onePlace = new OnePlace();
                onePlace.setId(place.getId());
                onePlace.setNome(place.getName().toString());
                onePlace.setEndereco(place.getAddress().toString());
                onePlace.setStatus(com.br.caminholivre.model.Status.NAO_AVALIADO);

                i.putExtra("id", onePlace.getId());
                i.putExtra("nome", onePlace.getNome());
                i.putExtra("endereco", onePlace.getEndereco());
                i.putExtra("status", onePlace.getStatus_name());
                startActivity(i);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Solucionar o erro.
                System.out.println(status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
