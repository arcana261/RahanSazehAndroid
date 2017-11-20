package com.example.arcana.rahansazeh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.arcana.rahansazeh.adapters.LicensePlateAdapter;
import com.example.arcana.rahansazeh.adapters.TimeRangeAdapter;
import com.example.arcana.rahansazeh.adapters.VehicleTypeAdapter;
import com.example.arcana.rahansazeh.model.LicensePlate;
import com.example.arcana.rahansazeh.model.OutgoingPassengerRecord;
import com.example.arcana.rahansazeh.model.OutgoingPassengerRecordDao;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecord;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecordDao;
import com.example.arcana.rahansazeh.model.Project;
import com.example.arcana.rahansazeh.model.ProjectDao;
import com.example.arcana.rahansazeh.model.ProjectLine;
import com.example.arcana.rahansazeh.model.ProjectLineDao;
import com.example.arcana.rahansazeh.model.Time;
import com.example.arcana.rahansazeh.model.TimeRange;
import com.example.arcana.rahansazeh.model.User;
import com.example.arcana.rahansazeh.model.UserDao;
import com.example.arcana.rahansazeh.model.Vehicle;
import com.example.arcana.rahansazeh.model.VehicleDao;
import com.example.arcana.rahansazeh.model.VehicleType;
import com.example.arcana.rahansazeh.model.VehicleTypeDao;
import com.example.arcana.rahansazeh.service.VehicleService;
import com.example.arcana.rahansazeh.service.VehicleTypeService;
import com.example.arcana.rahansazeh.service.data.ServiceVehicle;
import com.example.arcana.rahansazeh.service.data.ServiceVehicleType;
import com.example.arcana.rahansazeh.utils.AsyncTaskResult;
import com.example.arcana.rahansazeh.utils.LicensePlateFormatter;
import com.example.arcana.rahansazeh.validation.TextValidator;
import com.gurkashi.lava.lambdas.Predicate;
import com.gurkashi.lava.lambdas.Selector;
import com.gurkashi.lava.queries.stracture.Queriable;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DataEntryActivity extends BaseActivity {
    private TextView txtTimeHour;
    private TextView txtTimeMinute;
    private TextView txtTimeSecond;
    private Spinner spinTaxiType;
    private EditText unloadPassengersCount;
    private EditText loadPassengersCount;
    private TextView txtDepartureTime;
    private TextView txtArrivalTime;
    private ToggleButton btnTaxiUnLoad;
    private ToggleButton btnTaxiLoad;
    private AppCompatAutoCompleteTextView txtLicensePlate;
    private Button btnSelectArrivalTime;
    private Button btnSelectDepartureTime;

    private TextValidator loadPassengerCountValidator;
    private TextValidator unloadPassengerCountValidator;

    private VehicleTypeAdapter vehicleTypeAdapter;
    private TimeRangeAdapter timeRangeAdapter;
    private Spinner spinTimeRange;
    private Button btnSavePassenger;
    private EditText txtPassengerCount;

    public static class Params implements Serializable {
        private String userName;
        private Long projectId;
        private Long lineId;
        private boolean selectedHead;
        private int year;
        private int month;
        private int day;
        private boolean hasSelectedHeadTerminal;

        private Project project;
        private ProjectLine projectLine;

        public Params(String userName, Long projectId, Long lineId, boolean selectedHead,
                      int year, int month, int day, boolean hasSelectedHeadTerminal) {
            this.userName = userName;
            this.projectId = projectId;
            this.lineId = lineId;
            this.selectedHead = selectedHead;
            this.year = year;
            this.month = month;
            this.setDay(day);
            this.hasSelectedHeadTerminal = hasSelectedHeadTerminal;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Long getProjectId() {
            return projectId;
        }

        public void setProjectId(Long projectId) {
            this.projectId = projectId;
        }

        public Long getLineId() {
            return lineId;
        }

        public void setLineId(Long lineId) {
            this.lineId = lineId;
        }

        public boolean isSelectedHead() {
            return selectedHead;
        }

        public void setSelectedHead(boolean selectedHead) {
            this.selectedHead = selectedHead;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public Project getProject() {
            return project;
        }

        public ProjectLine getProjectLine() {
            return projectLine;
        }

        private void refresh(BaseActivity activity) {
            ProjectDao projectDao = activity.getDaoSession().getProjectDao();
            ProjectLineDao projectLineDao = activity.getDaoSession().getProjectLineDao();

            if (projectId != null) {
                List<Project> projects = projectDao.queryBuilder()
                        .where(ProjectDao.Properties.Id.eq(projectId))
                        .limit(1)
                        .offset(0)
                        .list();

                if (projects.size() > 0) {
                    project = projects.get(0);
                }
            }

            if (lineId != null) {
                List<ProjectLine> projectLines = projectLineDao.queryBuilder()
                        .where(ProjectLineDao.Properties.Id.eq(lineId))
                        .limit(1)
                        .offset(0)
                        .list();

                if (projectLines.size() > 0) {
                    projectLine = projectLines.get(0);
                }
            }
        }
    }

    private Params params;

    private TabHost tabHost;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuVerify:
                VerifyDataActivity.Params verifyParams = new VerifyDataActivity.Params(
                        params.getUserName(), params.getProjectId(),
                        params.getLineId());

                Intent intent = new Intent(this, VerifyDataActivity.class);
                intent.putExtra("params", verifyParams);

                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static class RefreshDataResult {
        private List<ServiceVehicleType> vehicleTypes;
        private List<ServiceVehicle> vehicles;

        public RefreshDataResult(List<ServiceVehicleType> vehicleTypes,
                                 List<ServiceVehicle> vehicles) {
            this.vehicleTypes = vehicleTypes;
            this.vehicles = vehicles;
        }

        public List<ServiceVehicleType> getVehicleTypes() {
            return vehicleTypes;
        }

        public List<ServiceVehicle> getVehicles() {
            return vehicles;
        }
    }

    private class RefreshDataAsyncTask
            extends AsyncTask<Void, Void, AsyncTaskResult<RefreshDataResult>> {
        private ProgressDialog progressDialog;

        @Override
        protected AsyncTaskResult<RefreshDataResult> doInBackground(Void... voids) {
            try {
                VehicleTypeService vehicleTypeService = services().createVehicleType();
                VehicleService vehicleService = services().createVehicle();

                List<ServiceVehicleType> vehicleTypes = vehicleTypeService.getVehicleTypes(
                        params.getUserName(),
                        params.getProjectLine().getExternalId());

                List<ServiceVehicle> vehicles = vehicleService.getVehicles(
                        params.getUserName(), params.getProjectLine().getExternalId());

                return new AsyncTaskResult<>(
                        new RefreshDataResult(vehicleTypes, vehicles));
            }
            catch (Exception err) {
                return new AsyncTaskResult<>(err);
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(DataEntryActivity.this);
            progressDialog.setTitle(getString(R.string.server_loading));
            progressDialog.setMessage(DataEntryActivity.this.getResources().getString(R.string.please_wait));
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onPostExecute(final AsyncTaskResult<RefreshDataResult> result) {
            progressDialog.cancel();

            if (result.getError() != null) {
                showErrorDialog(getString(R.string.connection_error));
            }
            else {
                VehicleTypeDao vehicleTypeDao = getDaoSession().getVehicleTypeDao();
                VehicleDao vehicleDao = getDaoSession().getVehicleDao();

                for (final ServiceVehicleType serviceVehicleType :
                        result.getResult().getVehicleTypes()) {
                    VehicleType vehicleType = Queriable.create(vehicleTypeDao.queryBuilder()
                            .where(VehicleTypeDao.Properties.ExternalId.eq(serviceVehicleType.getId()))
                            .limit(1)
                            .offset(0)
                            .list()).firstOrNull().execute();

                    if (vehicleType == null) {
                        vehicleType = new VehicleType(null, serviceVehicleType.getTitle(),
                                serviceVehicleType.getId());
                        vehicleTypeDao.insert(vehicleType);
                    }
                    else {
                        vehicleType.setTitle(serviceVehicleType.getTitle());
                        vehicleTypeDao.update(vehicleType);
                    }
                }

                Collection<VehicleType> vehicleTypeRemoveList =
                        Queriable.create(vehicleTypeDao.loadAll())
                            .filter(new Predicate<VehicleType>() {
                                @Override
                                public boolean predict(final VehicleType vehicleType) {
                                    return !Queriable.create(result.getResult().getVehicleTypes())
                                            .exists(new Predicate<ServiceVehicleType>() {
                                                @Override
                                                public boolean predict(ServiceVehicleType serviceVehicleType) {
                                                    return vehicleType.getExternalId().equals(
                                                            serviceVehicleType.getId());
                                                }
                                            }).execute();
                                }
                            }).execute();

                for (VehicleType toRemove : vehicleTypeRemoveList) {
                    vehicleTypeDao.delete(toRemove);
                }

                for (final ServiceVehicle serviceVehicle : result.getResult().getVehicles()) {
                    Vehicle vehicle = Queriable.create(
                            vehicleDao.queryBuilder()
                                    .where(VehicleDao.Properties.ExternalId.eq(serviceVehicle.getId()))
                                    .limit(1)
                                    .offset(0)
                                    .list())
                            .firstOrNull().execute();

                    VehicleType vehicleType = Queriable.create(
                            vehicleTypeDao.queryBuilder()
                                    .where(VehicleTypeDao.Properties.ExternalId.eq(serviceVehicle.getVehicleTypeId()))
                                    .limit(1)
                                    .offset(0)
                                    .list()
                    ).firstOrNull().execute();

                    if (vehicle == null) {
                        vehicle = new Vehicle(null,
                                vehicleType != null ? vehicleType.getId() : null,
                                new LicensePlate(serviceVehicle.getLicensePlateLeft(),
                                        serviceVehicle.getLicensePlateType(),
                                        serviceVehicle.getLicensePlateRight(),
                                        serviceVehicle.getLicensePlateNationalCode()),
                                params.getProjectLine().getId(),
                                serviceVehicle.getId());

                        vehicleDao.insert(vehicle);
                    }
                    else {
                        vehicle.setLicense(new LicensePlate(serviceVehicle.getLicensePlateLeft(),
                                serviceVehicle.getLicensePlateType(),
                                serviceVehicle.getLicensePlateRight(),
                                serviceVehicle.getLicensePlateNationalCode()));
                        vehicle.setProjectLine(params.getProjectLine());
                        vehicle.setVehicleType(vehicleType);

                        vehicleDao.update(vehicle);
                    }
                }

                Collection<Vehicle> removeList = Queriable.create(
                        vehicleDao.queryBuilder()
                                .where(VehicleDao.Properties.ProjectLineId.eq(params.getProjectLine().getId()))
                                .list()
                ).filter(new Predicate<Vehicle>() {
                    @Override
                    public boolean predict(final Vehicle vehicle) {
                        return !Queriable.create(result.getResult().getVehicles())
                                .exists(new Predicate<ServiceVehicle>() {
                                    @Override
                                    public boolean predict(ServiceVehicle serviceVehicle) {
                                        return vehicle.getExternalId().equals(serviceVehicle.getId());
                                    }
                                }).execute();
                    }
                }).execute();

                for (Vehicle toRemove : removeList) {
                    vehicleDao.delete(toRemove);
                }

                vehicleTypeAdapter =
                        new VehicleTypeAdapter(DataEntryActivity.this, getDaoSession());
                spinTaxiType.setAdapter(vehicleTypeAdapter);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        forceRTLIfSupported();
        enableBackButton();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("params")) {
                params = (Params) extras.getSerializable("params");
                params.refresh(this);
            }
        }

        new RefreshDataAsyncTask().execute();

        final DataEntryActivity activity = this;

        txtTimeHour = findViewById(R.id.txtTimeHour);
        txtTimeMinute = findViewById(R.id.txtTimeMinute);
        txtTimeSecond = findViewById(R.id.txtTimeSecond);
        spinTaxiType = findViewById(R.id.spinTaxiType);
        unloadPassengersCount = findViewById(R.id.unloadPassengersCount);
        loadPassengersCount = findViewById(R.id.loadPassengersCount);
        txtDepartureTime = findViewById(R.id.txtDepartureTime);
        txtArrivalTime = findViewById(R.id.txtArrivalTime);
        btnTaxiLoad = findViewById(R.id.btnTaxiLoad);
        btnTaxiUnLoad = findViewById(R.id.btnTaxiUnLoad);
        txtLicensePlate = findViewById(R.id.txtLicensePlate);
        btnSelectArrivalTime = findViewById(R.id.btnSelectArrivalTime);
        btnSelectDepartureTime = findViewById(R.id.btnSelectDepartureTime);
        tabHost = findViewById(R.id.tabHost);
        spinTimeRange = findViewById(R.id.spinTimeRange);
        btnSavePassenger = findViewById(R.id.btnSavePassenger);
        txtPassengerCount = findViewById(R.id.txtPassengerCount);

        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("ثبت تاکسی");
        spec.setContent(R.id.tabVehicleEntry);
        spec.setIndicator("ثبت تاکسی");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("ثبت مسافر");
        spec.setContent(R.id.tabPassengerEntry);
        spec.setIndicator("ثبت مسافر");
        tabHost.addTab(spec);

        List<TimeRange> timeRangeList = new ArrayList<>();
        for (int i = 7; i < 23; i++) {
            for (int j = 0; j < 60; j+=15) {
                timeRangeList.add(new TimeRange((long)(i * 60 + j),
                        i, i + ((j + 15) / 60), j, (j + 15) % 60));
            }
        }

        timeRangeAdapter = new TimeRangeAdapter(this, timeRangeList);
        spinTimeRange.setAdapter(timeRangeAdapter);

        final LicensePlateAdapter licensePlateAdapter =
                new LicensePlateAdapter(this, getDaoSession());
        txtLicensePlate.setAdapter(licensePlateAdapter);

        vehicleTypeAdapter =
                new VehicleTypeAdapter(this, getDaoSession());

        txtLicensePlate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vehicle selectedVehicle = licensePlateAdapter.getVehicleById(id);

                txtLicensePlate.setText(
                        LicensePlateFormatter.toString(selectedVehicle.getLicense()));

                boolean found = false;
                for (int i = 0; i < vehicleTypeAdapter.getCount(); i++) {
                    VehicleType type = vehicleTypeAdapter.getItem(i);

                    if (type.getId() == selectedVehicle.getVehicleTypeId()) {
                        spinTaxiType.setSelection(i);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    spinTaxiType.setSelection(0);
                }
            }
        });

        txtLicensePlate.addTextChangedListener(new TextWatcher() {
            private boolean isInsertion = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isInsertion = count > before;
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (isInsertion && s.length() == 2) {
//                    s.append(" " + "ت" + " ");
//                }
//                else if (!isInsertion && s.length() > 2 && s.length() < 5) {
//                    s.replace(1, s.length(), "");
//                }

                if (isInsertion && s.length() == 2) {
                    s.insert(0, " " + "ت" + " ");
                }
                else if (isInsertion && s.length() > 2) {
                    int index = s.length() - 1;
                    while (index >= 0 && s.charAt(index) != ' ') {
                        index = index - 1;
                    }

                    if (index >= 0) {
                        index = index + 1 + 2;

                        if (index < s.length()) {
                            String excess = s.subSequence(index, s.length()).toString();

                            int head = 0;
                            while (s.charAt(head) != ' ') {
                                head = head + 1;
                            }

                            s.replace(index, s.length(), "");
                            s.insert(head, excess);
                        }
                    }
                }

                if (s.length() >= 8) {
                    spinTaxiType.requestFocus();
                }

                for (int i = 0; i < s.length(); i++) {
                    switch (s.charAt(i)) {
                        case '۱':
                            s.replace(i, i + 1, "1");
                            break;
                        case '۲':
                            s.replace(i, i + 1, "2");
                            break;
                        case '۳':
                            s.replace(i, i + 1, "3");
                            break;
                        case '۴':
                            s.replace(i, i + 1, "4");
                            break;
                        case '۵':
                            s.replace(i, i + 1, "5");
                            break;
                        case '۶':
                            s.replace(i, i + 1, "6");
                            break;
                        case '۷':
                            s.replace(i, i + 1, "7");
                            break;
                        case '۸':
                            s.replace(i, i + 1, "8");
                            break;
                        case '۹':
                            s.replace(i, i + 1, "9");
                            break;
                        case '۰':
                            s.replace(i, i + 1, "0");
                            break;
                        case ' ':
                        case 'ت':
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            break;
                        default:
                            s.replace(i, i + 1, "");
                    }
                }
            }
        });

        loadPassengerCountValidator = TextValidator.applyLength(loadPassengersCount, 1, 2);
        unloadPassengerCountValidator = TextValidator.applyLength(unloadPassengersCount, 1, 2);

        txtLicensePlate.requestFocus();

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

        vehicleTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTaxiType.setAdapter(vehicleTypeAdapter);
    }

    public void clear() {
        txtLicensePlate.setText("");
        loadPassengersCount.setText("");
        unloadPassengersCount.setText("");
        txtArrivalTime.setText(getString(R.string.choose_time));
        txtDepartureTime.setText(getString(R.string.choose_time));
        spinTaxiType.setSelection(0);

        txtLicensePlate.requestFocus();
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
    }

    public int getPassengerCount() {
        try {
            int ret = Integer.parseInt(txtPassengerCount.getText().toString());

            if (ret < 0) {
                return 0;
            }

            return ret;
        }
        catch (Exception err) {
            return 0;
        }
    }

    public void setPassengerCount(int value) {
        if (value < 0) {
            value = 0;
        }

        txtPassengerCount.setText("" + value);
    }

    public void onBtnIncrementPassengerCountClicked(View view) {
        setPassengerCount(getPassengerCount() + 1);
    }

    public void onBtnDecrementPassengerCountClicked(View view) {
        setPassengerCount(getPassengerCount() - 1);
    }

    public void onSavePassengerClicked(View view) {
        if (spinTimeRange.getSelectedItemPosition() < 1) {
            btnSavePassenger.setError("بازه زمانی را انتخاب کنید");
        }
        else {
            btnSavePassenger.setError(null);

            UserDao userDao = getDaoSession().getUserDao();
            User user = Queriable.create(
                    userDao.queryBuilder()
                            .where(UserDao.Properties.NationalCode.eq(params.getUserName()))
                            .limit(1)
                            .offset(0)
                            .list()
            ).first().execute();

            TimeRange range = timeRangeAdapter.getItem(spinTimeRange.getSelectedItemPosition());

            OutgoingPassengerRecord record = new OutgoingPassengerRecord(
                    null, user.getId(), params.getProjectId(),
                    params.getLineId(), params.getYear(),
                    params.getMonth(), params.getDay(),
                    range.getStartHour(), range.getStartMinute(),
                    range.getEndHour(), range.getEndMinute(),
                    getPassengerCount()
            );

            OutgoingPassengerRecordDao passengerRecordDao =
                    getDaoSession().getOutgoingPassengerRecordDao();
            passengerRecordDao.save(record);

            showToast("ذخیره شد");
        }
    }

    public void onSaveClicked(View view) {
        TextValidator[] allValidators = new TextValidator[] {
                loadPassengerCountValidator,
                unloadPassengerCountValidator
        };

        boolean isValid = true;

        for (TextValidator validator : allValidators) {
            if (!validator.isValid()) {
                isValid = false;
            }
        }

        if (!txtLicensePlate.getText().toString().matches("^\\d{3} ت \\d{2}")) {
            txtLicensePlate.setError("پلاک را به صورت صحیح وارد کنید");
            isValid = false;
        }
        else {
            txtLicensePlate.setError(null);
        }

        if (!btnTaxiLoad.isChecked() && !btnTaxiUnLoad.isChecked()) {
            btnTaxiLoad.setError("یکی از گزینه ها را انتخاب کنید");
            btnTaxiUnLoad.setError("یکی از گزینه ها را انتخاب کنید");
            isValid = false;
        }
        else {
            btnTaxiLoad.setError(null);
            btnTaxiUnLoad.setError(null);
        }

        if (txtArrivalTime.isEnabled() &&
                !txtArrivalTime.getText().toString().matches("^\\d{2}:\\d{2}:\\d{2}")) {
            btnSelectArrivalTime.setError("زمان رسیدن را انتخاب کنید");
            isValid = false;
        }
        else {
            btnSelectArrivalTime.setError(null);
        }

        if (txtDepartureTime.isEnabled() &&
                !txtDepartureTime.getText().toString().matches("^\\d{2}:\\d{2}:\\d{2}")) {
            btnSelectDepartureTime.setError("زمان اعزام را انتخاب کنید");
            isValid = false;
        }
        else {
            btnSelectDepartureTime.setError(null);
        }

        if (spinTaxiType.getSelectedItemPosition() < 1) {
            isValid = false;
        }

        if (isValid) {
            UserDao userDao = getDaoSession().getUserDao();
            User user = Queriable.create(
                userDao.queryBuilder()
                        .where(UserDao.Properties.NationalCode.eq(params.getUserName()))
                        .limit(1)
                        .offset(0)
                        .list()
            ).first().execute();

            Integer[] defaultTimes = new Integer[3];
            for (int i = 0; i < defaultTimes.length; i++) {
                defaultTimes[i] = 0;
            }

            Integer[] arrivalTime = txtArrivalTime.isEnabled() ?
                    Queriable.create(txtArrivalTime.getText().toString().split(":"))
                    .map(new Selector<String, Integer>() {
                        @Override
                        public Integer select(String s) {
                            return Integer.parseInt(s);
                        }
                    }).execute().toArray(new Integer[0]) : defaultTimes;

            Integer[] departureTime = txtDepartureTime.isEnabled() ?
                    Queriable.create(txtDepartureTime.getText().toString().split(":"))
                            .map(new Selector<String, Integer>() {
                                @Override
                                public Integer select(String s) {
                                    return Integer.parseInt(s);
                                }
                            }).execute().toArray(new Integer[0]) : defaultTimes;

            int loadPassenger = 0;
            int unloadPassenger = 0;

            try {
                if (loadPassengersCount.isEnabled()) {
                    loadPassenger = Integer.parseInt(loadPassengersCount.getText().toString());
                }
                else {
                    loadPassenger = 0;
                }
            }
            catch (Exception err) {
                loadPassenger = 0;
            }

            try {
                if (unloadPassengersCount.isEnabled()) {
                    unloadPassenger = Integer.parseInt(unloadPassengersCount.getText().toString());
                }
                else {
                    unloadPassenger = 0;
                }
            }
            catch (Exception err) {
                unloadPassenger = 0;
            }

            OutgoingVehicleRecord record = new OutgoingVehicleRecord(
                    null, user.getId(), btnTaxiLoad.isChecked(),
                    btnTaxiUnLoad.isChecked(), params.getProjectId(),
                    params.getLineId(), params.getYear(), params.getMonth(),
                    params.getDay(), txtArrivalTime.isEnabled(), arrivalTime[0],
                    arrivalTime[1], arrivalTime[2], txtDepartureTime.isEnabled(),
                    departureTime[0], departureTime[1], departureTime[2],
                    loadPassenger, unloadPassenger, params.hasSelectedHeadTerminal,
                    txtLicensePlate.getText().toString().replace(" ", "") + "00",
                    vehicleTypeAdapter.getItem(spinTaxiType.getSelectedItemPosition()).getTitle()
            );

            OutgoingVehicleRecordDao outgoingDao = getDaoSession().getOutgoingVehicleRecordDao();
            outgoingDao.save(record);

            clear();
            showToast("ذخیره شد");
        }
    }
}
