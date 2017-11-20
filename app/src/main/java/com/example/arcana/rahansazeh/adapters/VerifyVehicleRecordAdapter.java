package com.example.arcana.rahansazeh.adapters;

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
import android.widget.QuickContactBadge;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.arcana.rahansazeh.R;
import com.example.arcana.rahansazeh.model.DaoSession;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecord;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecordDao;
import com.example.arcana.rahansazeh.utils.LicensePlateFormatter;
import com.example.arcana.rahansazeh.utils.NumberPadder;
import com.gurkashi.lava.queries.stracture.Queriable;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by arcana on 11/19/17.
 */

public class VerifyVehicleRecordAdapter extends BaseAdapter implements Filterable, ThemedSpinnerAdapter, SpinnerAdapter {
    private Context context;
    private DaoSession session;
    private Resources.Theme spinnerTheme;
    private Long projectId;
    private Long projectLineId;

    public VerifyVehicleRecordAdapter(Context context, DaoSession session,
                                      Long projectId, Long projectLineId) {
        this.context = context;
        this.session = session;
        this.projectId = projectId;
        this.projectLineId = projectLineId;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private QueryBuilder<OutgoingVehicleRecord> createQuery() {
        OutgoingVehicleRecordDao vehicleRecordDao = session.getOutgoingVehicleRecordDao();

        return vehicleRecordDao.queryBuilder()
                .where(
                        OutgoingVehicleRecordDao.Properties.ProjectId.eq(projectId),
                        OutgoingVehicleRecordDao.Properties.ProjectLineId.eq(projectLineId))
                .orderAsc(OutgoingVehicleRecordDao.Properties.Id);
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
        return (int) createQuery().count();
    }

    public OutgoingVehicleRecord getRecord(int position) {
        return Queriable.create(
                createQuery().offset(position).limit(1).list()
        ).first().execute();
    }

    @Override
    public Object getItem(int position) {
        return getRecord(position);
    }

    @Override
    public long getItemId(int position) {
        return getRecord(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OutgoingVehicleRecord record = getRecord(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_vehicle_record, parent, false);

            viewHolder.txtDepartureTime = convertView.findViewById(R.id.txtDepartureTime);
            viewHolder.loadPassengersCount = convertView.findViewById(R.id.loadPassengersCount);
            viewHolder.txtArrivalTime = convertView.findViewById(R.id.txtArrivalTime);
            viewHolder.unloadPassengersCount = convertView.findViewById(R.id.unloadPassengersCount);
            viewHolder.txtLicensePlate = convertView.findViewById(R.id.txtLicensePlate);
            viewHolder.txtVehicleType = convertView.findViewById(R.id.txtVehicleType);
            viewHolder.txtDate = convertView.findViewById(R.id.txtDate);
            viewHolder.btnRemoveRecord = convertView.findViewById(R.id.btnRemoveRecord);

            viewHolder.btnRemoveRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Long id = (Long)v.getTag();
                    final OutgoingVehicleRecordDao vehicleRecordDao = session.getOutgoingVehicleRecordDao();

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    vehicleRecordDao.deleteByKey(id);

                                    if (vehicleRecordDao.count() > 0) {
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

        String departureTime =
                record.getHasDepartureTime() ?
                        "" +
                        NumberPadder.formatNumber(record.getDepartureTimeHour(), 2) +
                        ":" +
                        NumberPadder.formatNumber(record.getDepartuerTimeMinute(), 2) +
                        ":" +
                        NumberPadder.formatNumber(record.getDepartureTimeSecond(), 2)
                        : "";
        String arrivalTime =
                record.getHasArrivalTime() ?
                        "" +
                                NumberPadder.formatNumber(record.getArrivalTimeHour(), 2) +
                                ":" +
                                NumberPadder.formatNumber(record.getArrivalTimeMinute(), 2) +
                                ":" +
                                NumberPadder.formatNumber(record.getArrivalTimeSecond(), 2)
                        : "";

        String loadPassengersCount =
                record.getHasLoaded() ? "" + record.getLoadPassengerCount() : "";

        String unloadPassengersCount =
                record.getHasUnLoaded() ? "" + record.getUnloadPassengerCount() : "";

        String licensePlate =
                LicensePlateFormatter.toString(
                    LicensePlateFormatter.ParseLicensePlate(record.getLicensePlate()));

        String vehicleType = record.getVehicleType();

        String date = "" + NumberPadder.formatNumber(record.getYear(), 4) +
                "/" +
                NumberPadder.formatNumber(record.getMonth(), 2) +
                "/" +
                NumberPadder.formatNumber(record.getDay(), 2);

        viewHolder.txtDepartureTime.setText(departureTime);
        viewHolder.txtArrivalTime.setText(arrivalTime);
        viewHolder.loadPassengersCount.setText(loadPassengersCount);
        viewHolder.unloadPassengersCount.setText(unloadPassengersCount);
        viewHolder.txtLicensePlate.setText(licensePlate);
        viewHolder.txtVehicleType.setText(vehicleType);
        viewHolder.txtDate.setText(date);
        viewHolder.btnRemoveRecord.setTag(record.getId());

        return result;
    }

    private static class ViewHolder {
        private TextView txtDepartureTime;
        private TextView loadPassengersCount;
        private TextView txtArrivalTime;
        private TextView unloadPassengersCount;
        private TextView txtLicensePlate;
        private TextView txtVehicleType;
        private TextView txtDate;
        private Button btnRemoveRecord;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
