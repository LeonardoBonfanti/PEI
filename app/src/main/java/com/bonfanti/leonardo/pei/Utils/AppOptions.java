package com.bonfanti.leonardo.pei.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bonfanti.leonardo.pei.R;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;
import java.util.TimeZone;

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
}
