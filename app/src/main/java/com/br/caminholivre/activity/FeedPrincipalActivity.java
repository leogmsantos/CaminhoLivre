package com.br.caminholivre.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.br.caminholivre.adapter.TabsAdapter;
import com.br.caminholivre.R;
import com.br.caminholivre.util.SlidingTabLayout;
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
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
