package com.bonfanti.leonardo.pei.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bonfanti.leonardo.pei.Utils.UserDetails;

import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter;

/**
 * Created by Usuário on 4/26/2017.
 */

public class CarTableDataAdapter extends LongPressAwareTableDataAdapter<UserDetails> {

    private static final int TEXT_SIZE = 14;


    public CarTableDataAdapter(final Context context, final List<UserDetails> data, final TableView<UserDetails> tableView) {
        super(context, data, tableView);
    }

    @Override
    public View getDefaultCellView(int rowIndex, int columnIndex, ViewGroup parentView)
    {
        final UserDetails userDetails = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex)
        {
            case 0:
                renderedView = renderCatName(userDetails);
                break;
            case 1:
                renderedView = renderCatProf(userDetails);
                break;
            case 2:
                renderedView = renderCatTest(userDetails);
                break;
            case 3:
                renderedView = renderCatData(userDetails);
                break;
            case 4:
                renderedView = renderCatResult(userDetails);
                break;
        }

        return renderedView;
    }

    @Override
    public View getLongPressCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final UserDetails userDetails = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 1:
                renderedView = renderEditableCatName(userDetails);
                break;
            default:
                renderedView = getDefaultCellView(rowIndex, columnIndex, parentView);
        }

        return renderedView;
    }

    private View renderEditableCatName(final UserDetails userDetails)
    {
        final EditText editText = new EditText(getContext());
        editText.setText(userDetails.getName());
        editText.setPadding(20, 10, 20, 10);
        editText.setTextSize(TEXT_SIZE);
        editText.setSingleLine();
        editText.addTextChangedListener(new CarNameUpdater(userDetails));
        return editText;
    }

    private View renderCatName(final UserDetails userDetails) {
        return renderString(userDetails.getName());
    }

    private View renderCatProf(final UserDetails userDetails) {
        return renderString(userDetails.getProf());
    }

    private View renderCatTest(final UserDetails userDetails) {
        return renderString(userDetails.getTest());
    }

    private View renderCatData(final UserDetails userDetails) {
        return renderString(userDetails.getData());
    }

    private View renderCatResult(final UserDetails userDetails) {
        return renderString(userDetails.getResult());
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

        private UserDetails userDetailsToUpdate;

        public CarNameUpdater(UserDetails userDetailsToUpdate) {
            this.userDetailsToUpdate = userDetailsToUpdate;
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
            userDetailsToUpdate.setName(s.toString());
        }
    }

}
