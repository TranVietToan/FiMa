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
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.tika.fima.Adapter.SpendingAdapter;
import vn.tika.fima.Interface.SpendingInterface;
import vn.tika.fima.MainActivity;
import vn.tika.fima.Model.Income;
import vn.tika.fima.Model.Spending;
import vn.tika.fima.R;
import vn.tika.fima.Sqlite.SqliteOpenHelper;

public class SpendingFragment extends Fragment {
    View view_Spending;
    SpendingInterface spendingInterface;
    SqliteOpenHelper sqliteOpenHelper;
    GestureDetector gestureDetector;

    LinearLayout frameTime1_Spending, frameTime2_Spending;
    CalendarView calFrameTime2_Spending;

    LinearLayout frameFindType4_Spending, frameFindTopic7_Spending;
    HorizontalScrollView layoutFindTopic_Spending;
    LinearLayout layoutFindType_Spending;

    LinearLayout frameFindType1_Spending,frameFindType2_Spending,frameFindType3_Spending;
    LinearLayout  frameFindTopic1_Spending,  frameFindTopic2_Spending,  frameFindTopic3_Spending;
    LinearLayout  frameFindTopic4_Spending,  frameFindTopic5_Spending,  frameFindTopic6_Spending;

    ListView lvData_Spending;
    SpendingAdapter spendingAdapter;
    ArrayList<Spending> spendingArrayList;

    ImageButton btnAddData_Spending;
    TextView timeNow_Spending;

    RelativeLayout layoutStatistical_Spending;
    FrameLayout frameStatistical1_Spending,frameStatistical2_Spending,frameStatistical3_Spending;
    TextView contentStatistical1_Spending, contentStatistical2_Spending, contentStatistical3_Spending;

