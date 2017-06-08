package com.br.caminholivre.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.br.caminholivre.activity.PlaceFormActivty;
import com.br.caminholivre.model.OnePlace;
import com.br.caminholivre.model.Status;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 29/03/2017.
 */

public class FeedTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private ListView lista_de_locais;
    private ProgressDialog progress;
    private int codigo;


    public FeedTask(Context context, GoogleApiClient mGoogleApiClient, ListView lista_de_locais, int codigo){
        this.context = context;
        this.mGoogleApiClient = mGoogleApiClient;
        this.lista_de_locais = lista_de_locais;
        this.codigo = codigo;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(context);
        progress.setMessage("Carregando...");
        progress.show();
    }

    @Override
    protected Void doInBackground(Void... params) throws SecurityException{
        System.out.println("1");
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);


        System.out.println("2");
     result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(final PlaceLikelihoodBuffer likelyPlaces) {
                List<OnePlace> locais = new ArrayList<OnePlace>();
                System.out.println("3");
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {

                    OnePlace place = new OnePlace();
                    place.setId(placeLikelihood.getPlace().getId());
                    place.setEndereco(placeLikelihood.getPlace().getAddress().toString());
                    place.setNome(placeLikelihood.getPlace().getName().toString());
                    place.setTipo(placeLikelihood.getPlace().getPlaceTypes());
                    place.setStatus(com.br.caminholivre.model.Status.NAO_AVALIADO);

                    if (place.getTipo().contains(codigo)){
                        locais.add(place);
                    }
                    System.out.println(place.getNome());
                }
                System.out.println("4");
                likelyPlaces.release();
                System.out.println("5");
                ArrayAdapter<OnePlace> places = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, locais);
                lista_de_locais.setAdapter(places);

                progress.dismiss();
            }
        });
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}