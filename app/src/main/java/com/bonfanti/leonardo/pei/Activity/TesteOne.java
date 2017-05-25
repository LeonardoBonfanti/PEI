package com.bonfanti.leonardo.pei.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Usuário on 3/10/2017.
 */

public class TesteOne extends AppCompatActivity implements View.OnClickListener
{
    ImageView myImage;
    Button btnOk;
    Button btnCancel;
    EditText myEdit;
    List<String> listRespostas;
    List<Integer> resultadoConjunto;
    int handlerImg;
    boolean validado;

    RelativeLayout relativeLayout;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_one);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        myImage = (ImageView)findViewById(R.id.imageTest);

        myEdit = (EditText)findViewById(R.id.editTextResposta);
        myEdit.setTextColor(Color.WHITE);
        myEdit.setTypeface(AppOptions.getTypeface(getAssets()));
        myEdit.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        listRespostas = new ArrayList<>();
        resultadoConjunto = new ArrayList<>();
        handlerImg = 0;
        validado = false;
        imgHandler(myImage);

        setImageTest(myImage);

        btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setOnClickListener(TesteOne.this);

        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(TesteOne.this);

        relativeLayout = (RelativeLayout)findViewById(R.id.myRelativeOne);
        AppOptions.onTapOutsideBehaviour(relativeLayout, this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    void setImageTest(ImageView myView)
    {
        int displayWidth = getWindowManager().getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(displayWidth/3, displayWidth/3);
        AppOptions.setLocation(myView, getWindowManager(), params, - displayWidth/6, - displayWidth/3);
    }

    @Override
    public void onClick(View view)
    {
        btnOk = (Button)findViewById(R.id.btnOk);
        myEdit = (EditText)findViewById(R.id.editTextResposta);
        myImage = (ImageView)findViewById(R.id.imageTest);

        if(view == btnOk)
        {
            listRespostas.add(myEdit.getText().toString());
            imgHandler(myImage);
        }

        myEdit.setText("");
    }

    void imgHandler(ImageView myView)
    {
        if (handlerImg == 0)
            myView.setImageResource(R.drawable.apontador);
        else if(handlerImg == 1)
            myView.setImageResource(R.drawable.caderno);
        else if(handlerImg == 2)
            myView.setImageResource(R.drawable.quadro);
        else if(handlerImg == 3)
            myView.setImageResource(R.drawable.cola);
        else if(handlerImg > 3)
            iniciaValidacao();

        if(validado)
            handlerImg = 0;
        else
            handlerImg++;
    }

    void iniciaValidacao()
    {
        for(int i=0; i <= listRespostas.size(); i++)
        {
            switch (i) {
                case 0: {
                    resultadoConjunto.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "APONTADOR"));
                    break;
                }
                case 1: {
                    ArrayList<Integer> temp = new ArrayList<>();

                    temp.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "CADERNO"));
                    temp.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "LIVRO"));
                    temp.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "AGENDA"));

                    int aux = temp.get(0);

                    for(int k = 0; k < temp.size(); k++)
                        if(temp.get(k) <= aux)
                            aux = temp.get(k);

                    resultadoConjunto.add(aux);

                    break;
                }
                case 2: {
                    ArrayList<Integer> temp = new ArrayList<>();

                    temp.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "QUADRO"));
                    temp.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "QUADRO NEGRO"));
                    temp.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "LOUSA"));

                    int aux = temp.get(0);

                    for(int k = 0; k < temp.size(); k++)
                        if(temp.get(k) <= aux)
                            aux = temp.get(k);

                    resultadoConjunto.add(aux);

                    break;
                }
                case 3: {
                    resultadoConjunto.add(AppOptions.computeLevenshteinDistance(listRespostas.get(i), "COLA"));
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

        AppOptions.saveData(result, sala, key, "UM", databaseReference);

        validado = true;

       createPopUpBeforeEnding();
    }

    private void createPopUpBeforeEnding()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("REALIZAR OUTRO TESTE?");

        relativeLayout = null;

        builder.setCancelable(false).setPositiveButton("SIM", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(TesteOne.this, TelaTestes.class);
                startActivity(intent);
            }
        });

        builder.setCancelable(false).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                firebaseAuth.signOut();

                dialog.cancel();

                Intent intent = new Intent(TesteOne.this, TelaLoginCadastro.class);
                startActivity(intent);

                return;
            }
        });

        builder.show();
    }
}
