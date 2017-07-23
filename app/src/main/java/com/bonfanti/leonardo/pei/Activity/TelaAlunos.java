package com.bonfanti.leonardo.pei.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bonfanti.leonardo.pei.Adapters.AlunosAdapter;
import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.FireApp;
import com.bonfanti.leonardo.pei.Utils.SortableCarTableView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by Usuário on 7/22/2017.
 */

public class TelaAlunos extends AppCompatActivity
{
    Context context;
    Toolbar myToolbar;
    ListView listaAlunos;
    ArrayList<ArrayList> alunos;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alunos);

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        firebaseAuth = FirebaseAuth.getInstance();

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_result);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("ALUNOS");
        myToolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppOptions.setOverflowButtonColor(myToolbar, Color.WHITE);

        context = this;
        listaAlunos = (ListView)findViewById(R.id.myList);

        setBackArrowColor();

        getData();

        addAluno();
    }

    private void setBackArrowColor()
    {
        final Drawable upArrow = getResources().getDrawable(R.mipmap.arrow_back_white);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void addAluno()
    {
        Button btnAdd = (Button)findViewById(R.id.btnIncluir);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("INCLUIR ALUNO");

                // Set up the input
                final EditText edtNome = new EditText(context);

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                edtNome.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                edtNome.setHint("NOME");
                builder.setView(edtNome);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String nome = edtNome.getText().toString().toUpperCase();

                        if(nome.isEmpty())
                            Toast.makeText(getBaseContext(), "Nome não digitado!", Toast.LENGTH_SHORT).show();
                        else
                        {
                            String pass = passGenerator();

                            ArrayList<String> aux = new ArrayList<String>();
                            aux.add(nome);
                            aux.add(pass);

                            newUser(nome, pass);

                            alunos.add(aux);

                            databaseReference.child(nome).setValue(pass);

                            initAdapter();
                        }

                        dialog.cancel();
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
        });
    }

    private void getData()
    {
        FireApp fireApp = (FireApp) getApplicationContext();
        alunos = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("CadastroAlunos")
                .child(fireApp.getUserName()).child(fireApp.getUserSala());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {
                    ArrayList<String> aux = new ArrayList<String>();

                    aux.add(childSnapshot.getKey().toString());
                    aux.add(childSnapshot.getValue().toString());
                    alunos.add(aux);
                }

                initAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initAdapter()
    {
        AlunosAdapter adapter = new AlunosAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
    }

    public void newUser(final String nome, final String pass)
    {
        String email = nome.replace(" ", "_");

        firebaseAuth.createUserWithEmailAndPassword(email+"@PEI.COM", pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            String user_id = firebaseAuth.getCurrentUser().getUid();

                            DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance()
                                    .getReference().child("Users").child(user_id);

                            databaseReferenceUser.child("Nome").setValue(nome);
                            databaseReferenceUser.child("Senha").setValue(pass);
                            databaseReferenceUser.child("Admin").setValue(0);
                            databaseReferenceUser.child("Professor").setValue(0);

                        }
                        else
                            Toast.makeText(TelaAlunos.this, "ERRO AO CRIAR CONTA!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private String passGenerator()
    {
        char[] chars1 = "ABCDEF12GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++)
        {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }

        return sb1.toString();
    }
}
