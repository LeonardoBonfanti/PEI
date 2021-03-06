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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;
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

    public static void saveData(String result, String sala, String key, String teste, DatabaseReference data, List<String> respostas)
    {
        DatabaseReference current_user = data.child(key);

        String date = getDateFormated();

        if(teste.equals("UM"))
        {
            current_user.child("Sala").child(sala).child(date).child(teste).child("APONTADOR").setValue(respostas.get(0));
            current_user.child("Sala").child(sala).child(date).child(teste).child("CADERNO").setValue(respostas.get(1));
            current_user.child("Sala").child(sala).child(date).child(teste).child("QUADRO").setValue(respostas.get(2));
            current_user.child("Sala").child(sala).child(date).child(teste).child("COLA").setValue(respostas.get(3));
        }
        else if(teste.equals("DOIS"))
        {
            current_user.child("Sala").child(sala).child(date).child(teste).child("CALENDARIO").setValue(respostas.get(0));
            current_user.child("Sala").child(sala).child(date).child(teste).child("TESOURA").setValue(respostas.get(1));
            current_user.child("Sala").child(sala).child(date).child(teste).child("REGUA").setValue(respostas.get(2));
            current_user.child("Sala").child(sala).child(date).child(teste).child("LAPIS").setValue(respostas.get(3));
            current_user.child("Sala").child(sala).child(date).child(teste).child("GIZ").setValue(respostas.get(4));
        }
        else if(teste.equals("TRÊS"))
            current_user.child("Sala").child(sala).child(date).child(teste).child("A MENINA DESENHA UMA CASA").setValue(respostas.get(0));
        else if(teste.equals("QUATRO"))
            current_user.child("Sala").child(sala).child(date).child(teste).child("O MENINO DESENHA NO CADERNO").setValue(respostas.get(0));

        current_user.child("Sala").child(sala).child(date).child(teste).child("Resultado").setValue(result);
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

        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTextSize(20);
    }

    static private String getAbout()
    {
        String about = "        A criança desde de muito cedo, por volta de 2 anos e meio ou 3 anos, é capaz de tornar-se um produtor de textos. ";
        about += "Habituada desde pequena a fazer uso de lápis e papel, registra suas primeiras tentativas de escrever, diferentemente das tentativas de desenhar.\n\n";
        about += "      Sendo assim esse aplicativo tem o intuito de auxiliar educadores na tarefa de identificar o nível hipotético de escrita em que a criança encontra-se ";
        about += "de acordo com a fase de alfabetização em que está inserida.";

        return about;
    }

    static public void getUserAnswers(Context context, TextView textView, String teste)
    {

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

                mount = "1. Pré-silábico, sem variações quantitativas ou qualitativas dentro da palavra e entre as palavras.\n\n";
                mount += "O aluno diferencia desenhos (que não podem ser lidos) de “escritos” (que podem ser lidos), mesmo que sejam\n";
                mount += "compostos por grafismos, símbolos ou letras. (ESCREVE COM QUALQUER TIPO DE LETRA SEM SE PREOCUPAR COM O\n";
                mount += " TAMANHO DA PALAVRA OU SONORIDADE DAS LETRAS – MERTSTEDRGHAS (CADERNO) MERTSTEDRGHAS (LÁPIS))\n";
                mount += "A leitura que realiza do escrito é sempre global, com o dedo deslizando por todo o registro escrito.\n\n";
                mount += "2. Pré-silábico com exigência mínima de letras ou símbolos, com variação de caracteres dentro da palavra,\n";
                mount += "mas não entre as palavras.\n(FAZ O MESMO TIPO DE ESCRITA INDEPENDENTE SE É PARA CADERNO OU LÁPIS, MAS PREOCUPA-SE\n";
                mount += "COM O TAMANHO – MERSCT(CADERNO) MERS(LÁPIS))\n\n";
                mount += "A leitura do escrito é sempre global, com o dedo deslizando por todo o registro escrito.\n\n";
                mount += "3. Pré-silábico com exigência mínima de letras ou símbolos, com variação de caracteres dentro da\n";
                mount += "palavra e entre as palavras (variação qualitativa intrafigural e interfigural, ou seja,quantidade\n";
                mount += "e variedade de letras)\n\nNeste nível, o aluno considera que coisas diferentes devem ser escritas de forma diferente.\n";
                mount += "(ADNEO (CADERNO) API (LÁPIS)\nA leitura do escrito continua global, com o dedo deslizando por todo o registro escrito.\n\n";

                break;

            case "SILÁBICA":

                mount = "1. Silábico com letras não pertinentes ou sem valor sonoro convencional.\n\n";
                mount += "Cada letra ou símbolo corresponde a uma sílaba falada, mas o que se escreve ainda não tem correspondência\n";
                mount += "com o som convencional daquela sílaba. A leitura é silabada. (MANO (CADERNO) AP (LÁPIS))\n\n";
                mount += "2. Silábico com vogais pertinentes ou com valor sonoro convencional de vogais.\n\n";
                mount += "Cada letra corresponde a uma sílaba falada e o que se escreve tem correspondência com o som convencional\n";
                mount += "daquela sílaba, representada pela vogal. A leitura é silabada. (AEO (CADERNO) AI (LAPIS))\n\n";
                mount += "3. Silábico com consoantes pertinentes ou com valor sonoro convencional de consoantes.\n\n";
                mount += "Cada letra corresponde a uma sílaba falada e o que se escreve tem correspondência com o som convencional\n";
                mount += "daquela sílaba, representada pela consoante. A leitura é silabada. (KDNO (CADERNO) LAIS (LÁPIS)\n\n";
                mount += "4. Silábico com vogais e consoantes pertinentes.\n\n";
                mount += " Cada letra corresponde a uma sílaba falada e o que se escreve tem correspondência com o som convencional\n";
                mount += "daquela sílaba, representada ora pela vogal, ora pela consoante. A leitura é silabada.\n(CADNO (CADERNO) LAPI (LÁPIS)\n\n";

                break;

            case "SILÁBICO-ALFABÉTICA":

                mount = "1. Silábico-Alfabético\n\n";
                mount = "Este nível marca a transição do aluno da hipótese silábica para a hipótese alfabética.\n";
                mount += "Ora ele escreve atribuindo a cada sílaba uma letra, ora representando as unidades sonoras menores,\n";
                mount += "os fonemas. (KDENO; CADNO; CADENO (CADERNO) LAIS; APIS; LAPS (LAPIS))\n\n";

                break;

            case "ALFABÉTICA":

                mount = "1. Alfabético inicial\n\n";
                mount += "Neste estágio, o aluno já compreendeu o sistema de escrita, entendendo que cada um dos caracteres da\n";
                mount += "palavra corresponde a um valor sonoro menor do que a sílaba. Agora, falta-lhe dominar as convenções\n";
                mount += "ortográficas. CADENO (CADERNO) LAPISS (LAPIS)\n\n";
                mount += "2. Alfabético\n\n";
                mount += "Neste estágio, o aluno já compreendeu o sistema de escrita, entendendo que cada um dos caracteres\n";
                mount += "da palavra corresponde a um valor sonoro menor do que a sílaba e também domina as convenções ortográficas.\n";
                mount += "(CADERNO; LAPIS)\n\n";

                break;
        }

        return mount;
    }
}
