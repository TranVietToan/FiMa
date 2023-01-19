package vn.tika.fima.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.tika.fima.Interface.TargetInterface;
import vn.tika.fima.Model.Target;
import vn.tika.fima.R;
import vn.tika.fima.Sqlite.SqliteOpenHelper;

public class ShortTargetAdapter extends ArrayAdapter {
    Activity context;
    int resource;
    List objects;

    View rowItem;
    SqliteOpenHelper sqliteOpenHelper;
    TargetInterface targetInterface;

    TextView titleTarget, contentTarget, timeEndTarget;
    TextView btnAddSubContent_Target;
    ProgressBar progressBarContentTarget;

    String valueType, valueTopic;
    int subContent;

    public ShortTargetAdapter(@NonNull Activity context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        rowItem = layoutInflater.inflate(this.resource, null);
        addControls();
        Target target = (Target) this.objects.get(position);
        titleTarget.setText(target.getTitleTarget());
        contentTarget.setText(String.valueOf(target.getContentTarget()));
        timeEndTarget.setText(target.getTimeEnd_Target());
        int value1 = target.getContentTarget();
        int value2 = target.getSubContentTarget();
        float Progress = ((float) value2*100/value1);
        progressBarContentTarget.setProgress((int) Progress);

        btnAddSubContent_Target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_add_money_shorttarget);
                TextView edtMoney_InputShortTarget = dialog.findViewById(R.id.edt_money_InputShortTarget);

                Spinner spType_InputShortTarget = dialog.findViewById(R.id.sp_type_InputShortTarget);
                ArrayList<String> arrListType_InputShortTarget = new ArrayList<>();
                arrListType_InputShortTarget.add("Tiền mặt");
                arrListType_InputShortTarget.add("Chuyển khoản");
                ArrayAdapter<String> arrAdapterType_InputShortTarget = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, arrListType_InputShortTarget);
                arrAdapterType_InputShortTarget.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spType_InputShortTarget.setAdapter(arrAdapterType_InputShortTarget);

                spType_InputShortTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        valueType = arrListType_InputShortTarget.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                Spinner spTopic_InputShortTarget = dialog.findViewById(R.id.sp_topic_InputShortTarget);
                ArrayList<String> arrListTopic_InputShortTarget = new ArrayList<>();
                arrListTopic_InputShortTarget.add("Thiết yếu");
                arrListTopic_InputShortTarget.add("Giáo dục");
                arrListTopic_InputShortTarget.add("Hưởng thụ");
                arrListTopic_InputShortTarget.add("Đầu tư");
                arrListTopic_InputShortTarget.add("Tiết kiệm");
                arrListTopic_InputShortTarget.add("Từ thiện");
                ArrayAdapter<String> arrAdapterTopic_InputShortTarget = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, arrListTopic_InputShortTarget);
                arrAdapterTopic_InputShortTarget.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spTopic_InputShortTarget.setAdapter(arrAdapterTopic_InputShortTarget);
                spTopic_InputShortTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        valueTopic = arrListTopic_InputShortTarget.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                Button btnConfirmInputShortTarget = dialog.findViewById(R.id.btn_confirm_InputShortTarget);
                btnConfirmInputShortTarget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = target.getTitleTarget();
                        subContent = Integer.valueOf(edtMoney_InputShortTarget.getText().toString());
                        sqliteOpenHelper.queryData("INSERT INTO Spending VALUES(null,'Mục tiêu "+title+"',"+subContent+",'"+valueType+"','"+valueTopic+"','"+title+"','"+getTimeNow()+"')");
                        sqliteOpenHelper.queryData("UPDATE ShortTarget SET SubContentShortTarget="+(Integer.valueOf(target.getSubContentTarget())+subContent)+" WHERE IdShortTarget="+target.getIdTarget());
                        targetInterface.reloadTargetFragment();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return rowItem;
    }

    private void addControls() {
        sqliteOpenHelper = new SqliteOpenHelper(context,"FINANCE.sqlite", null,1);
        targetInterface = (TargetInterface) context;
        titleTarget = rowItem.findViewById(R.id.title_target);
        contentTarget = rowItem.findViewById(R.id.content_target);
        timeEndTarget = rowItem.findViewById(R.id.time_end_target);
        btnAddSubContent_Target = rowItem.findViewById(R.id.btn_add_subcontent_Target);
        progressBarContentTarget = rowItem.findViewById(R.id.progress_bar_target);

    }


    private String getTimeNow() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

}
