package vn.tika.fima.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.zip.Inflater;

import vn.tika.fima.Model.Income;
import vn.tika.fima.R;

public class IncomeAdapter extends ArrayAdapter {
    Activity context;
    int resource;
    List objects;
    public IncomeAdapter(@NonNull Activity context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View rowItem = layoutInflater.inflate(this.resource, null);
        TextView titleIncome = rowItem.findViewById(R.id.title_income);
        TextView contentIncome = rowItem.findViewById(R.id.content_income);
        TextView timeIncome = rowItem.findViewById(R.id.time_income);
        TextView typeIncome = rowItem.findViewById(R.id.type_income);
        Income income = (Income) this.objects.get(position);
        titleIncome.setText(income.getTitleIncome());
        contentIncome.setText(String.valueOf(income.getContentIncome()));
        timeIncome.setText(income.getTimeIncome());
        typeIncome.setText(income.getTypeIncome());
        return rowItem;
    }
}
