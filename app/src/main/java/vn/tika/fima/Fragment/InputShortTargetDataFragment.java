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

public class InputShortTargetDataFragment extends Fragment {
    View view_InputShortTarget;
    TargetInterface targetInterface;
    
    EditText edtTitle_InputShortTarget, edtContent_InputShortTarget, edtTimeEnd_InputShortTarget;
    Button btnConfirmData_InputShortTarget;
    CalendarView calendarView_InputShortTarget;

    String valueTimeSelect_InputShortTarget;
    int idUpdateShortTarget , contentShortTargetOld, subContentShortTargetOld;
    String titleShortTargetOld, timeStartShortTargetOld,timeEndShortTargetOld;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_InputShortTarget= inflater.inflate(R.layout.fragment_input_short_target_data,container,false);
        addControls();
        addEvents();
        return view_InputShortTarget;
    }

    private void addControls() {
        targetInterface = (TargetInterface) getActivity();
        edtTitle_InputShortTarget = view_InputShortTarget.findViewById(R.id.edt_title_data_InputShortTarget);
        edtContent_InputShortTarget = view_InputShortTarget.findViewById(R.id.edt_content_data_InputShortTarget);
        edtTimeEnd_InputShortTarget = view_InputShortTarget.findViewById(R.id.edt_time_end_InputShortTarget);
        btnConfirmData_InputShortTarget = view_InputShortTarget.findViewById(R.id.btn_confirm_data_InputShortTarget);
        calendarView_InputShortTarget =view_InputShortTarget.findViewById(R.id.calendar_view_InputShortTarget);
    }
    
    private void addEvents() {
        getStatusDataShortTarget();
        loadDataShortTargetOld();
        eventSelectDayChange_InputShortTarget();
        eventOnClickBtnConfirmData_InputShortTarget();
    }

    private void loadDataShortTargetOld() {
        edtTitle_InputShortTarget.setText(titleShortTargetOld);
        edtContent_InputShortTarget.setText(String.valueOf(contentShortTargetOld));
        edtTimeEnd_InputShortTarget.setText(timeEndShortTargetOld);
    }

    private boolean getStatusDataShortTarget() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            Target target = (Target) bundle.getSerializable("DATA_SHORT_TARGET");
            idUpdateShortTarget = target.getIdTarget();
            titleShortTargetOld = target.getTitleTarget();
            contentShortTargetOld = target.getContentTarget();
            subContentShortTargetOld = target.getSubContentTarget();
            timeStartShortTargetOld = target.getTimeStart_Target();
            timeEndShortTargetOld = target.getTimeEnd_Target();
            return false;
        }else {
            return true;
        }
    }

    private void eventOnClickBtnConfirmData_InputShortTarget() {
        btnConfirmData_InputShortTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getStatusDataShortTarget()!=true){
                    targetInterface.updateShortTargetData(
                            idUpdateShortTarget,
                            edtTitle_InputShortTarget.getText().toString(),
                            Integer.valueOf(edtContent_InputShortTarget.getText().toString()),
                            contentShortTargetOld,valueTimeSelect_InputShortTarget,
                            edtTimeEnd_InputShortTarget.getText().toString()
                    );
                }else {
                    targetInterface.insertShortTargetDate(
                            edtTitle_InputShortTarget.getText().toString(),
                            Integer.valueOf(edtContent_InputShortTarget.getText().toString()),
                            0,valueTimeSelect_InputShortTarget,
                            edtTimeEnd_InputShortTarget.getText().toString()
                    );
                }
            }
        });
    }

    private void eventSelectDayChange_InputShortTarget() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        valueTimeSelect_InputShortTarget = dateFormat.format(Calendar.getInstance().getTime());
        
        calendarView_InputShortTarget.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String Day = String.valueOf(i2), Month= String.valueOf(i1+1);
                if((i1)<=8){
                    Month = "0"+(i1+1);
                }
                if(Integer.valueOf(i2)<=9){
                    Day = "0"+ i2;
                }
                valueTimeSelect_InputShortTarget = Day +"/" +Month+ "/"+i;
            }
        });
    }

}
