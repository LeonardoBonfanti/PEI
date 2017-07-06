package com.bonfanti.leonardo.pei.Activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;

/**
 * Created by Usuário on 7/5/2017.
 */

public class TelaBlog extends AppCompatActivity
{
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_blog);

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("BLOG");
        myToolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppOptions.setOverflowButtonColor(myToolbar, Color.WHITE);

        setBackArrowColor();

        WebView webView = (WebView)findViewById(R.id.myWeb);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("http://ceaapp.blogspot.com.br");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    private void setBackArrowColor()
    {
        final Drawable upArrow = getResources().getDrawable(R.mipmap.arrow_back_white);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }
}
