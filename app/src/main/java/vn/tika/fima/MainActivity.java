package vn.tika.fima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.tika.fima.Fragment.IncomeFragment;
import vn.tika.fima.Fragment.InputLongTargetDataFragment;
import vn.tika.fima.Fragment.StatisticalFragment;
import vn.tika.fima.Fragment.TargetFragment;
import vn.tika.fima.Fragment.InputIncomeDataFragment;
import vn.tika.fima.Fragment.InputShortTargetDataFragment;
import vn.tika.fima.Fragment.InputSpendingDataFragment;
import vn.tika.fima.Fragment.SpendingFragment;
import vn.tika.fima.Interface.IncomeInterface;
import vn.tika.fima.Interface.TargetInterface;
import vn.tika.fima.Interface.SpendingInterface;
import vn.tika.fima.Model.Income;
import vn.tika.fima.Model.Spending;
import vn.tika.fima.Model.Target;
import vn.tika.fima.Sqlite.SqliteOpenHelper;

public class MainActivity extends AppCompatActivity implements IncomeInterface, SpendingInterface, TargetInterface {
    public SqliteOpenHelper sqliteOpenHelper;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
    }

    private void addEvents() {
        createDataBase();
        loadFragment(new TargetFragment());
        eventBotNavOnclick();
    }

    public void createDataBase() {
        sqliteOpenHelper = new SqliteOpenHelper(MainActivity.this, "FINANCE.sqlite", null,1);

        sqliteOpenHelper.queryData("CREATE TABLE IF NOT EXISTS Income(IdIncome INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, TitleIncome VARCHAR, ContentIncome INTEGER," +
                " TypeIncome VARCHAR, NoteIncome VARCHAR, TimeIncome VARCHAR)");

        sqliteOpenHelper.queryData("CREATE TABLE IF NOT EXISTS Spending(IdSpending INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, TitleSpending VARCHAR, ContentSpending INTEGER," +
                "TypeSpending VARCHAR, TopicSpending VARCHAR, NoteSpending VARCHAR, TimeSpending VARCHAR)");

        sqliteOpenHelper.queryData("CREATE TABLE IF NOT EXISTS ShortTarget(IdShortTarget INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, TitleShortTarget VARCHAR, ContentShortTarget INTEGER," +
                "SubContentShortTarget INTEGER, TimeStartShortTarget VARCHAR, TimeEndShortTarget VARCHAR)");
        sqliteOpenHelper.queryData("CREATE TABLE IF NOT EXISTS LongTarget(IdLongTarget INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, TitleLongTarget VARCHAR, ContentLongTarget INTEGER," +
                "SubContentLongTarget INTEGER, TimeStartLongTarget VARCHAR, TimeEndLongTarget VARCHAR)");
        sqliteOpenHelper.queryData("CREATE TABLE IF NOT EXISTS PersonalAccount(Cash INTEGER, MoneyInCard INTEGER)");
    }

    private void eventBotNavOnclick() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()){
                    case R.id.item_target:{
                        fragment = new TargetFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.item_income:{
                        fragment = new IncomeFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.item_spending:{
                        fragment = new SpendingFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.item_statistics:{
                        fragment = new StatisticalFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    default:{
                        return false;
                    }
                }
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void showInputIncomeDataFragment() {
        loadFragment(new InputIncomeDataFragment());
    }

    @Override
    public void reloadIncomeFragment() {
        loadFragment(new IncomeFragment());
    }

    @Override
    public void getDataIncomeUpdate(int ID, String Title, int Content, String Type, String Note, String Time) {
        InputIncomeDataFragment inputIncomeDataFragment = new InputIncomeDataFragment();
        Income income= new Income(ID, Title, Content, Type, Note, Time );
        Bundle bundle = new Bundle();
        bundle.putSerializable("DATA_INCOME", income);
        inputIncomeDataFragment.setArguments(bundle);
        loadFragment(inputIncomeDataFragment);
    }

    @Override
    public void insertIncomeDate(String Title, int Content, String Type, String Note, String Time) {
        sqliteOpenHelper.queryData("INSERT INTO Income VALUES(null,'"+Title+"',"+Content+" ,'"+Type+"','"+Note+"','"+Time+"')");
        loadFragment(new IncomeFragment());
    }

    @Override
    public void updateIncomeData(int ID, String Title, int Content, String Type, String Note, String Time) {
        sqliteOpenHelper.queryData("UPDATE Income SET TitleIncome='"+Title+"',ContentIncome="+Content+",TypeIncome='"+Type+"',NoteIncome='"+Note+"',TimeIncome='"+Time+"' WHERE IdIncome="+ID);
        loadFragment(new IncomeFragment());
    }


    @Override
    public void showInputSpendingDataFragment() {
        loadFragment(new InputSpendingDataFragment());
    }

    @Override
    public void reloadSpendingFragment() {
        loadFragment(new SpendingFragment());
    }

    @Override
    public void getDataSpendingUpdate(int ID, String Title, int Content, String Type, String Topic, String Note, String Time) {
        InputSpendingDataFragment inputSpendingDataFragment = new InputSpendingDataFragment();
        Spending spending = new Spending(ID, Title, Content, Type, Topic, Note, Time );
        Bundle bundle = new Bundle();
        bundle.putSerializable("DATA_SPENDING", spending);
        inputSpendingDataFragment.setArguments(bundle);
        loadFragment(inputSpendingDataFragment);
    }

    @Override
    public void insertSpendingDate(String Title, int Content, String Type, String Topic, String Note, String Time) {
        sqliteOpenHelper.queryData("INSERT INTO Spending VALUES(null,'"+Title+"',"+Content+" ,'"+Type+"','"+Topic+"','"+Note+"','"+Time+"')");
        loadFragment(new SpendingFragment());
    }

    @Override
    public void updateSpendingData(int ID, String Title, int Content, String Type, String Topic, String Note, String Time) {
        sqliteOpenHelper.queryData("UPDATE Spending SET TitleSpending='"+Title+"',ContentSpending="+Content+",TypeSpending='"+Type+"',TopicSpending='"+Topic+"',NoteSpending='"+Note+"',TimeSpending='"+Time+"' WHERE IdSpending="+ID);
        loadFragment(new SpendingFragment());
    }

    @Override
    public void showInputShortTargetData() {
        loadFragment(new InputShortTargetDataFragment());
    }

    @Override
    public void reloadTargetFragment() {
        loadFragment(new TargetFragment());
    }

    @Override
    public void getDataShortTargetUpdate(int ID, String Title, int Content, int subContent, String TimeStart, String timeEnd) {
        InputShortTargetDataFragment inputShortTargetDataFragment = new InputShortTargetDataFragment();
        Target target = new Target(ID, Title, Content, subContent, TimeStart, timeEnd);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DATA_SHORT_TARGET", target);
        inputShortTargetDataFragment.setArguments(bundle);
        loadFragment(inputShortTargetDataFragment);
    }

    @Override
    public void insertShortTargetDate(String Title, int Content, int subContent, String TimeStart, String timeEnd) {
        sqliteOpenHelper.queryData("INSERT INTO ShortTarget VALUES(null,'"+Title+"',"+Content+" ,"+subContent+",'"+TimeStart+"','"+timeEnd+"')");
        loadFragment(new TargetFragment());
    }

    @Override
    public void updateShortTargetData(int ID, String Title, int Content, int subContent, String timeStart, String timeEnd) {
        sqliteOpenHelper.queryData("UPDATE ShortTarget SET TitleShortTarget='"+Title+"',ContentShortTarget="+Content+",SubContentShortTarget="+subContent+",TimeStartShortTarget='"+timeStart+"',TimeEndShortTarget='"+timeEnd+"' WHERE IdShortTarget="+ID);
        loadFragment(new TargetFragment());
    }

    @Override
    public void showInputLongTargetData() {
        loadFragment(new InputLongTargetDataFragment());
    }

    @Override
    public void getDataLongTargetUpdate(int ID, String Title, int Content, int subContent, String TimeStart, String timeEnd) {
        InputLongTargetDataFragment inputLongTargetDataFragment = new InputLongTargetDataFragment();
        Target target = new Target(ID, Title, Content, subContent, TimeStart, timeEnd);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DATA_LONG_TARGET", target);
        inputLongTargetDataFragment.setArguments(bundle);
        loadFragment(inputLongTargetDataFragment);
    }

    @Override
    public void insertLongTargetDate(String Title, int Content, int subContent, String TimeStart, String timeEnd) {
        sqliteOpenHelper.queryData("INSERT INTO LongTarget VALUES(null,'"+Title+"',"+Content+" ,"+subContent+",'"+TimeStart+"','"+timeEnd+"')");
        loadFragment(new TargetFragment());
    }

    @Override
    public void updateLongTargetData(int ID, String Title, int Content, int subContent, String timeStart, String timeEnd) {
        sqliteOpenHelper.queryData("UPDATE LongTarget SET TitleLongTarget='"+Title+"',ContentLongTarget="+Content+",SubContentLongTarget="+subContent+",TimeStartLongTarget='"+timeStart+"',TimeEndLongTarget='"+timeEnd+"' WHERE IdLongTarget="+ID);
        loadFragment(new TargetFragment());
    }
}