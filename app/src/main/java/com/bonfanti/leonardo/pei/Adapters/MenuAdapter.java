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
            case "Salas de Teste": {
                Intent intent = new Intent(mContext, TelaSalas.class);
                mContext.startActivity(intent);
                break;
            }
            case "Níveis da Escrita": {

                String mount = "";

                mount = "Nível 1 - Escrever é reproduzir os traços típicos da escrita\n\n";
                mount += "Escrever é reproduzir os traços típicos da escrita que a criança identifica como a forma básica da mesma.\n\n";
                mount += "Se esta forma básica é a escrita de imprensa, teremos grafismos separados entre si, compostos de linhas curvas\n";
                mount += "e repostas ou combinações entre ambas. Se a forma básica é a cursiva, teremos grafismos ligados entre si\n";
                mount += "com uma linha ondulada como forma de base, na qual se inserem curvas fechadas ou semifechadas.\n\n";
                mount += "A criança espera que a escrita dos nomes de pessoas seja proporcional ao tamanho (ou idade) dessa pessoa,\n";
                mount += "e não ao comprimento do nome correspondente. Estes dados e outros recolhidos nos mais diversos contextos\n";
                mount += "evidência uma tendência da criança refletir na escrita algumas das características do objeto. (p.194)\n\n";
                mount += "A correspondência se estabelece entre aspectos quantificáveis do objeto e aspectos quantificáveis\n";
                mount += "da escrita, e não entre um aspecto figural do objeto e aspecto figural do escrito. Isto é, não se buscam\n";
                mount += "letras com ângulos marcados para escrever “casa”, ou letras redondas para escrever “bola”, mas sim um\n";
                mount += "maior número de grafias, grafias maiores ou maior comprimento do traçado total se o objeto é maior,\n";
                mount += "mais comprido, tem mais idade ou há maior número de objetos referidos. (p.198)\n\n";
                mount += "Referencial teórico - Psicogênese da Língua Escrita, Emilia Ferreiro e Ana Teberosky, 1999, Ed. Artmed\n\n";

                mount += "Nível 2 - Para ler coisas diferentes, deve haver diferença objetiva nas escritas\n\n";
                mount += "Para poder ler coisas diferentes, deve haver uma diferença objetiva nas escritas. Segue-se com a hipótese\n";
                mount += "de que faz falta uma certa quantidade mínima de grafismos para escrever algo e com a hipótese da variedade\n";
                mount += "de grafismos. (p. 202)\n\nA combinação utiliza duas razões: primeiro começa sempre pela mesma letra; segundo\n";
                mount += "não tem nenhum método próximo para comparar entre si que não sejam espacialmente próximas (em outras palavras,\n";
                mount += "para saber quais das combinações possíveis já foram realizadas). Porém, a intenção de usar as permutas na ordem\n";
                mount += "linear para expressar diferenças de significado, mantendo constante a quantidade e a exigência de variedade, é\n";
                mount += "indubitável. (p. 204) (ex de sequência)\n\nOutra possibilidade é explorar a permuta de foram linear, com um\n";
                mount += "registro de formas gráficas extremamente limitado. (p. 204) (ex de sequência)\n\n";
                mount += "Tratando de resolver os problemas que a escrita lhe apresenta, as crianças enfrentam, necessariamente, problemas\n";
                mount += "gerais de classificação e de ordenação. Descobrir que duas ordens diferentes dos mesmos elementos possam dar\n";
                mount += "lugar a duas totalidades diferentes é uma descoberta que terá enormes consequências para o desenvolvimento\n";
                mount += "cognitivo nos mais variados domínios em que se exerça a atividade de pensar.\n\n";
                mount += "No curso deste desenvolvimento, a aquisição de formas fixas está sujeita a contingências culturais e pessoais,\n";
                mount += "sendo o nome próprio uma das mais importantes. Para poder ler coisas diferentes, deve haver uma diferença objetiva\n";
                mount += "nas escritas. Segue-se com a hipótese de que faz falta uma certa quantidade mínima de grafismos para escrever algo\n";
                mount += "e com a hipótese da variedade de grafismos. (p. 202)\n\n";
                mount += "Neste nível a criança trata de respeitar duas exigências, a seu ver básicas, que são a quantidade de grafias\n";
                mount += "(nunca menor que 3) e a variedade de grafias.\n\n";
                mount += "Referencial teórico - Psicogênese da Língua Escrita, Emilia Ferreiro e Ana Teberosky, 1999, Ed. Artmed\n\n";

                mount += "Nível 3 - Atribuir um valor sonoro a cada uma das letras que compõem uma escrita\n\n";
                mount += "Nesta tentativa, a criança passa por um período da maior importância evolutiva: a cada letra vale por uma sílaba.\n";
                mount += "Neste nível a criança dá um salto qualitativo com respeito com respeito aos níveis precedentes. É o surgimento do\n";
                mount += "que chamaremos a hipótese silábicas. Com esta hipótese, a criança dá um salto qualitativo com respeito aos níveis\n";
                mount += "precedentes. A mudança qualitativa consiste em que: a) se supera a etapa de uma correspondência global entre a\n";
                mount += "forma escrita e a expressão oral atribuída, para passar a uma correspondência entre partes do texto (cada letra)\n";
                mount += "e partes da expressão oral (recorte silábico do nome); mas além disso, b) pela primeira vez a criança trabalha\n";
                mount += "claramente com a hipótese de que a escrita representa partes sonoras da fala. (p.209)\n\n";
                mount += "Quando a criança começa a trabalhar com a hipótese silábica, duas das características importantes da escrita\n";
                mount += "anterior podem desaparecer momentaneamente: as exigências de variedade e de quantidade mínima de caracteres.\n";
                mount += "Assim, é possível ver aparecer novamente caracteres idênticos (por certo, quando ainda não há valor sonoro\n";
                mount += "estável para cada um deles) no momento em que a criança demasiado ocupada em efetuar um recorte silábico da\n";
                mount += "palavra, não consegue atender simultaneamente em ambas as exigências. Porém, uma vez já bem instalada a hipótese\n";
                mount += "silábica, a exigência de variedade reaparece.\n\n";
                mount += "No que diz respeito ao conflito entre a quantidade mínima de caracteres e a hipótese silábica, o problema é ainda\n";
                mount += "mais interessante, em virtude de suas consequências. Trabalhando com a hipótese silábica, a criança está obrigada a\n";
                mount += "escrever somente duas grafias para as palavras dissílabas (o que, em muitos casos, está abaixo da quantidade mínima\n";
                mount += "que lhe parece necessária), e o problema é ainda mais grave quando se trata de substantivos monossílabos (pouco\n";
                mount += "frequente em espanhol, ainda que “sol” e “sal” constituam conhecidos exemplos das palavras iniciais na aprendizagem\n";
                mount += "tradicional).\n\nReferencial teórico - Psicogênese da Língua Escrita, Emilia Ferreiro e Ana Teberosky, 1999, Ed. Artmed\n\n";

                mount += "Nível 4 - Passagem da hipótese silábica para a alfabética\n\n";
                mount += "A criança abandona a hipótese silábica e descobre a necessidade de fazer uma análise que vá \"mais além\" da sílaba\n";
                mount += "pelo conflito entre a hipótese silábica e a exigência de quantidade mínima de gramas (ambas exigências puramente\n";
                mount += "internas, no sentido de serem hipóteses originais da criança) e o conflito entre as formas gráficas  que o meio lhe\n";
                mount += "propõe e a leitura dessas formas em termos de hipótese silábica (conflito entre uma exigência interna e uma realidade\n";
                mount += "exterior ao próprio sujeito).\n\n";
                mount += "Referencial teórico - Psicogênese da Língua Escrita, Emilia Ferreiro e Ana Teberosky, 1999, Ed. Artmed\n";

                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("Hipóteses")
                        .setMessage(mount)
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
            case "YouTube": {
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
