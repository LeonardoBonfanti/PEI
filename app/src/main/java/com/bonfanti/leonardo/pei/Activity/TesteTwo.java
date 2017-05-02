package com.bonfanti.leonardo.pei.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuário on 3/25/2017.
 */

public class TesteTwo extends AppCompatActivity implements View.OnClickListener
{
    Button myImage;
    Button btnOk;
    Button btnCancel;
    EditText myEdit;
    List<String> listRespostas;
    List<Integer> resultadoConjunto;
    int handlerImg;
    boolean validado;
    RelativeLayout relativeLayout;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_two);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        myImage = (Button)findViewById(R.id.imgTesteAudio);
        myImage.setOnClickListener(this);

        myEdit = (EditText)findViewById(R.id.editTextRespostaTwo);
        myEdit.setTextColor(Color.WHITE);
        myEdit.setTypeface(AppOptions.getTypeface(getAssets()));
        myEdit.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        listRespostas = new ArrayList<>();
        resultadoConjunto = new ArrayList<>();
        handlerImg = 0;
        validado = false;

        setImageTest(myImage);

        btnOk = (Button)findViewById(R.id.btnOkTesteTwo);
        btnOk.setOnClickListener(TesteTwo.this);

        btnCancel = (Button)findViewById(R.id.btnCancelTesteTwo);
        btnCancel.setOnClickListener(TesteTwo.this);

        relativeLayout = (RelativeLayout)findViewById(R.id.myRelativeTwo);
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
        btnOk = (Button)findViewById(R.id.btnOkTesteTwo);
        myEdit = (EditText)findViewById(R.id.editTextRespostaTwo);
        myImage = (Button)findViewById(R.id.imgTesteAudio);

        if(view == btnOk && handlerImg <= 4)
        {
            listRespostas.add(myEdit.getText().toString());

            if(handlerImg == 4)
            {
                iniciaValidacao();

                if(validado)
                    handlerImg = 0;
                else
                    handlerImg++;
            }
            else
                handlerImg++;
        }
        else if(view == myImage)
            playSound();

        myEdit.setText("");
    }

    void playSound()
    {
        MediaPlayer mp = null;

        if(handlerImg == 0)
            mp = MediaPlayer.create(this, R.raw.calendario);
        else if(handlerImg == 1)
            mp = MediaPlayer.create(this, R.raw.tesoura);
        else if(handlerImg == 2)
            mp = MediaPlayer.create(this, R.raw.regua);
        else if(handlerImg == 3)
            mp = MediaPlayer.create(this, R.raw.lapis);
        else if(handlerImg == 4)
            mp = MediaPlayer.create(this, R.raw.giz);

        mp.start();
    }

    void iniciaValidacao()
    {
        for(int i=0; i <= listRespostas.size(); i++)
        {
            switch (i)
            {
                case 0:
                {
                    resultadoConjunto.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "CALENDARIO"));
                    break;
                }
                case 1:
                {
                    resultadoConjunto.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "TESOURA"));
                    break;
                }
                case 2:
                {
                    resultadoConjunto.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "REGUA"));
                    break;
                }
                case 3:
                {
                    resultadoConjunto.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "LAPIS"));
                    break;
                }
                case 4:
                {
                    resultadoConjunto.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "GIZ"));
                    break;
                }
            }
        }

        verificaNivel();
    }

    void verificaNivel()
    {
        int total = 0;
        String result;

        for (int i = 0; i < resultadoConjunto.size(); i++)
            total += resultadoConjunto.get(i);

        total = total/resultadoConjunto.size();

        Intent intent = new Intent(this, TelaResultadoTemp.class);

        if(total <= 1)
            result = "Alfabética";
        else if(total > 1 & total <= 3)
            result = "Silábico-Alfabética";
        else if(total == 4)
            result = "Silábica";
        else
            result = "Pré-Silábica";

        intent.putExtra("EXTRA_SESSION_ID", result);

        final FireApp fireApp= (FireApp) getApplicationContext();
        String key = fireApp.getUserKey();
        String sala = fireApp.getUserSala();

        AppOptions.saveData(result, sala, key, "DOIS", databaseReference);

        validado = true;
        startActivity(intent);
    }
}
