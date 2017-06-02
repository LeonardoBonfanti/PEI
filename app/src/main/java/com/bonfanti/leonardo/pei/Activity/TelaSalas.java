package com.bonfanti.leonardo.pei.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.bonfanti.leonardo.pei.Adapters.GridSalasAdapter;
import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.FireApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Usuário on 4/11/2017.
 */

public class TelaSalas extends AppCompatActivity
{
    int controler;
    Toolbar myToolbar;
    ArrayList<String> names;
    GridSalasAdapter gridViewArrayAdapter;
    GridView gridView;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_salas);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Salas");

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("SALAS");
        myToolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppOptions.setOverflowButtonColor(myToolbar, Color.WHITE);

        controler = 1;
        names = new ArrayList<>();
        progressDialog = new ProgressDialog(this);

        gridView = (GridView) findViewById(R.id.myGrid);

        retrieveDatabase();
        setBackArrowColor();
    }

    private void setBackArrowColor()
    {
        final Drawable upArrow = getResources().getDrawable(R.mipmap.arrow_back_white);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void showInput()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("PROFESSOR(A):");

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String nome = input.getText().toString().toUpperCase();

                if(nome.isEmpty())
                    Toast.makeText(getBaseContext(), "Nome não digitado!", Toast.LENGTH_SHORT).show();
                else
                    databaseReference.child(nome).setValue(1);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                return;
            }
        });

        builder.show();
    }

    /* Adiciona a descrição no devido array e atualiza a GridView com o novo adapter */
    private void retrieveDatabase()
    {
        progressDialog.setMessage("CRIANDO SALAS...");
        progressDialog.show();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                String key = dataSnapshot.getKey();
                names.add(key);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = names.indexOf(key);
                names.remove(index);
                geraSalas();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                geraSalas();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void geraSalas()
    {
        gridViewArrayAdapter = new GridSalasAdapter(this, names);
        gridView.setAdapter(gridViewArrayAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent;

        switch (item.getItemId())
        {
            case R.id.action_add:
                // User chose the "Add" action, mark the current item

                showInput();

                return true;

            case R.id.action_resultados:

                intent = new Intent(this, TelaResultados.class);
                startActivity(intent);

                return true;

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

        final FireApp fireApp= (FireApp) getApplicationContext();
        int admin = fireApp.getAdmin();
        int professor = fireApp.getProfessor();

        MenuItem item;

        if(admin != 1)
        {
            if(professor == 1)
            {
                item = menu.findItem(R.id.action_add);
                item.setVisible(false);

                return true;
            }

            item = menu.findItem(R.id.action_add);
            item.setVisible(false);
            item = menu.findItem(R.id.action_resultados);
            item.setVisible(false);
        }

        return true;
    }
}
