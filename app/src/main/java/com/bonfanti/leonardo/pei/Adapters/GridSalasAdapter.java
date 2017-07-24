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

import com.bonfanti.leonardo.pei.Activity.TelaAlunos;
import com.bonfanti.leonardo.pei.Activity.TelaTestes;
import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.FireApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Usuário on 4/21/2017.
 */

public class GridSalasAdapter extends ArrayAdapter<String[]>
{
    Context mContext;
    ArrayList<ArrayList> data;
    DatabaseReference databaseReference;

    public GridSalasAdapter(Context context, ArrayList<ArrayList> temp) {
        super(context, R.layout.custom_button_salas);
        mContext = context;
        this.data = temp;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Salas");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View row;
        final ViewHolder holder = new ViewHolder();

        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.custom_button_salas, parent,false);
        holder.sala = (Button) row.findViewById(R.id.btnSala);
        holder.professor = (TextView) row.findViewById(R.id.txtProfessor);
        holder.delete = (Button) row.findViewById(R.id.btnDeleteSala);
        holder.edit = (Button) row.findViewById(R.id.btnEditClass);

        final FireApp fireApp= (FireApp) mContext.getApplicationContext();
        int admin = fireApp.getAdmin();
        int prof = fireApp.getProfessor();

        if(admin != 1)
        {
            holder.delete.setVisibility(View.INVISIBLE);
            holder.delete.setEnabled(false);
        }
        if(prof != 1)
        {
            holder.edit.setVisibility(View.INVISIBLE);
            holder.edit.setEnabled(false);
        }

        row.setTag(holder);

        ArrayList<String> arrayList = data.get(position);

        holder.professor.setText((arrayList.get(0).toString()));
        holder.sala.setText(arrayList.get(1).toString());

        GradientDrawable bgShape = (GradientDrawable)holder.sala.getBackground();
        bgShape.setColor(Color.parseColor(AppOptions.getBackgroundColor(position)));

        holder.sala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mContext, TelaTestes.class);

                final FireApp fireApp = (FireApp) mContext.getApplicationContext();
                fireApp.setUserSala(holder.professor.getText().toString());

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
                        databaseReference.child(holder.sala.getText().toString()).removeValue();
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

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FireApp fireApp = (FireApp) mContext.getApplicationContext();
                fireApp.setUserSala(holder.sala.getText().toString());
                fireApp.setUserProfessor(holder.professor.getText().toString());

                Intent intent = new Intent(mContext, TelaAlunos.class);
                mContext.startActivity(intent);
            }
        });

        return row;
    }

    public static class ViewHolder
    {
        Button delete;
        Button edit;
        Button sala;
        TextView professor;
    }

    public int getCount() {
        return data.size();
    }
}