package com.example.arcana.rahansazeh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import com.example.arcana.rahansazeh.adapters.VerifyPassengerRecordAdapter;
import com.example.arcana.rahansazeh.adapters.VerifyVehicleRecordAdapter;
import com.example.arcana.rahansazeh.model.OutgoingPassengerRecord;
import com.example.arcana.rahansazeh.model.OutgoingPassengerRecordDao;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecord;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecordDao;
import com.example.arcana.rahansazeh.model.Project;
import com.example.arcana.rahansazeh.model.ProjectDao;
import com.example.arcana.rahansazeh.model.ProjectLine;
import com.example.arcana.rahansazeh.model.ProjectLineDao;
import com.example.arcana.rahansazeh.model.User;
import com.example.arcana.rahansazeh.model.UserDao;
import com.example.arcana.rahansazeh.service.DataEntryService;
import com.example.arcana.rahansazeh.utils.AsyncTaskResult;
import com.gurkashi.lava.queries.stracture.Queriable;

import java.io.Serializable;
import java.util.List;

public class VerifyDataActivity extends BaseActivity {
    private TabHost tabHost;
    private ListView listVehicleRecords;
    private ListView listPassengerRecords;
    private VerifyVehicleRecordAdapter vehicleRecordAdapter;
    private VerifyPassengerRecordAdapter passengerRecordAdapter;
    private OutgoingPassengerRecordDao outgoingPassengerRecordDao;
    private OutgoingVehicleRecordDao outgoingVehicleRecordDao;

    private Params params;

    private abstract class BaseUpdateAsyncTask<RECORD> extends AsyncTask<Void, Integer, AsyncTaskResult<Void>> {
        private List<RECORD> records;
        private ProgressDialog progressDialog;

        public BaseUpdateAsyncTask(List<RECORD> records) {
            this.records = records;
        }

        protected abstract void processItem(RECORD item);

        protected abstract void removeItem(RECORD item);

        @Override
        protected AsyncTaskResult<Void> doInBackground(Void... voids) {
            try {
                for (int i = 0; i < records.size(); i++) {
                    if (isCancelled()) {
                        break;
                    }

                    processItem(records.get(i));
                    publishProgress(i + 1);
                }

                return new AsyncTaskResult<Void>((Void) null);
            }
            catch (Exception err) {
                return new AsyncTaskResult<Void>(err);
            }
        }

