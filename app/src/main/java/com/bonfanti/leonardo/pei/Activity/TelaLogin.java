package com.bonfanti.leonardo.pei.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
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

/**
 * Created by Usuário on 3/24/2017.
 */

public class TelaLogin extends AppCompatActivity implements View.OnClickListener
{
    Button btnOk;
    EditText myEditNome;
    EditText myEditPass;
    RelativeLayout relativeLayout;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        progressDialog = new ProgressDialog(this);

        relativeLayout = (RelativeLayout)findViewById(R.id.myRelativeLogin);
        myEditNome = (EditText) findViewById(R.id.editTextLoginNome);
        myEditPass = (EditText) findViewById(R.id.editTextLoginPass);

        configButton();
        configEditText();
        configLayout();

        AppOptions.onTapOutsideBehaviour(relativeLayout, this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    void configButton()
    {
        btnOk = (Button)findViewById(R.id.btnLoginOk);
        btnOk.setTypeface(AppOptions.getTypeface(getAssets()));
        btnOk.setOnClickListener(this);
        btnOk.setTextColor(Color.BLACK);
    }

    void configEditText()
    {
        myEditNome.setTextColor(Color.WHITE);
        myEditNome.setTypeface(AppOptions.getTypeface(getAssets()));
        myEditNome.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        myEditPass.setTextColor(Color.WHITE);
        myEditPass.setTypeface(AppOptions.getTypeface(getAssets()));
        myEditPass.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
    }

    void configLayout()
    {
        ImageView myImage = (ImageView)findViewById(R.id.imgLogoLogin);
        int displayWidth = getWindowManager().getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(displayWidth/3, displayWidth/2);
        AppOptions.setLocation(myImage, getWindowManager(), params, - displayWidth/6, - displayWidth/3);

        params = new RelativeLayout.LayoutParams(displayWidth/4,displayWidth/14);
        params.addRule(RelativeLayout.BELOW, R.id.editTextLoginPass);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        btnOk.setLayoutParams(params);
    }

    @Override
    public void onClick(View view)
    {
        final String nome = myEditNome.getText().toString();
        String monta = nome.replace(" ","_");

        final String email = monta + "@PEI.COM";
        final String pass = myEditPass.getText().toString();

        if(TextUtils.isEmpty(nome) || TextUtils.isEmpty(pass))
            Toast.makeText(TelaLogin.this, "ALGUM CAMPO VAZIO!", Toast.LENGTH_LONG).show();
        else
        {
            progressDialog.setMessage("ENTRANDO...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                String user_id = firebaseAuth.getCurrentUser().getUid();

                                DatabaseReference current_user = databaseReference.child(user_id);

                                current_user.child("Nome").setValue(nome);
                                current_user.child("Senha").setValue(pass);

                                progressDialog.dismiss();

                                final FireApp fireApp= (FireApp) getApplicationContext();
                                fireApp.setUserKey(user_id);

                                Intent intent = new Intent(TelaLogin.this, TelaSalas.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(TelaLogin.this, "FALHA AO ENTRAR", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }
}
