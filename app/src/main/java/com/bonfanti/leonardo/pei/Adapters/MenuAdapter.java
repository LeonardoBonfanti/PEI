package com.bonfanti.leonardo.pei.Adapters;

/**
 * Created by Usuário on 6/2/2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.bonfanti.leonardo.pei.Activity.MainActivity;
import com.bonfanti.leonardo.pei.Activity.TelaBlog;
import com.bonfanti.leonardo.pei.Activity.TelaLogin;
import com.bonfanti.leonardo.pei.Activity.TelaPodcast;
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

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
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
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
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
            public void onClick(View v)
            {
                redirect(holder.title.getText().toString());
            }
        });

        holder.myRelative.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                redirect(holder.title.getText().toString());
            }
        });
    }

    public void redirect(String item)
    {
        switch(item)
        {
            case "Sobre o Aplicativo":
                AppOptions.createPopUpAbout(mContext);

                break;

            case "Lista de Alunos": {
                Intent intent = new Intent(mContext, TelaResultados.class);
                mContext.startActivity(intent);
                break;
            }
            case "Realizar Teste": {
                Intent intent = new Intent(mContext, TelaSalas.class);
                mContext.startActivity(intent);
                break;
            }
            case "Níveis da Escrita": {

                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("Hipóteses (USAREI AS PALAVRAS CADERNO E LÁPIS COMO MODELO)")
                        .setMessage("Pré-silábico \n" +
                                "1. Pré-silábico, sem variações quantitativas ou qualitativas dentro da palavra e entre as palavras. O aluno diferencia desenhos (que não podem ser lidos) de “escritos” (que podem ser lidos), mesmo que sejam compostos por grafismos, símbolos ou letras. (ESCREVE COM QUALQUER TIPO DE LETRA SEM SE PREOCUPAR COM O TAMANHO DA PALAVRA OU SONORIDADE DAS LETRAS – MERTSTEDRGHAS (CADERNO) MERTSTEDRGHAS (LÁPIS) )\n" +
                                "A leitura que realiza do escrito é sempre global, com o dedo deslizando por todo o registro escrito. \n" +
                                "2. Pré-silábico com exigência mínima de letras ou símbolos, com variação de caracteres dentro da palavra, mas não entre as palavras. (FAZ O MESMO TIPO DE ESCRITA INDEPENDENTE SE É PARA CADERNO OU LÁPIS, MAS PREOCUPA-SE COM O TAMANHO – MERSCT(CADERNO) MERS(LÁPIS))\n" +
                                "A leitura do escrito é sempre global, com o dedo deslizando por todo o registro escrito. \n" +
                                "3. Pré-silábico com exigência mínima de letras ou símbolos, com variação de caracteres dentro da palavra e entre as palavras (variação qualitativa intrafigural e interfigural, ou seja,quantidade e variedade de letras). Neste nível, o aluno considera que coisas diferentes devem ser escritas de forma diferente. (ADNEO (CADERNO) API (LÁPIS)\n" +
                                "A leitura do escrito continua global, com o dedo deslizando por todo o registro escrito. \n" +
                                "Silábico \n" +
                                "1. Silábico com letras não pertinentes ou sem valor sonoro convencional. Cada letra ou símbolo corresponde a uma sílaba falada, mas o que se escreve ainda não tem correspondência com o som convencional daquela sílaba. A leitura é silabada. (MANO (CADERNO) AP (LÁPIS))\n" +
                                "2. Silábico com vogais pertinentes ou com valor sonoro convencional de vogais. Cada letra corresponde a uma sílaba falada e o que se escreve tem correspondência com o som convencional daquela sílaba, representada pela vogal. A leitura é silabada. (AEO (CADERNO) AI (LAPIS))\n" +
                                "3. Silábico com consoantes pertinentes ou com valor sonoro convencional de consoantes. Cada letra corresponde a uma sílaba falada e o que se escreve tem correspondência com o som convencional daquela sílaba, representada pela consoante. A leitura é silabada. (KDNO (CADERNO) LAIS (LÁPIS)\n" +
                                "4. Silábico com vogais e consoantes pertinentes. Cada letra corresponde a uma sílaba falada e o que se escreve tem correspondência com o som convencional daquela sílaba, representada ora pela vogal, ora pela consoante. A leitura é silabada. (CADNO (CADERNO) LAPI (LÁPIS) \n" +
                                "Silábico-alfabética \n" +
                                "1. Este nível marca a transição do aluno da hipótese silábica para a hipótese alfabética. Ora ele escreve atribuindo a cada sílaba uma letra, ora representando as unidades sonoras menores, os fonemas. (KDENO; CADNO; CADENO (CADERNO) LAIS; APIS; LAPS (LAPIS))\n" +
                                "Alfabético \n" +
                                "1. Alfabético inicial Neste estágio, o aluno já compreendeu o sistema de escrita, entendendo que cada um dos caracteres da palavra corresponde a um valor sonoro menor do que a sílaba. Agora, falta-lhe dominar as convenções ortográficas. CADENO (CADERNO) LAPISS (LAPIS)\n" +
                                "2. Alfabético. Neste estágio, o aluno já compreendeu o sistema de escrita, entendendo que cada um dos caracteres da palavra corresponde a um valor sonoro menor do que a sílaba e também domina as convenções ortográficas. (CADERNO; LAPIS)\n")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                textView.setScroller(new Scroller(mContext));
                textView.setVerticalScrollBarEnabled(true);
                textView.setMovementMethod(new ScrollingMovementMethod());

                break;
            }
            case "Podcast": {
                Intent intent = new Intent(mContext, TelaPodcast.class);
                mContext.startActivity(intent);
                break;
            }

            case "Página Web": {
                Intent intent = new Intent(mContext, TelaBlog.class);
                mContext.startActivity(intent);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
