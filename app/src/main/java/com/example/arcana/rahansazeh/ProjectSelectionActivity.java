package com.example.arcana.rahansazeh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.Toast;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class ProjectSelectionActivity extends AppCompatActivity {
    private Button btnSelectProject;
    private Button btnSelectDate;
    private Button btnSelectLine;
    private Button btnSelectTerminal;
    private Button btnNext;
    private TableRow rowTerminalContainer;

    private boolean hasProject = false;
    private boolean hasDate = false;
    private boolean hasLine = false;
    private boolean hasTerminal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_selection);

        btnSelectProject = findViewById(R.id.btnSelectProject);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSelectLine = findViewById(R.id.btnSelectLine);
        btnSelectTerminal = findViewById(R.id.btnSelectTerminal);
        btnNext = findViewById(R.id.btnNext);
        rowTerminalContainer = findViewById(R.id.rowTerminalContainer);

        btnNext.setVisibility(View.INVISIBLE);
        rowTerminalContainer.setVisibility(View.INVISIBLE);
    }

    public void recheck() {
        if (hasProject && hasDate && hasLine) {
            rowTerminalContainer.setVisibility(View.VISIBLE);

            if (hasTerminal) {
                btnNext.setVisibility(View.VISIBLE);
            }
            else {
                btnNext.setVisibility(View.INVISIBLE);
            }
        }
        else {
            rowTerminalContainer.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.INVISIBLE);
        }
    }

    public void onSelectProjectClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] array = new String[]{
                "ساماندهی سامانه تاکسی‌رانی تهران" + "\n" +
                        "ساماندهی میدان کتاب - منطقه ۲ تهران" + "\n" +
                        "طرح جامع حمل و نقل ترافیک سیرجان"
        };

        builder.setTitle("انتخاب کنید")
                .setItems(array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        btnSelectProject.setText(array[which]);
                        hasProject = true;
                        recheck();
                    }
                });
        AlertDialog dlg = builder.create();

        dlg.show();
    }

    public void onSelectDateClicked(View view) {
        final Context ctx = this;
        PersianDatePickerDialog picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setInitDate(new PersianCalendar())
                .setMaxYear(1398)
                .setMinYear(1300)
                .setActionTextColor(Color.GRAY)
                //.setTypeFace(typeface)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        String date = persianCalendar.getPersianYear() + "/" +
                                persianCalendar.getPersianMonth() + "/" +
                                persianCalendar.getPersianDay();
                        btnSelectDate.setText(date);
                        hasDate = true;
                        recheck();
                        //Toast.makeText(ctx, , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDisimised() {

                    }
                });

        picker.show();
    }

    public void onSelectLineClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] array = new String[]{
                "293"
        };

        builder.setTitle("انتخاب کنید")
                .setItems(array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        btnSelectLine.setText(array[which]);
                        hasLine = true;
                        recheck();
                    }
                });
        AlertDialog dlg = builder.create();

        dlg.show();
    }

    public void onSelectTerminalClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] array = new String[]{
                "میدان فاطمی",
                "پایانه سازمان آب"
        };

        builder.setTitle("انتخاب کنید")
                .setItems(array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        btnSelectTerminal.setText(array[which]);
                        hasTerminal = true;
                        recheck();
                    }
                });
        AlertDialog dlg = builder.create();

        dlg.show();
    }

    public void onNextClicked(View view) {
        Intent intent = new Intent(this, DataEntryActivity.class);
        startActivity(intent);
    }
}
