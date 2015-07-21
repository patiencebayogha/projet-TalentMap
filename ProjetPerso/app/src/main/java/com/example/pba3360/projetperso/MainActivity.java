package com.example.pba3360.projetperso;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.CollapsibleActionView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    ImageView header;
   RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.mCollapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("Bienvenue");

        //toolbar
        toolbar= (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        header= (ImageView)findViewById(R.id.mImageView);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ModelData itemsData[] = { new ModelData("Help","Delete"),
                new ModelData("Nos parcs","Bonjour"),
                new ModelData("Visiter","Delete"),
                new ModelData("Favorite","Delete"),
                new ModelData("Like","Delete"),
                new ModelData("Delete","Delete"),
                new ModelData("Cloud","Delete"),
                new ModelData("Favorite","Delete"),
                new ModelData("Like","Delete"),
                new ModelData("Rating","Delete")};


        // 3. create an adapter
        RecycleAdapter mAdapter = new RecycleAdapter(itemsData);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Snackbar.make(v,"Vous venez de cliquer sur ajouter", Snackbar.LENGTH_SHORT).show();
                Intent classTwo=  new Intent( MainActivity.this, ActivityOne.class);
                startActivity(classTwo);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
