package com.bonfanti.leonardo.pei.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
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

import java.util.ArrayList;

/**
 * Created by Usuário on 4/11/2017.
 */

public class TelaSalas extends AppCompatActivity
{
    Toolbar myToolbar;
    ArrayList<String> names;
    GridSalasAdapter gridViewArrayAdapter;
    GridView gridView;

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

        AppOptions.setOverflowButtonColor(myToolbar, Color.WHITE);

        names = new ArrayList<>();

        gridView = (GridView) findViewById(R.id.myGrid);

        retrieveDatabase();
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
                String nome = input.getText().toString();

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
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                names.add(key);
                geraSalas();
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

            case R.id.action_inicio:
                // User chose the "Início" item, show the app settings UI...
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
        String key = fireApp.getUserKey();

        if(!key.equals("jqAjpmq6xbez4G8s4Ujj1vuaLHa2"))
        {
            MenuItem item = menu.findItem(R.id.action_add);
            item.setVisible(false);
            item = menu.findItem(R.id.action_resultados);
            item.setVisible(false);
        }

        return true;
    }
}