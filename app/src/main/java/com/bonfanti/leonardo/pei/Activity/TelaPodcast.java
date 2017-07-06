package com.bonfanti.leonardo.pei.Activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.bonfanti.leonardo.pei.Adapters.VideoAdapter;
import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Usuário on 7/5/2017.
 */

public class TelaPodcast extends AppCompatActivity
{
    ListView listaVideos;
    HashMap<String, String> fireVideos;
    DatabaseReference databaseReference;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_podcast);

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Podcast");

        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("PODCASTS");
        myToolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppOptions.setOverflowButtonColor(myToolbar, Color.WHITE);

        setBackArrowColor();

        fireVideos = new HashMap<>();

        getData();
    }

    private void setBackArrowColor()
    {
        final Drawable upArrow = getResources().getDrawable(R.mipmap.arrow_back_white);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void getData()
    {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                    fireVideos.put(childDataSnapshot.getKey().toString(), childDataSnapshot.getValue().toString());

                listaVideos = (ListView) findViewById(R.id.listVideo);
                VideoAdapter videoAdapter = new VideoAdapter(TelaPodcast.this, fireVideos);
                listaVideos.setAdapter(videoAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
