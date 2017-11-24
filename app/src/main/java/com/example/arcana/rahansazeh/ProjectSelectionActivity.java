package com.example.arcana.rahansazeh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.arcana.rahansazeh.model.Project;
import com.example.arcana.rahansazeh.model.ProjectDao;
import com.example.arcana.rahansazeh.model.ProjectLine;
import com.example.arcana.rahansazeh.model.ProjectLineDao;
import com.example.arcana.rahansazeh.service.ProjectListService;
import com.example.arcana.rahansazeh.service.data.ServiceProject;
import com.example.arcana.rahansazeh.service.data.ServiceProjectLine;
import com.example.arcana.rahansazeh.utils.AsyncTaskResult;
import com.gurkashi.lava.lambdas.Predicate;
import com.gurkashi.lava.lambdas.Selector;
import com.gurkashi.lava.queries.stracture.Queriable;

import java.util.Collection;
import java.util.List;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class ProjectSelectionActivity extends BaseActivity {
    private Button btnSelectProject;
    private Button btnSelectDate;
    private Button btnSelectLine;
    private Button btnSelectTerminal;
    private Button btnNext;
    private TableRow rowTerminalContainer;
    private TableRow rowProjectLineContainer;

    private String userName;
    private Project selectedProject;
    private ProjectLine selectedLine;
    private boolean hasSelectedHead;
    private boolean hasDate = false;
    private boolean hasTerminal = false;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;

    private class RefreshProjectsAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<List<ServiceProject>>> {
        private ProgressDialog progressDialog;

        @Override
        protected AsyncTaskResult<List<ServiceProject>> doInBackground(Void... voids) {
            try {
                ProjectListService service = services().createProjectList();
                return new AsyncTaskResult<>(service.getProjects(userName));
            }
            catch (Exception err) {
                return new AsyncTaskResult<>(err);
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ProjectSelectionActivity.this);
            progressDialog.setTitle(getString(R.string.server_loading));
            progressDialog.setMessage(ProjectSelectionActivity.this.getResources().getString(R.string.please_wait));
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onPostExecute(final AsyncTaskResult<List<ServiceProject>> result) {
            progressDialog.cancel();

            if (result.getError() != null) {
                showErrorDialog(getString(R.string.connection_error));
            }
            else {
                ProjectDao projectDao = getDaoSession().getProjectDao();
                ProjectLineDao projectLineDao = getDaoSession().getProjectLineDao();


                Collection<Project> removeList = Queriable.create(projectDao.queryBuilder().list())
                        .filter(new Predicate<Project>() {
                            @Override
                            public boolean predict(final Project project) {
                                return !Queriable.create(result.getResult())
                                        .exists(new Predicate<ServiceProject>() {
                                            @Override
                                            public boolean predict(ServiceProject serviceProject) {
                                                return project.getExternalId().equals(
                                                        serviceProject.getId());
                                            }
                                        }).execute();
                            }
                        }).execute();

                for (Project toRemove : removeList) {
                    projectDao.delete(toRemove);
                }

                for (final ServiceProject serviceProject : result.getResult()) {
                    Project project;

                    List<Project> projects = projectDao.queryBuilder()
                            .where(ProjectDao.Properties.ExternalId.eq(serviceProject.getId()))
                            .offset(0)
                            .limit(1)
                            .list();

                    if (projects.size() > 0) {
                        project = projects.get(0);
                        project.setTitle(serviceProject.getTitle());
                        projectDao.update(project);
                    }
                    else {
                        project = new Project(null, serviceProject.getTitle(),
                                serviceProject.getId());
                        projectDao.insert(project);
                    }

                    List<ProjectLine> existingProjectLines = project.getProjectLines();

                    for (final ServiceProjectLine serviceProjectLine : serviceProject.getLines()) {
                        ProjectLine projectLine = Queriable.create(existingProjectLines)
                                .filter(new Predicate<ProjectLine>() {
                                    @Override
                                    public boolean predict(ProjectLine projectLine) {
                                        return projectLine.getExternalId().equals(
                                                serviceProjectLine.getId());
                                    }
                                }).firstOrNull().execute();

                        if (projectLine == null) {
                            projectLine = new ProjectLine(
                                    null,
                                    serviceProjectLine.getTitle(),
                                    null,
                                    serviceProjectLine.getHead(),
                                    serviceProjectLine.getTail(),
                                    serviceProjectLine.getId());
                            projectLine.setProject(project);
                            projectLineDao.insert(projectLine);
                        }
                        else {
                            projectLine.setHead(serviceProjectLine.getHead());
                            projectLine.setTail(serviceProjectLine.getTail());
                            projectLine.setTitle(serviceProjectLine.getTitle());
                            projectLineDao.save(projectLine);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_selection);

        forceRTLIfSupported();
        enableBackButton();

        btnSelectProject = findViewById(R.id.btnSelectProject);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSelectLine = findViewById(R.id.btnSelectLine);
        btnSelectTerminal = findViewById(R.id.btnSelectTerminal);
        btnNext = findViewById(R.id.btnNext);
        rowTerminalContainer = findViewById(R.id.rowTerminalContainer);
        rowProjectLineContainer = findViewById(R.id.rowProjectLineContainer);

        userName = null;
        selectedProject = null;
        selectedLine = null;
        hasSelectedHead = false;
        hasDate = false;
        hasTerminal = false;
        selectedYear = 0;
        selectedMonth = 0;
        selectedDay = 0;

        Bundle params = getIntent().getExtras();
        if (params != null) {
            if (params.containsKey("userName")) {
                userName = params.getString("userName");
            }
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("userName")) {
                userName = savedInstanceState.getString("userName");
            }

            if (savedInstanceState.containsKey("projectId")) {
                long projectId = savedInstanceState.getLong("projectId");

                if (projectId >= 0) {
                    ProjectDao projectDao = getDaoSession().getProjectDao();

                    List<Project> projects = projectDao.queryBuilder()
                            .where(ProjectDao.Properties.Id.eq(projectId))
                            .limit(1)
                            .offset(0)
                            .list();

                    if (projects.size() > 0) {
                        selectedProject = projects.get(0);
                    }
                }
            }

            if (savedInstanceState.containsKey("lineId")) {
                long lineId = savedInstanceState.getLong("lineId");

                if (lineId >= 0) {
                    ProjectLineDao projectLineDao = getDaoSession().getProjectLineDao();

                    List<ProjectLine> lines = projectLineDao.queryBuilder()
                            .where(ProjectLineDao.Properties.Id.eq(lineId))
                            .limit(1)
                            .offset(0)
                            .list();

                    if (lines.size() > 0) {
                        selectedLine = lines.get(0);
                    }
                }
            }

            if (savedInstanceState.containsKey("hasDate")) {
                hasDate = savedInstanceState.getBoolean("hasDate");
            }

            if (savedInstanceState.containsKey("year")) {
                selectedYear = savedInstanceState.getInt("year");
            }

            if (savedInstanceState.containsKey("month")) {
                selectedMonth = savedInstanceState.getInt("month");
            }

            if (savedInstanceState.containsKey("day")) {
                selectedDay = savedInstanceState.getInt("day");
            }

            if (savedInstanceState.containsKey("hasTerminal")) {
                hasTerminal = savedInstanceState.getBoolean("hasTerminal");
            }

            if (savedInstanceState.containsKey("selectedHead")) {
                hasSelectedHead = savedInstanceState.getBoolean("selectedHead");
            }
        }

        recheck();

        new RefreshProjectsAsyncTask().execute();
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("userName", userName);
        outState.putLong("projectId", selectedProject != null ? selectedProject.getId() : -1);
        outState.putLong("lineId", selectedLine != null ? selectedLine.getId() : -1);
        outState.putBoolean("selectedHead", hasSelectedHead);
        outState.putInt("year", selectedYear);
        outState.putInt("month", selectedMonth);
        outState.putInt("day", selectedDay);
        outState.putBoolean("hasDate", hasDate);
        outState.putBoolean("hasTerminal", hasTerminal);
    }

    public void recheck() {
        boolean hasProject = selectedProject != null;
        boolean hasLine = selectedLine != null;

        rowProjectLineContainer.setVisibility(hasProject && hasDate ?
                View.VISIBLE : View.INVISIBLE);
        rowTerminalContainer.setVisibility(hasProject && hasDate && hasLine ?
                View.VISIBLE : View.INVISIBLE);
        btnNext.setVisibility(hasProject && hasDate && hasLine && hasTerminal ?
                View.VISIBLE : View.INVISIBLE);

        if (hasProject) {
            btnSelectProject.setText(selectedProject.getTitle());
        }

        if (hasDate) {
            String date = selectedYear + "/" +
                    selectedMonth + "/" +
                    selectedDay;
            btnSelectDate.setText(date);
        }

        if (hasLine) {
            btnSelectLine.setText(selectedLine.getTitle());
        }

        if (hasTerminal && hasLine) {
            btnSelectTerminal.setText(hasSelectedHead ?
                    selectedLine.getHead() : selectedLine.getTail());
        }
    }

    public void onSelectProjectClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ProjectDao projectDao = getDaoSession().getProjectDao();
        final List<Project> projects = projectDao.loadAll();

        final String[] titles = Queriable.create(projects).map(new Selector<Project, String>() {
            @Override
            public String select(Project project) {
                return project.getTitle();
            }
        }).execute().toArray(new String[0]);

        builder.setTitle("انتخاب کنید")
                .setItems(titles, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        selectedProject = projects.get(which);
                        recheck();
                    }
                });
        AlertDialog dlg = builder.create();

        dlg.show();
    }

    public void onSelectDateClicked(View view) {
        PersianCalendar now = new PersianCalendar();

        PersianDatePickerDialog picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("انتخاب")
                .setNegativeButton("انصراف")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setInitDate(now)
                .setMaxYear(now.getPersianYear())
                .setMinYear(now.getPersianYear() - 1)
                .setActionTextColor(Color.BLACK)
                //.setTypeFace(typeface)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        hasDate = true;
                        selectedYear = persianCalendar.getPersianYear();
                        selectedMonth = persianCalendar.getPersianMonth();
                        selectedDay = persianCalendar.getPersianDay();
                        recheck();
                    }

                    @Override
                    public void onDisimised() {

                    }
                });

        picker.show();
    }

    public void onSelectLineClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        selectedProject.resetProjectLines();
        final List<ProjectLine> lines = selectedProject.getProjectLines();
        final String[] titles = Queriable.create(lines)
                .map(new Selector<ProjectLine, String>() {
                    @Override
                    public String select(ProjectLine projectLine) {
                        return projectLine.getTitle();
                    }
                }).execute().toArray(new String[0]);

        builder.setTitle("انتخاب کنید")
                .setItems(titles, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        selectedLine = lines.get(which);
                        recheck();
                    }
                });
        AlertDialog dlg = builder.create();

        dlg.show();
    }

    public void onSelectTerminalClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final String[] array = new String[]{
                selectedLine.getHead(),
                selectedLine.getTail()
        };

        builder.setTitle("انتخاب کنید")
                .setItems(array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        hasTerminal = true;
                        hasSelectedHead = which < 1;
                        recheck();
                    }
                });
        AlertDialog dlg = builder.create();

        dlg.show();
    }

    public void onNextClicked(View view) {
        Intent intent = new Intent(this, DataEntryActivity.class);

        DataEntryActivity.Params params = new DataEntryActivity.Params(userName,
                selectedProject.getId(), selectedLine.getId(),
                hasSelectedHead, selectedYear, selectedMonth,
                selectedDay, hasSelectedHead);
        intent.putExtra("params", params);

        startActivity(intent);
    }
}