    int s=10;
    int v= 10;
    int valueVi_1=0, valueVi_2=4, valueVi_3=4;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_Spending = inflater.inflate(R.layout.fragment_spending, container, false);
        addControls();
        addEvents();
        return view_Spending;
    }
    private void addControls() {
        sqliteOpenHelper = new SqliteOpenHelper(getActivity(),"FINANCE.sqlite", null,1);
        spendingInterface = (SpendingInterface) getActivity();
        lvData_Spending = view_Spending.findViewById(R.id.lv_data_Spending);
        btnAddData_Spending = view_Spending.findViewById(R.id.btn_add_data_Spending);
        timeNow_Spending = view_Spending.findViewById(R.id.time_now_Spending);

        frameTime1_Spending = view_Spending.findViewById(R.id.frame_time_1_Spending);
        frameTime2_Spending = view_Spending.findViewById(R.id.frame_time_2_Spending);
        calFrameTime2_Spending = view_Spending.findViewById(R.id.cal_frame_time_2_Spending);

        layoutStatistical_Spending = view_Spending.findViewById(R.id.layout_statistical_Spending);
        frameStatistical1_Spending = view_Spending.findViewById(R.id.frame_statistical_1_Spending);
        frameStatistical2_Spending = view_Spending.findViewById(R.id.frame_statistical_2_Spending);
        frameStatistical3_Spending = view_Spending.findViewById(R.id.frame_statistical_3_Spending);
        contentStatistical1_Spending = view_Spending.findViewById(R.id.content_statistical_1_Spending);
        contentStatistical2_Spending = view_Spending.findViewById(R.id.content_statistical_2_Spending);
        contentStatistical3_Spending = view_Spending.findViewById(R.id.content_statistical_3_Spending);


        frameFindType1_Spending = view_Spending.findViewById(R.id.frame_find_type_1_Spending);
        frameFindType2_Spending = view_Spending.findViewById(R.id.frame_find_type_2_Spending);
        frameFindType3_Spending = view_Spending.findViewById(R.id.frame_find_type_3_Spending);
        frameFindType4_Spending = view_Spending.findViewById(R.id.frame_find_type_4_Spending);

        frameFindTopic1_Spending = view_Spending.findViewById(R.id.frame_find_topic_1_Spending);
        frameFindTopic2_Spending = view_Spending.findViewById(R.id.frame_find_topic_2_Spending);
        frameFindTopic3_Spending = view_Spending.findViewById(R.id.frame_find_topic_3_Spending);
        frameFindTopic4_Spending = view_Spending.findViewById(R.id.frame_find_topic_4_Spending);
        frameFindTopic5_Spending = view_Spending.findViewById(R.id.frame_find_topic_5_Spending);
        frameFindTopic6_Spending = view_Spending.findViewById(R.id.frame_find_topic_6_Spending);
        frameFindTopic7_Spending = view_Spending.findViewById(R.id.frame_find_topic_7_Spending);

        layoutFindTopic_Spending = view_Spending.findViewById(R.id.layout_find_topic_Spending);
        layoutFindType_Spending = view_Spending.findViewById(R.id.layout_find_type_Spending);
    }

    private void addEvents() {
        initListView_Spending();
        getTimeNow();
        animationSlide_Spending();
        loadDataInLvData_Spending();
        loadDataStatistical_Spending();
        eventOnclickLvData_Spending();
        eventOnClickBtnAddData_Spending();
        eventOnclickLvLongData_Spending();
        eventOnClickFrameTime1_Spending();
        eventOnClickFrameTime2_Income();
        eventLoadLayoutFind_Spending();
        eventFindData_Spending();
    }

    private void loadDataStatistical_Spending() {
        int sumMonth=0;
        int Cash = 0;
        int moneyInCard = 0;
        Cursor cursor =  sqliteOpenHelper.getData("SELECT * FROM Spending ");
        while (cursor.moveToNext()){
            int content = cursor.getInt(2);
            String time = cursor.getString(6);
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
        contentStatistical1_Spending.setText(String.valueOf(sumMonth));
        contentStatistical2_Spending.setText(String.valueOf(Cash));
        contentStatistical3_Spending.setText(String.valueOf(moneyInCard));
    }
    private void eventOnClickFrameTime2_Income() {
        calFrameTime2_Spending.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Cursor cursor = sqliteOpenHelper.getData("SELECT * FROM Spending");
                spendingArrayList.clear();
                int sumMonth=0;
                int Cash = 0;
                int moneyInCard = 0;
                while (cursor.moveToNext()){
                    int ID = cursor.getInt(0);
                    String Title = cursor.getString(1);
                    int Content = cursor.getInt(2);
                    String Type = cursor.getString(3);
                    String Topic = cursor.getString(4);
                    String Note = cursor.getString(5);
                    String Time = cursor.getString(6);
                    String timeSelect = (month+1)+"/"+year;
                    String Day = String.valueOf(dayOfMonth);
                    if((month)<=8){
                        timeSelect = "0"+timeSelect;
                    }
                    if(Integer.valueOf(Day)<=9){
                        Day = "0"+Day;
                    }
                    if(Time.substring(3).equals(timeSelect)){
                        spendingArrayList.add(new Spending(ID, Title, Content, Type, Topic, Note, Time));
                        sumMonth= sumMonth + Content;
                        if(Type.equals("Tiền mặt")){
                            Cash = Cash + Content;
                        }else {
                            moneyInCard = moneyInCard + Content;
                        }
                    }
                    timeNow_Spending.setText(String.valueOf(Day+"/"+timeSelect));
                }
                contentStatistical1_Spending.setText(String.valueOf(sumMonth));
                contentStatistical2_Spending.setText(String.valueOf(Cash));
                contentStatistical3_Spending.setText(String.valueOf(moneyInCard));
                spendingAdapter.notifyDataSetChanged();
            }
        });
    }

    private void animationSlide_Spending() {
        gestureDetector = new GestureDetector(getActivity(),new MyGes_Spending());
        frameStatistical2_Spending.setVisibility(View.INVISIBLE);
        frameStatistical3_Spending.setVisibility(View.INVISIBLE);
        layoutStatistical_Spending.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }
    class MyGes_Spending extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX()-e2.getX()>s && Math.abs(velocityX)>v){
                if(valueVi_1==0){
                    frameStatistical1_Spending.setVisibility(View.INVISIBLE);
                    frameStatistical2_Spending.setVisibility(View.VISIBLE);
                    valueVi_1 = 4;
                    valueVi_2 = 0;
                    valueVi_3 = 4;
                }
                else if(valueVi_2==0){
                    frameStatistical2_Spending.setVisibility(View.INVISIBLE);
                    frameStatistical3_Spending.setVisibility(View.VISIBLE);
                    valueVi_1 = 4;
                    valueVi_2 = 4;
                    valueVi_3 = 0;

                }
                else if(valueVi_3==0){
                    frameStatistical3_Spending.setVisibility(View.INVISIBLE);
                    frameStatistical1_Spending.setVisibility(View.VISIBLE);
                    valueVi_1 = 0;
                    valueVi_2 = 4;
                    valueVi_3 = 4;
                }
            }
            if(e2.getX()-e1.getX()>s && Math.abs(velocityX)>v){
                if(valueVi_1==0){
                    frameStatistical1_Spending.setVisibility(View.INVISIBLE);
                    frameStatistical3_Spending.setVisibility(View.VISIBLE);
                    valueVi_1 = 4;
                    valueVi_2 = 4;
                    valueVi_3 = 0;

                }
                else if(valueVi_2==0){
                    frameStatistical2_Spending.setVisibility(View.INVISIBLE);
                    frameStatistical1_Spending.setVisibility(View.VISIBLE);
                    valueVi_1 = 0;
                    valueVi_2 = 4;
                    valueVi_3 = 4;
                }
                else if(valueVi_3==0){
                    frameStatistical3_Spending.setVisibility(View.INVISIBLE);
                    frameStatistical2_Spending.setVisibility(View.VISIBLE);
                    valueVi_1 = 4;
                    valueVi_2 = 0;
                    valueVi_3 = 4;
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
    private void eventFindData_Spending() {
        String Time = timeNow_Spending.getText().toString().substring(3);
        findData(frameFindType3_Spending,"TypeSpending", "Tiền mặt", Time);
        findData(frameFindType2_Spending,"TypeSpending", "Chuyển khoản", Time);
        frameFindType1_Spending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataInLvData_Spending();
            }
        });
        findData(frameFindTopic1_Spending,"TopicSpending", "Thiết yếu", Time);
        findData(frameFindTopic2_Spending,"TopicSpending", "Giáo dục", Time);
        findData(frameFindTopic3_Spending,"TopicSpending", "Hưởng thụ",Time);
        findData(frameFindTopic4_Spending,"TopicSpending", "Đầu tư", Time);
        findData(frameFindTopic5_Spending,"TopicSpending", "Tiết kiệm", Time);
        findData(frameFindTopic6_Spending,"TopicSpending", "Từ thiện", Time);

    }

    private void findData(LinearLayout linearLayout, String property, String value, String timeSelect  ){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor =  sqliteOpenHelper.getData("SELECT * FROM Spending WHERE "+property+" = '"+value+"'");
                spendingArrayList.clear();
                while (cursor.moveToNext()){
                    int ID = cursor.getInt(0);
                    String Title = cursor.getString(1);
                    int Content = cursor.getInt(2);
                    String Type = cursor.getString(3);
                    String Topic = cursor.getString(4);
                    String Note = cursor.getString(5);
                    String Time = cursor.getString(6);
                    if(Time.substring(3).equals(timeSelect)){
                        spendingArrayList.add(new Spending(ID, Title, Content, Type, Topic, Note, Time));
                    }
                }
                spendingAdapter.notifyDataSetChanged();
            }
        });
        spendingAdapter.notifyDataSetChanged();
    }
    private void eventLoadLayoutFind_Spending() {
        frameFindType4_Spending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutFindTopic_Spending.setVisibility(view.VISIBLE);
                layoutFindType_Spending.setVisibility(view.INVISIBLE);
            }
        });

        frameFindTopic7_Spending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutFindTopic_Spending.setVisibility(view.INVISIBLE);
                layoutFindType_Spending.setVisibility(view.VISIBLE);
            }
        });
    }

    private void eventOnClickFrameTime1_Spending() {
        frameTime1_Spending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int valueVisibility= Integer.valueOf(frameTime2_Spending.getVisibility());
                if(valueVisibility==8){
                    frameTime2_Spending.setVisibility(View.VISIBLE);
                }
                else {
                    frameTime2_Spending.setVisibility(View.GONE);
                }
            }
        });
    }

    private void eventOnclickLvLongData_Spending() {
        lvData_Spending.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                        sqliteOpenHelper.queryData("DELETE FROM Spending WHERE IdSpending="+spendingArrayList.get(i).getIdSpending());
                        spendingInterface.reloadSpendingFragment();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private String getTimeNow() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String timeNow = dateFormat.format(Calendar.getInstance().getTime());
        timeNow_Spending.setText(timeNow);
        return timeNow;
    }

    private void loadDataInLvData_Spending() {
        Cursor cursor = sqliteOpenHelper.getData("SELECT * FROM Spending");
        spendingArrayList.clear();
        while (cursor.moveToNext()){
            int ID = cursor.getInt(0);
            String Title = cursor.getString(1);
            int Content = cursor.getInt(2);
            String Type = cursor.getString(3);
            String Topic = cursor.getString(4);
            String Note = cursor.getString(5);
            String Time = cursor.getString(6);
            if(Time.substring(3).equals(timeNow_Spending.getText().toString().substring(3))){
                spendingArrayList.add(new Spending(ID, Title, Content, Type, Topic, Note, Time));
            }
        }
        spendingAdapter.notifyDataSetChanged();
    }

    private void eventOnclickLvData_Spending() {
        lvData_Spending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                spendingInterface.getDataSpendingUpdate(
                        Integer.valueOf(spendingArrayList.get(i).getIdSpending()),
                        spendingArrayList.get(i).getTitleSpending(),
                        Integer.valueOf(spendingArrayList.get(i).getContentSpending()),
                        spendingArrayList.get(i).getTypeSpending(),
                        spendingArrayList.get(i).getTopicSpending(),
                        spendingArrayList.get(i).getNoteSpending(),
                        spendingArrayList.get(i).getTimeSpending()
                );
            }
        });
    }

    private void eventOnClickBtnAddData_Spending() {
        btnAddData_Spending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spendingInterface.showInputSpendingDataFragment();
            }
        });
    }

    private void initListView_Spending() {
        spendingArrayList = new ArrayList<>();
        spendingAdapter = new SpendingAdapter(getActivity(), R.layout.layout_item_listview_spending, spendingArrayList);
        lvData_Spending.setAdapter(spendingAdapter);
    }

}
