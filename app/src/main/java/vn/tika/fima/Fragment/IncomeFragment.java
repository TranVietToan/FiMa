package vn.tika.fima.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.tika.fima.Adapter.IncomeAdapter;
import vn.tika.fima.Interface.IncomeInterface;
import vn.tika.fima.MainActivity;
import vn.tika.fima.Model.Income;
import vn.tika.fima.Model.Spending;
import vn.tika.fima.R;
import vn.tika.fima.Sqlite.SqliteOpenHelper;

public class IncomeFragment extends Fragment {
    View view_Income;
    IncomeInterface incomeInterface;
    SqliteOpenHelper sqliteOpenHelper;
    GestureDetector gestureDetector;

    LinearLayout frameTime1_Income, frameTime2_Income;
    CalendarView calFrameTime2_Income;

    ListView lvData_Income;
    IncomeAdapter incomeAdapter;
    ArrayList<Income> incomeArrayList;

    LinearLayout frameFindType1_Income,frameFindType2_Income,frameFindType3_Income;

    RelativeLayout layoutStatistical_Income;
    FrameLayout frameStatistical1_Income,frameStatistical2_Income,frameStatistical3_Income;
    TextView contentStatistical1_Income, contentStatistical2_Income, contentStatistical3_Income;

    ImageButton btnAddData_Income;
    TextView timeNow_Income;

    int s=10;
    int v= 10;
    int valueVi_1=0, valueVi_2=4, valueVi_3=4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view_Income = inflater.inflate(R.layout.fragment_income, container, false);
        addControls();
        addEvents();

