package vn.tika.fima.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vn.tika.fima.Interface.IncomeInterface;
import vn.tika.fima.MainActivity;
import vn.tika.fima.Model.Income;
import vn.tika.fima.R;
import vn.tika.fima.Sqlite.SqliteOpenHelper;

public class InputIncomeDataFragment extends Fragment {
    View view_InputIncome;
    IncomeInterface incomeInterface;
    SqliteOpenHelper sqliteOpenHelper;

    Spinner spType_InputIncome;
    ArrayAdapter<String> arrayAdapterType_InputIncome;
    ArrayList<String> arrayListType_InputIncome;

    Button btnConfirmData_InputIncome;
    EditText edtTitleData_InputIncome, edtContentData_InputIncome, edtNoteData_InputIncome;
    CalendarView calendarView_InputIncome;

    String valueTimeSelect_InputIncome;
    String valueItemTypeSelect_InputIncome;

    int idUpdateIncome;
    String titleIncomeOld, noteIncomeOld;
    int contentIncomeOld;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_InputIncome = inflater.inflate(R.layout.fragment_input_income_data, container, false);
        addControls();
        addEvents();
        return view_InputIncome;
    }

    private void addControls() {
        incomeInterface = (IncomeInterface) getActivity();
        sqliteOpenHelper = new SqliteOpenHelper(getActivity(),"FINANCE.sqlite",null,1);
        spType_InputIncome = view_InputIncome.findViewById(R.id.sp_type_InputIncome);
        edtTitleData_InputIncome = view_InputIncome.findViewById(R.id.title_data_InputIncome);
        edtContentData_InputIncome = view_InputIncome.findViewById(R.id.content_data_InputIncome);
        edtNoteData_InputIncome = view_InputIncome.findViewById(R.id.note_data_InputIncome);
        btnConfirmData_InputIncome = view_InputIncome.findViewById(R.id.btn_confirm_data_InputIncome);
        calendarView_InputIncome = view_InputIncome.findViewById(R.id.calendar_view_InputIncome);
    }

    private void addEvents() {
        getStatusDataIncome();
        loadIncomeDataOld();
        eventSelectDayChange_InputIncome();
        eventOnClickSpType_InputIncome();
        eventOnClickBtnConfirmData_InputIncome();
    }

    private void loadIncomeDataOld() {
        edtTitleData_InputIncome.setText(titleIncomeOld);
        edtContentData_InputIncome.setText(String.valueOf(contentIncomeOld));
        edtNoteData_InputIncome.setText(noteIncomeOld);
    }

    private boolean getStatusDataIncome(){

        Bundle bundle = getArguments();
        if(bundle!=null){
            Income income = (Income) bundle.getSerializable("DATA_INCOME");
            idUpdateIncome = income.getIdIncome();
            titleIncomeOld = income.getTitleIncome();
            contentIncomeOld = income.getContentIncome();
            noteIncomeOld = income.getNoteIncome();
            return false;
        }else {
            return true;
        }
    }
    private void eventSelectDayChange_InputIncome() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //get time now
        valueTimeSelect_InputIncome = df.format(Calendar.getInstance().getTime());
        //get time value on change
        calendarView_InputIncome.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String Day = String.valueOf(i2), Month= String.valueOf(i1+1);
                if((i1)<=8){
                    Month = "0"+(i1+1);
                }
                if(Integer.valueOf(i2)<=9){
                    Day = "0"+ i2;
                }
                valueTimeSelect_InputIncome = Day + "/" + Month + "/" + i;
            }
        });
    }

    private void eventOnClickSpType_InputIncome() {
        //init spinner type income
        arrayListType_InputIncome = new ArrayList<>();
        arrayListType_InputIncome.add("Tiền mặt");
        arrayListType_InputIncome.add("Chuyển khoản");
        arrayAdapterType_InputIncome = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,arrayListType_InputIncome);
        arrayAdapterType_InputIncome.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType_InputIncome.setAdapter(arrayAdapterType_InputIncome);

        // event  spType_InputIncome on item click
       spType_InputIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               valueItemTypeSelect_InputIncome = arrayListType_InputIncome.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void eventOnClickBtnConfirmData_InputIncome() {
        btnConfirmData_InputIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getStatusDataIncome()){
                    incomeInterface.insertIncomeDate(
                            edtTitleData_InputIncome.getText().toString(),
                            Integer.valueOf(edtContentData_InputIncome.getText().toString()),
                            valueItemTypeSelect_InputIncome,
                            edtNoteData_InputIncome.getText().toString(),
                            valueTimeSelect_InputIncome);
                }else {
                    incomeInterface.updateIncomeData(
                            idUpdateIncome,
                            edtTitleData_InputIncome.getText().toString(),
                            Integer.valueOf(edtContentData_InputIncome.getText().toString()),
                            valueItemTypeSelect_InputIncome,
                            edtNoteData_InputIncome.getText().toString(),
                            valueTimeSelect_InputIncome);
                }
            }
        });
    }

}
