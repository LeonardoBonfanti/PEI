package com.bonfanti.leonardo.pei.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;


/**
 * Created by Usuário on 3/19/2017.
 */

public class TelaSenhaGerada extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_senha_gerada);

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());

        AppOptions.UiChangeListener(getWindow().getDecorView());

        configImage();
        setTypeFont();
        setButtonPosition();
        getInfosUser();
    }

    void configImage()
    {
        float alpha = (float)0.1;
        ImageView myImage = (ImageView)findViewById(R.id.logoAlpha);

        int displayWidth = getWindowManager().getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(displayWidth, displayWidth);
        params.leftMargin = AppOptions.getDisplaySize(getWindowManager()).x / 2 - displayWidth/2;
        params.topMargin = AppOptions.getDisplaySize(getWindowManager()).y / 2 - displayWidth/2;

        myImage.setLayoutParams(params);
        myImage.setAlpha(alpha);
    }

    void setButtonPosition()
    {
        Button btn = (Button)findViewById(R.id.btnOkCadastro);
        btn.setOnClickListener(this);

        int displayWidth = getWindowManager().getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(displayWidth/4, displayWidth/4);
        AppOptions.setLocation(btn, getWindowManager(), params, - displayWidth/8, - displayWidth/16 + displayWidth/4);
    }

    void setTypeFont()
    {
        TextView myText;

        myText = (TextView)findViewById(R.id.txtBemVindo);
        myText.setTypeface(AppOptions.getTypeface(getAssets()));

        myText = (TextView)findViewById(R.id.txtUserName);
        myText.setTypeface(AppOptions.getTypeface(getAssets()));

        myText = (TextView)findViewById(R.id.txtInfoPass);
        myText.setTypeface(AppOptions.getTypeface(getAssets()));

        myText = (TextView)findViewById(R.id.txtUserPass);
        myText.setTypeface(AppOptions.getTypeface(getAssets()));
    }

    void getInfosUser()
    {
        TextView myTextName = (TextView)findViewById(R.id.txtUserName);

        String nome = getIntent().getStringExtra("NEW_NOME");
        nome = nome.replace("_", " ");
        myTextName.setText(nome);

        TextView myTextPass= (TextView)findViewById(R.id.txtUserPass);
        myTextPass.setText(getIntent().getStringExtra("NEW_PASS"));

    }

    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent(this, TelaSalas.class);
        startActivity(intent);
    }
}