        return view_Income;
    }

    private void addControls() {
        sqliteOpenHelper = new SqliteOpenHelper(getActivity(),"FINANCE.sqlite", null,1);
        incomeInterface = (IncomeInterface) getActivity();
        lvData_Income = view_Income.findViewById(R.id.lv_data_Income);
        btnAddData_Income = view_Income.findViewById(R.id.btn_add_data_Income);

        frameTime1_Income = view_Income.findViewById(R.id.frame_time_1_Income);
        frameTime2_Income = view_Income.findViewById(R.id.frame_time_2_Income);
        calFrameTime2_Income = view_Income.findViewById(R.id.cal_frame_time_2_Income);
        timeNow_Income = view_Income.findViewById(R.id.time_now_Income);

        layoutStatistical_Income = view_Income.findViewById(R.id.layout_statistical_Income);
        frameStatistical1_Income = view_Income.findViewById(R.id.frame_statistical_1_Income);
        frameStatistical2_Income = view_Income.findViewById(R.id.frame_statistical_2_Income);
        frameStatistical3_Income = view_Income.findViewById(R.id.frame_statistical_3_Income);
        contentStatistical1_Income = view_Income.findViewById(R.id.content_statistical_1_Income);
        contentStatistical2_Income = view_Income.findViewById(R.id.content_statistical_2_Income);
        contentStatistical3_Income = view_Income.findViewById(R.id.content_statistical_3_Income);

        frameFindType1_Income = view_Income.findViewById(R.id.frame_find_type_1_Income);
        frameFindType2_Income = view_Income.findViewById(R.id.frame_find_type_2_Income);
        frameFindType3_Income = view_Income.findViewById(R.id.frame_find_type_3_Income);

    }
    private void addEvents() {
        initListView_Income();
        getTimeNow();
        loadDataInLvData_Income();
        loadDataStatictiscal_Income();
        eventOnClickBtnAddData_Income();
        eventOnClickLvData_Income();
        eventOnLongClickLvData_Income();
        eventOnClickFrameTime1_Income();
        eventOnClickFrameTime2_Income();
        animationSlide_Income();
        eventFindData_Income();

    }
    private void eventFindData_Income() {
        findData(frameFindType3_Income,"TypeIncome", "Tiền mặt", timeNow_Income.getText().toString().substring(3));
        findData(frameFindType2_Income,"TypeIncome", "Chuyển khoản",timeNow_Income.getText().toString().substring(3));
        frameFindType1_Income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataInLvData_Income();
            }
        });
    }
    private void findData(LinearLayout linearLayout, String property, String value, String timeSelect ){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor =  sqliteOpenHelper.getData("SELECT * FROM Income WHERE "+property+" = '"+value+"'");
                incomeArrayList.clear();
                while (cursor.moveToNext()){
                    int ID = cursor.getInt(0);
                    String Title = cursor.getString(1);
                    int Content = cursor.getInt(2);
                    String Type = cursor.getString(3);
                    String Note = cursor.getString(4);
                    String Time = cursor.getString(5);
                    if(Time.substring(3).equals(timeSelect)){
                        incomeArrayList.add(new Income(ID, Title, Content, Type, Note, Time));
                    }
                }
                incomeAdapter.notifyDataSetChanged();
            }
        });
        incomeAdapter.notifyDataSetChanged();
    }
    private void eventOnClickFrameTime2_Income() {
        calFrameTime2_Income.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Cursor cursor = sqliteOpenHelper.getData("SELECT * FROM Income");
                incomeArrayList.clear();
                int sumMonth=0;
                int Cash = 0;
                int moneyInCard = 0;
                while (cursor.moveToNext()){
                    int ID = cursor.getInt(0);
                    String Title = cursor.getString(1);
                    int Content = cursor.getInt(2);
                    String Type = cursor.getString(3);
                    String Note = cursor.getString(4);
                    String Time = cursor.getString(5);
                    String timeSelect = (month+1)+"/"+year;
                    String Day = String.valueOf(dayOfMonth);
                    if((month)<=8){
                        timeSelect = "0"+timeSelect;
                    }
                    if(Integer.valueOf(Day)<=9){
                        Day = "0"+Day;
                    }
                    if(Time.substring(3).equals(timeSelect)){
                        incomeArrayList.add(new Income(ID, Title, Content, Type, Note, Time));
                        sumMonth= sumMonth + Content;
                        if(Type.equals("Tiền mặt")){
                            Cash = Cash + Content;
                        }else {
                            moneyInCard = moneyInCard + Content;
                        }
                    }
                    timeNow_Income.setText(String.valueOf(Day+"/"+timeSelect));
                }
                contentStatistical1_Income.setText(String.valueOf(sumMonth));
                contentStatistical2_Income.setText(String.valueOf(Cash));
                contentStatistical3_Income.setText(String.valueOf(moneyInCard));
                incomeAdapter.notifyDataSetChanged();
            }
        });

    }

    private String getTimeNow() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String Time= dateFormat.format(Calendar.getInstance().getTime());
        timeNow_Income.setText(Time);
        return Time;
    }

    private void loadDataStatictiscal_Income() {
        int sumMonth=0;
        int Cash = 0;
        int moneyInCard = 0;
        Cursor cursor =  sqliteOpenHelper.getData("SELECT * FROM Income ");
        while (cursor.moveToNext()){
            int content = cursor.getInt(2);
            String time = cursor.getString(5);
            String type = cursor.getString(3);
            if(time.substring(3).equals(getTimeNow().substring(3))){
                sumMonth = sumMonth + content;
            }
            if(type.equals("Tiền mặt")){
                Cash = Cash + content;
            }else {
                moneyInCard = moneyInCard + content;
            }
        }
        contentStatistical1_Income.setText(String.valueOf(sumMonth));
        contentStatistical2_Income.setText(String.valueOf(Cash));
        contentStatistical3_Income.setText(String.valueOf(moneyInCard));
    }

    private void animationSlide_Income() {
        gestureDetector = new GestureDetector(getActivity(),new MyGes_Income());
        frameStatistical2_Income.setVisibility(View.INVISIBLE);
        frameStatistical3_Income.setVisibility(View.INVISIBLE);
        layoutStatistical_Income.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }
    class MyGes_Income extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX()-e2.getX()>s && Math.abs(velocityX)>v){
                if(valueVi_1==0){
                    frameStatistical1_Income.setVisibility(View.INVISIBLE);
                    frameStatistical2_Income.setVisibility(View.VISIBLE);
                    valueVi_1 = 4;
                    valueVi_2 = 0;
                    valueVi_3 = 4;
                }
                else if(valueVi_2==0){
                    frameStatistical2_Income.setVisibility(View.INVISIBLE);
                    frameStatistical3_Income.setVisibility(View.VISIBLE);
                    valueVi_1 = 4;
                    valueVi_2 = 4;
                    valueVi_3 = 0;

                }
                else if(valueVi_3==0){
                    frameStatistical3_Income.setVisibility(View.INVISIBLE);
                    frameStatistical1_Income.setVisibility(View.VISIBLE);
                    valueVi_1 = 0;
                    valueVi_2 = 4;
                    valueVi_3 = 4;
                }
            }
            if(e2.getX()-e1.getX()>s && Math.abs(velocityX)>v){
                if(valueVi_1==0){
                    frameStatistical1_Income.setVisibility(View.INVISIBLE);
                    frameStatistical3_Income.setVisibility(View.VISIBLE);
                    valueVi_1 = 4;
                    valueVi_2 = 4;
                    valueVi_3 = 0;

                }
                else if(valueVi_2==0){
                    frameStatistical2_Income.setVisibility(View.INVISIBLE);
                    frameStatistical1_Income.setVisibility(View.VISIBLE);
                    valueVi_1 = 0;
                    valueVi_2 = 4;
                    valueVi_3 = 4;
                }
                else if(valueVi_3==0){
                    frameStatistical3_Income.setVisibility(View.INVISIBLE);
                    frameStatistical2_Income.setVisibility(View.VISIBLE);
                    valueVi_1 = 4;
                    valueVi_2 = 0;
                    valueVi_3 = 4;
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
    private void eventOnClickFrameTime1_Income() {
        frameTime1_Income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int valueVisibility= Integer.valueOf(frameTime2_Income.getVisibility());
                if(valueVisibility==8){
                    frameTime2_Income.setVisibility(View.VISIBLE);
                }
                else {
                    frameTime2_Income.setVisibility(View.GONE);
                }
            }
        });
    }

    private void eventOnLongClickLvData_Income() {
        lvData_Income.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
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
                        sqliteOpenHelper.queryData("DELETE FROM Income WHERE IdIncome="+incomeArrayList.get(i).getIdIncome());
                        incomeInterface.reloadIncomeFragment();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private void loadDataInLvData_Income() {
        Cursor cursor = sqliteOpenHelper.getData("SELECT * FROM Income");
        incomeArrayList.clear();
        while (cursor.moveToNext()){
            int ID = cursor.getInt(0);
            String Title = cursor.getString(1);
            int Content = cursor.getInt(2);
            String Type = cursor.getString(3);
            String Note = cursor.getString(4);
            String Time = cursor.getString(5);
            if(Time.substring(3).equals(timeNow_Income.getText().toString().substring(3))){
                incomeArrayList.add(new Income(ID, Title, Content, Type, Note, Time));
            }
        }
        incomeAdapter.notifyDataSetChanged();
    }

    private void eventOnClickLvData_Income() {
       lvData_Income.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               incomeInterface.getDataIncomeUpdate(incomeArrayList.get(i).getIdIncome(),
                       incomeArrayList.get(i).getTitleIncome(),
                       incomeArrayList.get(i).getContentIncome(),
                       incomeArrayList.get(i).getTypeIncome(),
                       incomeArrayList.get(i).getNoteIncome(),
                       incomeArrayList.get(i).getTimeIncome());
           }
       });
    }

    private void eventOnClickBtnAddData_Income() {
        btnAddData_Income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeInterface.showInputIncomeDataFragment();
            }
        });
    }

    private void initListView_Income() {
        incomeArrayList = new ArrayList<>();
        incomeAdapter = new IncomeAdapter(getActivity(), R.layout.layout_item_listview_income, incomeArrayList);
        lvData_Income.setAdapter(incomeAdapter);
    }

}
