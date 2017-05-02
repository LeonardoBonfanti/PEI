package com.bonfanti.leonardo.pei.Activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bonfanti.leonardo.pei.Adapters.CarTableDataAdapter;
import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.SortableCarTableView;
import com.bonfanti.leonardo.pei.Utils.UserDetails;
import com.google.firebase.database.ChildEventListener;
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

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_resultados);

        controler = 1;
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
                    String[] splited =  nome.split("@");
                    nome = splited[0].trim();

                    ArrayList<String> tratados = trataRetorno(childDataSnapshot.child("Sala").getValue().toString());

                    final UserDetails row = new UserDetails(nome, tratados.get(UserDetails.PROF).toString(), tratados.get(UserDetails.TESTE).toString(),
                            tratados.get(UserDetails.DATA).toString(), tratados.get(UserDetails.RESULT).toString());
                    userDetailses.add(row);

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

    private ArrayList<String> trataRetorno(String texto)
    {
        ArrayList<String> retorno = new ArrayList<>();

        String[] splited = texto.split("\\{");

        String prof = splited[1].trim();
        prof = prof.replace("=", "");

        String data = splited[3].trim();
        data = data.replace("=", "");
        data = data.replace("_", "/");

        String[] teste = splited[5].split("=");
        String numero = teste[0].trim();
        String result = teste[1].trim();
        result = result.replace("}", "");

        retorno.add(prof.toUpperCase());
        retorno.add(numero.toUpperCase());
        retorno.add(data.toUpperCase());
        retorno.add(result.toUpperCase());

        return retorno;
    }
}