package com.bonfanti.leonardo.pei.Adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bonfanti.leonardo.pei.Activity.TelaSalas;
import com.bonfanti.leonardo.pei.Activity.TelaTestes;
import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.FireApp;

import java.util.ArrayList;

/**
 * Created by Usuário on 4/21/2017.
 */

public class GridSalasAdapter extends ArrayAdapter<String[]> {
    Context mContext;
    ArrayList<String> data;

    public GridSalasAdapter(Context context, ArrayList<String> temp) {
        super(context, R.layout.custom_button_salas);
        mContext = context;
        this.data = temp;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View row;
        final ViewHolder holder = new ViewHolder();

        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.custom_button_salas, parent,false);
        holder.button = (Button) row.findViewById(R.id.btnSala);
        holder.textView = (TextView) row.findViewById(R.id.txtProfessor);
        holder.delete = (Button) row.findViewById(R.id.btnDeleteSala);

        row.setTag(holder);

        holder.button.setText(this.data.get(position));

        GradientDrawable bgShape = (GradientDrawable)holder.button.getBackground();
        bgShape.setColor(Color.parseColor(AppOptions.getBackgroundColor(position)));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mContext, TelaTestes.class);

                final FireApp fireApp = (FireApp) mContext.getApplicationContext();
                fireApp.setUserSala(holder.button.getText().toString());

                mContext.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Deseja realmente deletar esta sala?:");

                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        return;
                    }
                });

                builder.show();
            }
        });

        return row;
    }

    public static class ViewHolder
    {
        Button delete;
        Button button;
        TextView textView;
    }

    public int getCount() {
        return data.size();
    }
}