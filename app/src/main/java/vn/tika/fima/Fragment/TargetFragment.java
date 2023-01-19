package vn.tika.fima.Fragment;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.tika.fima.Adapter.LongTargetAdapter;
import vn.tika.fima.Adapter.ShortTargetAdapter;
import vn.tika.fima.Interface.TargetInterface;
import vn.tika.fima.Model.Target;
import vn.tika.fima.R;
import vn.tika.fima.Sqlite.SqliteOpenHelper;

public class TargetFragment extends Fragment {
    View view_Target;
    SqliteOpenHelper sqliteOpenHelper;
    TargetInterface targetInterface;
    GestureDetector gestureDetector;

    ListView lvData_ShortTarget;
    ShortTargetAdapter shortTargetAdapter;
    ArrayList<Target> shortTargetArrayList;

    RecyclerView rvData_LongTarget;
    LongTargetAdapter longTargetAdapter;
    ArrayList<Target> longTargetArrayList;

    ImageButton ibAddData_ShortTargetData;
    ImageButton ibAddData_LongTarget;
    ImageButton ibUpdateMoney_Target;

    TextView contentStatistical1_Target, contentStatistical2_Target, contentStatistical3_Target;

    RelativeLayout layoutStatistical_Target ;
    FrameLayout frameStatistical1_Target, frameStatistical2_Target, frameStatistical3_Target ;

