package com.bonfanti.leonardo.pei.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.FireApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Usuário on 7/22/2017.
 */

public class AlunosAdapter extends ArrayAdapter<String>
{
    Context context;
    ArrayList<ArrayList> data;
    LayoutInflater layoutInflater;

    int checkedItem;

    public AlunosAdapter(Context context, ArrayList<ArrayList> temp)
    {
        super(context, R.layout.row_tela_alunos);
        this.context = context;
        this.data = temp;
        checkedItem = 0;
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
            holder.edit = (Button) convertView.findViewById(R.id.btnEditAluno);

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        ArrayList<String> arrayList = data.get(position);

        holder.nome.setText((arrayList.get(0).toString()));
        holder.senha.setText(arrayList.get(1).toString());

        final ViewHolder finalHolder = holder;
        holder.edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final ArrayList<String> salas = new ArrayList<String>();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Salas");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for(DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                        {
                            String turma = childDataSnapshot.getKey();

                            FireApp fireApp = (FireApp) context.getApplicationContext();

                            if(!turma.equals(fireApp.getUserSala()))
                            {
                                String professor = childDataSnapshot.getValue().toString();
                                salas.add(professor + ": " + turma);
                            }
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Escolha a turma");

                        CharSequence[] cs = salas.toArray(new CharSequence[salas.size()]);

                        builder.setSingleChoiceItems(cs, checkedItem, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                checkedItem = which;
                            }
                        });

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String[] chose = salas.get(checkedItem).split(":");
                                String professor = chose[0];
                                String turma = chose[1].replace(" ", "");

                                trocaAluno(professor, turma, finalHolder.nome.getText().toString(),
                                        finalHolder.senha.getText().toString());
                            }
                        });
                        builder.setNegativeButton("Cancel", null);

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        return convertView;
    }

    private void trocaAluno(final String professor, final String turma, final String aluno, final String senha)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("CadastroAlunos");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                {
                    String nome = childDataSnapshot.getKey();

                    FireApp fireApp = (FireApp) context.getApplicationContext();
                    String userSala = fireApp.getUserSala();

                    for(DataSnapshot childDataSnapshot2 : childDataSnapshot.getChildren())
                    {
                        String key = childDataSnapshot2.getKey();

                        if(key.equals(userSala))
                        {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                    .getReference().child("CadastroAlunos").child(nome).child(key);

                            databaseReference.child(aluno).removeValue();
                        }
                    }

                    if(nome.equals(professor))
                    {
                        for(DataSnapshot childDataSnapshot2 : childDataSnapshot.getChildren())
                        {
                            String key = childDataSnapshot2.getKey();

                            if(key.equals(turma))
                            {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                        .getReference().child("CadastroAlunos").child(nome).child(key);

                                databaseReference.child(aluno).setValue(senha);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static class ViewHolder
    {
        TextView nome;
        TextView senha;
        Button edit;
    }

    public int getCount() {
        return data.size();
    }
}
