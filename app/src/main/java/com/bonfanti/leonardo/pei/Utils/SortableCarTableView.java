package com.bonfanti.leonardo.pei.Utils;

import android.content.Context;
import android.util.AttributeSet;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Created by Usuário on 4/26/2017.
 */

public class SortableCarTableView extends SortableTableView<Car> {

    public SortableCarTableView(final Context context) {
        this(context, null);
    }

    public SortableCarTableView(final Context context, final AttributeSet attributes) {
        this(context, attributes, android.R.attr.listViewStyle);
    }

    public SortableCarTableView(final Context context, final AttributeSet attributes, final int styleAttributes) {
        super(context, attributes, styleAttributes);

        final SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(context, "NOME", "PROFESSOR(A)", "TESTE", "RESULTADO");
        setHeaderAdapter(simpleTableHeaderAdapter);

        final TableColumnWeightModel tableColumnWeightModel = new TableColumnWeightModel(4);
        tableColumnWeightModel.setColumnWeight(0, 2);
        tableColumnWeightModel.setColumnWeight(1, 3);
        tableColumnWeightModel.setColumnWeight(2, 3);
        tableColumnWeightModel.setColumnWeight(3, 2);
        setColumnModel(tableColumnWeightModel);

        setColumnComparator(0, CarComparators.getCarNameComparator());
        setColumnComparator(1, CarComparators.getCarProfComparator());
        setColumnComparator(2, CarComparators.getCarTestComparator());
        setColumnComparator(3, CarComparators.getCarResultComparator());
    }
}
