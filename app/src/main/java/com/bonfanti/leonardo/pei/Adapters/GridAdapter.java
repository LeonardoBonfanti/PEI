package com.bonfanti.leonardo.pei.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bonfanti.leonardo.pei.R;

/**
 * Created by Usuário on 4/11/2017.
 */

public class GridAdapter extends BaseAdapter
{
    private String name;
    private Context context;
    private LayoutInflater inflater;

    public GridAdapter(Context context, String name)
    {
        this.context = context;
        this.name = name;
    }
    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return name;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View gridView = view;

        if(view == null)
        {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            gridView = inflater.inflate(R.layout.custom_layout, null);
        }

        TextView letter = (TextView) gridView.findViewById(R.id.txtNomeProfessor);
        letter.setText(name);

        return gridView;
    }
}
