package com.bonfanti.leonardo.pei.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bonfanti.leonardo.pei.R;

import java.util.ArrayList;

/**
 * Created by Usuário on 7/22/2017.
 */

public class AlunosAdapter extends ArrayAdapter<String>
{
    Context context;
    ArrayList<ArrayList> data;
    LayoutInflater layoutInflater;

    public AlunosAdapter(Context context, ArrayList<ArrayList> temp)
    {
        super(context, R.layout.row_tela_alunos);
        this.context = context;
        this.data = temp;
        layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;

        if (convertView == null)
        {
            holder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.row_tela_alunos, parent, false);

            holder.nome = (TextView) convertView.findViewById(R.id.txtNome);
            holder.senha = (TextView) convertView.findViewById(R.id.txtSenha);

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        ArrayList<String> arrayList = data.get(position);

        holder.nome.setText((arrayList.get(0).toString()));
        holder.senha.setText(arrayList.get(1).toString());

        return convertView;
    }

    public static class ViewHolder
    {
        TextView nome;
        TextView senha;
    }

    public int getCount() {
        return data.size();
    }
}
