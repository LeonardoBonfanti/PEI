package com.bonfanti.leonardo.pei.Activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bonfanti.leonardo.pei.Adapters.CarTableDataAdapter;
import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.DataFactory;
import com.bonfanti.leonardo.pei.Utils.SortableCarTableView;

/**
 * Created by Usuário on 4/23/2017.
 */

public class TelaResultados extends AppCompatActivity
{
    Toolbar myToolbar;
    SortableCarTableView carTableView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_resultados);

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        carTableView = (SortableCarTableView)findViewById(R.id.tableView);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_result);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("RESULTADOS");
        myToolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppOptions.setOverflowButtonColor(myToolbar, Color.WHITE);

        setBackArrowColor();

        populate();
    }

    private void setBackArrowColor()
    {
        final Drawable upArrow = getResources().getDrawable(R.mipmap.arrow_back_white);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void populate()
    {
        final CarTableDataAdapter carTableDataAdapter = new CarTableDataAdapter(this, DataFactory.createCarList(), carTableView);
        carTableView.setDataAdapter(carTableDataAdapter);
    }
}