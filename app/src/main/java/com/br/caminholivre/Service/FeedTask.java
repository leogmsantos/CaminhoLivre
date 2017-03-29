package com.br.caminholivre.Service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.br.caminholivre.Model.OnePlace;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

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


    public FeedTask(Context context, GoogleApiClient mGoogleApiClient, ListView lista_de_locais){
        this.context = context;
        this.mGoogleApiClient = mGoogleApiClient;
        this.lista_de_locais = lista_de_locais;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(context);
        progress.setMessage("Carregando...");
        progress.show();
    }

    @Override
    protected Void doInBackground(Void... params) throws SecurityException{

        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                List<OnePlace> locais = new ArrayList<OnePlace>();
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {

                    OnePlace place = new OnePlace();
                    place.setId(placeLikelihood.getPlace().getId());
                    place.setEndereco(placeLikelihood.getPlace().getAddress().toString());
                    place.setNome(placeLikelihood.getPlace().getName().toString());

                    locais.add(place);
                    System.out.println(place.getNome());
                }
                likelyPlaces.release();
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
