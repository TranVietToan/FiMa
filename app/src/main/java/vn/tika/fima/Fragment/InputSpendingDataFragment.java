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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.tika.fima.Interface.SpendingInterface;
import vn.tika.fima.Model.Spending;
import vn.tika.fima.R;

public class InputSpendingDataFragment extends Fragment {
    View view_InputSpending;
    SpendingInterface spendingInterface;

    Button btnConfirmData_InputSpending;
    EditText edtTitleData_InputSpending, edtContentData_InputSpending, edtNoteData_InputSpending;
    CalendarView calendarView_InputSpending;

    Spinner spType_InputSpending;
    ArrayAdapter<String> arrayAdapterType_InputSpending;
    ArrayList<String> arrayListType_InputSpending;

    Spinner spTopic_InputSpending;
    ArrayAdapter<String> arrayAdapterTopic_InputSpending;
    ArrayList<String> arrayListTopic_InputSpending;

    String valueTimeSelect_InputSpending;
    String valueItemTypeSelect_InputSpending;
    String valueItemTopicSelect_InputSpending;


    int idUpdateSpending;
    String titleSpendingOld, noteSpendingOld;
    int contentSpendingOld;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_InputSpending = inflater.inflate(R.layout.fragment_input_spending_data, container, false);
        addControls();
        addEvents();
        return view_InputSpending;
    }

    private void addControls() {
        spendingInterface =(SpendingInterface) getActivity();

        spTopic_InputSpending = view_InputSpending.findViewById(R.id.sp_topic_InputSpending);
        spType_InputSpending = view_InputSpending.findViewById(R.id.sp_type_InputSpending);

        calendarView_InputSpending = view_InputSpending.findViewById(R.id.calendar_view_InputSpending);

        edtTitleData_InputSpending = view_InputSpending.findViewById(R.id.title_data_InputSpending);
        edtContentData_InputSpending  = view_InputSpending.findViewById(R.id.content_data_InputSpending);
        edtNoteData_InputSpending = view_InputSpending.findViewById(R.id.note_data_InputSpending);

        btnConfirmData_InputSpending= view_InputSpending.findViewById(R.id.btn_confirm_data_InputSpending);
    }

    private void addEvents() {
        getStatusDataSpending();
        loadSpendingDataOld();
        eventSelectDayChange_InputSpending();
        eventOnclickSpType_InputSpending();
        eventOnclickSpTopic_InputSpending();
        eventOnClickBtnConfirmData_InputSpending();
    }

    private void loadSpendingDataOld() {
        edtTitleData_InputSpending.setText(titleSpendingOld);
        edtContentData_InputSpending.setText(String.valueOf(contentSpendingOld));
        edtNoteData_InputSpending.setText(noteSpendingOld);
    }

    private boolean getStatusDataSpending() {
        Bundle bundle = getArguments();
        if(bundle != null){
            Spending spending = (Spending) bundle.getSerializable("DATA_SPENDING");
            idUpdateSpending = spending.getIdSpending();
            titleSpendingOld = spending.getTitleSpending();
            contentSpendingOld = spending.getContentSpending();
            noteSpendingOld = spending.getNoteSpending();
            return false;
        }
        else {
            return true;
        }
    }

    private void eventOnclickSpTopic_InputSpending() {
        //init spTopic
        arrayListTopic_InputSpending = new ArrayList<>();
        arrayListTopic_InputSpending.add("Thiết yếu");
        arrayListTopic_InputSpending.add("Giáo dục");
        arrayListTopic_InputSpending.add("Hưởng thụ");
        arrayListTopic_InputSpending.add("Đầu tư");
        arrayListTopic_InputSpending.add("Tiết kiệm");
        arrayListTopic_InputSpending.add("Từ thiện");
        arrayAdapterTopic_InputSpending = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                arrayListTopic_InputSpending);
        arrayAdapterTopic_InputSpending.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTopic_InputSpending.setAdapter(arrayAdapterTopic_InputSpending);

        spTopic_InputSpending.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valueItemTopicSelect_InputSpending = arrayListTopic_InputSpending.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void eventOnclickSpType_InputSpending() {
        // init SpType
        arrayListType_InputSpending = new ArrayList<>();
        arrayListType_InputSpending.add("Tiền mặt");
        arrayListType_InputSpending.add("Chuyển khoản");
        arrayAdapterType_InputSpending = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                arrayListType_InputSpending);
        arrayAdapterType_InputSpending.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType_InputSpending.setAdapter(arrayAdapterType_InputSpending);

        spType_InputSpending.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valueItemTypeSelect_InputSpending = arrayListType_InputSpending.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void eventSelectDayChange_InputSpending() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        valueTimeSelect_InputSpending = dateFormat.format(Calendar.getInstance().getTime());
        calendarView_InputSpending.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String Day = String.valueOf(i2), Month= String.valueOf(i1+1);
                if((i1)<=8){
                    Month = "0"+(i1+1);
                }
                if(Integer.valueOf(i2)<=9){
                    Day = "0"+ i2;
                }
                valueTimeSelect_InputSpending =  Day + "/" + Month + "/" + i;
            }
        });
    }

    private void eventOnClickBtnConfirmData_InputSpending() {
        btnConfirmData_InputSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getStatusDataSpending()){
                    spendingInterface.insertSpendingDate(edtTitleData_InputSpending.getText().toString(),
                            Integer.valueOf(edtContentData_InputSpending.getText().toString()),
                            valueItemTypeSelect_InputSpending,
                            valueItemTopicSelect_InputSpending,
                            edtNoteData_InputSpending.getText().toString(),
                            valueTimeSelect_InputSpending);
                }
                if(getStatusDataSpending()==false){
                    String Title = edtTitleData_InputSpending.getText().toString();
                    int Content  = Integer.valueOf(edtContentData_InputSpending.getText().toString());
                    String Note =  edtNoteData_InputSpending.getText().toString();
                    spendingInterface.updateSpendingData(idUpdateSpending,
                            Title, Content,
                            valueItemTypeSelect_InputSpending,
                            valueItemTopicSelect_InputSpending,
                            edtNoteData_InputSpending.getText().toString(),
                            valueTimeSelect_InputSpending);
                }
            }
        });
    }

}
