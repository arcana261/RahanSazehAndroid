package com.example.arcana.rahansazeh;

import android.os.Handler;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.arcana.rahansazeh.model.LicensePlate;
import com.example.arcana.rahansazeh.model.Record;
import com.example.arcana.rahansazeh.model.Time;
import com.example.arcana.rahansazeh.validation.TextValidator;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataEntryActivity extends BaseActivity {
    private TextView txtTimeHour;
    private TextView txtTimeMinute;
    private TextView txtTimeSecond;
    private Spinner spinnerLicensePlate;
    private Spinner spinTaxiType;
    private EditText txtLicensePlateLeft;
    private EditText txtLicensePlateRight;
    private EditText unloadPassengersCount;
    private EditText loadPassengersCount;
    private TextView txtDepartureTime;
    private TextView txtArrivalTime;
    private ToggleButton btnTaxiUnLoad;
    private ToggleButton btnTaxiLoad;

    private TextValidator licensePlateLeftValidator;
    private TextValidator licensePlateRightValidator;
    private TextValidator arrivalTimeValidator;
    private TextValidator departureTimeValidator;
    private TextValidator loadPassengerCountValidator;
    private TextValidator unloadPassengerCountValidator;

    private ArrayList<Record> records;

    private String fixDateStr(String value) {
        if (value.length() < 1) {
            return "00";
        }
        else if (value.length() < 2) {
            return "0" + value;
        }
        else {
            return value;
        }
    }

    private String fixDateStr(int value) {
        return fixDateStr("" + value);
    }

    public void onTimerHandler() {
        Date date = new Date();

        txtTimeHour.setText(fixDateStr(date.getHours()));
        txtTimeMinute.setText(fixDateStr(date.getMinutes()));
        txtTimeSecond.setText(fixDateStr(date.getSeconds()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.data_entry, menu);
        /*
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        */

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        forceRTLIfSupported();
        enableBackButton();

        final DataEntryActivity activity = this;

        txtTimeHour = findViewById(R.id.txtTimeHour);
        txtTimeMinute = findViewById(R.id.txtTimeMinute);
        txtTimeSecond = findViewById(R.id.txtTimeSecond);
        spinnerLicensePlate = findViewById(R.id.spinnerLicensePlate);
        spinTaxiType = findViewById(R.id.spinTaxiType);
        txtLicensePlateLeft = findViewById(R.id.txtLicensePlateLeft);
        txtLicensePlateRight = findViewById(R.id.txtLicensePlateRight);
        unloadPassengersCount = findViewById(R.id.unloadPassengersCount);
        loadPassengersCount = findViewById(R.id.loadPassengersCount);
        txtDepartureTime = findViewById(R.id.txtDepartureTime);
        txtArrivalTime = findViewById(R.id.txtArrivalTime);
        btnTaxiLoad = findViewById(R.id.btnTaxiLoad);
        btnTaxiUnLoad = findViewById(R.id.btnTaxiUnLoad);

        licensePlateLeftValidator = TextValidator.applyLength(txtLicensePlateLeft, 2);
        licensePlateRightValidator = TextValidator.applyLength(txtLicensePlateRight, 3);
        arrivalTimeValidator = TextValidator.applyRegExp(txtArrivalTime,
                "^\\d\\d:\\d\\d:\\d\\d",
                "زمان رسیدن را انتخاب کنید");
        departureTimeValidator = TextValidator.applyRegExp(txtDepartureTime,
                "\\d\\d:\\d\\d:\\d\\d",
                "ساعت اعزام را انتخاب کنید");
        loadPassengerCountValidator = TextValidator.applyLength(loadPassengersCount, 1, 2);
        unloadPassengerCountValidator = TextValidator.applyLength(unloadPassengersCount, 1, 2);

        txtLicensePlateLeft.requestFocus();

        txtLicensePlateLeft.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 2) {
                    spinnerLicensePlate.requestFocus();
                }
            }
        });

        spinnerLicensePlate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtLicensePlateRight.requestFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtLicensePlateRight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 3) {
                    spinTaxiType.requestFocus();
                }
            }
        });

        spinTaxiType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unloadPassengersCount.requestFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        onTimerHandler();

        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.onTimerHandler();
                        h.postDelayed(this, 1000);
                    }
                });
            }
        }, 1000);

        List<String> licenseTypeList;

        licenseTypeList = new ArrayList<String>();
        licenseTypeList.add("نوع پلاک را انتخاب کنید");
        licenseTypeList.add("ت");
        licenseTypeList.add("ع");

        ArrayAdapter<String> licenseTypeAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, licenseTypeList);
        licenseTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLicensePlate.setAdapter(licenseTypeAdapter);

        List<String> vehicleTypeList;

        vehicleTypeList = new ArrayList<String>();
        vehicleTypeList.add("نوع خودرو را انتخاب کنید");
        vehicleTypeList.add("ون");
        vehicleTypeList.add("پژو (۴۰۵-روآ-آردی)");
        vehicleTypeList.add("سمند");
        vehicleTypeList.add("پیکان");
        vehicleTypeList.add("سایر");

        ArrayAdapter<String> vehicleTypeAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, vehicleTypeList);
        vehicleTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTaxiType.setAdapter(vehicleTypeAdapter);

        records = new ArrayList<>();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("records")) {
                records = (ArrayList<Record>) savedInstanceState.getSerializable("records");
            }
        }
    }

    public void clear() {
        txtLicensePlateLeft.setText("");
        txtLicensePlateRight.setText("");
        loadPassengersCount.setText("");
        unloadPassengersCount.setText("");
        txtArrivalTime.setText(getString(R.string.choose_time));
        txtDepartureTime.setText(getString(R.string.choose_time));
        spinTaxiType.setSelection(0);
        spinnerLicensePlate.setSelection(0);

        txtLicensePlateLeft.requestFocus();
    }

    public void onTimeSet(String cause, int hour, int minute, int second) {
        if (cause.equals("departure")) {
            txtDepartureTime.setText(fixDateStr(hour) + ":" + fixDateStr(minute) + ":" + fixDateStr(second));
        }
        else if (cause.equals("arrival")) {
            txtArrivalTime.setText(fixDateStr(hour) + ":" + fixDateStr(minute) + ":" + fixDateStr(second));
        }
    }

    private void showTimePickerDialog(final String cause) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        final DataEntryActivity owner = this;

        TimePickerDialog dlg = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                owner.onTimeSet(cause, hourOfDay, minute, second);
            }
        }, hour, minute, second, true);

        dlg.enableSeconds(true);
        dlg.setCancelText("لغو");
        dlg.setOkText("تأیید");
        dlg.show(getFragmentManager(), "Datepickerdialog");
    }

    public void onBtnSelectDepartureTimeClicked(View view) {
        showTimePickerDialog("departure");
    }

    public void onBtnSelectArrivalTimeClicked(View view) {
        showTimePickerDialog("arrival");
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("records", records);
    }

    public void onSaveClicked(View view) {
        if (licensePlateLeftValidator.isValid() &&
                licensePlateRightValidator.isValid() &&
                arrivalTimeValidator.isValid() &&
                departureTimeValidator.isValid() &&
                loadPassengerCountValidator.isValid() &&
                unloadPassengerCountValidator.isValid()) {
            Record newRecord = new Record();

            if (txtArrivalTime.isEnabled()) {
                newRecord.setArrivalTime(new Time(txtArrivalTime.getText().toString()));
            }

            if (txtDepartureTime.isEnabled()) {
                newRecord.setDepartureTime(new Time(txtDepartureTime.getText().toString()));
            }

            newRecord.setLicensePlate(new LicensePlate(
                    txtLicensePlateLeft.getText().toString(),
                    txtLicensePlateRight.getText().toString(),
                    spinnerLicensePlate.getSelectedItem().toString()));
            newRecord.setVehicleType(spinTaxiType.getSelectedItem().toString());
            newRecord.setPassengerLoaded(btnTaxiLoad.isChecked());
            newRecord.setPassengerUnloaded(btnTaxiUnLoad.isChecked());

            if (loadPassengersCount.getText().length() > 0) {
                newRecord.setPassengerLoadCount(Integer.parseInt(loadPassengersCount.getText().toString()));
            }

            if (unloadPassengersCount.getText().length() > 0) {
                newRecord.setPassengerUnloadCount(Integer.parseInt(unloadPassengersCount.getText().toString()));
            }

            records.add(newRecord);
            clear();
        }
    }
}
