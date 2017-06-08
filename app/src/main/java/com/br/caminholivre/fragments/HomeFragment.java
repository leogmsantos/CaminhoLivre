package com.br.caminholivre.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.br.caminholivre.activity.PlaceFormActivty;
import com.br.caminholivre.model.OnePlace;
import com.br.caminholivre.R;
import com.br.caminholivre.service.FeedTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final String LOG_TAG = "PlacesAPIActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private ListView lista_de_locais;
    private int codigo;
    private Place place_type;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws SecurityException{

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(getContext())
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(getActivity(), this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        mGoogleApiClient.connect();
        verificarPermissao();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lista_de_locais = (ListView) view.findViewById(R.id.places_list);
        lista_de_locais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnePlace p = (OnePlace) lista_de_locais.getItemAtPosition(position);
                Intent i = new Intent(getContext(), PlaceFormActivty.class);
                i.putExtra("nome", p.getNome());
                i.putExtra("endereco", p.getEndereco());
                i.putExtra("id", p.getId());
                startActivity(i);
            }
        });
        // Inflate the layout for this fragment
        Bundle extra = getActivity().getIntent().getExtras();
        if (extra != null){
           codigo = extra.getInt("codigo");
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mGoogleApiClient.isConnected() == false){
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onResume() throws SecurityException{
        FeedTask f = new FeedTask(getContext(), this.mGoogleApiClient, lista_de_locais, codigo);
        f.execute();
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
