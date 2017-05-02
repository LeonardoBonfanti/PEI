package com.bonfanti.leonardo.pei.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bonfanti.leonardo.pei.Utils.Car;

import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter;

/**
 * Created by Usuário on 4/26/2017.
 */

public class CarTableDataAdapter extends LongPressAwareTableDataAdapter<Car> {

    private static final int TEXT_SIZE = 14;


    public CarTableDataAdapter(final Context context, final List<Car> data, final TableView<Car> tableView) {
        super(context, data, tableView);
    }

    @Override
    public View getDefaultCellView(int rowIndex, int columnIndex, ViewGroup parentView)
    {
        final Car car = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex)
        {
            case 0:
                renderedView = renderCatName(car);
                break;
            case 1:
                renderedView = renderCatProf(car);
                break;
            case 2:
                renderedView = renderCatTest(car);
                break;
            case 3:
                renderedView = renderCatResult(car);
                break;
        }

        return renderedView;
    }

    @Override
    public View getLongPressCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final Car car = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 1:
                renderedView = renderEditableCatName(car);
                break;
            default:
                renderedView = getDefaultCellView(rowIndex, columnIndex, parentView);
        }

        return renderedView;
    }

    private View renderEditableCatName(final Car car)
    {
        final EditText editText = new EditText(getContext());
        editText.setText(car.getName());
        editText.setPadding(20, 10, 20, 10);
        editText.setTextSize(TEXT_SIZE);
        editText.setSingleLine();
        editText.addTextChangedListener(new CarNameUpdater(car));
        return editText;
    }

    private View renderCatName(final Car car) {
        return renderString(car.getName());
    }

    private View renderCatProf(final Car car) {
        return renderString(car.getProf());
    }

    private View renderCatTest(final Car car) {
        return renderString(String.valueOf(car.getTest()));
    }

    private View renderCatResult(final Car car) {
        return renderString(car.getResult());
    }

    private View renderString(final String value)
    {
        final TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        return textView;
    }

    private static class CarNameUpdater implements TextWatcher {

        private Car carToUpdate;

        public CarNameUpdater(Car carToUpdate) {
            this.carToUpdate = carToUpdate;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // no used
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // not used
        }

        @Override
        public void afterTextChanged(Editable s) {
            carToUpdate.setName(s.toString());
        }
    }

}
