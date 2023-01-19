package vn.tika.fima.Fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.tika.fima.Model.Spending;
import vn.tika.fima.R;
import vn.tika.fima.Sqlite.SqliteOpenHelper;

public class StatisticalFragment extends Fragment {
    View view_Statistical;
    SqliteOpenHelper sqliteOpenHelper;
    TextView tab1_Statistical, tab2_Statistical, tab3_Statistical;
    RelativeLayout frame_statistical_income, frame_statistical_spending, frame_statistical_target;

    BarChart barChartSpending_Statistical, barChartIncome_Statistical;
    PieChart pieChartSpending_Statistical;

    TextView  averageIncome, averageSpending;
    TextView savingTarget, moneyTarget;

    String timeSelect="";

    ArrayList<PieEntry> visitorsPieChartSpending = new ArrayList<>();
    ArrayList<BarEntry> visitorsBarChartIncome = new ArrayList<>();
    ArrayList<BarEntry> visitorsBarChartSpending = new ArrayList<>();

    int s=10;
    int v= 10;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_Statistical = inflater.inflate(R.layout.fragment_statistical, container, false);
        addControls();
        addEvents();
        return view_Statistical;
    }

    private void addControls() {
        sqliteOpenHelper = new SqliteOpenHelper(getActivity(),"FINANCE.sqlite", null,1);

        tab1_Statistical = view_Statistical.findViewById(R.id.tab_1_Statistical);
        tab2_Statistical = view_Statistical.findViewById(R.id.tab_2_Statistical);
        tab3_Statistical = view_Statistical.findViewById(R.id.tab_3_Statistical);

        frame_statistical_income = view_Statistical.findViewById(R.id.frame_statistical_income);
        frame_statistical_spending = view_Statistical.findViewById(R.id.frame_statistical_spending);
        frame_statistical_target = view_Statistical.findViewById(R.id.frame_statistical_target);


        barChartSpending_Statistical = view_Statistical.findViewById(R.id.barchart_spending_Statistical);
        barChartIncome_Statistical = view_Statistical.findViewById(R.id.barchart_income_Statistical);
        pieChartSpending_Statistical = view_Statistical.findViewById(R.id.piechart_spending_Statistical);

        averageIncome = view_Statistical.findViewById(R.id.averageIncome);
        averageSpending = view_Statistical.findViewById(R.id.averageSpending);
        savingTarget = view_Statistical.findViewById(R.id.target_saving_Statistical);
        moneyTarget = view_Statistical.findViewById(R.id.money_saving_Statistical);
    }

    private void addEvents() {
       getTimeNow();
       eventOnClickTab_Statistical();
       setDataAverage();
       setDataTarget();
       drawPieChartSpending();
       drawBarChartIncome();
       drawBarChartSpending();

    }

    private void setDataAverage() {
        averageIncome.setText(String.valueOf(getAverage("Income")));
        averageSpending.setText(String.valueOf(getAverage("Spending")));
    }

    private String getTimeNow() {
        DateFormat df = new SimpleDateFormat("MM/yyyy");
        timeSelect = df.format(Calendar.getInstance().getTime());
        return df.format(Calendar.getInstance().getTime());
    }

    private void eventOnClickTab_Statistical() {
       onClickTab_Statistical(tab1_Statistical,tab2_Statistical,tab3_Statistical,
               frame_statistical_income,frame_statistical_spending,frame_statistical_target);
       onClickTab_Statistical(tab2_Statistical,tab3_Statistical,tab1_Statistical,
               frame_statistical_spending,frame_statistical_income,frame_statistical_target);
       onClickTab_Statistical(tab3_Statistical,tab2_Statistical,tab1_Statistical,
               frame_statistical_target,frame_statistical_income,frame_statistical_spending);
    }

    private void onClickTab_Statistical(TextView txt1,TextView txt2,TextView txt3, RelativeLayout rl1, RelativeLayout rl2, RelativeLayout rl3){
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setTextColor(getResources().getColor(R.color.item, null));
                txt2.setTextColor(getResources().getColor(R.color.secondary_text,null));
                txt3.setTextColor(getResources().getColor(R.color.secondary_text,null));
                rl1.setVisibility(View.VISIBLE);
                rl2.setVisibility(View.INVISIBLE);
                rl3.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void drawPieChartSpending(){
        getDataTopicSpending("Giáo dục");
        getDataTopicSpending("Từ thiện");
        getDataTopicSpending("Hưởng thụ");
        getDataTopicSpending("Cần thiết");
        getDataTopicSpending("Đầu tư");
        getDataTopicSpending("Tiết kiệm");

        PieDataSet pieDataSet = new PieDataSet(visitorsPieChartSpending,"Time "+timeSelect);
        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10);
        PieData pieData = new PieData(pieDataSet);
        pieChartSpending_Statistical.setData(pieData);
        pieChartSpending_Statistical.getDescription().setEnabled(false);
        pieChartSpending_Statistical.setCenterText("Thống kê chi tiêu");
        pieChartSpending_Statistical.getLegend().setTextColor(Color.WHITE);
        pieChartSpending_Statistical.animate();
    }
    public float getAverage(String nameDatabase){
        int sum = 0;
        int month = Integer.valueOf(getTimeNow().substring(0,2));
        Cursor cursor = sqliteOpenHelper.getData("SELECT * FROM "+nameDatabase);
        while (cursor.moveToNext()){
            int content = cursor.getInt(2);
            sum = sum + content;
        }
        return (float) (sum/month);
    }
    public void setDataTarget(){
        String Year = getTimeNow().substring(3);
        Cursor cursor = sqliteOpenHelper.getData("SELECT * FROM LongTarget");
        while (cursor.moveToNext()){
            String time = cursor.getString(4).substring(6);

            if(time.equals(Year)){
                savingTarget.setText(String.valueOf(cursor.getInt(2)));
                moneyTarget.setText(String.valueOf(cursor.getInt(3)));
            }
        }
    }
    public void drawBarChart(String nameDataBase,int index1, int index2,ArrayList<BarEntry> nameArr, BarChart nameBarChart){
        int month = Integer.valueOf(getTimeNow().substring(0,2));
        int sum=0;
        Cursor cursor = sqliteOpenHelper.getData("SELECT * FROM "+nameDataBase);
        int i=1;
        String t = String.valueOf(i)+"/"+getTimeNow().substring(3);
        if(i<10){
            t= "0"+t;
        }
        while (i<=month){
            while (cursor.moveToNext()){
                int content = cursor.getInt(index1);
                String time = cursor.getString(index2);
                if(time.substring(3).equals(t)){
                    sum = sum + content;
                }
            }
            nameArr.add(new BarEntry(i, sum));
            i++;
        }

        BarDataSet barDataSet = new BarDataSet(nameArr,"Thu nhập");
        barDataSet.setColor(getResources().getColor(R.color.item, null));
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(8);
        BarData barData =new BarData(barDataSet);
        nameBarChart.setData(barData);
        nameBarChart.getDescription().setTextColor(getResources().getColor(R.color.primary_text, null));
        nameBarChart.getAxisLeft().setTextColor(getResources().getColor(R.color.primary_text, null));
        nameBarChart.getAxisRight().setTextColor(getResources().getColor(R.color.secondary_text, null));
        nameBarChart.getXAxis().setTextColor(getResources().getColor(R.color.primary_text, null));
        nameBarChart.getLegend().setTextColor(Color.WHITE);

    }
    private void drawBarChartIncome(){
        drawBarChart("Income", 2,5,visitorsBarChartIncome,barChartIncome_Statistical);
    }
    private void drawBarChartSpending() {
        drawBarChart("Spending", 2,6,visitorsBarChartSpending,barChartSpending_Statistical);

    }
    private void getDataTopicSpending(String Topic){
        int sum=0;
        Cursor cursor = sqliteOpenHelper.getData("SELECT * FROM Spending WHERE TopicSpending='"+Topic+"'");
        while (cursor.moveToNext()){
            int content = cursor.getInt(2);
            String topic = cursor.getString(4);
            String time = cursor.getString(6);
            if(time.substring(3).equals(timeSelect) && topic.equals(Topic)){
                sum = sum+content;
            }
        }
        if(sum!=0){
            visitorsPieChartSpending.add(new PieEntry(sum,Topic));
        }
    }

}
