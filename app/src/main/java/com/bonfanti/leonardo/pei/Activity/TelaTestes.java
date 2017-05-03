package com.bonfanti.leonardo.pei.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.FireApp;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Usuário on 3/8/2017.
 */

public class TelaTestes extends AppCompatActivity
{
    Toolbar myToolbar;
    CardView myCard;
    CardView myCard2;
    CardView myCard3;
    CardView myCard4;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_testes);

        firebaseAuth = FirebaseAuth.getInstance();

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        myCard = (CardView) findViewById(R.id.cardTestOne);
        setCard(myCard, 1);

        myCard2 = (CardView) findViewById(R.id.cardTestTwo);
        setCard(myCard2, 2);

        myCard3 = (CardView) findViewById(R.id.cardTestThree);
        setCard(myCard3, 3);

        myCard4 = (CardView) findViewById(R.id.cardTestFour);
        setCard(myCard4, 4);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_testes);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("TESTES");
        myToolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppOptions.setOverflowButtonColor(myToolbar, Color.WHITE);

        setBackArrowColor();
    }

    void setCard(CardView cardView, int number)
    {
        cardView.setAlpha((float) 0.7);

        cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Intent intent;

                if(v.equals(myCard))
                    intent = new Intent(getApplicationContext(), TesteOne.class);
                else if(v.equals(myCard2))
                    intent = new Intent(getApplicationContext(), TesteTwo.class);
                else if(v.equals(myCard3))
                    intent = new Intent(getApplicationContext(), TesteThree.class);
                else
                    intent = new Intent(getApplicationContext(), TesteFour.class);

                intent.putExtra("USER_KEY", getIntent().getStringExtra("USER_KEY"));
                intent.putExtra("PROF", getIntent().getStringExtra("PROF"));

                startActivity(intent);
            }
        });

        int displayWidth = getWindowManager().getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(displayWidth/3, displayWidth/3);

        switch (number)
        {
            case 1: {
                params.leftMargin = displayWidth/3;
                params.topMargin = displayWidth/3 - displayWidth/6;
                cardView.setLayoutParams(params);

                TextView myText = (TextView)findViewById(R.id.txtTextOne);
                myText.setTypeface(AppOptions.getTypeface(getAssets()));

                break;
            }

            case 2: {

                params.leftMargin = displayWidth;
                params.topMargin = displayWidth/3 - displayWidth/6;
                cardView.setLayoutParams(params);

                TextView myText = (TextView)findViewById(R.id.txtTextTwo);
                myText.setTypeface(AppOptions.getTypeface(getAssets()));

                break;
            }

            case 3: {
                params.leftMargin = displayWidth/3;
                params.topMargin = displayWidth/2 + displayWidth/10;
                cardView.setLayoutParams(params);

                TextView myText = (TextView)findViewById(R.id.txtTextThree);
                myText.setTypeface(AppOptions.getTypeface(getAssets()));

                break;
            }

            case 4: {
                params.leftMargin = displayWidth;
                params.topMargin = displayWidth/2 + displayWidth/10;
                cardView.setLayoutParams(params);

                TextView myText = (TextView)findViewById(R.id.txtTextFour);
                myText.setTypeface(AppOptions.getTypeface(getAssets()));

                break;
            }
        }
    }

    private void setBackArrowColor()
    {
        final Drawable upArrow = getResources().getDrawable(R.mipmap.arrow_back_white);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent;

        switch (item.getItemId())
        {
            case R.id.action_resultados:

                intent = new Intent(this, TelaResultados.class);
                startActivity(intent);

                return true;

            case R.id.action_inicio:
                // User chose the "Início" item, show the app settings UI...
                return true;

            case R.id.action_logout:

                firebaseAuth.signOut();

                intent = new Intent(this, TelaLoginCadastro.class);
                startActivity(intent);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    /* Trigger para mostar devidamente a Toolbar com os elementos criados no xml */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tool_itens, menu);

        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(false);

        final FireApp fireApp= (FireApp) getApplicationContext();
        String key = fireApp.getUserKey();

        if(!key.equals(AppOptions.LEO_ID) && !key.equals(AppOptions.ALE_ID))
        {
            item = menu.findItem(R.id.action_resultados);
            item.setVisible(false);
        }

        return true;
    }
}

