package vn.tika.fima.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import vn.tika.fima.Interface.TargetInterface;
import vn.tika.fima.Model.Target;
import vn.tika.fima.R;

public class InputLongTargetDataFragment extends Fragment {
    View view_InputLongTarget;
    TargetInterface targetInterface;

    EditText edtTitle_InputLongTarget, edtContent_InputLongTarget, edtTimeEnd_InputLongTarget;
    Button btnConfirmData_InputLongTarget;
    CalendarView calendarView_InputLongTarget;

    String valueTimeSelect_InputLongTarget;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_InputLongTarget= inflater.inflate(R.layout.fragmen_input_long_target_data,container,false);
        addControls();
        addEvents();
        return view_InputLongTarget;
    }

    private void addControls() {
        targetInterface = (TargetInterface) getActivity();
        edtTitle_InputLongTarget = view_InputLongTarget.findViewById(R.id.edt_title_data_InputLongTarget);
        edtContent_InputLongTarget = view_InputLongTarget.findViewById(R.id.edt_content_data_InputLongTarget);
        edtTimeEnd_InputLongTarget = view_InputLongTarget.findViewById(R.id.edt_time_end_InputLongTarget);
        btnConfirmData_InputLongTarget = view_InputLongTarget.findViewById(R.id.btn_confirm_data_InputLongTarget);
        calendarView_InputLongTarget =view_InputLongTarget.findViewById(R.id.calendar_view_InputLongTarget);
    }

    private void addEvents() {
        eventSelectDayChange_InputShortTarget();
        eventOnClickBtnConfirmData_InputShortTarget();
    }


    private void eventOnClickBtnConfirmData_InputShortTarget() {
        btnConfirmData_InputLongTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetInterface.insertLongTargetDate(
                        edtTitle_InputLongTarget.getText().toString(),
                        Integer.valueOf(edtContent_InputLongTarget.getText().toString()),
                        0,valueTimeSelect_InputLongTarget,
                        edtTimeEnd_InputLongTarget.getText().toString()
                );
            }
        });
    }

    private void eventSelectDayChange_InputShortTarget() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        valueTimeSelect_InputLongTarget = dateFormat.format(Calendar.getInstance().getTime());
        calendarView_InputLongTarget.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String Day = String.valueOf(i2), Month= String.valueOf(i1+1);
                if((i1)<=8){
                    Month = "0"+(i1+1);
                }
                if(Integer.valueOf(i2)<=9){
                    Day = "0"+ i2;
                }
                valueTimeSelect_InputLongTarget = Day +"/" +Month+ "/"+i;
            }
        });
    }
}
