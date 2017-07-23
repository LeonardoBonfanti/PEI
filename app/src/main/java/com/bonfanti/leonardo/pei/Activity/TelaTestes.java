package com.bonfanti.leonardo.pei.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.FireApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

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

    @Override
    protected void onResume()
    {
        super.onResume();

        verifyTestDone();
    }

    private void verifyTestDone()
    {
        final FireApp fireApp= (FireApp) getApplicationContext();
        ArrayList<String> tests = fireApp.getTests();

        if(tests == null)
            return;

        for(int i = 0; i < tests.size(); i++)
        {
            switch(tests.get(i))
            {
                case "1":
                {
                    ImageView img = (ImageView)findViewById(R.id.imgTestDone1);
                    img.setVisibility(View.VISIBLE);
                    TextView txt = (TextView)findViewById(R.id.txtTextOne);
                    txt.setVisibility(View.INVISIBLE);

                    myCard.setCardBackgroundColor(Color.parseColor("#4BFF61"));
                    myCard.setClickable(false);

                    break;
                }

                case "2":
                {
                    ImageView img = (ImageView)findViewById(R.id.imgTestDone2);
                    img.setVisibility(View.VISIBLE);
                    TextView txt = (TextView)findViewById(R.id.txtTextTwo);
                    txt.setVisibility(View.INVISIBLE);

                    myCard2.setCardBackgroundColor(Color.parseColor("#4BFF61"));
                    myCard2.setClickable(false);

                    break;
                }

                case "3":
                {
                    ImageView img = (ImageView)findViewById(R.id.imgTestDone3);
                    img.setVisibility(View.VISIBLE);
                    TextView txt = (TextView)findViewById(R.id.txtTextThree);
                    txt.setVisibility(View.INVISIBLE);

                    myCard3.setCardBackgroundColor(Color.parseColor("#4BFF61"));
                    myCard3.setClickable(false);

                    break;
                }

                case "4":
                {
                    ImageView img = (ImageView)findViewById(R.id.imgTestDone4);
                    img.setVisibility(View.VISIBLE);
                    TextView txt = (TextView)findViewById(R.id.txtTextFour);
                    txt.setVisibility(View.INVISIBLE);

                    myCard4.setCardBackgroundColor(Color.parseColor("#4BFF61"));
                    myCard4.setClickable(false);

                    break;
                }
            }
        }
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

        int displayWidth = getWindowManager().getDefaultDisplay().getWidth();
        int displayHeight = getWindowManager().getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(displayHeight/4, displayHeight/4);

        switch (number)
        {
            case 1: {
                params.leftMargin = displayWidth/12;
                params.topMargin = displayHeight/6;
                cardView.setLayoutParams(params);

                TextView myText = (TextView)findViewById(R.id.txtTextOne);
                myText.setTypeface(AppOptions.getTypeface(getAssets()));

                break;
            }

            case 2: {

                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.rightMargin = displayWidth/12;
                params.topMargin = displayHeight/6;
                cardView.setLayoutParams(params);

                TextView myText = (TextView)findViewById(R.id.txtTextTwo);
                myText.setTypeface(AppOptions.getTypeface(getAssets()));

                break;
            }

            case 3: {
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.leftMargin = displayWidth/12;
                params.bottomMargin = displayHeight/6;
                cardView.setLayoutParams(params);

                TextView myText = (TextView)findViewById(R.id.txtTextThree);
                myText.setTypeface(AppOptions.getTypeface(getAssets()));

                break;
            }

            case 4: {
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.rightMargin = displayWidth/12;
                params.bottomMargin = displayHeight/6;
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
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.tool_itens, menu);
//
//        final FireApp fireApp= (FireApp) getApplicationContext();
//        int admin = fireApp.getAdmin();
//        int professor = fireApp.getProfessor();
//
//        MenuItem item = menu.findItem(R.id.action_add);
//        item.setVisible(false);
//
//        if(admin != 1)
//        {
//            if(professor == 1)
//                return true;
//
//            item = menu.findItem(R.id.action_resultados);
//            item.setVisible(false);
//        }

        return true;
    }
}

