package com.example.arcana.rahansazeh;

import android.app.DialogFragment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataEntryActivity extends AppCompatActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
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
        licenseTypeList.add("ت");
        licenseTypeList.add("ع");

        ArrayAdapter<String> licenseTypeAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, licenseTypeList);
        licenseTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLicensePlate.setAdapter(licenseTypeAdapter);

        List<String> vehicleTypeList;

        vehicleTypeList = new ArrayList<String>();
        vehicleTypeList.add("ون");
        vehicleTypeList.add("پژو (۴۰۵-روآ-آردی)");
        vehicleTypeList.add("سمند");
        vehicleTypeList.add("پیکان");
        vehicleTypeList.add("سایر");

        ArrayAdapter<String> vehicleTypeAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, vehicleTypeList);
        vehicleTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTaxiType.setAdapter(vehicleTypeAdapter);
    }

    public void clear() {
        txtLicensePlateLeft.setText("");
        txtLicensePlateRight.setText("");
        loadPassengersCount.setText("");
        unloadPassengersCount.setText("");
        txtArrivalTime.setText(getString(R.string.choose_time));
        txtDepartureTime.setText(getString(R.string.choose_time));
        txtLicensePlateLeft.requestFocus();
    }

    public void onTimeSet(String cause, int hour, int minute) {
        if (cause.equals("departure")) {
            txtDepartureTime.setText(fixDateStr(hour) + ":" + fixDateStr(minute));
        }
        else if (cause.equals("arrival")) {
            txtArrivalTime.setText(fixDateStr(hour) + ":" + fixDateStr(minute));
        }
    }

    public void onBtnSelectDepartureTimeClicked(View view) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setActivity(this);
        newFragment.setCause("departure");
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void onBtnSelectArrivalTimeClicked(View view) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setActivity(this);
        newFragment.setCause("arrival");
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void onSaveClicked(View view) {
        clear();
    }
}
