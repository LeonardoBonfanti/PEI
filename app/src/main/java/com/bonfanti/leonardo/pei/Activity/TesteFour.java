package com.bonfanti.leonardo.pei.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.FireApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Usuário on 4/10/2017.
 */

public class TesteFour extends AppCompatActivity implements View.OnClickListener
{
    Button myImage;
    Button btnOk;
    Button btnCancel;
    EditText myEdit;
    String resposta;
    RelativeLayout relativeLayout;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_four);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        myImage = (Button)findViewById(R.id.imgTesteAudio4);
        myImage.setOnClickListener(this);

        myEdit = (EditText)findViewById(R.id.editTextRespostaFour);
        myEdit.setTextColor(Color.WHITE);
        myEdit.setTypeface(AppOptions.getTypeface(getAssets()));
        myEdit.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        setImageTest(myImage);

        btnOk = (Button)findViewById(R.id.btnOkTesteFour);
        btnOk.setOnClickListener(TesteFour.this);

        btnCancel = (Button)findViewById(R.id.btnCancelTesteFour);
        btnCancel.setOnClickListener(TesteFour.this);

        relativeLayout = (RelativeLayout)findViewById(R.id.myRelativeFour);
        AppOptions.onTapOutsideBehaviour(relativeLayout, this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    void setImageTest(Button myView)
    {
        int displayWidth = getWindowManager().getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(displayWidth/3, displayWidth/3);
        AppOptions.setLocation(myView, getWindowManager(), params, - displayWidth/6, - displayWidth/3);
    }

    @Override
    public void onClick(View view)
    {
        btnOk = (Button)findViewById(R.id.btnOkTesteFour);
        myEdit = (EditText)findViewById(R.id.editTextRespostaFour);
        myImage = (Button)findViewById(R.id.imgTesteAudio4);

        if(view == btnOk)
        {
            resposta = myEdit.getText().toString();

            myEdit.setText("");

            verificaNivel();
        }
        else if(view == myImage) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.sound_teste4);
            mp.start();
        }
    }

    void verificaNivel()
    {
        int total = AppOptions.computeLevenshteinDistance(resposta, "O MENINO DESENHA NO CADERNO");
        String result;

        if(total <= 1)
            result = "ALFABÉTICA";
        else if(total > 1 & total <= 3)
            result = "SILÁBICO-ALFABÉTICA";
        else if(total == 4)
            result = "SILÁBICA";
        else
            result = "PRÉ-SILÁBICA";

        final FireApp fireApp= (FireApp) getApplicationContext();
        String key = fireApp.getUserKey();
        String sala = fireApp.getUserSala();

        AppOptions.saveData(result, sala, key, "QUATRO", databaseReference);

        firebaseAuth.signOut();

        Intent intent = new Intent(this, TelaLoginCadastro.class);
        startActivity(intent);
    }
}