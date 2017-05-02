package com.bonfanti.leonardo.pei.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.FireApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Usuário on 4/11/2017.
 */

public class TesteThree extends AppCompatActivity implements View.OnClickListener
{
    ImageView myImage;
    Button btnOk;
    Button btnCancel;
    EditText myEdit;
    String resposta;
    RelativeLayout relativeLayout;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_three);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        setImageTest();

        myEdit = (EditText)findViewById(R.id.editTextRespostaThree);
        myEdit.setTextColor(Color.WHITE);
        myEdit.setTypeface(AppOptions.getTypeface(getAssets()));
        myEdit.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        btnOk = (Button)findViewById(R.id.btnOkThree);
        btnOk.setOnClickListener(TesteThree.this);

        btnCancel = (Button)findViewById(R.id.btnCancelThree);
        btnCancel.setOnClickListener(TesteThree.this);

        relativeLayout = (RelativeLayout)findViewById(R.id.myRelativeThree);
        AppOptions.onTapOutsideBehaviour(relativeLayout, this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    void setImageTest()
    {
        myImage = (ImageView)findViewById(R.id.imageTestThree);

        int displayWidth = getWindowManager().getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(displayWidth/2, displayWidth/3);
        AppOptions.setLocation(myImage, getWindowManager(), params, - displayWidth/4, - displayWidth/3);
    }

    @Override
    public void onClick(View view)
    {
        btnOk = (Button)findViewById(R.id.btnOkThree);
        myEdit = (EditText)findViewById(R.id.editTextRespostaThree);

        if(view == btnOk) {
            resposta = myEdit.getText().toString();
            verificaNivel();
        }

        myEdit.setText("");
    }

    int possibilities()
    {
        ArrayList<Integer> temp = new ArrayList<>();

        String[] lista = new String[]{"A MENINA PINTA A CASA", "MENINA PINTA A CASA", "A GAROTA PINTA A CASA","GAROTA PINTA A CASA",
                "A MENINA PINTA A CASINHA", "A MENINA PINTA A CASINHA", "MENINA PINTA A CASINHA", "A GAROTA PINTA A CASINHA", "GAROTA PINTA A CASINHA",
                "A MENINA PINTA UMA CASA", "MENINA PINTA UMA CASA", "A GAROTA PINTA UMA CASA", "GAROTA PINTA UMA CASA", "A MENINA PINTA UMA CASINHA",
                "MENINA PINTA UMA CASINHA", "A GAROTA PINTA UMA CASINHA", "GAROTA PINTA UMA CASINHA", "A MENINA PINTANDO A CASA", "MENINA PINTANDO A CASA",
                "A GAROTA PINTANDO A CASA", "GAROTA PINTANDO A CASA", "A MENINA PINTANDO A CASINHA", "MENINA PINTANDO A CASINHA", "A GAROTA PINTANDO A CASINHA",
                "GAROTA PINTANDO A CASINHA", "A MENINA PINTANDO UMA CASA", "MENINA PINTANDO UMA CASA", "A GAROTA PINTANDO UMA CASA", "GAROTA PINTANDO UMA CASA",
                "A MENINA PINTANDO UMA CASINHA", "MENINA PINTANDO UMA CASINHA", "A GAROTA PINTANDO UMA CASINHA", "GAROTA PINTANDO UMA CASINHA", "A MENINA DESENHA A CASA",
                "A MENINA DESENHA UMA CASA", "A MENINA DESENHA SUA CASA", "A GAROTA DESENHA A CASA", "A GAROTA DESENHA UMA CASA", "A GAROTA DESENHA SUA CASA",
                "A MENINA DESENHA A CASA", "A MENINA DESENHA UMA CASA", "A MENINA DESENHA SUA CASA", "A GAROTA DESENHA A CASA", "A GAROTA DESENHA UMA CASA",
                "A GAROTA DESENHA SUA CASA", "MENINA DESENHANDO A CASA", "MENINA DESENHANDO UMA CASA", "MENINA DESENHANDO SUA CASA", "GAROTA DESENHANDO A CASA",
                "GAROTA DESENHANDO UMA CASA", "GAROTA DESENHANDO SUA CASA", "MENINA DESENHANDO A CASINHA", "MENINA DESENHANDO UMA CASINHA", "MENINA DESENHANDO SUA CASINHA",
                "GAROTA DESENHANDO A CASINHA", "GAROTA DESENHANDO UMA CASINHA", "GAROTA DESENHANDO SUA CASINHA"};

        for (int i = 0; i < lista.length; i++)
            temp.add(AppOptions.computeLevenshteinDistance(resposta, lista[i]));

        int aux = temp.get(0);

        for(int k = 0; k < temp.size(); k++)
            if(temp.get(k) <= aux)
                aux = temp.get(k);

        return aux;
    }

    void verificaNivel()
    {
        int total = possibilities();
        String result;

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

        AppOptions.saveData(result, sala, key, "TRÊS", databaseReference);

        startActivity(intent);
    }
}
