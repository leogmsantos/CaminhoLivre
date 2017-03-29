package com.br.caminholivre.Fragments;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.br.caminholivre.Model.OnePlace;
import com.br.caminholivre.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final String LOG_TAG = "PlacesAPIActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private ListView lista_de_locais;
    private List<String> locais = new ArrayList<String>();



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws SecurityException{
        mGoogleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        verificarPermissao();

        lista_de_locais = (ListView) view.findViewById(R.id.places_list);
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                //List<OnePlace> locais = new ArrayList<OnePlace>();
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {

                    OnePlace place = new OnePlace();
                    place.setId(placeLikelihood.getPlace().getId());
                    place.setEndereco(placeLikelihood.getPlace().getAddress().toString());
                    place.setNome(placeLikelihood.getPlace().getName().toString());

                    locais.add(placeLikelihood.getPlace().getName().toString());
                    //System.out.println(locais);
                    //locais.add(place);
                    System.out.println(place.getNome());
                }
                likelyPlaces.release();
                ArrayAdapter<String> places = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, locais);
                lista_de_locais.setAdapter(places);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onResume() throws SecurityException{
                super.onResume();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google OnePlace API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(getContext(),
                "Google OnePlace API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("onRequestPermissionResult");
                }
                break;
        }
    }

    public void verificarPermissao(){
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        }else{
            System.out.println("verificarPermissao: Tudo ok");
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        System.out.println("Conectado");
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("Conexão Suspensa");
    }
}
