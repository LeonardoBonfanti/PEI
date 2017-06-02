package com.bonfanti.leonardo.pei.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;

/**
 * Created by Usuário on 2/16/2017.
 */

public class SplashScreen extends AppCompatActivity implements Runnable
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        Handler handler = new Handler();
        handler.postDelayed(this, 2000);

        configLayout();
    }

    void configLayout()
    {
        ImageView myImage = (ImageView)findViewById(R.id.imgLogoSplash);

        int displayWidth = getWindowManager().getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(displayWidth/3, displayWidth/2);

        AppOptions.setLocation(myImage, getWindowManager(), params, - displayWidth/6, - displayWidth/4);
    }

    @Override
    public void run()
    {
        getWindow().setWindowAnimations(0);
        startActivity(new Intent(this, TelaLoginCadastro.class));
        finish();
    }
}
