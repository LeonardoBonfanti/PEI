package com.bonfanti.leonardo.pei.Activity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.bonfanti.leonardo.pei.Adapters.MenuAdapter;
import com.bonfanti.leonardo.pei.R;
import com.bonfanti.leonardo.pei.Utils.AppOptions;
import com.bonfanti.leonardo.pei.Utils.MenuInit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    private List<MenuInit> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_content);

        getWindow().getDecorView().setSystemUiVisibility(AppOptions.getUiOptions());
        AppOptions.UiChangeListener(getWindow().getDecorView());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new MenuAdapter(this, albumList);

        int displayWidth = getWindowManager().getDefaultDisplay().getWidth();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(displayWidth/6), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums()
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

        a = new MenuInit("Realizar Teste", covers[2]);
        albumList.add(a);

        a = new MenuInit("Níveis da Escrita", covers[3]);
        albumList.add(a);

        a = new MenuInit("Podcast", covers[4]);
        albumList.add(a);

        a = new MenuInit("Página Web", covers[5]);
        albumList.add(a);

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
                    outRect.top = spacing - spacing/2;

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
}

