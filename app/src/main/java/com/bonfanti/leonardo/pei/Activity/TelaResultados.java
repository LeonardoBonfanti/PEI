package com.bonfanti.leonardo.pei.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bonfanti.leonardo.pei.Adapters.CarTableDataAdapter;
import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.FireApp;
import com.bonfanti.leonardo.pei.Utils.SortableCarTableView;
import com.bonfanti.leonardo.pei.Utils.UserDetails;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuário on 4/23/2017.
 */

public class TelaResultados extends AppCompatActivity
{
    int controler;
    Toolbar myToolbar;
    List<UserDetails> userDetailses;
    SortableCarTableView carTableView;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_resultados);

        controler = 1;
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        userDetailses = new ArrayList<>();

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        carTableView = (SortableCarTableView)findViewById(R.id.tableView);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_result);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("RESULTADOS");
        myToolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppOptions.setOverflowButtonColor(myToolbar, Color.WHITE);

        setBackArrowColor();

        getData();
    }

    private void setBackArrowColor()
    {
        final Drawable upArrow = getResources().getDrawable(R.mipmap.arrow_back_white);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void getData()
    {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                {
                    String nome = childDataSnapshot.child("Nome").getValue().toString();

                    if(childDataSnapshot.hasChild("Sala"))
                    {
                        for (DataSnapshot child : childDataSnapshot.child("Sala").getChildren())
                        {
                            String prof = child.getKey().toString();
                            String dataFormated = "";
                            String teste = "";
                            String result = "";

                            for (DataSnapshot child2 : childDataSnapshot.child("Sala").child(prof).getChildren())
                            {
                                String data = child2.getKey().toString();
                                dataFormated = data.replace("_","/");

                                for (DataSnapshot child3 : childDataSnapshot.child("Sala").child(prof).child(data).getChildren())
                                {
                                    teste = child3.getKey().toString();
                                    result = child3.getValue().toString();

                                    final FireApp fireApp= (FireApp) getApplicationContext();
                                    String profName = fireApp.getUserSala();

                                    final UserDetails row;

                                    if(profName.equals(prof))
                                    {
                                        row = new UserDetails(nome, prof, teste, dataFormated, result);
                                        userDetailses.add(row);
                                    }
                                }
                            }
                        }
                    }

                    if(controler == dataSnapshot.getChildrenCount())
                        populate();

                    controler++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void populate()
    {
        final CarTableDataAdapter carTableDataAdapter = new CarTableDataAdapter(this, userDetailses, carTableView);
        carTableView.setDataAdapter(carTableDataAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent;

        switch (item.getItemId())
        {
            case R.id.action_about:

                AppOptions.createPopUpAbout(this);

                return true;

            case R.id.action_logout:

                firebaseAuth.signOut();

                intent = new Intent(this, TelaLoginCadastro.class);
                startActivity(intent);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    /* Trigger para mostar devidamente a Toolbar com os elementos criados no xml */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tool_itens, menu);

        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(false);
        item = menu.findItem(R.id.action_resultados);
        item.setVisible(false);

        return true;
    }
}