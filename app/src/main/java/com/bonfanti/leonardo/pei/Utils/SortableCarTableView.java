package com.bonfanti.leonardo.pei.Utils;

import android.content.Context;
import android.util.AttributeSet;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Created by Usuário on 4/26/2017.
 */

public class SortableCarTableView extends SortableTableView<UserDetails> {

    public SortableCarTableView(final Context context) {
        this(context, null);
    }

    public SortableCarTableView(final Context context, final AttributeSet attributes) {
        this(context, attributes, android.R.attr.listViewStyle);
    }

    public SortableCarTableView(final Context context, final AttributeSet attributes, final int styleAttributes) {
        super(context, attributes, styleAttributes);

        final SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(context, "PROFESSOR(A)", "NOME", "TESTE", "DATA", "RESULTADO", "RESPOSTAS");
        setHeaderAdapter(simpleTableHeaderAdapter);

        final TableColumnWeightModel tableColumnWeightModel = new TableColumnWeightModel(6);
        tableColumnWeightModel.setColumnWeight(0, 2);
        tableColumnWeightModel.setColumnWeight(1, 2);
        tableColumnWeightModel.setColumnWeight(2, 2);
        tableColumnWeightModel.setColumnWeight(3, 2);
        tableColumnWeightModel.setColumnWeight(4, 2);
        tableColumnWeightModel.setColumnWeight(5, 3);
        setColumnModel(tableColumnWeightModel);

        setColumnComparator(0, CarComparators.getCarProfComparator());
        setColumnComparator(1, CarComparators.getCarNameComparator());
        setColumnComparator(2, CarComparators.getCarTestComparator());
        setColumnComparator(3, CarComparators.getCarDataComparator());
        setColumnComparator(4, CarComparators.getCarResultComparator());
    }
}
