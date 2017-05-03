package com.bonfanti.leonardo.pei.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Usuário on 2/22/2017.
 */

public class TelaLoginCadastro extends AppCompatActivity implements View.OnClickListener{

    Button login;
    Button cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_cadastro);

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());

        AppOptions.UiChangeListener(getWindow().getDecorView());

        login = (Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(TelaLoginCadastro.this);
        login.setTextColor(Color.BLACK);
        login.setTypeface(AppOptions.getTypeface(getAssets()));

        cadastro = (Button)findViewById(R.id.btnCadastro);
        cadastro.setTextColor(Color.BLACK);
        cadastro.setTypeface(AppOptions.getTypeface(getAssets()));
        cadastro.setOnClickListener(TelaLoginCadastro.this);

        configLayout();
    }

    void configLayout()
    {
        ImageView myImage = (ImageView)findViewById(R.id.imgLogo);

        int displayWidth = getWindowManager().getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(displayWidth/3, displayWidth/2);
        AppOptions.setLocation(myImage, getWindowManager(), params, - displayWidth/6, - displayWidth/3);

        params = new RelativeLayout.LayoutParams(displayWidth/4,displayWidth/14);
        AppOptions.setLocation(login, getWindowManager(), params, -displayWidth/8, displayWidth/4);

        params = new RelativeLayout.LayoutParams(displayWidth/4,displayWidth/14);
        AppOptions.setLocation(cadastro, getWindowManager(), params, -displayWidth/8, displayWidth/3 + displayWidth/100);
    }

    @Override
    public void onClick(View view)
    {
        if(view == (Button)findViewById(R.id.btnLogin))
            startActivity(new Intent(this, TelaLogin.class));
        else
            startActivity(new Intent(this, TelaCadastro.class));
    }
}