    int s=10;
    int v= 10;
    int valueVi_1=0, valueVi_2=4, valueVi_3=4;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_Target = inflater.inflate(R.layout.fragment_target, container, false);
        addControls();
        addEvents();
        return view_Target;
    }
    private void addControls() {
        sqliteOpenHelper = new SqliteOpenHelper(getActivity(),"FINANCE.sqlite", null,1);
        targetInterface = (TargetInterface) getActivity();
        lvData_ShortTarget = view_Target.findViewById(R.id.lv_data_ShortTarget);
        ibAddData_ShortTargetData = view_Target.findViewById(R.id.ib_add_data_ShortTarget);
        ibUpdateMoney_Target = view_Target.findViewById(R.id.ib_update_money_Target);
        contentStatistical1_Target = view_Target.findViewById(R.id.content_statistical_1_Target);
        contentStatistical2_Target = view_Target.findViewById(R.id.content_statistical_2_Target);
        contentStatistical3_Target = view_Target.findViewById(R.id.content_statistical_3_Target);
        layoutStatistical_Target = view_Target.findViewById(R.id.layout_statistical_Target);
        frameStatistical1_Target = view_Target.findViewById(R.id.frame_statistical_1_Target);
        frameStatistical2_Target = view_Target.findViewById(R.id.frame_statistical_2_Target);
        frameStatistical3_Target = view_Target.findViewById(R.id.frame_statistical_3_Target);

        rvData_LongTarget = view_Target.findViewById(R.id.rv_data_LongTarget);
        ibAddData_LongTarget = view_Target.findViewById(R.id.ib_add_data_LongTarget);
    }
    private void addEvents() {
        initLvData_ShortTarget();
        initLvData_LongTarget();
        loadDataInLvData_ShortTarget();
        loadDataInRvData_LongTarget();
        loadDataStatistical_Target();
        animationSlide_Target();
        eventOnItemClickLvData_ShortTarget();
        eventOnLongItemClickLvData_ShortTarget();
        eventOnClickIbAddData_ShortTarget();
        eventOnClickIbAddData_LongTarget();
        eventOnclickIbUpdateMoney_Target();
    }


    private void eventOnClickIbAddData_LongTarget() {
        ibAddData_LongTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetInterface.showInputLongTargetData();
            }
        });
    }

    private void eventOnLongItemClickLvData_ShortTarget() {
        lvData_ShortTarget.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_remove_data);
                Button btnCanerRemove = dialog.findViewById(R.id.btn_caner_remove);
                Button btnConfirmRemove = dialog.findViewById(R.id.btn_confirm_remove);
                btnCanerRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnConfirmRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sqliteOpenHelper.queryData("DELETE FROM ShortTarget WHERE IdShortTarget="+shortTargetArrayList.get(position).getIdTarget());
                        targetInterface.reloadTargetFragment();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private void loadDataStatistical_Target() {
        int sum = 0;
        int cash = 0;
        int moneyInCard = 0;
        Cursor cursor_Target = sqliteOpenHelper.getData("SELECT * FROM PersonalAccount");
        while (cursor_Target.moveToNext()){
            sum = sum+ cursor_Target.getInt(0)+ cursor_Target.getInt(1);
        }
        Cursor cursor_Income = sqliteOpenHelper.getData("SELECT * FROM Income");
        while (cursor_Income.moveToNext()){
            sum = sum + cursor_Income.getInt(2);

        }
        Cursor cursor_Spending = sqliteOpenHelper.getData("SELECT * FROM Spending");
        while (cursor_Spending.moveToNext()){
            sum = sum - cursor_Spending.getInt(2);
        }
        contentStatistical1_Target.setText(String.valueOf(sum));

        Cursor cursor_Target2 = sqliteOpenHelper.getData("SELECT * FROM PersonalAccount");
        while (cursor_Target2.moveToNext()){
            cash = cash+ cursor_Target2.getInt(0);
        }
        cash = cash + getDataStatictical_Target("Income", "TypeIncome", "Tiền mặt")-getDataStatictical_Target("Spending", "TypeSpending", "Tiền mặt");
        contentStatistical2_Target.setText(String.valueOf(cash));

        Cursor cursor_Target3 = sqliteOpenHelper.getData("SELECT * FROM PersonalAccount");
        while (cursor_Target3.moveToNext()){
            moneyInCard = moneyInCard + cursor_Target3.getInt(1);
        }
        moneyInCard = moneyInCard + getDataStatictical_Target("Income", "TypeIncome", "Chuyển khoản")-getDataStatictical_Target("Spending", "TypeSpending", "Chuyển khoản");
        contentStatistical3_Target.setText(String.valueOf(moneyInCard));
    }
    private int getDataStatictical_Target(String nameTable, String Type,String valueType){
        int i=0;
        Cursor cursor = sqliteOpenHelper.getData("SELECT * FROM "+nameTable+" WHERE "+Type+" ='"+valueType+"'");
        while (cursor.moveToNext()){
            i = i + cursor.getInt(2);
        }
        return i;
    }

    private void eventOnclickIbUpdateMoney_Target() {
        ibUpdateMoney_Target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_update_money_target);
                EditText edtCash_Target = dialog.findViewById(R.id.edt_cash_Target);
                EditText edtMoneyInCard_Target = dialog.findViewById(R.id.edt_money_in_card_Target);
                Button btnConfirmMoney_Target = dialog.findViewById(R.id.btn_confirm_money_Target);
                btnConfirmMoney_Target.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int Cash = Integer.valueOf(edtCash_Target.getText().toString());
                        int MoneyInCard = Integer.valueOf(edtMoneyInCard_Target.getText().toString());
                        sqliteOpenHelper.queryData("INSERT INTO PersonalAccount VALUES("+Cash+","+MoneyInCard+")");
                        dialog.dismiss();
                        targetInterface.reloadTargetFragment();
                    }
                });
                dialog.show();
            }
        });
    }

    private void animationSlide_Target() {
        gestureDetector = new GestureDetector(getActivity(),new MyGes());
        frameStatistical2_Target.setVisibility(View.INVISIBLE);
        frameStatistical3_Target.setVisibility(View.INVISIBLE);
        layoutStatistical_Target.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
               gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });

    }
    class MyGes extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX()-e2.getX()>s && Math.abs(velocityX)>v){

                if(valueVi_1==0){
                    frameStatistical1_Target.setVisibility(View.INVISIBLE);
                    frameStatistical2_Target.setVisibility(View.VISIBLE);
                    valueVi_1 = 4;
                    valueVi_2 = 0;
                    valueVi_3 = 4;
                }
                else if(valueVi_2==0){
                    frameStatistical2_Target.setVisibility(View.INVISIBLE);
                    frameStatistical3_Target.setVisibility(View.VISIBLE);
                    valueVi_1 = 4;
                    valueVi_2 = 4;
                    valueVi_3 = 0;

                }
                else if(valueVi_3==0){
                    frameStatistical3_Target.setVisibility(View.INVISIBLE);
                    frameStatistical1_Target.setVisibility(View.VISIBLE);
                    valueVi_1 = 0;
                    valueVi_2 = 4;
                    valueVi_3 = 4;
                }
            }
            if(e2.getX()-e1.getX()>s && Math.abs(velocityX)>v){
                if(valueVi_1==0){
                    frameStatistical1_Target.setVisibility(View.INVISIBLE);
                    frameStatistical3_Target.setVisibility(View.VISIBLE);
                    valueVi_1 = 4;
                    valueVi_2 = 4;
                    valueVi_3 = 0;

                }
                else if(valueVi_2==0){
                    frameStatistical2_Target.setVisibility(View.INVISIBLE);
                    frameStatistical1_Target.setVisibility(View.VISIBLE);
                    valueVi_1 = 0;
                    valueVi_2 = 4;
                    valueVi_3 = 4;
                }
                else if(valueVi_3==0){
                    frameStatistical3_Target.setVisibility(View.INVISIBLE);
                    frameStatistical2_Target.setVisibility(View.VISIBLE);
                    valueVi_1 = 4;
                    valueVi_2 = 0;
                    valueVi_3 = 4;
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    private void loadDataInLvData_ShortTarget() {
        Cursor cursor = sqliteOpenHelper.getData("SELECT * FROM ShortTarget");
        shortTargetArrayList.clear();
        while (cursor.moveToNext()){
            int ID = cursor.getInt(0);
            String Title = cursor.getString(1);
            int Content = cursor.getInt(2);
            int subContent = cursor.getInt(3);
            String timeStart = cursor.getString(4);
            String timeEnd = cursor.getString(5);

            shortTargetArrayList.add(new Target(ID, Title, Content, subContent, timeStart, timeEnd));
        }
        shortTargetAdapter.notifyDataSetChanged();
    }

    private void eventOnItemClickLvData_ShortTarget() {
       lvData_ShortTarget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                targetInterface.getDataShortTargetUpdate(
                        shortTargetArrayList.get(i).getIdTarget(),
                        shortTargetArrayList.get(i).getTitleTarget(),
                        shortTargetArrayList.get(i).getContentTarget(),
                        shortTargetArrayList.get(i).getSubContentTarget(),
                        shortTargetArrayList.get(i).getTimeStart_Target(),
                        shortTargetArrayList.get(i).getTimeEnd_Target());
            }
        });
    }


    private void loadDataInRvData_LongTarget() {
        Cursor cursor = sqliteOpenHelper.getData("SELECT * FROM LongTarget ");
        longTargetArrayList.clear();
        while (cursor.moveToNext()){
            int ID = cursor.getInt(0);
            String Title = cursor.getString(1);
            int Content = cursor.getInt(2);
            int subContent = cursor.getInt(3);
            String timeStart = cursor.getString(4);
            String timeEnd = cursor.getString(5);
            longTargetArrayList.add(new Target(ID, Title, Content, subContent, timeStart, timeEnd));
        }
        longTargetAdapter.notifyDataSetChanged();
    }
    private void eventOnClickIbAddData_ShortTarget() {
        ibAddData_ShortTargetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetInterface.showInputShortTargetData();
            }
        });
    }

    private void initLvData_ShortTarget() {
        shortTargetArrayList = new ArrayList<>();
        shortTargetAdapter = new ShortTargetAdapter(getActivity(),
                R.layout.layout_item_listview_shorttarget,
                shortTargetArrayList);
        lvData_ShortTarget.setAdapter(shortTargetAdapter);
    }
    private void initLvData_LongTarget() {
        rvData_LongTarget.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rvData_LongTarget.setLayoutManager(linearLayoutManager);
        longTargetArrayList = new ArrayList<>();
        longTargetAdapter = new LongTargetAdapter(longTargetArrayList, getActivity());
        rvData_LongTarget.setAdapter(longTargetAdapter);
    }

}