        @Override
        protected void onPreExecute() {
            final AsyncTask<Void, Integer, AsyncTaskResult<Void>> self = this;

            progressDialog = new ProgressDialog(VerifyDataActivity.this);
            progressDialog.setTitle("بارگزاری داده ها");
            progressDialog.setMessage(VerifyDataActivity.this.getResources().getString(R.string.please_wait));
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgress(0);
            progressDialog.setMax(records.size());

            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "لغو", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    self.cancel(true);
                }
            });

            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];
            progressDialog.setProgress(progress);

            removeItem(records.get(progress - 1));
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<Void> result) {
            progressDialog.cancel();

            if (result.getError() != null) {
                result.getError().printStackTrace();
                showErrorDialog(getStringResource(R.string.connection_error));
            }
        }

        @Override
        protected void onCancelled(AsyncTaskResult<Void> result) {
            progressDialog.cancel();
        }
    }

    private class VerifyPassengerRecordsAsyncTask extends BaseUpdateAsyncTask<OutgoingPassengerRecord> {
        private OutgoingPassengerRecordDao recordDao;
        private DataEntryService service;

        public VerifyPassengerRecordsAsyncTask(
                OutgoingPassengerRecordDao recordDao,
                List<OutgoingPassengerRecord> outgoingPassengerRecords) {
            super(outgoingPassengerRecords);

            this.recordDao = recordDao;
            this.service = services().createDataEntry();
        }

        @Override
        protected void processItem(OutgoingPassengerRecord item) {
            service.addPassengerRecord(item);
        }

        @Override
        protected void removeItem(OutgoingPassengerRecord item) {
            recordDao.delete(item);
        }

        private void refreshData() {
            if (recordDao.count() > 0) {
                passengerRecordAdapter.notifyDataSetChanged();
            }
            else {
                passengerRecordAdapter.notifyDataSetInvalidated();
            }
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<Void> result) {
            super.onPostExecute(result);
            refreshData();
        }

        @Override
        protected void onCancelled(AsyncTaskResult<Void> result) {
            super.onCancelled(result);
            refreshData();
        }
    }

    private class VerifyVehicleRecordsAsyncTask extends BaseUpdateAsyncTask<OutgoingVehicleRecord> {
        private OutgoingVehicleRecordDao recordDao;
        private DataEntryService service;

        public VerifyVehicleRecordsAsyncTask(
                OutgoingVehicleRecordDao recordDao,
                List<OutgoingVehicleRecord> outgoingVehicleRecords) {
            super(outgoingVehicleRecords);

            this.recordDao = recordDao;
            this.service = services().createDataEntry();
        }

        @Override
        protected void processItem(OutgoingVehicleRecord item) {
            service.addVehicleRecord(item);
        }

        @Override
        protected void removeItem(OutgoingVehicleRecord item) {
            recordDao.delete(item);
        }

        private void refreshData() {
            if (recordDao.count() > 0) {
                vehicleRecordAdapter.notifyDataSetChanged();
            }
            else {
                vehicleRecordAdapter.notifyDataSetInvalidated();
            }
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<Void> result) {
            super.onPostExecute(result);
            refreshData();
        }

        @Override
        protected void onCancelled(AsyncTaskResult<Void> result) {
            super.onCancelled(result);
            refreshData();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_data);

        tabHost = findViewById(R.id.tabHost);
        listVehicleRecords = findViewById(R.id.listVehicleRecords);
        listPassengerRecords = findViewById(R.id.listPassengerRecords);

        forceRTLIfSupported();
        enableBackButton();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("params")) {
                params = (Params) extras.getSerializable("params");
                params.refresh(this);
            }
        }


        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("ثبت تاکسی");
        spec.setContent(R.id.tabVehicleEntry);
        spec.setIndicator("ثبت تاکسی");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("ثبت مسافر");
        spec.setContent(R.id.tabPassengerEntry);
        spec.setIndicator("ثبت مسافر");
        tabHost.addTab(spec);

        outgoingPassengerRecordDao = getDaoSession().getOutgoingPassengerRecordDao();
        outgoingVehicleRecordDao = getDaoSession().getOutgoingVehicleRecordDao();

        vehicleRecordAdapter = new VerifyVehicleRecordAdapter(this, getDaoSession(),
                params.projectId, params.lineId);
        listVehicleRecords.setAdapter(vehicleRecordAdapter);

        passengerRecordAdapter = new VerifyPassengerRecordAdapter(this,
                outgoingPassengerRecordDao,
                params.projectId, params.lineId);
        listPassengerRecords.setAdapter(passengerRecordAdapter);
    }

    public void onVerifyVehicleRecordsClicked(View view) {
        List<OutgoingVehicleRecord> records =
                outgoingVehicleRecordDao.queryBuilder().list();

        new VerifyVehicleRecordsAsyncTask(outgoingVehicleRecordDao, records).execute();
    }

    public void onBtnVerifyPassengerRecordsClicked(View view) {
        List<OutgoingPassengerRecord> records =
                outgoingPassengerRecordDao.queryBuilder().list();

        new VerifyPassengerRecordsAsyncTask(outgoingPassengerRecordDao, records).execute();
    }

    public static class Params implements Serializable {
        private String userName;
        private Long projectId;
        private Long lineId;
        private Project project;
        private ProjectLine projectLine;
        private User user;

        public Params(String userName, Long projectId, Long lineId) {
            this.userName = userName;
            this.projectId = projectId;
            this.lineId = lineId;
        }

        private void refresh(BaseActivity activity) {
            ProjectDao projectDao = activity.getDaoSession().getProjectDao();
            ProjectLineDao projectLineDao = activity.getDaoSession().getProjectLineDao();
            UserDao userDao = activity.getDaoSession().getUserDao();

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

            if (userName != null) {
                user = Queriable.create(
                        userDao.queryBuilder()
                                .where(UserDao.Properties.NationalCode.eq(userName))
                                .limit(1)
                                .offset(0)
                                .list()
                ).firstOrNull().execute();
            }
        }
    }
}
