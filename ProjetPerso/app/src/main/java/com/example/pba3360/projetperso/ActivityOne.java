package com.example.pba3360.projetperso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by PBA3360 on 29/06/2015.
 */
public class ActivityOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitone);

    TextInputLayout textView=(TextInputLayout)findViewById(R.id.view);
        textView.setErrorEnabled(true);
        textView.setError("écris ecris ecris!!!!");

        ImageView imageView = (ImageView) findViewById(R.id.myImageView);
        ImageView imView = (ImageView) findViewById(R.id.imView);

        imView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Youpiiii", Snackbar.LENGTH_SHORT).show();

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "yes", Snackbar.LENGTH_SHORT).show();

            }
        });


        Glide.with(this)
                .load("http://orig00.deviantart.net/373b/f/2012/359/f/2/f2d743b48b9c27464e3a723debd77874-d5l28rl.gif")
                .centerCrop()
                .fitCenter()
                .override(700, 500)
                .error(R.drawable.pointedenis)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imView);

        Picasso.with(this)
                //.load("http://www.joomlaworks.net/images/demos/galleries/abstract/7.jpg")
                .load("http://orig00.deviantart.net/373b/f/2012/359/f/2/f2d743b48b9c27464e3a723debd77874-d5l28rl.gif")
                .resize(700, 500)
                .error(R.drawable.pointedenis)
                .noFade()
                .centerCrop()
                .into(imageView);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Profil"));
        tabLayout.addTab(tabLayout.newTab().setText("Cours"));
        tabLayout.addTab(tabLayout.newTab().setText("Choix langue"));

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
