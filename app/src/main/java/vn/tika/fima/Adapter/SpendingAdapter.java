package vn.tika.fima.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.tika.fima.Model.Spending;
import vn.tika.fima.R;

public class SpendingAdapter extends ArrayAdapter {
    Activity context;
    int resource;
    List objects;
    public SpendingAdapter(@NonNull Activity context, int resource, @NonNull List objects) {
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
        TextView titleSpending = rowItem.findViewById(R.id.title_spending);
        TextView contentSpending = rowItem.findViewById(R.id.content_spending);
        TextView typeSpending = rowItem.findViewById(R.id.type_spending);
        TextView timeSpending = rowItem.findViewById(R.id.time_spending);
        ImageView topicSpending = rowItem.findViewById(R.id.topic_spending);
        Spending spending = (Spending) this.objects.get(position);
        titleSpending.setText(spending.getTitleSpending());
        contentSpending.setText(String.valueOf(spending.getContentSpending()));
        typeSpending.setText(spending.getTypeSpending());
        timeSpending.setText(spending.getTimeSpending());

        switch (spending.getTopicSpending()) {
            case "Thiết yếu": {
                topicSpending.setImageResource(R.drawable.ic_credit_card_mini);
                break;
            }
            case "Giáo dục": {
                topicSpending.setImageResource(R.drawable.ic_graduation_cap_mini);
                break;
            }
            case "Hưởng thụ": {
                topicSpending.setImageResource(R.drawable.ic_chess_queen_mini);
                break;
            }
            case "Đầu tư": {
                topicSpending.setImageResource(R.drawable.ic_shopping_basket_mini);
                break;
            }
            case "Tiết kiệm": {
                topicSpending.setImageResource(R.drawable.ic_piggy_bank_mini);
                break;
            }
            case "Từ thiện": {
                topicSpending.setImageResource(R.drawable.ic_hand_holding_heart_mini);
                break;
            }
            default: {

            }
        }
        return rowItem;
    }
}
