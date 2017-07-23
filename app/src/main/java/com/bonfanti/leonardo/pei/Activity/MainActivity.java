package com.bonfanti.leonardo.pei.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bonfanti.leonardo.pei.Adapters.MenuAdapter;
import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.FireApp;
import com.bonfanti.leonardo.pei.Utils.MenuInit;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    private List<MenuInit> albumList;

    FirebaseAuth firebaseAuth;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_content);

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new MenuAdapter(this, albumList);

        int displayWidth = getWindowManager().getDefaultDisplay().getWidth();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(displayWidth/20), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_main);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("MENU");
        myToolbar.setTitleTextColor(Color.WHITE);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
        AppOptions.setOverflowButtonColor(myToolbar, Color.WHITE);
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums()
    {
        FireApp fireApp= (FireApp) getApplicationContext();
        int admin = fireApp.getAdmin();
        int professor = fireApp.getProfessor();

        if(admin != 1 && professor != 1)
        {
            int[] covers = new int[]{
                    R.drawable.icon_about,
                    R.drawable.icon_testes_menu};

            MenuInit a = new MenuInit("Sobre o Aplicativo", covers[0]);
            albumList.add(a);

            a = new MenuInit("Salas de Teste", covers[1]);
            albumList.add(a);
        }
        else
        {
            int[] covers = new int[]{
                    R.drawable.icon_about,
                    R.drawable.icon_lista_menu,
                    R.drawable.icon_testes_menu,
                    R.drawable.icon_niveis_menu,
                    R.drawable.icon_podcasts_menu,
                    R.drawable.icon_web_menu};

            MenuInit a = new MenuInit("Sobre o Aplicativo", covers[0]);
            albumList.add(a);

            a = new MenuInit("Lista de Alunos", covers[1]);
            albumList.add(a);

            a = new MenuInit("Salas de Teste", covers[2]);
            albumList.add(a);

            a = new MenuInit("Níveis da Escrita", covers[3]);
            albumList.add(a);

            a = new MenuInit("YouTube", covers[4]);
            albumList.add(a);

            a = new MenuInit("Página Web", covers[5]);
            albumList.add(a);
        }

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration
    {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge)
        {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge)
            {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount)// top edge
                    outRect.top = spacing;

                outRect.bottom = spacing - spacing/2; // item bottom
            }
            else
            {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)

                if (position >= spanCount)
                    outRect.top = spacing; // item top
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp)
    {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent;

        switch (item.getItemId())
        {
            case R.id.action_logout:

                firebaseAuth.signOut();

                intent = new Intent(this, TelaLoginCadastro.class);
                startActivity(intent);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    /* Trigger para mostar devidamente a Toolbar com os elementos criados no xml */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tool_itens, menu);

        MenuItem item;

        item = menu.findItem(R.id.action_add);
        item.setVisible(false);
        item = menu.findItem(R.id.action_resultados);
        item.setVisible(false);

        return true;
    }
}

