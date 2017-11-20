package com.example.arcana.rahansazeh.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.arcana.rahansazeh.R;
import com.example.arcana.rahansazeh.model.DaoSession;
import com.example.arcana.rahansazeh.model.OutgoingPassengerRecord;
import com.example.arcana.rahansazeh.model.OutgoingPassengerRecordDao;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecordDao;
import com.example.arcana.rahansazeh.utils.NumberPadder;
import com.gurkashi.lava.queries.stracture.Queriable;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by arcana on 11/19/17.
 */

public class VerifyPassengerRecordAdapter extends BaseAdapter implements Filterable, ThemedSpinnerAdapter, SpinnerAdapter {
    private Context context;
    private OutgoingPassengerRecordDao recordDao;
    private Resources.Theme spinnerTheme;
    private Long projectId;
    private Long projectLineId;

    public VerifyPassengerRecordAdapter(Context context, OutgoingPassengerRecordDao recordDao,
                                      Long projectId, Long projectLineId) {
        this.context = context;
        this.recordDao = recordDao;
        this.projectId = projectId;
        this.projectLineId = projectLineId;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private QueryBuilder<OutgoingPassengerRecord> createQuery() {
        return recordDao.queryBuilder()
                .where(
                        OutgoingPassengerRecordDao.Properties.ProjectId.eq(projectId),
                        OutgoingPassengerRecordDao.Properties.ProjectLineId.eq(projectLineId)
                ).orderAsc(OutgoingPassengerRecordDao.Properties.Id);
    }

    public OutgoingPassengerRecord getRecord(int position) {
        return Queriable.create(
                createQuery()
                        .limit(1)
                        .offset(position)
                        .list()
        ).first().execute();
    }

    @Override
    public void setDropDownViewTheme(@Nullable Resources.Theme theme) {

    }

    @Nullable
    @Override
    public Resources.Theme getDropDownViewTheme() {
        return null;
    }

    @Override
    public int getCount() {
        int result = (int)createQuery().count();
        return result;
    }

    @Override
    public Object getItem(int position) {
        return getRecord(position);
    }

    @Override
    public long getItemId(int position) {
        return getRecord(position).getId();
    }

    private String formatTimeRange(OutgoingPassengerRecord record) {
        StringBuilder builder = new StringBuilder();

        builder.append(record.getFinishHour())
                .append(":")
                .append(NumberPadder.formatNumber(record.getFinishMinute(), 2));

        builder.append(" - ");

        builder.append(record.getStartHour())
                .append(":")
                .append(NumberPadder.formatNumber(record.getStartMinute(), 2));

        return builder.toString();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OutgoingPassengerRecord record = getRecord(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_passenger_record, parent, false);

            viewHolder.txtDate = convertView.findViewById(R.id.txtDate);
            viewHolder.txtTimeRange = convertView.findViewById(R.id.txtTimeRange);
            viewHolder.txtPassengerCount = convertView.findViewById(R.id.txtPassengerCount);
            viewHolder.btnRemoveRecord = convertView.findViewById(R.id.btnRemoveRecord);

            viewHolder.btnRemoveRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Long id = (Long)v.getTag();

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    recordDao.deleteByKey(id);

                                    if (recordDao.count() > 0) {
                                        notifyDataSetChanged();
                                    }
                                    else {
                                        notifyDataSetInvalidated();
                                    }
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("داده حذف گردد؟").setPositiveButton("بله", dialogClickListener)
                            .setNegativeButton("خیر", dialogClickListener).show();
                }
            });

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
            result = convertView;
        }

        String date = "" + NumberPadder.formatNumber(record.getYear(), 4) +
                "/" +
                NumberPadder.formatNumber(record.getMonth(), 2) +
                "/" +
                NumberPadder.formatNumber(record.getDay(), 2);

        String timeRange = formatTimeRange(record);

        String passengerCount = "" + record.getPassengerCount();

        viewHolder.txtDate.setText(date);
        viewHolder.txtTimeRange.setText(timeRange);
        viewHolder.txtPassengerCount.setText(passengerCount);
        viewHolder.btnRemoveRecord.setTag(record.getId());

        return result;
    }

    private static class ViewHolder {
        private TextView txtDate;
        private TextView txtTimeRange;
        private TextView txtPassengerCount;
        private Button btnRemoveRecord;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
