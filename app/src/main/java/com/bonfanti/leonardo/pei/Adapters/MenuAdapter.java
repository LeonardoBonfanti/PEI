package com.bonfanti.leonardo.pei.Adapters;

/**
 * Created by Usuário on 6/2/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bonfanti.leonardo.pei.Activity.MainActivity;
import com.bonfanti.leonardo.pei.Activity.TelaLogin;
import com.bonfanti.leonardo.pei.Activity.TelaResultados;
import com.bonfanti.leonardo.pei.Activity.TelaSalas;
import com.bonfanti.leonardo.pei.Activity.TelaTestes;
import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.MenuInit;

import java.util.List;

import com.bumptech.glide.Glide;

/**
 * Created by Leonardo Bonfanti on 3/8/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder>
{

    private Context mContext;
    private List<MenuInit> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public RelativeLayout myRelative;

        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            myRelative = (RelativeLayout) view.findViewById(R.id.myRelativeMenu);
        }
    }


    public MenuAdapter(Context mContext, List<MenuInit> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_main, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        MenuInit album = albumList.get(position);
        holder.title.setText(album.getDescription());
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                redirect(position);
            }
        });

        holder.myRelative.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                redirect(position);
            }
        });
    }

    public void redirect(int page)
    {
        switch(page)
        {
            case 0:
                AppOptions.createPopUpAbout(mContext);

                break;

            case 1: {
                Intent intent = new Intent(mContext, TelaResultados.class);
                mContext.startActivity(intent);
                break;
            }
            case 2: {
                Intent intent = new Intent(mContext, TelaSalas.class);
                mContext.startActivity(intent);
                break;
            }
            case 3:

                break;

            case 4:

                break;

            case 5:

                break;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
