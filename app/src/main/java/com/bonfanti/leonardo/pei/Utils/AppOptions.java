package com.bonfanti.leonardo.pei.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bonfanti.leonardo.pei.R;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;
import java.util.TimeZone;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

/**
 * Created by Usuário on 3/16/2017.
 */

public class AppOptions extends AppCompatActivity
{
    public static final int LIGHT_GREEN = 0;
    public static final int LIGHT_YELLOW = 1;
    public static final int LIGHT_BLUE = 2;
    public static final int LIGHT_PURPLE = 3;
    public static final int LIGHT_RED = 4;
    public static final int MEDIUM_GREEN = 5;
    public static final int MEDIUM_YELLOW = 6;
    public static final int MEDIUM_BLUE = 7;
    public static final int MEDIUM_PURPLE = 8;
    public static final int MEDIUM_RED = 9;
    public static final int HEAVY_GREEN = 10;
    public static final int HEAVY_YELLOW = 11;
    public static final int HEAVY_BLUE = 12;
    public static final int HEAVY_PURPLE = 13;
    public static final int HEAVY_RED = 14;

    public static int getUiOptions()
    {
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

        return uiOptions;
    }

    public static void setLocation(View view, WindowManager manager, RelativeLayout.LayoutParams params, int deslocX, int deslocY)
    {
        params.leftMargin = getDisplaySize(manager).x / 2 + deslocX;
        params.topMargin = getDisplaySize(manager).y / 2 + deslocY;

        view.setLayoutParams(params);
    }

