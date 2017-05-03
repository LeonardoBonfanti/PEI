package com.bonfanti.leonardo.pei.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.FireApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

/**
 * Created by Usuário on 3/15/2017.
 */

public class TelaCadastro extends AppCompatActivity implements View.OnClickListener
{
    RelativeLayout relativeLayout;
    EditText myEdit;
    Button btnOk;
    ImageView myImage;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());

        AppOptions.UiChangeListener(getWindow().getDecorView());

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        progressDialog = new ProgressDialog(this);

        btnOk = (Button)findViewById(R.id.btnCadastroOk);
        btnOk.setTypeface(AppOptions.getTypeface(getAssets()));
        btnOk.setOnClickListener(this);
        btnOk.setTextColor(Color.BLACK);

        myEdit = (EditText) findViewById(R.id.editTextNome);
        myEdit.setTypeface(AppOptions.getTypeface(getAssets()));
        myEdit.setTextColor(Color.WHITE);

        myImage = (ImageView)findViewById(R.id.imageLogoCadastro);
        relativeLayout = (RelativeLayout)findViewById(R.id.myRelativeCadastro);

        configLayout();

        AppOptions.onTapOutsideBehaviour(relativeLayout, this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    void configLayout()
    {
        int displayWidth = getWindowManager().getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(displayWidth/3, displayWidth/2);
        AppOptions.setLocation(myImage, getWindowManager(), params, - displayWidth/6, - displayWidth/3);

        params = new RelativeLayout.LayoutParams(displayWidth/4,displayWidth/14);
        AppOptions.setLocation(btnOk, getWindowManager(), params, -displayWidth/8, displayWidth/3 + displayWidth/100);
    }

    @Override
    public void onClick(View view)
    {
        String nome = myEdit.getText().toString().toUpperCase();

        if(nome.isEmpty())
            Toast.makeText(this, "INSIRA SEU NOME!", Toast.LENGTH_SHORT).show();
        else
        {
            String pass = passGenerator();
            newUser(nome, pass);
        }
    }

    public void newUser(final String nome, final String pass)
    {
        progressDialog.setMessage("CRIANDO...");
        progressDialog.show();

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

                            DatabaseReference current_user = databaseReference.child(user_id);

                            current_user.child("Nome").setValue(nome);
                            current_user.child("Senha").setValue(pass);

                            progressDialog.dismiss();

                            Intent intent = new Intent(TelaCadastro.this, TelaSenhaGerada.class);

                            intent.putExtra("NEW_NOME", nome);
                            intent.putExtra("NEW_PASS", pass);

                            final FireApp fireApp= (FireApp) getApplicationContext();
                            fireApp.setUserKey(user_id);

                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(TelaCadastro.this, "ERRO AO CRIAR CONTA!", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    String passGenerator()
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
