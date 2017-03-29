package com.br.caminholivre.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.br.caminholivre.Adapter.TabsAdapter;
import com.br.caminholivre.R;
import com.br.caminholivre.Util.SlidingTabLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.parse.Parse;
import com.parse.ParseUser;

public class FeedPrincipalActivity extends AppCompatActivity{

    private Toolbar toolbarPrincipal;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_principal);

        //configura toolbar
        toolbarPrincipal = (Toolbar) findViewById(R.id.toolbar_principal);
        toolbarPrincipal.setLogo(R.drawable.logo_toolbar);
        setSupportActionBar(toolbarPrincipal);

        //Configura abas
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab_feed);
        viewPager = (ViewPager) findViewById(R.id.view_pager_feed);

        //configura adapter
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabsAdapter);
        slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.text_item_tab);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sair:
                deslogarUsuario();
            case R.id.action_configuracoes:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void deslogarUsuario(){
        ParseUser.logOut();
        Intent intent = new Intent(FeedPrincipalActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