    public static void UiChangeListener(final View deco)
    {
        deco.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    deco.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        });
    }

    public static Point getDisplaySize(WindowManager manager)
    {
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }

    public static Typeface getTypeface(AssetManager manager)
    {
        Typeface myType = Typeface.createFromAsset(manager, "SketchetikLight.ttf");
        return  myType;
    }

    public static void onTapOutsideBehaviour(View view, final Activity myActivity)
    {
        if(!(view instanceof EditText) || !(view instanceof Button))
        {
            view.setOnTouchListener(new View.OnTouchListener()
            {
                public boolean onTouch(View v, MotionEvent event)
                {
                    InputMethodManager inputMethodManager = (InputMethodManager)  myActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(myActivity.getCurrentFocus().getWindowToken(), 0);

                    return false;
                }
            });
        }
    }

    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs)
    {
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

        for (int i = 0; i <= lhs.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= rhs.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= lhs.length(); i++)
            for (int j = 1; j <= rhs.length(); j++)
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));

        return distance[lhs.length()][rhs.length()];
    }

    /* Troca a cor de todos os ícones da Toolbar */
    public static void setOverflowButtonColor(final Toolbar toolbar, final int color)
    {
        Drawable drawable = toolbar.getOverflowIcon();
        if(drawable != null)
        {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), color);
            toolbar.setOverflowIcon(drawable);
        }
    }

    public static void saveData(String result, String sala, String key, String teste, DatabaseReference data)
    {
        DatabaseReference current_user = data.child(key);

        String date = getDateFormated();

        current_user.child("Sala").child(sala).child(date).child(teste).setValue(result);
    }

    private static String getDateFormated()
    {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        String date = String.valueOf(day) + "_" + String.valueOf(month) + "_" + String.valueOf(year);
        date = date + " - " + getTimeFormated();

        return  date;
    }

    private static String getTimeFormated()
    {

        Calendar cal = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("Brazil/East");
        cal.setTimeZone(tz);

        String currentHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String currentMinute = String.valueOf(cal.get(Calendar.MINUTE));
        String currentSecond = String.valueOf(cal.get(Calendar.SECOND));

        return currentHour + ":" + currentMinute + ":" + currentSecond;
    }

    /* RETORNA A COR DESEJADA */
    public static String getBackgroundColor(int color)
    {
        if(color > HEAVY_RED)
            color = color - HEAVY_RED - 1;

        switch (color)
        {
            case LIGHT_GREEN:
            {
                return  "#99FFD1";
            }
            case LIGHT_YELLOW:
            {
                return  "#FFD799";
            }
            case LIGHT_BLUE:
            {
                return  "#99C1FF";
            }
            case LIGHT_PURPLE:
            {
                return  "#C699FF";
            }
            case LIGHT_RED:
            {
                return  "#FF9999";
            }
            case MEDIUM_GREEN:
            {
                return  "#4CFFAE";
            }
            case MEDIUM_YELLOW:
            {
                return  "#FFB74C";
            }
            case MEDIUM_BLUE:
            {
                return  "#4C93FF";
            }
            case MEDIUM_PURPLE:
            {
                return  "#994CFF";
            }
            case MEDIUM_RED:
            {
                return  "#FF4C4C";
            }
            case HEAVY_GREEN:
            {
                return  "#2D9968";
            }
            case HEAVY_YELLOW:
            {
                return  "#996E2D";
            }
            case HEAVY_BLUE:
            {
                return  "#996E2D";
            }
            case HEAVY_PURPLE:
            {
                return  "#5A2D96";
            }
            case HEAVY_RED:
            {
                return  "#992D2D";
            }
        }

        return "";
    }

    public static void createPopUpAbout(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(AppOptions.getAbout()).setTitle("Hipótese da Escrita");
        builder.setIcon(R.drawable.icon_about);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    static private String getAbout()
    {
        String about = "        A criança desde de muito cedo, por volta de 2 anos e meio ou 3 anos, é capaz de tornar-se um produtor de textos. ";
        about += "Habituada desde pequena a fazer uso de lápis e papel, registra suas primeiras tentativas de escrever, diferentemente das tentativas de desenhar.\n\n";
        about += "      Sendo assim esse aplicativo tem o intuito de auxiliar educadores na tarefa de identificar o nível hipotético de escrita em que a criança encontra-se ";
        about += "de acordo com a fase de alfabetização em que está inserida.";

        return about;
    }

    static public void getResultDescription(Context context, TextView textView, String result)
    {
        String description = mountText(result);

        new SimpleTooltip.Builder(context)
                .anchorView(textView)
                .text(description)
                .gravity(Gravity.START)
                .animated(false)
                .transparentOverlay(true)
                .arrowColor(Color.parseColor("#99C1FF"))
                .backgroundColor(Color.parseColor("#99C1FF"))
                .build()
                .show();
    }

    static private String mountText(String result)
    {
        String mount = "";

        switch (result)
        {
            case "PRÉ-SILÁBICA":

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
                mount += "Referencial teórico - Psicogênese da Língua Escrita, Emilia Ferreiro e Ana Teberosky, 1999, Ed. Artmed";

                break;

            case "SILÁBICA":

                mount = "Nível 2 - Para ler coisas diferentes, deve haver diferença objetiva nas escritas\n\n";
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
                mount += "Referencial teórico - Psicogênese da Língua Escrita, Emilia Ferreiro e Ana Teberosky, 1999, Ed. Artmed";

                break;

            case "SILÁBICO-ALFABÉTICA":

                mount = "Nível 3 - Atribuir um valor sonoro a cada uma das letras que compõem uma escrita\n\n";
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
                mount += "tradicional).\n\nReferencial teórico - Psicogênese da Língua Escrita, Emilia Ferreiro e Ana Teberosky, 1999, Ed. Artmed";

                break;

            case "ALFABÉTICA":

                mount = "Nível 4 - Passagem da hipótese silábica para a alfabética\n\n";
                mount += "A criança abandona a hipótese silábica e descobre a necessidade de fazer uma análise que vá \"mais além\" da sílaba\n";
                mount += "pelo conflito entre a hipótese silábica e a exigência de quantidade mínima de gramas (ambas exigências puramente\n";
                mount += "internas, no sentido de serem hipóteses originais da criança) e o conflito entre as formas gráficas  que o meio lhe\n";
                mount += "propõe e a leitura dessas formas em termos de hipótese silábica (conflito entre uma exigência interna e uma realidade\n";
                mount += "exterior ao próprio sujeito).\n\n";
                mount += "Referencial teórico - Psicogênese da Língua Escrita, Emilia Ferreiro e Ana Teberosky, 1999, Ed. Artmed";

                break;
        }

        return mount;
    }
}
