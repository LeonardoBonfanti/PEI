package com.bonfanti.leonardo.pei.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bonfanti.leonardo.pei.R;

import java.util.HashMap;

/**
 * Created by Usuário on 7/6/2017.
 */

public class VideoAdapter extends ArrayAdapter<String>
{
    Context context;
    Object[] links, title;
    HashMap<String, String> videos;
    private LayoutInflater layoutInflater;

    public VideoAdapter(Context context, HashMap<String, String> videos){
        super(context, R.layout.custom_video);

        this.context = context;
        this.videos = videos;

        this.links = videos.values().toArray();
        this.title = videos.keySet().toArray();
        layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            holder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.custom_video, parent, false);

            holder.descricao = (TextView) convertView.findViewById(R.id.txtVideo);

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        holder.descricao.setText("  " + title[position].toString());
        holder.descricao.setTextColor(Color.WHITE);
        holder.uri = links[position].toString();

        final ViewHolder finalHolder = holder;
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.tela_podcast, null, false);
                WebView webView = (WebView) view.findViewById(R.id.myWebVideo);
                webView.loadUrl(finalHolder.uri);
                webView.setVisibility(View.VISIBLE);
            }
        });

        return convertView;
    }

    public static class ViewHolder
    {
        TextView descricao;
        String uri;
    }

    @Override
    public int getCount() { return videos.size(); }
}
