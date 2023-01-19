package vn.tika.fima.Adapter;

import android.app.Dialog;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.tika.fima.Interface.TargetInterface;
import vn.tika.fima.Model.Target;
import vn.tika.fima.R;
import vn.tika.fima.Sqlite.SqliteOpenHelper;

public class LongTargetAdapter extends RecyclerView.Adapter<LongTargetAdapter.ViewHolder>{
    ArrayList<Target> arrTarget;
    Context context;
    SqliteOpenHelper sqliteOpenHelper;
    TargetInterface targetInterface;

    String valueType;
    int Money;
    int i=0;

    public LongTargetAdapter(ArrayList<Target> arrTarget, Context context) {
        this.arrTarget = arrTarget;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.layout_item_recyclerview_longtarget,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Target target = arrTarget.get(position);
        holder.titleLongTarget.setText(target.getTitleTarget());
        int content = target.getContentTarget();
        int subContent = target.getSubContentTarget();
        float Progress = ((float) subContent*100/content);
        holder.progressBarLongTarget.setProgress((int) Progress);

        holder.titleLongTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_add_money_longtarget);
                TextView edtMoney_InputLongTarget = dialog.findViewById(R.id.edt_money_InputLongTarget);
                Spinner spType_InputLongTarget = dialog.findViewById(R.id.sp_type_InputLongTarget);
                ArrayList<String> arrListType_InputLongTarget = new ArrayList<>();
                arrListType_InputLongTarget.add("Tiền mặt");
                arrListType_InputLongTarget.add("Chuyển khoản");
                ArrayAdapter<String> arrAdapterType_InputLongTarget = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, arrListType_InputLongTarget);
                arrAdapterType_InputLongTarget.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spType_InputLongTarget.setAdapter(arrAdapterType_InputLongTarget);

                spType_InputLongTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        valueType = arrListType_InputLongTarget.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                Button btnConfirmInputLongTarget = dialog.findViewById(R.id.btn_confirm_InputLongTarget);
                btnConfirmInputLongTarget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = target.getTitleTarget();
                        Money = Integer.valueOf(edtMoney_InputLongTarget.getText().toString());
                        sqliteOpenHelper.queryData("INSERT INTO Spending VALUES(null,'Mục tiêu "+title+"',"+Money+",'"+valueType+"','Tiết kiệm','"+title+"','"+getTimeNow()+"')");
                        sqliteOpenHelper.queryData("UPDATE LongTarget SET SubContentLongTarget="+target.getSubContentTarget()+Money+" WHERE IdLongTarget="+(target.getIdTarget()));
                        targetInterface.reloadTargetFragment();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    private String getTimeNow() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    @Override
    public int getItemCount() {
        return arrTarget.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBarLongTarget;
        TextView titleLongTarget;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            targetInterface = (TargetInterface) context;
            sqliteOpenHelper = new SqliteOpenHelper(context,"FINANCE.sqlite", null,1);
            progressBarLongTarget = itemView.findViewById(R.id.progress_bar_LongTarget);
            titleLongTarget = itemView.findViewById(R.id.title_LongTarget);

        }
    }
}
