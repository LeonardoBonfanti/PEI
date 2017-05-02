package com.bonfanti.leonardo.pei.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;

/**
 * Created by Usuário on 3/14/2017.
 */

public class TelaResultadoTemp extends AppCompatActivity implements Runnable
{
    TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_temp);

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());

        AppOptions.UiChangeListener(getWindow().getDecorView());

        Handler handler = new Handler();
        handler.postDelayed(this, 4000);

        myText = (TextView) findViewById(R.id.txtHeader);
        myText.setTypeface(AppOptions.getTypeface(getAssets()));

        myText = (TextView) findViewById(R.id.txtResult);
        myText.setTypeface(AppOptions.getTypeface(getAssets()));

        String result = getIntent().getStringExtra("EXTRA_SESSION_ID");
        myText.setText(result);
    }

    @Override
    public void run()
    {
        startActivity(new Intent(this, TelaTestes.class));
        finish();
    }
}
